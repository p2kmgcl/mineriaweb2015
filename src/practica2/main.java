package practica2;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import websphinx.Link;

public class main {
    
    public static void main (String args[]) {        
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
            
            String urls2[] = {
                "http://www.elmundotoday.com/2015/03/mujer-con-baja-autoestima-busca-desesperadamente-galerias-de-fotos-de-famosas-sin-maquillar/",
                "http://www.elmundotoday.com/2015/03/exhibicionista-callejero-obligado-a-retirarse-porque-la-gente-va-por-la-calle-mirando-el-movil/",
                "http://www.elmundotoday.com/2015/03/ahorcarse-en-la-oficina-primer-sintoma-de-estres-laboral/",
                "http://www.elmundotoday.com/2015/03/arqueologos-de-television-espanola-expondran-los-restos-de-noche-de-fiesta/",
                "http://www.elmundotoday.com/2015/03/cancelan-una-merienda-feminista-porque-no-pueden-abrir-el-bote-de-la-mermelada/",
                "http://www.elmundotoday.com/2015/03/amancio-ortega-publicara-sus-memorias-en-las-etiquetas-de-las-camisetas-de-zara/",
                "http://www.elmundotoday.com/2015/03/renfe-anadira-un-mas-o-menos-a-la-hora-de-llegada-de-sus-trenes/",
                "http://www.elmundotoday.com/2015/03/un-estudio-confirma-que-el-divorcio-de-tus-padres-fue-culpa-tuya/",
                "http://www.elmundotoday.com/2015/03/ikea-reconoce-que-todos-sus-muebles-los-ha-sacado-de-la-basura/",
                "http://www.elmundotoday.com/2015/02/durex-pide-que-liberes-los-doce-condones-que-retienes-en-su-caja-desde-2006/",
                "http://www.elmundotoday.com/2015/02/gordo-se-acaba-de-comer-un-bocadillo-y-ya-lo-echa-de-menos/",
                "http://www.elmundotoday.com/2015/02/elogio-sincero-repetido-muchas-veces-acaba-convirtiendose-en-insulto/"
            };

            Boby boby = new Boby();
            Kiwi kiwi = new Kiwi();
            Sparky sparky = new Sparky(urls2);
            
            // Añade las urls de los sitios dados
            for (String url : urls) {
                boby.addRoot(new Link(new URL(url)));
            }
            
            // Añade las urls personalizadas (elegir entre
            // las urls anteriores y éstas)
            //for (String url : urls2) {
            //    boby.addRoot(new Link(new URL(url)));
            //}
            
            // Comentar estas 3 lineas para usar los ficheros locales
            // sin descargar nada
            System.out.println("Lanzando arañas");
            boby.init(); // Borra los ficheros existentes
            boby.run(); // Empieza a descargar
            
            //System.out.println("\nAnalizando resultados");
            kiwi.run(Boby.OUTPUT_TEXT);
            
            System.out.println("Buscando adyacencias");
            sparky.fillMatrix();
            // Sólo usado para las pruebas
            //sparky.fillCachedMatrix();
            System.out.println("Mostrando adyacencias");
            sparky.showAdjacence();
            System.out.println("Calculando ranking");
            sparky.getRank();
            
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
