package pl.pt.put.poznan.webscraper;



import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.pt.put.poznan.webscraperdb.CurrencyManagement;
import pl.pt.put.poznan.webscraperdb.beans.CurrencyValue;

public class Bitbay {

    public Bitbay(WebDriver driver) throws Exception {
        driver.get("https://bitbay.net/pl/kurs-walut");
        System.out.println(driver.getTitle());

        HashMap<String, Prices> prices = new HashMap<>();
        List<WebElement> rows = driver.findElements(By.className("currency-table__item-row"));

        if (rows.size() > 0) {
            for (int i = 3; i < rows.size(); i++) {
                prices.put(rows.get(i).findElement(By.xpath(".//td[1]")).getAttribute("data-currency"),
                        new Prices(
                                Double.parseDouble(rows.get(i).findElement(By.xpath(".//td[2]")).getText().replace(",", "")),
                                Double.parseDouble(rows.get(i).findElement(By.xpath(".//td[4]")).getText().replace(",", "")),
                                Double.parseDouble(rows.get(i).findElement(By.xpath(".//td[5]")).getText().replace(",", "")),
                                Double.parseDouble(rows.get(i).findElement(By.xpath(".//td[6]")).getText().replace(",", "").split(" ")[0]))
                );
            }
        } else {
            System.out.println("Bitaby: Currencies not found!");
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.className("currency-select")));
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-currency='USD']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.className("close close-button")));

        rows = driver.findElements(By.className("currency-table__item-row"));

        if (rows.size() > 0) {
            for (WebElement e : rows) {
                String symbol = e.findElement(By.xpath(".//td[1]")).getAttribute("data-currency");
                if (symbol.equals("BTC")) {
                    CurrencyValue cv = new CurrencyValue(e.findElement(By.xpath(".//td[1]")).getAttribute("data-currency"));
                    cv.setPriceInDollars(Double.parseDouble(e.findElement(By.xpath(".//td[2]")).getText().replace(",", "")));
                    cv.setPriceInBitcoin(1);
                    cv.setAsk(1);
                    cv.setBid(1);
                    cv.setVolume(Double.parseDouble(e.findElement(By.xpath(".//td[6]")).getText().split(" ")[0].replace(",", "")));
                    cv.setMarketName("Bitbay");
                    CurrencyManagement management = CurrencyManagement.getInstance();
                    management.addCurrencyValue(cv);
                } else {
                    if (prices.containsKey(symbol)) {
                        CurrencyValue cv = new CurrencyValue(e.findElement(By.xpath(".//td[1]")).getAttribute("data-currency"));
                        cv.setPriceInDollars(Double.parseDouble(e.findElement(By.xpath(".//td[2]")).getText().replace(",", "")));
                        cv.setPriceInBitcoin(prices.get(symbol).priceInBtc);
                        cv.setAsk(prices.get(symbol).ask);
                        cv.setBid(prices.get(symbol).bid);
                        cv.setVolume(prices.get(symbol).volume);
                        cv.setMarketName("Bitbay");
                        CurrencyManagement management = CurrencyManagement.getInstance();
                        management.addCurrencyValue(cv);
                    }
                }
            }
        } else {
            System.out.println("Bitbay: Currencies not found!");
        }
    }
}

class Prices {

    double priceInBtc;
    double ask;
    double bid;
    double volume;

    public Prices(double priceInBtc, double ask, double bid, double volume) {
        this.priceInBtc = priceInBtc;
        this.ask = ask;
        this.bid = bid;
        this.volume = volume;
    }
}
