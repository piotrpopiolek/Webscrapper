/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pt.put.poznan.webscraper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.pt.put.poznan.webscraperdb.CurrencyManagement;
import pl.pt.put.poznan.webscraperdb.beans.CurrencyValue;

/**
 *
 * @author Lenovo
 */
public class Kucoin {

    private static JavascriptExecutor js;

    public Kucoin(WebDriver driver) throws Exception {
        List<WebElement> elements;
        List<String> rows = new ArrayList<String>();

        driver.get("https://www.kucoin.com/#/markets");

        System.out.println(driver.getTitle());

        //waitSeconds(5);
        WebElement table = driver.findElement(By.xpath("/html/body/div/div/div[4]/div/div/div/div/div/div/div[2]/div/div[2]/div[1]/div/div/div/div/div/div/table"));
        if (!table.equals(null)) {
            int ignorefirst = 9;
            int columnInRow = 7;
            String[] allTable = table.getText().split("\\s+");
            for (int j = ignorefirst; j < allTable.length; j = j + columnInRow) {
                if (allTable[j].indexOf("/ETH") != -1) {
                    System.out.println(StringUtils.substringBefore(allTable[j], "/ETH"));
                    System.out.println(allTable[j + 1]);
                    System.out.println(StringUtils.substringAfter(allTable[j + 2].replace(",", ""), "$"));
                    System.out.println(allTable[j + 4].replace(",", ""));
                    System.out.println(allTable[j + 5].replace(",", ""));
                    System.out.println("Kucoin");
                    System.out.println();
                } else if (allTable[j].indexOf("/NEO") != -1) {
                    System.out.println(StringUtils.substringBefore(allTable[j], "/NEO"));
                    System.out.println(allTable[j + 1]);
                    System.out.println(StringUtils.substringAfter(allTable[j + 2].replace(",", ""), "$"));
                    System.out.println(allTable[j + 4].replace(",", ""));
                    System.out.println(allTable[j + 5].replace(",", ""));
                    System.out.println("Kucoin");
                    System.out.println();
                } else if (allTable[j].indexOf("/USDT") != -1) {
                    System.out.println(StringUtils.substringBefore(allTable[j], "/USDT"));
                    System.out.println(allTable[j + 1]);
                    System.out.println(StringUtils.substringAfter(allTable[j + 2].replace(",", ""), "$"));
                    System.out.println(allTable[j + 4].replace(",", ""));
                    System.out.println(allTable[j + 5].replace(",", ""));
                    System.out.println("Kucoin");
                    System.out.println();
                } else if (allTable[j].indexOf("/KCS") != -1) {
                    System.out.println(StringUtils.substringBefore(allTable[j], "/KCS"));
                    System.out.println(allTable[j + 1]);
                    System.out.println(StringUtils.substringAfter(allTable[j + 2].replace(",", ""), "$"));
                    System.out.println(allTable[j + 4].replace(",", ""));
                    System.out.println(allTable[j + 5].replace(",", ""));
                    System.out.println("Kucoin");
                    System.out.println();
                } else {
                    System.out.println(StringUtils.substringBefore(allTable[j], "/BTC"));
                    System.out.println(allTable[j + 1]);
                    System.out.println(StringUtils.substringAfter(allTable[j + 2].replace(",", ""), "$"));
                    System.out.println(allTable[j + 4].replace(",", ""));
                    System.out.println(allTable[j + 5].replace(",", ""));
                    System.out.println("Kucoin");
                    System.out.println();
                    CurrencyValue currencyvalue = new CurrencyValue(StringUtils.substringBefore(allTable[j], "/BTC"));
                    currencyvalue.setPriceInBitcoin(Double.parseDouble(allTable[j + 1]));
                    currencyvalue.setPriceInDollars(Double.parseDouble(StringUtils.substringAfter(allTable[j + 2].replace(",", ""), "$")));
                    currencyvalue.setMarketCapCurrency(Double.parseDouble((allTable[j + 4].replace(",", ""))));
                    currencyvalue.setVolume(Double.parseDouble(allTable[j + 5].replace(",", "")));
                    currencyvalue.setMarketName("Kucoin");
                    CurrencyManagement management = CurrencyManagement.getInstance();
                    management.addCurrencyValue(currencyvalue);
                }
            }
        } else {
            driver.quit();
            throw new Exception("Currencies not found!");
        }
    }

    public static void waitSeconds(int secons) {
        System.out.print("Pausing for " + secons + " seconds: ");
        try {
            Thread.currentThread();
            int x = 1;
            while (x <= secons) {
                Thread.sleep(1000);
                System.out.print(" " + x);
                x = x + 1;
            }
            System.out.print('\n');
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
