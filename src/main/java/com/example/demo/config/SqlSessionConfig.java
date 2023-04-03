//package com.example.demo.config;
//
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import java.io.IOException;
//import java.io.InputStream;
//
//@Configuration
//public class SqlSessionConfig {
//
//    @Bean
//    public SqlSession sqlSessionReturn(){
//        SqlSession sqlSession = null;
//        try {
//            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
//            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
//            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
//            System.out.println(sqlSessionFactory);
//            sqlSession = sqlSessionFactory.openSession(false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sqlSession;
//    }
//
//    @Bean
//    public SqlSession sqlSessionReturn01(){
//        SqlSession sqlSession = null;
//        try {
//            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
//            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
//            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
//            System.out.println(sqlSessionFactory);
//            sqlSession = sqlSessionFactory.openSession(false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sqlSession;
//    }
//}
