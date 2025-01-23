package fetchers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import alex.store.Archive;
import alex.store.Store;
import alex.utils.Constants;

/*
 * Index (level 0 container)
 * * Archive (level 1 container)
 * * * File (data)
 */

public class ModelsFetcher {

     public static void main(String[] args) throws IOException {

        Store cachePath = new Store("C:\\Users\\ethan\\OneDrive\\Desktop\\CodingScratchBook\\Cacheing\\Cache\\src\\realizationcache\\");
        dumpModels(cachePath);
    
    }
    
    public static void dumpModels(Store cache) {
        //final int numModels = cache.getIndexes()[7].getLastArchiveId() + 1; // Maxes out at like 40k models??? Im sure its because of an offset.
        
        int maxPossibleId = 80000;// Corrected to Index 1 for models
        System.out.println("Attempting to dump models...");
        
        File modelsDir = new File("models");
        if (!modelsDir.exists()) {
            modelsDir.mkdir();
        }
    
        for (int modelId = 0; modelId < maxPossibleId; modelId++) {
            try { 
                Archive data = cache.getIndexes()[Constants.MODELS_INDEX].getArchive(modelId);// Fetching from Index 1

                if (data == null) {
                    System.out.println("Model " + modelId + " is null or corrupted.");
                    continue;
                }
                byte[] modelData = data.getData();
                // Write the model to a .dat file
                File modelFile = new File(modelsDir, modelId + ".dat");
                try (FileOutputStream fos = new FileOutputStream(modelFile)) {
                    fos.write(modelData);
                }
                System.out.println("Dumped model: " + modelId);
            } catch (Exception e) {
                System.err.println("Error dumping model " + modelId + ": " + e.getMessage());
            }
        }
        System.out.println("Model dumping completed!");
    } 
}


