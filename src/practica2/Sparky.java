package practica2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import websphinx.Crawler;
import websphinx.DownloadParameters;
import websphinx.Link;
import websphinx.Page;

public class Sparky extends Crawler {
    private int currentUrlIndex;
    private String currentUrl;
    private final String[] urls;
    private boolean[][] adjacenceMatrix;

    public Sparky(String[] urls) {
        super();
        
        // Adjacence
        this.urls = urls;
        this.adjacenceMatrix = new boolean[urls.length][urls.length];
        for (int i = 0; i < urls.length; i++) {
            for (int j = 0; j < urls.length; j++) {
                this.adjacenceMatrix[i][j] = false;
            }
        }
        
        // User agent, robots.txt...
        DownloadParameters downloadParameters = new DownloadParameters();
        downloadParameters.changeObeyRobotExclusion(false); // :)
        downloadParameters.changeUserAgent("ChupiCrawler Mozilla/5.0 (X11; U; Linux x86_64;");
        this.setDownloadParameters(downloadParameters);
        
        // SUBTREE, SERVER, WEB
        this.setDomain(Crawler.SERVER);
        // ALL_LINKS, HYPERLINKS, LINKS_AND_IMAGES
        this.setLinkType(Crawler.HYPERLINKS);
        // Depth
        this.setMaxDepth(1);
    }
    
    public void fillMatrix () {
        for (int i = 0; i < this.urls.length; i++) {
            try {
                this.currentUrlIndex = i;
                this.currentUrl = this.urls[i];
                this.clear();
                this.setRoot(new Link(new URL(this.urls[i])));
                this.run();
                
            }catch (MalformedURLException ex) {
                Logger.getLogger(Sparky.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.run();
    }
    
    @Override
    public void visit(Page page) {
        this.doVisit(page);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            // Mec
        }
    }
    
    private void doVisit (Page page) {
        String origin = this.currentUrl;
        String current = page.getBase().toString();

        System.out.println(this.currentUrlIndex + " > " + origin + " > " + current);
        
        for (int i = 0; i < this.urls.length; i++) {
            if (current.equals(urls[i])) {
                System.out.println("WOOF!!! " + this.currentUrlIndex + " > " + i);
                this.adjacenceMatrix[this.currentUrlIndex][i] = true;
            }
        }
        
    }

    public void goodBoy () {
        System.out.println("");
        for (int i = 0; i < this.adjacenceMatrix.length; i++) {
            for (int j = 0; j < this.adjacenceMatrix[i].length; j++) {
                int value = (this.adjacenceMatrix[i][j]) ? 1 : 0;
                System.out.print(value + " ");
            }
            System.out.println("");
        }
        
        System.out.println("\nArff!!");
    }
}
