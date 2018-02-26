package com.orange.config;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * @author:GLGGAG
 * @Date:2017/12/4
 * @Description:实现springBoot banner接口，可自定义在程序启动时输出信息
 */
public class OrangeBootBanner implements Banner{

    private static final String BANNER = "" +
            "                   _ooOoo_ \n" +
            "                  o8888888o \n" +
            "                  88\" . \"88 \n" +
            "                  (| -_- |) \n" +
            "                  O\\  =  /O \n" +
            "               ____/`---'\\____ \n" +
            "             .'  \\\\|     |//  `. \n" +
            "            /  \\\\|||  :  |||//  \\ \n" +
            "           /  _||||| -:- |||||-  \\ \n" +
            "           |   | \\\\\\  -  /// |   | \n" +
            "           | \\_|  ''\\---/''  |   | \n" +
            "           \\  .-\\__  `-`  ___/-. / \n" +
            "         ___`. .'  /--.--\\  `. . __ \n" +
            "      .\"\" '<  `.___\\_<|>_/___.'  >'\"\". \n" +
            "     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | | \n" +
            "     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  / \n" +
            "======`-.____`-.___\\_____/___.-`____.-'====== \n" +
            "                   `=---=' \n" +
            "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ \n" +
            "         佛祖保佑       永无BUG      GLGGAG      ";

    private static final String SPRING_BOOT = " :: Spring Boot :: ";

    private static final int STRAP_LINE_SIZE = 42;


    @Override
    public void printBanner(Environment environment, Class<?> sourceClass,
                            PrintStream printStream) {
        printStream.println(BANNER);
        String version = SpringBootVersion.getVersion();
        version = (version == null ? "" : " (v" + version + ")");
        String padding = "";
        while (padding.length() < STRAP_LINE_SIZE
                - (version.length() + SPRING_BOOT.length())) {
            padding += " ";
        }

        printStream.println(AnsiOutput.toString(AnsiColor.GREEN, SPRING_BOOT,
                AnsiColor.DEFAULT, padding, AnsiStyle.FAINT, version));
        printStream.println();
    }
}
