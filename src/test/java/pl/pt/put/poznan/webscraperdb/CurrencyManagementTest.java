package pl.pt.put.poznan.webscraperdb;

import org.junit.Test;
import pl.pt.put.poznan.webscraperdb.beans.*;

import java.util.List;

public class CurrencyManagementTest {
    CurrencyManagement currencyManagement = CurrencyManagement.getInstance();

    @Test
    public void connectionTest() {
        List<CurrencyValue> currency = currencyManagement.getEntities(CurrencyValue.class);
        for (CurrencyValue currencies : currency) {
            System.out.println(currencies.getPriceInDollars());
        }
        List<Currency> currencyv = currencyManagement.getEntities(Currency.class);
        for (Currency currencies : currencyv) {
            System.out.println(currencies.getName());
        }
    }
}