package com.xuanwu.ump.HttpSimulateGate.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class HttpSimulateGateServer {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("applicationcontext.xml");
        System.out.println("HttpSimulateGateServer has started.");
    }
}
