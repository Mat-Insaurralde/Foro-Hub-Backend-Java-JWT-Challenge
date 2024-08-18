package com.lastByte.Foro.Hub.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger implements CommandLineRunner {

    @Value("${MYSQL_ADDON_HOST:undefined}")
    private String mysqlHost;

    @Value("${MYSQL_ADDON_PORT:undefined}")
    private String mysqlPort;

    @Value("${MYSQL_ADDON_DB:undefined}")
    private String mysqlDb;

    @Value("${MYSQL_ADDON_USER:undefined}")
    private String mysqlUser;

    @Value("${MYSQL_ADDON_PASSWORD:undefined}")
    private String mysqlPassword;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("MySQL Host: " + mysqlHost);
        System.out.println("MySQL Port: " + mysqlPort);
        System.out.println("MySQL DB: " + mysqlDb);
        System.out.println("MySQL User: " + mysqlUser);
        System.out.println("MySQL Password: " + mysqlPassword);
    }
}