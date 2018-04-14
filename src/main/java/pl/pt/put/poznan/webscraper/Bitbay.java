package pl.pt.put.poznan.webscraper;







import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.pt.put.poznan.webscraperdb.CurrencyManagement;
import pl.pt.put.poznan.webscraperdb.beans.CurrencyValue;

public class Bitbay {

    public Bitbay(WebDriver driver) throws Exception {
        List<WebElement> elements;

        System.out.println(driver.getTitle());

        driver.get("https://bitbay.net/pl/kurs-walut");
        elements = driver.findElements(By.xpath("//table[@class='table currency-table__table']/tbody/tr"));

        if (!elements.isEmpty()) {
            elements.stream().forEach((e) -> {
                String position = e.getText();
                String[] splited = position.split("\\s+");
                System.out.println(splited[1]);
                System.out.println(splited[2]);
                System.out.println(splited[4]);
                System.out.println(splited[5]);
                System.out.println(splited[6]);
                System.out.println(splited[7]);
                System.out.println(splited[8]);
                System.out.println("Bitbay");
                System.out.println();
                
                if(splited[1].equals("PLN"))
                {
                CurrencyValue currencyvalue = new CurrencyValue("BTC");
                currencyvalue.setPriceInBitcoin(1.0);
                currencyvalue.setBid(Double.parseDouble(splited[4]));
                currencyvalue.setAsk(Double.parseDouble(splited[5]));
                currencyvalue.setVolume(Double.parseDouble(splited[6].replace(",","")));
                currencyvalue.setMarketName("Bitbay");
                CurrencyManagement management = CurrencyManagement.getInstance();
                management.addCurrencyValue(currencyvalue);
                }
                else if(splited[1].equals("EUR"))
                {
                    
                }
                else if(splited[1].equals("USD"))
                {

                }
                //Do poprawy
                else if(splited[8].equals("BCC"))
                {
                CurrencyValue currencyvalue = new CurrencyValue("BCC");
                currencyvalue.setPriceInBitcoin(Double.parseDouble(splited[3]));
                currencyvalue.setBid(Double.parseDouble(splited[5]));
                currencyvalue.setAsk(Double.parseDouble(splited[6]));
                currencyvalue.setVolume(Double.parseDouble(splited[7].replace(",","")));
                currencyvalue.setMarketName("Bitbay");
                CurrencyManagement management = CurrencyManagement.getInstance();
                management.addCurrencyValue(currencyvalue);
                }
                else if(splited[8].equals("BTG"))
                {
                CurrencyValue currencyvalue = new CurrencyValue("BTG");
                currencyvalue.setPriceInBitcoin(Double.parseDouble(splited[3]));
                currencyvalue.setBid(Double.parseDouble(splited[5]));
                currencyvalue.setAsk(Double.parseDouble(splited[6]));
                currencyvalue.setVolume(Double.parseDouble(splited[7].replace(",","")));
                currencyvalue.setMarketName("Bitbay");
                CurrencyManagement management = CurrencyManagement.getInstance();
                management.addCurrencyValue(currencyvalue);
                }
                else
                {
                CurrencyValue currencyvalue = new CurrencyValue(splited[7]);
                currencyvalue.setPriceInBitcoin(Double.parseDouble(splited[2]));
                currencyvalue.setBid(Double.parseDouble(splited[4]));
                currencyvalue.setAsk(Double.parseDouble(splited[5]));
                currencyvalue.setVolume(Double.parseDouble(splited[6].replace(",","")));
                currencyvalue.setMarketName("Bitbay");
                CurrencyManagement management = CurrencyManagement.getInstance();
                management.addCurrencyValue(currencyvalue);
                }
            });
        } else {
            driver.quit();
            throw new Exception("Currencies not found!");
        }
    }
}
