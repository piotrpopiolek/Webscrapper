import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.pt.put.poznan.webscraperdb.CurrencyManagement;

public class Coinmarketcap {

    public Coinmarketcap(WebDriver driver) throws Exception {
        boolean stop = true;
        int nextPage = 1;
        List<WebElement> elementsLogo;
        List<WebElement> elementsSymbol;
        List<WebElement> elementsName;
        List<String> links = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        List<String> symbols = new ArrayList<String>();
        
        driver.get("https://coinmarketcap.com/all/views/all/");

        elementsSymbol = driver.findElements(By.className("text-left col-symbol"));
        elementsName = driver.findElements(By.className("currency-name-container"));

        if (!elementsSymbol.isEmpty()) {
            elementsSymbol.stream().forEach((e) -> {
                symbols.add(e.getText());
            });
        } else {
            throw new Exception("Symbols not found!");
        }

        if (!elementsName.isEmpty()) {
            elementsName.stream().forEach((e) -> {
                names.add(e.getText());
            });
        } else {
            throw new Exception("Names not found!");
        }

        String url = "https://coinmarketcap.com/";
        driver.get(url);
        System.out.println(driver.getTitle());

        do {
            for(int i=0;i<6200;i++) {
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1)", "");
            }
            elementsLogo = driver.findElements(By.xpath("//img[contains(@class,'logo-sprite')]"));

            if (!elementsLogo.isEmpty()) {
                elementsLogo.stream().forEach((e) -> {
                    String position = e.getText();
                    links.add(e.getAttribute("src"));
                });
                nextPage = nextPage + 1;
                System.out.println(url + Integer.toString(nextPage));
                driver.get(url + Integer.toString(nextPage));
            } else {
                stop = false;
            }
        } while (stop);

        int ID = 0;
        for (String s : links) {
            System.out.println(Integer.toString(ID)+" "+symbols.get(ID)+" "+names.get(ID)+" "+links.get(ID));
            CurrencyManagement management = CurrencyManagement.getInstance();
            management.addCurrency(symbols.get(ID), names.get(ID), links.get(ID));
            ID=ID+1;
        }
    }
}
