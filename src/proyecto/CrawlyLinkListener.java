package proyecto;

import websphinx.LinkEvent;
import websphinx.LinkListener;


public class CrawlyLinkListener implements LinkListener {

    @Override
    public void crawled(LinkEvent event) {
        String link = event.getLink().getURL().toString();
        
        if (event.getName().equals("error")) {
            System.out.println(">> error");
            System.out.println(event);
            System.out.println("<< error");
        } else {
            //System.out.println(event.getName() + ": " + link);
        }
        
        switch (event.getName()) {
            case "visited":
                main.getMainWindow().incVisitedLinks();
                //main.getMainWindow().log("Descargando " + link);
                break;
            case "too deep": main.getMainWindow().incTooDeepLinks(); break;
            case "skipped": main.getMainWindow().incSkippedLinks(); break;
            case "error": main.getMainWindow().incErrorLinks(); break;
        }
    }
}
