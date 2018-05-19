package pl.pt.put.poznan.currencies;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.pt.put.poznan.webscraperdb.CurrencyManagement;

public class Coinmarketcap {

    public Coinmarketcap(WebDriver driver) throws Exception {
        List<WebElement> elementsLogo;
        List<WebElement> elementsSymbol;
        List<WebElement> elementsName;
        List<WebElement> elementsMarketCap;
        List<String> links = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> symbols = new ArrayList<>();
        List<Long> marketCap = new ArrayList<>();
        
        driver.get("https://coinmarketcap.com/all/views/all/");

        elementsSymbol = driver.findElements(By.xpath("//td[contains(@class,'col-symbol')]"));
        elementsName = driver.findElements(By.xpath("//td[contains(@class,'currency-name')]"));
        elementsLogo = driver.findElements(By.xpath("//div[contains(@class,'logo-sprite')]"));
        elementsMarketCap = driver.findElements(By.xpath("//td[contains(@class,'market-cap')]"));

        if (!elementsSymbol.isEmpty()) {
            elementsSymbol.stream().forEach((e) -> {
                symbols.add(e.getText());
            });
        } else {
            throw new Exception("Symbols not found!");
        }

        if (!elementsName.isEmpty()) {
            elementsName.stream().forEach((e) -> {
                names.add(e.getAttribute("data-sort"));
            });
        } else {
            throw new Exception("Names not found!");
        }
        
        if (!elementsLogo.isEmpty()) {
            elementsLogo.stream().forEach((e) -> {
                links.add("https://s2.coinmarketcap.com/static/img/coins/16x16/" + e.getAttribute("class").replaceAll("\\D+", "") + ".png");
            });
        } else {
            throw new Exception("Logos not found!");
        }
        
        if (!elementsMarketCap.isEmpty()) {
            elementsMarketCap.stream().forEach((e) -> {
                System.out.println(e);
                marketCap.add(Long.parseLong(e.getText().replaceAll(" ", "").replace("$", "").replace("?", "0")));
            });
        } else {
            throw new Exception("MarketCaps not found!");
        }

        for (int i = 0; i < links.size(); i++) {
            System.out.println(Integer.toString(i) + " " + symbols.get(i) + " " + names.get(i) + " " + links.get(i));
            CurrencyManagement management = CurrencyManagement.getInstance();
            management.addCurrency(symbols.get(i), names.get(i), links.get(i));
        }
    }
}
