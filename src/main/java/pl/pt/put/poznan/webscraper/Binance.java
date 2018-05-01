package pl.pt.put.poznan.webscraper;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.pt.put.poznan.webscraperdb.CurrencyManagement;
import pl.pt.put.poznan.webscraperdb.beans.CurrencyValue;

public class Binance {

    public Binance(WebDriver driver) throws Exception {
        driver.get("https://www.binance.com/");
        System.out.println(driver.getTitle());

        List<WebElement> rows = driver.findElements(By.xpath("//tr[contains(@ng-click,'changeProduct') and not(contains(@style,'display: none;'))]"));

        if (rows.size() > 0) {
            rows.forEach((e) -> {
                CurrencyValue cv = new CurrencyValue(e.findElement(By.xpath(".//td[2]")).getText().replace("/BTC", ""));
                String[] prices = e.findElement(By.xpath(".//td[3]")).getText().split("/");
                if (prices.length != 2) {
                    prices[1] = prices[1].replace("$", "").replace(",", "");
                    cv.setPriceInDollars(Double.parseDouble(prices[1]));
                    cv.setPriceInBitcoin(Double.parseDouble(prices[0]));
                    cv.setAsk(Double.parseDouble(prices[0]));
                    cv.setBid(Double.parseDouble(prices[0]));
                    cv.setVolume(Double.parseDouble(e.findElement(By.xpath(".//td[7]")).getText().replace(",", "")));
                    cv.setMarketName("Binance");
                    System.out.println(e.findElement(By.xpath(".//td[2]")).getText().replace("/BTC", "") + " "
                            + cv.getPriceInDollars() + " " + cv.getPriceInBitcoin() + " " + cv.getAsk() + " " + cv.getBid()
                            + " " + cv.getMarketName());
                    CurrencyManagement management = CurrencyManagement.getInstance();
                    management.addCurrencyValue(cv);
                }
            });
        } else {
            throw new Exception("Binance: Currencies not found!");
        }
    }
}
