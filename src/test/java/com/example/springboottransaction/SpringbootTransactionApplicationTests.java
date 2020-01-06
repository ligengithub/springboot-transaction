package com.example.springboottransaction;

import com.example.springboottransaction.transaction.sevice.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootTransactionApplicationTests {

    @Autowired
    private TestService testService;

    @Test
    void contextLoads() {
    }

    @Test
    public void test1(){
        testService.test1();
        testService.test2();
        testService.test3();
    }

}
