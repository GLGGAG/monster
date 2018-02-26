package com.orange;

import com.orange.config.OrangeBootBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author GLGGAG
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBanner(new OrangeBootBanner());
        //关闭Banner图
        //app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
	}
}
