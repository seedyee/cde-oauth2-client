package io.cde.oauth2.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by liaofangcai on 11/21/16.
 * 程序入口
 */
@SpringBootApplication
@ServletComponentScan
public class Application {

    /**
     * 程序入口.
     * @param args this args.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
