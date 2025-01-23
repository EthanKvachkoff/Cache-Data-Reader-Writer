package fetchers;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cache.Cache;
import cache.Utils;
import defintions.ItemDefinitions;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ItemDefinitionFetcher {

    // Fetches all item definitions by iterating through all valid item IDs
    public static Map<Integer, ItemDefinitions> fetchAllItemDefinitions() {
        Map<Integer, ItemDefinitions> allItemDefinitions = new HashMap<>();

        // Assuming the item definitions are available in the static array itemsDefinitions
        for (int itemId = 0; itemId < Utils.getItemDefinitionsSize(); itemId++) {
            // Get item definition for the current itemId
            ItemDefinitions itemDef = ItemDefinitions.getItemDefinitions(itemId);
            // If the item definition is loaded, add it to the map
            if (itemDef.isLoaded()) {
                allItemDefinitions.put(itemId, itemDef);
            }
        }

        return allItemDefinitions;
    }

    public static void main(String[] args) throws IOException {

        Cache.init();
        Map<Integer, ItemDefinitions> itemDefinitions = fetchAllItemDefinitions();

        dumpIdAndName(itemDefinitions);
        dumpAllItemDefs(itemDefinitions);


        /*
         * Fix it to read all of the files
         * 
        */
    }

    public static void dumpIdAndName(Map<Integer, ItemDefinitions> itemDefinitions){
        
        // Example: Print the name of each item
        for (Map.Entry<Integer, ItemDefinitions> entry : itemDefinitions.entrySet()) {
            ItemDefinitions itemDef = entry.getValue();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("itemDefs.txt", true));
                writer.write("Item ID: " + entry.getKey() + ", Name: " + itemDef.getName() + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
           
        } 
        System.out.println("itemDefs.txt has been Populated!");
    }

    public static void dumpAllItemDefs(Map<Integer, ItemDefinitions> itemDefinitions){

        // Example: Print the name of each item
        for (Map.Entry<Integer, ItemDefinitions> entry : itemDefinitions.entrySet()) {
            ItemDefinitions itemDef = entry.getValue();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("allItemDefs.txt", true));
                writer.write("Item ID: " + entry.getKey() + ", Name: " + itemDef.getName() + ", Model: " + itemDef.modelId + ", Female1 Worn Model: " + itemDef.getFemaleWornModelId1() + ", Male1 Worn Model: " + itemDef.getMaleWornModelId1()+"\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("allItemDefs.txt has been Populated!");
    }
}