package pl.pt.put.poznan.webscraper;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Bitbay {
    public Bitbay() throws Exception {
        WebDriver driver;
        List<WebElement> elements;
        
        driver = Crawler.config();
        
        driver.get("https://bitbay.net/pl/kurs-walut");
        elements = driver.findElements(By.xpath("//table[@class='table currency-table__table']/tbody/tr"));
        
        if (!elements.isEmpty()) {
            elements.stream().forEach((e) -> {
                System.out.println(e.getText() + "  ");
            });
        } else {
            driver.quit();
            throw new Exception("Currencies not found!");
        }
        
        driver.quit();
    }
}
