package pl.pt.put.poznan.webscraper;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.machinepublishers.jbrowserdriver.JBrowserDriver;

public class Crawler {
    public static void main(String args[]) throws IOException {
        WebDriver driver = new JBrowserDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    
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
