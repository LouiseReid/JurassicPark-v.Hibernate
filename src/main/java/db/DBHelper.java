package db;

import models.Dinosaur;
import models.DynoType;
import models.Paddock;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    public static Transaction transaction;
    public static Session session;

    public static void save(Object object) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(object);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static <T> void deleteAll(Class classType) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(classType);
            List<T> results = cr.list();
            for (T result : results) {
                session.delete(result);
            }
            transaction.commit();
        } catch (HibernateException ex) {
            transaction.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void delete(Object object) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.delete(object);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static <T> List<T> getAll(Class classType) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<T> results = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(classType);
            cr.addOrder(Order.desc("id"));
            cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            results = cr.list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static <T> T find(Class classType, int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        T result = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(classType);
            cr.add(Restrictions.eq("id", id));
            result = (T) cr.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public static List<Paddock> allPaddockofType(DynoType dynoType){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Paddock> results = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(Paddock.class);
            cr.add(Restrictions.eq("species", dynoType));
            cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            results = cr.list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static List<Dinosaur> allDinosaursofType(DynoType dinoType){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Dinosaur> results = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(Dinosaur.class);
            cr.add(Restrictions.eq("species", dinoType));
            cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            results = cr.list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static List<Dinosaur> dinosaursInPaddock(Paddock paddock){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Dinosaur> results = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(Dinosaur.class);
            cr.add(Restrictions.eq("paddock", paddock));
            cr.addOrder(Order.desc("id"));
            cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            results = cr.list();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static List<Paddock> availableTransferPaddocks(Dinosaur dinosaur){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Paddock> results = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(Paddock.class);
            cr.add(Restrictions.eq("species", dinosaur.getSpecies()));
            cr.add(Restrictions.ne("name", dinosaur.getPaddock().getName()));
            cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            results = cr.list();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }


    public static void transferDino(Dinosaur dinosaur, Paddock paddock1, Paddock paddock2){
        paddock1.transferDino(dinosaur, paddock1, paddock2);
        DBHelper.save(dinosaur);
        DBHelper.save(paddock1);
        DBHelper.save(paddock2);
    }

    public static int customerCount(){
        List<Paddock> paddocks = DBHelper.getAll(Paddock.class);
        int customers = 0;
        for(Paddock paddock : paddocks){
            customers += paddock.getCustomerCount();
        }
        return customers;
    }
}
