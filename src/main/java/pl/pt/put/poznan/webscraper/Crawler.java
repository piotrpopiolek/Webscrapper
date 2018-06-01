package pl.pt.put.poznan.webscraper;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.UserAgent;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.pt.put.poznan.currencies.Coinmarketcap;

public class Crawler {

    public static void main(String args[]) throws Exception {
        String packageName = Crawler.class.getPackage().getName();
        File dir = new File("src/main/java/" + packageName.replace(".", "/"));
        File[] listOfClasses = dir.listFiles();

        WebDriver driver = new JBrowserDriver(Settings.builder().headless(false).userAgent(UserAgent.CHROME).build());
        new Coinmarketcap(driver);
        driver.quit();
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(listOfClasses.length - 1);
        int initialDelay = 0;
        for (File e : listOfClasses) {
            String name = e.getName();
            if (!name.equalsIgnoreCase(Crawler.class.getSimpleName().concat(".java"))) {
                executor.scheduleAtFixedRate(runNewThread(packageName + "." + name.replace(".java", "")), initialDelay, 60, TimeUnit.SECONDS);
                initialDelay = initialDelay + 10;
            }
        }
        
        System.out.println("\nPress ENTER to terminate...");
        System.in.read();
        System.out.println("Waiting for termination...");
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);
        
        System.exit(0);
    }

    private static Runnable runNewThread(String className) {
        Runnable r = () -> {
            System.out.println("\n" + Thread.currentThread().getName() + "\n");
            WebDriver driver = new JBrowserDriver(Settings.builder().headless(false).userAgent(UserAgent.CHROME).build());
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            try {
                Class<?> c = Class.forName(className);
                c.getConstructor(WebDriver.class).newInstance(driver);
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
            }
            driver.quit();
        };
        return r;
    }
}
