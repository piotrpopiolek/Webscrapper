package pl.pt.put.poznan.webscraper;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Crawler {
    public static void main(String args[]) throws IOException {
        FirefoxOptions options = new FirefoxOptions();
        WebDriver driver = new FirefoxDriver(options);
    
        driver.get("https://bitbay.net/pl/kurs-walut");
        String source = driver.getPageSource();
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("source.html"), "UTF-8"))) {
            out.write(source);
        }
        List<WebElement> elements;
        elements = driver.findElements(By.xpath("//table[@class='table currency-table__table']/tbody/tr"));
        if (!elements.isEmpty()) {
            elements.stream().forEach((e) -> {
                System.out.println(e.getText() + "  ");
            });
        } else {
            driver.quit();
            throw new IOException("Currencies not found!");
        }
        driver.quit();
    }
}
