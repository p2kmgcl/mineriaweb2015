package practica2;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import websphinx.Link;

public class main {
    
    public static void main (String args[]) {
        MainCrawler crawler = new MainCrawler();
        Boby boby = new Boby();
        try {
            String urls[] = {
                "http://www.ccsu.edu/anthropology/",
                "http://www.art.ccsu.edu/",
                "http://www.communication.ccsu.edu/",
                "http://web.ccsu.edu/criminology/",
                "http://www.design.ccsu.edu/",
                "http://www.econ.ccsu.edu/",
                "http://www.english.ccsu.edu/",
                "http://www.geography.ccsu.edu/",
                "http://www.history.ccsu.edu/",
                "http://web.ccsu.edu/journalism/",
                "http://www.modlang.ccsu.edu/",
                "http://www.music.ccsu.edu/",
                "http://you.ccsu.edu/philosophy/",
                "http://www.polisci.ccsu.edu/",
                "http://web.ccsu.edu/psychology/",
                "http://www.sociology.ccsu.edu/",
                "http://www.theatre.ccsu.edu/" 
            };
            
            for (String url : urls) {
                crawler.addRoot(new Link(new URL(url)));
            }
            crawler.run();
            System.out.println("Fin :3");
            boby.clear_file(MainCrawler.OUTPUT_TEXT);
            
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
