package com.qlct.core;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanPackageLogger implements CommandLineRunner {
    private final ApplicationContext applicationContext;

    public BeanPackageLogger(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) {
        System.out.println("🔍 Kiểm tra package của các Bean:");
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            Class<?> beanClass = applicationContext.getType(beanName);
            if (beanClass != null) {
                System.out.println("✅ Bean: " + beanName + " -> Package: " + beanClass.getPackage().getName());
            }
        }
    }
}