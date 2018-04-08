package pl.pt.put.poznan.webscraperdb;

import pl.pt.put.poznan.webscraper.beans.Currency;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class CurrencyManagement {
    private EntityManagerFactory factory;
    private EntityManager entityManager;

    private static CurrencyManagement ourInstance = new CurrencyManagement();

    public static CurrencyManagement getInstance() {
        return ourInstance;
    }

    private CurrencyManagement() {
        factory = Persistence.createEntityManagerFactory("currenciesdb");
    }

    public void addEntity(Object entity) {
        synchronized (CurrencyManagement.class) {
            openTransaction();
            entityManager.persist(entity);
            commitAndCloseTransaction();
        }
    }

    private void openTransaction() {
        entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    private void commitAndCloseTransaction() {
        entityManager.getTransaction().commit();
        closeTransaction();
    }

    private void closeTransaction() {
        entityManager.close();
    }

    public <T> T getEntityByPrimaryKey(Class<T> aClass, String primaryKeyValue) {
        openTransaction();
        T entity = entityManager.find(aClass, primaryKeyValue);
        closeTransaction();
        return entity;
    }

    public <T> List<T> getEntities(Class<T> aClass) {
        return getEntities(aClass, "");
    }

    public <T> List<T> getEntities(Class<T> aClass, String conditionInSql) {
        openTransaction();
        String name;
        if (aClass == Currency.class) {
            name = "Currency";
        } else {
            name = "CurrencyValue";
        }
        List<T> entities = entityManager.createQuery("SELECT n FROM " + name + " n " + conditionInSql).getResultList();
        closeTransaction();
        return entities;
    }
}
