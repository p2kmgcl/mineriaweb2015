package proyecto;

import websphinx.CrawlEvent;
import websphinx.CrawlListener;

public class CrawlyListener implements CrawlListener {

    @Override
    public void started(CrawlEvent event) {}

    @Override
    public void stopped(CrawlEvent event) {
        main.getMainWindow().log("Proceso finalizado");
        main.getMainWindow().crawlerEnd();
    }

    @Override
    public void cleared(CrawlEvent event) {}

    @Override
    public void timedOut(CrawlEvent event) {}

    @Override
    public void paused(CrawlEvent event) {}
}
