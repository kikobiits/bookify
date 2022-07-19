package com.example.bookify.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.yml")
public class MailConfig {

//    @Bean
//    public JavaMailSender javaMailSender(
//            @Value("${mail.host}") String mailHost,
//            @Value("${mail.port}") Integer mailPort,
//            @Value("${mail.username}") String userName,
//            @Value("${mail.password}") String password) {
//
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        javaMailSender.setHost(mailHost);
//        javaMailSender.setPort(mailPort);
//        javaMailSender.setUsername(userName);
//        javaMailSender.setPassword(password);
//        javaMailSender.setJavaMailProperties(mailProperties());
//        javaMailSender.setDefaultEncoding("UTF-8");
//
//        return javaMailSender;
//    }
//
//    private Properties mailProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("mail.smtp.auth", "true");
//        properties.setProperty("mail.transport.protocol", "smtp");
//        return properties;
//    }

    @Value("${spring.mail.protocol}")
    private String protocol;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.smtp.auth}")
    private boolean auth;
    @Value("${spring.mail.smtp.starttls.enable}")
    private boolean starttls;
    @Value("${spring.mail.from}")
    private String from;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", auth);
        mailProperties.put("mail.smtp.starttls.enable", starttls);
        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        return mailSender;
    }
}
