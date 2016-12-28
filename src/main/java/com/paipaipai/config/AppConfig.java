package com.paipaipai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by weibo on 2016/12/25.
 */
@Component
@ConfigurationProperties(prefix = "server") //接收application.yml中的server下面的属性
    public class AppConfig {

        private String port;
        private String address;

        private List<Map<String, String>> session;
    //    private String tomcat.max-threads;
    //    private String tomcat.uri-encoding;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public List<Map<String, String>> getSession() {
            return session;
        }

        public void setSession(List<Map<String, String>> session) {
            this.session = session;
        }

}
