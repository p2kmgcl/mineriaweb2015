package practica2;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToBinary;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Kiwi {
    
    public void run (String fileName) {
        try {
            DataSource source = new DataSource(fileName + ".arff");
            Instances originalInstances = source.getDataSet();
            Instances instances;
            
            // Aplicamos el filtro StringToNominal a los nombres de los documentos
            System.out.println("Aplicando filtro StringToNominal");
            StringToNominal stringToNominal = new StringToNominal();
            stringToNominal.setAttributeRange("first");
            stringToNominal.setInputFormat(originalInstances);
            instances = Filter.useFilter(originalInstances, stringToNominal);
            
            // Aplicamos el filtro StringToWordVector al contenido separando palabras
            System.out.println("Aplicando filtro StringToWordVector");
            StringToWordVector stringToWordVector = new StringToWordVector(1000);
            stringToWordVector.setInputFormat(instances);
            stringToWordVector.setOutputWordCounts(true);
            instances = Filter.useFilter(instances, stringToWordVector);
            
            // Guardamos los datos con los filtros aplicados
            System.out.println("Guardando fichero filtrado");
            File filteredFile = new File(fileName + "_filtered.arff");
            FileUtils.writeStringToFile(filteredFile, instances.toString());
            
            // Aplicamos le filtro NumericToBinary
            System.out.println("Aplicando filtro NumericToBinary");
            NumericToBinary numericToBinary = new NumericToBinary();
            numericToBinary.setInputFormat(instances);
            numericToBinary.setIgnoreClass(true);
            instances = Filter.useFilter(instances, numericToBinary);
            
            // Guardamos los datos con los filtros aplicados
            System.out.println("Guardando fichero binario");
            File binaryFile = new File(fileName + "_binary.arff");
            FileUtils.writeStringToFile(binaryFile, instances.toString());
            
            // Aplicamos el filtro StringToWordVector al conjunto original
            System.out.println("Aplicando filtro StringToWordVector TFIFD");
            stringToWordVector.setInputFormat(originalInstances);
            stringToWordVector.setIDFTransform(true);
            instances = Filter.useFilter(originalInstances, stringToWordVector);
            
            // Guardamos los datos con los filtros aplicados
            System.out.println("Guardando fichero TFIFD");
            // TODO falla aquí
            File tfifdFile = new File(fileName + "_tfifd.arff");
            FileUtils.writeStringToFile(tfifdFile, instances.toString());
            
        } catch (Exception ex) {
            Logger.getLogger(Kiwi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
