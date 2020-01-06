package com.example.springboottransaction.transaction.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author ligen
 * @title: TestService
 * @projectName springboot-transaction
 * @description:
 * @date 2020/1/115:39
 */
@Service
public class TestService {


    /**
     * @说明: 我们通常不直接使用 PlatformTransactionManager 而是使用它的实现类
     * DataSourceTransactionManager
     */

    @Autowired
    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private PlatformTransactionManager transactionManager;

    // 方式1 通过事务管理器
    @Autowired
    private DataSourceTransactionManager transactionManager;

    //
//    // 方式2  通过事务模板
    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * @ desc : 编程式事务  两种方式
     * @ params
     * @ return
     * @ date 2020/1/1
     * @ author ligen
     */
    public void test1() {
        // 创建事务对象，可以设置事务的隔离级别，超时时间等
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 记录初始状态
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            //  执行操作
            // 测试，提交之前抛出异常
            jdbcTemplate.execute("insert into info values(null,\"ligen2\")");
            throw new Exception();
            // 提交
//            transactionManager.commit(status);
        } catch (Exception e) {
            // 回滚到之前状态
            //  回滚之前你可以加上自己的操作，比如打印日志，或者保存异常数据等
            System.out.println("--------catch Exception----------");
            transactionManager.rollback(status);
        } finally {

            // 最后判断一下status   如果没有完成，则回滚
            //  如果没有提交也没有执行会是没有完成
            if (!status.isCompleted()) {
                System.out.println("---------is not completed--------");
                transactionManager.rollback(status);
            }
            System.out.println(status);
        }
    }


    /**
     * @ desc :  方式2  TransactionTemplate
     * @ params
     * @ return   有返回值
     * @ date 2020/1/1
     * @ author ligen
     */
    public boolean test2() {
        return transactionTemplate.execute(status -> {

            try {
                jdbcTemplate.execute("insert into info values(null,\"ligen2\")");
                throw new Exception();
//                return true;
            } catch (Exception e) {
                System.out.println("TransactionTemplate 有返回值异常回滚");
                status.setRollbackOnly();
                return false;
            }

        });
    }

    /**
     * @ desc :  方式2  TransactionTemplate
     * @ params
     * @ return   无返回值
     * @ date 2020/1/1
     * @ author ligen
     */
    public void test3() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    jdbcTemplate.execute("insert into info values(null,\"ligen2\")");
                    throw new Exception();
                } catch (Exception e) {
                    System.out.println("TransactionTemplate 无返回值异常回滚");
                    status.setRollbackOnly();
                }
            }
        });
    }


    /**
     * @ desc :声明式事务
     * @ params
     * @ return
     * @ date 2020/1/1
     * @ author ligen
     */
//    public String test2() {
//
//
//    }

}
