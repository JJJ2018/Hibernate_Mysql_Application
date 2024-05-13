package org.example.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final ThreadLocal<Session> THREAD_LOCAL = new ThreadLocal<>();

    private static SessionFactory sessionFactory;

    static {
        try{
            Configuration configure = new Configuration().configure();
            sessionFactory = configure.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Hibernate 创建会话工厂失败！");
            e.printStackTrace();
        }
    }

    public static Session getSession() {
        Session session = THREAD_LOCAL.get();
        if (session == null || session.isOpen()) {
            if (sessionFactory == null) {
                rebuildSessionFactory();
            }
            session = (sessionFactory != null) ? sessionFactory.openSession() : null;
            THREAD_LOCAL.set(session);
        }
        return session;
    }

    private static void rebuildSessionFactory() {
        try{
            // 读取 hibernate.cfg.xml 配置文件并创建 SessionFactory
            Configuration configure = new Configuration().configure();
            sessionFactory = configure.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Hibernate 创建会话工厂失败！");
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSession() {
        Session session = THREAD_LOCAL.get();
        THREAD_LOCAL.remove();
        if (session != null) {
            session.close();
        }
    }

}
