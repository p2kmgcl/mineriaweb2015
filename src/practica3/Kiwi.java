package practica3;

import practica2.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToBinary;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.clusterers.SimpleKMeans;

public class Kiwi {
    
    public void run (String fileName) {
        try {
            DataSource source = new DataSource(fileName + ".arff");
            Instances originalInstances = source.getDataSet();
            Instances instances;
            SimpleKMeans kmeans = new SimpleKMeans();
            int[] assignments = null;
            StringToWordVector stringToWordVector = new StringToWordVector(1000);
            
            // Aplicamos le filtro NumericToBinary
            System.out.println("Aplicando filtro NumericToBinary");
            NumericToBinary numericToBinary = new NumericToBinary();
            numericToBinary.setInputFormat(originalInstances);
            numericToBinary.setIgnoreClass(true);
            instances = Filter.useFilter(originalInstances, numericToBinary);
            
            // Guardamos los datos con los filtros aplicados
            System.out.println("Guardando fichero binario");
            File binaryFile = new File(fileName + "_binary.arff");
            FileUtils.writeStringToFile(binaryFile, instances.toString());
            
             // Aplicamos el filtro StringToWordVector al contenido separando palabras
            System.out.println("Aplicando filtro StringToWordVector");
            stringToWordVector.setInputFormat(instances);
            stringToWordVector.setOutputWordCounts(true);
            instances = Filter.useFilter(instances, stringToWordVector);
            
            // Guardamos los datos con los filtros aplicados
            System.out.println("Guardando fichero filtrado");
            File filteredFile = new File(fileName + "_filtered.arff");
            FileUtils.writeStringToFile(filteredFile, instances.toString());
            
            // Aplicamos el filtro StringToWordVector al conjunto original
            System.out.println("Aplicando filtro StringToWordVector TFIFD");
            stringToWordVector.setInputFormat(originalInstances);
            stringToWordVector.setIDFTransform(true);
            instances = Filter.useFilter(originalInstances, stringToWordVector);
            
            // Guardamos los datos con los filtros aplicados
            System.out.println("Guardando fichero TFIFD");
            File tfifdFile = new File(fileName + "_tfifd.arff");
            FileUtils.writeStringToFile(tfifdFile, instances.toString());
            
            kmeans.setPreserveInstancesOrder(true);
            
            kmeans.setNumClusters(2);
            kmeans.buildClusterer(instances);
            assignments = kmeans.getAssignments();
            for(int i = 0; i < assignments.length; ++i){
                System.out.println(instances.attribute(i) + " " + assignments[i]);
            }
        } catch (Exception ex) {
            Logger.getLogger(Kiwi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
