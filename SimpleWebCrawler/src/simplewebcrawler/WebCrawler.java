package simplewebcrawler;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lenovo
 */
public class WebCrawler {

    //Variables
    Document doc;
    String adress;
    String title;
    Elements names;
    Elements imgs;

    public WebCrawler(String adress) {
        this.adress = adress;
    }

    //Methods
    public void get() throws IOException {
        this.doc = Jsoup.connect(adress).timeout(10000).get();
        title = doc.title();
    }

    public void show() {
        System.out.println("Tytuł strony: " + title);
        Element table = doc.getElementById("currencies");
        //System.out.println(table);
        
        Elements trs = table.select("tr");
        //System.out.println(trs);
        
        Elements tds = table.select("td");
        //System.out.println(tds);
        
        for(Element t : tds)
        {
            System.out.println(t.text());
        }
        
        
    }
}
