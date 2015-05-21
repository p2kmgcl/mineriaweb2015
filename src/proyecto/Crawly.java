/**
 * @author Pablo Molina y Gregorio Carazo
 */

package proyecto;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import websphinx.Crawler;
import websphinx.Page;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

abstract public class Crawly extends Crawler {
    
    abstract public void log(String log);
    abstract public void clearLog();

    public Crawly() {
        super();
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
     * Inicializa la estructura de ficheros de cache borrando todos los existentes
     * y preparando los ficheros que serán rellenados por el crawler.
     * Si no se invoca antes de llamar al método run() los resultados serán
     * incorporados a los ficheros ya existentes.
     */
    public void init () {
        // Create output folder
        if (main.SAVE_FOLDER != null) {
            try {
                if (main.SAVE_FOLDER.exists()) {
                    FileUtils.forceDelete(main.SAVE_FOLDER);
                }
                FileUtils.forceMkdir(main.SAVE_FOLDER);
            } catch (IOException ex) {
                Logger.getLogger(Crawly.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Create output file for matrix
        if (main.SAVE_MATRIX != null) {
            try {
                if (main.SAVE_MATRIX.exists()) {
                    FileUtils.forceDelete(main.SAVE_MATRIX);
                }
                FileUtils.writeStringToFile(main.SAVE_MATRIX, "");
            } catch (IOException ex) {
                Logger.getLogger(Crawly.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Create output arff
        if (main.SAVE_FILE != null) {
            try {
                if (main.SAVE_FILE.exists()) {
                    FileUtils.forceDelete(main.SAVE_FILE);
                }

                String header =  "@relation websphinx_crawl_string\n\n";
                       header += "@attribute document_name string\n";
                       header += "@attribute document_content string\n\n";
                       header += "@data\n";

                FileUtils.writeStringToFile(main.SAVE_FILE, header);
            } catch (IOException ex) {
                Logger.getLogger(Crawly.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void run () {
        System.out.println(">> Inicio <<");
        this.init();
        super.run();
        System.out.println(">>   Fin  <<");
    }

    /**
     * Este método se ejecuta para cada página encontrada por el crawler.
     * Se invoca automáticamente desde doVisit.
     * @param page Page to visit
     */
    private void doVisit (Page page) {        
        URL url = page.getURL();
        String fileName = File.separator + page.getURL().toString();
        fileName = fileName.replace("http://", "");
        fileName = fileName.replaceAll("[^A-Za-z0-9/]", "_");
        fileName = fileName.replace("/", File.separator);
        fileName = fileName.toLowerCase();
        
        byte[] bytes = page.getContentBytes();
        String content = page.getContent();
        
        // Add index.html to some files that are not real folders
        if (FilenameUtils.getExtension(fileName).equals("")) {
            fileName += "index.html";
        }

        // Creates the file in the output folder
        if (main.SAVE_FOLDER != null) {
            try {
                File file = new File(main.SAVE_FOLDER.getAbsolutePath(), fileName);
                FileUtils.writeByteArrayToFile(file, bytes);
            } catch (IOException ex) {
                Logger.getLogger(Crawly.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Appends the text content to a secondary text file
        if (main.SAVE_FILE != null) {
            try {
                content = content.replaceAll("[\"]", "'").replaceAll("[\n\r]", " ");
                FileUtils.writeStringToFile(main.SAVE_FILE, fileName + ", \" " + content + " \"\n", true);
            } catch (IOException ex) {
                Logger.getLogger(Crawly.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // TODO Create adyacence matrix
    }
}
