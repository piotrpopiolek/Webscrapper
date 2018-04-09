package pl.pt.put.poznan.webscraperdb;

import pl.pt.put.poznan.webscraperdb.beans.Currency;
import pl.pt.put.poznan.webscraperdb.beans.CurrencyValue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class CurrencyManagement {
    private EntityManagerFactory factory;
    private ThreadLocal<EntityManager> threadLocal;


    private static CurrencyManagement ourInstance = new CurrencyManagement();

    public static CurrencyManagement getInstance() {
        return ourInstance;
    }

    private CurrencyManagement() {
        factory = Persistence.createEntityManagerFactory("currenciesdb");
        threadLocal = new ThreadLocal<EntityManager>();
    }

    private EntityManager getEntityManager() {
        EntityManager entityManager = threadLocal.get();
        if (entityManager == null) {
            threadLocal.set(factory.createEntityManager());
            entityManager = threadLocal.get();
        }
        return entityManager;
    }

    public void addCurrency(Currency currency) {
        addEntity(currency);
    }

    public void addCurrency(String symbol, String name, String urlToLogo) {
        Currency currency = new Currency();
        currency.setSymbol(symbol);
        currency.setName(name);
        currency.setLogo(ImageConventer.imageToBytes(urlToLogo));
        addEntity(currency);
    }

    public void addCurrencyValue(CurrencyValue currencyValue) {
        addEntity(currencyValue);
    }

    private void addEntity(Object entity) {
        if (checkIfAlreadyExists(entity)) {
            return;
        }
        synchronized (CurrencyManagement.class) {
            try {
                openTransaction();
                getEntityManager().persist(entity);
                commitTransaction();
            } catch (Exception e) {
                getEntityManager().getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }

    private boolean checkIfAlreadyExists(Object entity) {
        if (entity.getClass() == Currency.class) {
            if (getEntityByPrimaryKey(Currency.class, ((Currency) entity).getSymbol()) != null) {
                return true;
            }
        }
        return false;
    }

    private void openTransaction() {
        getEntityManager().getTransaction().begin();
    }

    private void commitTransaction() {
        try {
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public <T> T getEntityByPrimaryKey(Class<T> aClass, String primaryKeyValue) {
        openTransaction();
        T entity = getEntityManager().find(aClass, primaryKeyValue);
        commitTransaction();
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
        List<T> entities = getEntityManager().createQuery("SELECT n FROM " + name + " n " + conditionInSql).getResultList();
        commitTransaction();
        return entities;
    }
}