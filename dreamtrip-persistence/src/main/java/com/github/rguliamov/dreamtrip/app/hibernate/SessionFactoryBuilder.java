package com.github.rguliamov.dreamtrip.app.hibernate;

import com.github.rguliamov.dreamtrip.app.hibernate.interceptors.TimestampInterceptor;
import com.github.rguliamov.dreamtrip.app.model.entity.geography.Address;
import com.github.rguliamov.dreamtrip.app.model.entity.geography.City;
import com.github.rguliamov.dreamtrip.app.model.entity.geography.Coordinate;
import com.github.rguliamov.dreamtrip.app.model.entity.geography.Station;
import com.github.rguliamov.dreamtrip.app.model.entity.person.Account;
import jakarta.persistence.PersistenceException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Component that is responsible for managing
 * Hibernate Session factory
 *
 * @author Guliamov Rustam
 */
public class SessionFactoryBuilder {
    private final SessionFactory sessionFactory;

    public SessionFactoryBuilder() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(loadProperties()).build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(City.class);
        metadataSources.addAnnotatedClass(Station.class);
        metadataSources.addAnnotatedClass(Account.class);
        metadataSources.addAnnotatedClass(Coordinate.class);
        metadataSources.addAnnotatedClass(Address.class);
        org.hibernate.boot.SessionFactoryBuilder builder = metadataSources.getMetadataBuilder().build()
                .getSessionFactoryBuilder().applyInterceptor(new TimestampInterceptor());

        sessionFactory = builder.build();
    }

    private Properties loadProperties() {
        InputStream inputStream = SessionFactoryBuilder.class.getResourceAsStream("/application.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new PersistenceException(e);
        }

        return properties;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @PreDestroy
    public void destroy() {
        if(sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
