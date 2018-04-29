package pl.pt.put.poznan.webscraper;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.pt.put.poznan.webscraperdb.CurrencyManagement;
import pl.pt.put.poznan.webscraperdb.beans.CurrencyValue;

/**
 *
 * @author Lenovo
 */
public class Binance {

    public Binance(WebDriver driver) throws Exception {
        List<WebElement> elements;
        List<String> rows = new ArrayList<String>();

        driver.get("https://www.binance.com/");
        System.out.println(driver.getTitle());

        WebElement table = driver.findElement(By.id("products"));
        if (!table.equals(null)) {
            int ignorefirst = 11;
            int columnInRow = 8;
            String[] allTable = table.getText().split("\\s+");
            for (int j = ignorefirst; j < allTable.length; j = j + columnInRow) {

                if (allTable[j].indexOf("/USDT") != -1) {
                    System.out.println(allTable[j]);
                    System.out.println(allTable[j + 1]);
                    System.out.println(allTable[j + 3]);
                    System.out.println(allTable[j + 5].replace(",", ""));
                    System.out.println(allTable[j + 6].replace(",", ""));
                    System.out.println(allTable[j + 7].replace(",", ""));
                    System.out.println("Binance");
                } else if (allTable[j].indexOf("/BNB") != -1) {
                    System.out.println(allTable[j]);
                    System.out.println(allTable[j + 1]);
                    System.out.println(allTable[j + 3]);
                    System.out.println(allTable[j + 5].replace(",", ""));
                    System.out.println(allTable[j + 6].replace(",", ""));
                    System.out.println(allTable[j + 7].replace(",", ""));
                    System.out.println("Binance");
                } else if (allTable[j].indexOf("/ETH") != -1) {
                    System.out.println(allTable[j]);
                    System.out.println(allTable[j + 1]);
                    System.out.println(allTable[j + 3]);
                    System.out.println(allTable[j + 5].replace(",", ""));
                    System.out.println(allTable[j + 6].replace(",", ""));
                    System.out.println(allTable[j + 7].replace(",", ""));
                    System.out.println("Binance");
                } else {
                    System.out.println(StringUtils.substringBefore(allTable[j], "/BTC"));
                    System.out.println(allTable[j + 1]);
                    System.out.println(StringUtils.substringAfter(allTable[j + 3].replace(",", ""), "$"));
                    System.out.println(allTable[j + 5].replace(",", ""));
                    System.out.println(allTable[j + 6].replace(",", ""));
                    System.out.println(allTable[j + 7].replace(",", ""));
                    System.out.println("Binance");
                    System.out.println();
                    CurrencyValue currencyvalue = new CurrencyValue(StringUtils.substringBefore(allTable[j], "/BTC"));
                    currencyvalue.setPriceInBitcoin(Double.parseDouble(allTable[j + 1]));
                    currencyvalue.setPriceInDollars(Double.parseDouble(StringUtils.substringAfter(allTable[j + 3].replace(",", ""), "$")));
                    currencyvalue.setBid(Double.parseDouble(allTable[j + 5].replace(",", "")));
                    currencyvalue.setAsk(Double.parseDouble(allTable[j + 6].replace(",", "")));
                    currencyvalue.setVolume(Double.parseDouble(allTable[j + 7].replace(",", "")));
                    currencyvalue.setMarketName("Binance");
                    CurrencyManagement management = CurrencyManagement.getInstance();
                    management.addCurrencyValue(currencyvalue);
                }
            }
        } else {
            driver.quit();
            throw new Exception("Currencies not found!");
        }
    }

}
