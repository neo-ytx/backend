package edu.fudan.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author tyuan@ea.com
 * @Date 8/11/2020 3:36 PM
 */
@Configuration
@EnableTransactionManagement
public class NeoConfig {
//    @Bean
//    public Neo4jTransactionManager transactionManager() throws Exception {
//        return new Neo4jTransactionManager(sessionFactory());
//    }
//
//    @Bean
//    public SessionFactory sessionFactory() {
//        return new SessionFactory("edu.fudan.backend");
//    }
}
