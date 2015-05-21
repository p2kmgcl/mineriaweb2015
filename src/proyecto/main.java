/**
 * @author Pablo Molina y Gregorio Carazo
 */

package proyecto;

import java.io.File;
import java.util.ArrayList;

public class main {
    static private MainWindow mainWindow;
    static private Crawly crawly;
    static private Thread crawlyThread;
    
    static public File SAVE_FOLDER = null;
    static public File SAVE_MATRIX = null;
    static public File SAVE_FILE = null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        main.mainWindow = new MainWindow() {
            @Override public void runCrawler(ArrayList<Restriccion> restrictions) {
                if (main.crawlyThread != null) {
                    main.crawlyThread.interrupt();
                    main.crawlyThread = null;
                }
                main.crawly.setRestrictions(restrictions);
                main.crawlyThread = new Thread(main.crawly);
                main.crawlyThread.start();
            }

            @Override public void stopCrawler() {
                if (main.crawly != null) {
                    main.crawly.stop();
                }
                
                if (main.crawlyThread != null) {
                    main.crawlyThread.interrupt();
                    main.crawlyThread = null;
                }
            }

            @Override public Crawly getCrawler() {
                return main.crawly;
            }

            @Override
            public void resetCrawler() {
                stopCrawler();
                main.crawly = new Crawly() {
                    @Override public void log(String log) { main.mainWindow.log(log); }
                    @Override public void clearLog() { main.mainWindow.clearLog(); }
                };
                
                main.crawly.setSynchronous(false);
                main.crawly.addLinkListener(new CrawlyLinkListener());
                main.crawly.addCrawlListener(new CrawlyListener());
            }
        };
        
        main.mainWindow.resetCrawler();
        main.mainWindow.setVisible(true);
    }
    
    public static MainWindow getMainWindow () {
        return main.mainWindow;
    }
}
