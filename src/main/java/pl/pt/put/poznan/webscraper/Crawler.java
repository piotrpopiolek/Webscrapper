package pl.pt.put.poznan.webscraper;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Crawler {

    public static void main(String args[]) throws Exception {
        WebDriver driver = new JBrowserDriver(Settings.builder().headless(false).build());
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        String packageName = Crawler.class.getPackage().getName();
        File dir = new File("src/main/java/" + packageName.replace(".", "/"));
        File[] listOfClasses = dir.listFiles();

        Thread t = runThread(driver, listOfClasses, packageName);

        t.start();
        System.out.println("\nPress ENTER to terminate...\n");
        System.in.read();
        t.stop();

        driver.quit();
    }

    static private Thread runThread(WebDriver driver, File[] listOfClasses, String packageName) {
        Thread t;
        t = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        for (File e : listOfClasses) {
                            String name = e.getName();
                            if (!name.equalsIgnoreCase(Crawler.class.getSimpleName().concat(".java"))) {
                                Class<?> c = Class.forName(packageName + "." + name.replace(".java", ""));
                                c.getConstructor(WebDriver.class).newInstance(driver);
                            }
                        }
                    }
                } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        };
        return t;
    }
}
