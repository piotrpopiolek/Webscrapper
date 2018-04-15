package pl.pt.put.poznan.webscraper;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import java.lang.reflect.InvocationTargetException;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Crawler {
    
    public static void main(String args[]) throws Exception {
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        String packageName = Crawler.class.getPackage().getName();
        File dir = new File("src/main/java/" + packageName.replace(".", "/"));
        File[] listOfClasses = dir.listFiles();
        
        Thread t = new Thread() { 
            @Override
            public void run() {
                try {
                    while(true) {
                        for (File e : listOfClasses) {
                            String name = e.getName();
                            if (!name.equalsIgnoreCase(Crawler.class.getSimpleName().concat(".java"))) {
                                Class<?> c = Class.forName(packageName + "." + name.replace(".java", ""));
                                c.getConstructor(WebDriver.class).newInstance(driver);
                            }
                        }
                        Thread.sleep(1000);
                    }
                } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | InterruptedException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    System.out.println(e.getMessage());
                }
            } 
        };
        
        t.start();
        System.out.println("\nPress ENTER to terminate...\n");
        System.in.read();
        t.stop();
        
        driver.quit();
    }
}
