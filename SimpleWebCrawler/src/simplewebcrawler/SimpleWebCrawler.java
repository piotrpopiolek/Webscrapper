/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplewebcrawler;

import java.io.IOException;

/**
 *
 * @author Lenovo
 */
public class SimpleWebCrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        WebCrawler Testowy = new WebCrawler("https://coinmarketcap.com/");
        Testowy.get();
        Testowy.show();
        
//        WebCrawler Testowy2 = new WebCrawler("https://bitbay.net/pl/kurs-walut");
//        Testowy2.get();
//        Testowy2.show();
    }
    
}
