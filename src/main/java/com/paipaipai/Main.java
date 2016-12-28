package com.paipaipai;

import com.paipaipai.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by weibo on 2016/12/16.
 */
@RestController
@SpringBootApplication
public class Main {

    @Autowired
    private AppConfig appConfig;

    @RequestMapping("/")
    public String index() {
        return  "address is " + appConfig.getAddress() + "and port is" + appConfig.getPort() +  "and session is " + appConfig.getSession();
    }
    public static void main(String[] args) {
 //       ApplicationContext ctx = new ClassPathXmlApplicationContext("application.yml");

        SpringApplication app = new SpringApplication(Main.class);
        app.run(args);
//        SpringApplication.run(Main.class);
    }
}
