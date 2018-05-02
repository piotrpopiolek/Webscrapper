package pl.pt.put.poznan.webscraper;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.pt.put.poznan.webscraperdb.CurrencyManagement;
import pl.pt.put.poznan.webscraperdb.beans.CurrencyValue;

public class Kucoin {

    public Kucoin(WebDriver driver) throws Exception {
        driver.get("https://www.kucoin.com/#/markets");
        System.out.println(driver.getTitle());

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("ant-table-row  ant-table-row-level-0"))));
        List<WebElement> rows = driver.findElements(By.className("ant-table-row  ant-table-row-level-0"));

        if (rows.size() > 0) {
            for (WebElement e : rows) {
                String symbol = e.findElement(By.xpath(".//td[1]/div/div/span")).getText().replace("/BTC", "");
                System.out.println("\n" + symbol + "\n");
                CurrencyValue cv = new CurrencyValue(symbol);
                cv.setPriceInDollars(Double.parseDouble(e.findElement(By.xpath(".//td[2]/div/span[2]")).getText().replace("$", "").replace(",", "")));
                cv.setPriceInBitcoin(Double.parseDouble(e.findElement(By.xpath(".//td[2]/div/span[1]")).getText().replace(",", "")));
                cv.setAsk(Double.parseDouble(e.findElement(By.xpath(".//td[2]/div/span[1]")).getText().replace(",", "")));
                cv.setBid(Double.parseDouble(e.findElement(By.xpath(".//td[2]/div/span[1]")).getText().replace(",", "")));
                cv.setVolume(Double.parseDouble(e.findElement(By.xpath(".//td[5]")).getText().replace(",", "")));
                cv.setMarketName("Kucoin");
                CurrencyManagement management = CurrencyManagement.getInstance();
                management.addCurrencyValue(cv);
            }
        } else {
            System.out.println("Kucoin: Currencies not found!");
        }
    }
}
