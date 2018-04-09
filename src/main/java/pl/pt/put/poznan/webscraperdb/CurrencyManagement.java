package pl.pt.put.poznan.webscraperdb;

import pl.pt.put.poznan.webscraperdb.beans.Currency;
import pl.pt.put.poznan.webscraperdb.beans.CurrencyValue;

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
        entityManager = factory.createEntityManager();
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
            openTransaction();
            entityManager.persist(entity);
            commitTransaction();
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
        entityManager.getTransaction().begin();
    }

    private void commitTransaction() {
        entityManager.getTransaction().commit();
    }

    public <T> T getEntityByPrimaryKey(Class<T> aClass, String primaryKeyValue) {
        openTransaction();
        T entity = entityManager.find(aClass, primaryKeyValue);
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
        List<T> entities = entityManager.createQuery("SELECT n FROM " + name + " n " + conditionInSql).getResultList();
        commitTransaction();
        return entities;
    }
}