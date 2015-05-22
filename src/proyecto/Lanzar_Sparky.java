/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;


/**
 *
 * @author Gregorio
 */
class Lanzar_Sparky extends Thread{
    
    public Sparky sparky;
    private boolean[][] matrixAdyacencia;
    
    public Lanzar_Sparky (String[] urls) {
        sparky = new Sparky(urls);
    }
    
    @Override
    public void start(){
        sparky.fillMatrix();
        matrixAdyacencia = sparky.getMatrizAdyacencia();
        main.getMainWindow().log("Calculo de matriz finalizado");
        try {
            FileUtils.writeStringToFile(main.SAVE_MATRIX, "", false);
        } catch (IOException ex) {
            Logger.getLogger(Lanzar_Sparky.class.getName()).log(Level.SEVERE, null, ex);
        }
        main.getMainWindow().log("Guardando la matriz en un fichero en la ruta " + main.SAVE_MATRIX);
        for(int i = 0; i < matrixAdyacencia.length; ++i){
            for(int j = 0; j < matrixAdyacencia.length; ++j){
                try {
                    FileUtils.writeStringToFile(main.SAVE_MATRIX, matrixAdyacencia[i][j] ? "1 " : "0 ", true);
                } catch (IOException ex) {
                    Logger.getLogger(Lanzar_Sparky.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                FileUtils.writeStringToFile(main.SAVE_MATRIX,"\n", true);
            } catch (IOException ex) {
                Logger.getLogger(Lanzar_Sparky.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
