package com.example.springboottransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class SpringbootTransactionApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringbootTransactionApplication.class, args);
    }


    /**
     * @ desc : 查看当前使用的默认事务管理
     * @ params
     * @ return
     * @ date 2020/1/3
     * @ author ligen
     */
    @Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager) {
        System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
        return new Object();
    }

}
