package com.orange.interceptor;

import com.orange.support.convert.String2DateConverter;
import com.orange.support.convert.StringToNumberConverter;
import com.orange.support.convert.StringTrimAndEscapeConverter;
import com.orange.support.message.ResponseMessage;
import com.orange.support.util.JSONUtil;
import com.orange.support.util.RequestUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author GLGGAG
 * 项目初始化配置
 */
@Configuration
public class OrangeWarInit extends WebMvcConfigurerAdapter {

/*    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 从前台过来的数据转换成对应类型的转换器
        registry.addConverter(new StringTrimAndEscapeConverter());
        registry.addConverterFactory(new StringToNumberConverter());
        registry.addConverter(new String2DateConverter());
    }*/

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        boolean charset = true, jackson = true;
        for (HttpMessageConverter<?> converter : converters) {
            // 使用 utf8 字符集
            if (converter instanceof StringHttpMessageConverter) {
                charset = false;
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
            // 渲染 json 使用自定义的规则
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                jackson = false;
                ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(JSONUtil.RENDER);
            }
        }
        if (charset) {
            converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        }
        if (jackson) {
            converters.add(new MappingJackson2HttpMessageConverter(JSONUtil.RENDER));
        }
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        // 如果 Controller 方法的返回值是 ResponseMessage 则使用此处理器
        returnValueHandlers.add(new HandlerMethodReturnValueHandler() {
            @Override
            public boolean supportsReturnType(MethodParameter returnType) {
                return ResponseMessage.class.isAssignableFrom(returnType.getParameterType());
            }

            @Override
            public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                          ModelAndViewContainer mavContainer,
                                          NativeWebRequest webRequest) throws Exception {
                mavContainer.setRequestHandled(true);
                RequestUtils.toJson((ResponseMessage<Object>) returnValue, webRequest.getNativeResponse(HttpServletResponse.class));
            }
        });
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogBindInterceptor()).addPathPatterns("/**");
    }
}
