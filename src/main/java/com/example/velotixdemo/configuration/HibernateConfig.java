//package com.example.velotixdemo.configuration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//
////
////
////import org.hibernate.SessionFactory;
////import org.hibernate.boot.Metadata;
////import org.hibernate.boot.MetadataSources;
////import org.hibernate.boot.registry.StandardServiceRegistry;
////import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////
//@Configuration
//public class HibernateConfig {
//
//
//    @Bean(name="entityManagerFactory")
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//
//        return sessionFactory;
//    }
//}
////    private StandardServiceRegistry registry;
////    private SessionFactory sessionFactory;
////
////
//////    @Bean
//////    public SessionFactory hibernateSessionFactory() {
//////        if (sessionFactory == null) {
//////            try {
//////                // Create registry
//////                registry = new StandardServiceRegistryBuilder().configure().build();
//////
//////                // Create MetadataSources
//////                MetadataSources sources = new MetadataSources(registry);
//////
//////                // Create Metadata
//////                Metadata metadata = sources.getMetadataBuilder().build();
//////
//////                // Create SessionFactory
//////                sessionFactory = metadata.getSessionFactoryBuilder().build();
//////
//////            } catch (Exception e) {
//////                e.printStackTrace();
//////                if (registry != null) {
//////                    StandardServiceRegistryBuilder.destroy(registry);
//////                }
//////            }
//////        }
//////        return sessionFactory;
//////    }
////}
