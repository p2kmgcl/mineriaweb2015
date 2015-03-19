package practica2;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import websphinx.Crawler;
import websphinx.DownloadParameters;
import websphinx.Page;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class Boby extends Crawler {
    
    static final private String OUTPUT_DIR = "output";
    static final public String OUTPUT_TEXT = "outputText";
    final public ArrayList<String> extensions;

    public Boby() {
        super();
        
        // Initiate array of extensions
        extensions = new ArrayList<>();
        
        // User agent, robots.txt...
        DownloadParameters downloadParameters = new DownloadParameters();
        downloadParameters.changeObeyRobotExclusion(false); // :)
        downloadParameters.changeUserAgent("ChupiCrawler Mozilla/5.0 (X11; U; Linux x86_64;");
        this.setDownloadParameters(downloadParameters);
        
        // SUBTREE, SERVER, WEB
        this.setDomain(Crawler.SERVER);
        // ALL_LINKS, HYPERLINKS, LINKS_AND_IMAGES
        this.setLinkType(Crawler.ALL_LINKS);
        // Depth
        this.setMaxDepth(1);
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
        try {
            File targetDirectory = new File(OUTPUT_DIR);
            if (targetDirectory.exists()) {
                FileUtils.forceDelete(targetDirectory);
            }
            FileUtils.forceMkdir(targetDirectory);
        } catch (IOException ex) {
            Logger.getLogger(Boby.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Create output file
        try {
            File targetFile = new File(OUTPUT_TEXT);
            if (targetFile.exists()) {
                FileUtils.forceDelete(targetFile);
            }
            FileUtils.writeStringToFile(targetFile, "");
        } catch (IOException ex) {
            Logger.getLogger(Boby.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Create output arff
        try {
            File targetFile = new File(OUTPUT_TEXT + ".arff");
            if (targetFile.exists()) {
                FileUtils.forceDelete(targetFile);
            }
            
            String header =  "@relation departments_string\n\n";
                   header += "@attribute document_name string\n";
                   header += "@attribute document_content string\n\n";
                   header += "@data\n";
                   
            FileUtils.writeStringToFile(targetFile, header);
        } catch (IOException ex) {
            Logger.getLogger(Boby.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este método se ejecuta para cada página encontrada por el crawler.
     * Se invoca automáticamente desde doVisit.
     * @param page Page to visit
     */
    private void doVisit (Page page) {
        URL url = page.getURL();
        String fileName = url.getFile();
        byte[] bytes = page.getContentBytes();
        String html = page.getContent();
        String text = Jsoup.parse(html).text();
        String extension = FilenameUtils.getExtension(fileName);
        
        boolean isText =
                extension.equals("htm") ||
                extension.equals("html") ||
                extension.equals("php") ||
                extension.equals("asp");
        
        if (extensions.indexOf(extension) == -1) {
            extensions.add(extension);
            //System.out.println("Added: " + extension);
        }
        
        if (!fileName.endsWith("/") && isText) {            
            // Add index.html to some files that are not real folders
            if (FilenameUtils.getExtension(fileName).equals("")) {
                fileName += "/index.html";
            }
            
            // Creates the file in the output folder
            try {
                fileName = OUTPUT_DIR + fileName;
                fileName = fileName.replaceAll("[^A-Za-z0-9áéíóúÁÉÍÓÚ\n\\/\\. ]", "");
                System.out.println(fileName);
                File file = new File(fileName);
                FileUtils.writeByteArrayToFile(file, bytes);
            } catch (IOException ex) {
                Logger.getLogger(Boby.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Appends the text content to a secondary text file
            try {
                File file = new File(OUTPUT_TEXT);
                FileUtils.writeStringToFile(file, text + "\n", true);
                
                File fileArff = new File(OUTPUT_TEXT + ".arff");
                fileName = fileName.replaceAll("[^A-Za-z0-9]", "_").toLowerCase();
                text = text.replaceAll("[^A-Za-z0-9áéíóúÁÉÍÓÚ ]", "").toLowerCase();
                String[] textArray = text.split(" ");
                String result = "";
                for (String word : textArray) {
                    if (word.length() >= 3) {
                        result += word + " ";
                    }
                }
                
                FileUtils.writeStringToFile(fileArff, fileName + ", \" " + result + " \"\n", true);
            } catch (IOException ex) {
                Logger.getLogger(Boby.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
