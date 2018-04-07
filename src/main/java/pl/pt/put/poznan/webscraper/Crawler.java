package pl.pt.put.poznan.webscraper;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import com.machinepublishers.jbrowserdriver.JBrowserDriver;

public class Crawler {
    public static void main(String args[]) throws Exception {
        String packageName = Crawler.class.getPackage().getName();
        File dir = new File("src/main/java/" + packageName.replace(".", "/"));
        File[] listOfClasses = dir.listFiles();
        
        for (File e : listOfClasses) {
            String name = e.getName();
            if (!name.equalsIgnoreCase(Crawler.class.getName())) {
                Class<?> c = Class.forName(packageName + "." + name.substring(0, name.length() - 5));
                c.getConstructor().newInstance();
            }
        }
    }
    
    public static WebDriver config() {
        WebDriver driver = new JBrowserDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return driver;
    }
}
