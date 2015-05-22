package proyecto;

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
    }
    
    /**
     * Ejecuta el crawler para cada página rellenando la matriz de adyacencias.
     */
    public void fillMatrix () {
        System.out.println("Funciona");
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
    
    /**
     * Este método es un contenedor del método doVisit que evita que el proceso
     * termine antes de analizar cada página.
     * Se invoca automáticamente desde fillMatrix.
     * @param page 
     */
    @Override
    public void visit(Page page) {
        this.doVisit(page);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            // Mec
        }
    }
    
    /**
     * Este método se ejecuta para cada página encontrada por el crawler.
     * Se invoca automáticamente desde doVisit.
     * @param page 
     */
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

    /**
     * Muestra la matriz de adyacencia.
     */
    public void showAdjacence () {
        System.out.println("");
        for (int i = 0; i < this.adjacenceMatrix.length; i++) {
            for (int j = 0; j < this.adjacenceMatrix[i].length; j++) {
                int value = (this.adjacenceMatrix[i][j]) ? 1 : 0;
                System.out.print(value + " ");
            }
            System.out.println("");
        }
    }
    
    public boolean[][] getMatrizAdyacencia(){
        return adjacenceMatrix;
    }
}
