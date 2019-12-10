package com.epam.javacourse.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HelloServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Init context");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Destroy context");
    }
}
