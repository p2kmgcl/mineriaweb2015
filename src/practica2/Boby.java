/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Gregorio
 */
public class Boby {
    public void clear_file(String name_file) throws IOException{
        File file = new File(name_file);
        String sFile = FileUtils.readFileToString(file);
        System.out.println(sFile.split(" ").length);
        sFile = sFile.replaceAll("[^A-Za-z0-9áéíóúÁÉÍÓÚ ]", "");
        FileUtils.writeStringToFile(file, sFile);
    }
    
    // Contar palabras
    // ..
}
