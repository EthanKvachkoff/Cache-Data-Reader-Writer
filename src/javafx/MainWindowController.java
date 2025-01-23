package javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import alex.io.OutputStream;
import java.util.Arrays;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import alex.io.InputStream;
import alex.utils.Constants;
import cache.Cache;
import cache.Utils;
import defintions.ItemDefinitions;


public class MainWindowController {

    @FXML
    private ListView<String> listview;

    @FXML
    private TextField searchList;  

    @FXML
    private TextField itemName;  
    @FXML
    private TextField itemValue;
    @FXML
    private TextField itemInvenModel;
    @FXML
    private TextField itemMaleEquip1;
    @FXML
    private TextField itemMaleEquip2;
    @FXML
    private TextField itemMaleEquip3;
    @FXML
    private TextField itemFemaleEquip1;
    @FXML
    private TextField itemFemaleEquip2;
    @FXML
    private TextField itemFemaleEquip3;
    @FXML
    private TextField itemInvenZoom;
    @FXML
    private TextField itemModelRot1;
    @FXML
    private TextField itemModelRot2;
    @FXML
    private TextField itemModelOSX;
    @FXML
    private TextField itemModelOSY;
    @FXML
    private TextField itemStackIds;
    @FXML
    private TextField itemStackAmounts;
    @FXML
    private TextField itemInvenOptions;
    @FXML
    private TextField itemGroundOptions;
    @FXML
    private Button newItem;
    @FXML
    private Button reloadItemDef;
    @FXML
    private Button saveItemDef;

    @SuppressWarnings("unused")
    @FXML
    public void initialize() throws IOException {
        Cache.init();
        
        // Fetch the data (replace with your actual method)
        Map<Integer, ItemDefinitions> itemDefinitions = fetchAllItemDefinitions();
    
        // Convert the map into a list of strings
        ObservableList<String> items = FXCollections.observableArrayList();
        itemDefinitions.forEach((key, value) -> items.add(key + ": " + value.getName()));

        // Create a FilteredList with an initial predicate that allows all items
        FilteredList<String> filteredItems = new FilteredList<>(items, s -> true);


        newItem.setOnAction(event -> {
            System.out.println("Button clicked!");
        });

        reloadItemDef.setOnAction(event -> {
            refreshTextFieldData();
            System.out.println("ItemDefs has be Reloaded");
        });

        saveItemDef.setOnAction(event -> {
            try {
                saveEditedFields(itemDefinitions);
            } catch (IOException e) {
                e.printStackTrace();
            }

            
            System.out.println("Item has been saved");
        });


        

        // Add a listener to the search field to filter the items in the list
        searchList.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredItems.setPredicate(item -> {
                // If the search field is empty, show all items
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                // Compare each item to the search query (case insensitive)
                String lowerCaseFilter = newValue.toLowerCase();
                return item.toLowerCase().contains(lowerCaseFilter);
            });
        });

        
        // Set the filtered list to the ListView
        listview.setItems(filteredItems);
        
        listview.setOnMouseClicked(event -> {
            String selectedItem = listview.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Extract the key (ID) from the selectedItem string, which is in the format "key: itemName"
                try {
                    // Split the selected item to get the key
                    String[] parts = selectedItem.split(":");
                    int itemId = Integer.parseInt(parts[0].trim()); // Extract the numeric ID and parse it
        
                    // Use the itemId to fetch the corresponding ItemDefinitions
                    ItemDefinitions selectedItemDefinition = itemDefinitions.get(itemId);
    
                    if (selectedItemDefinition != null) {
                        // Item Information
                        itemName.textProperty().setValue(selectedItemDefinition.getName());
                        itemValue.textProperty().setValue(String.valueOf(selectedItemDefinition.getValue()));
                        
                        // Item Model Information
                        itemInvenModel.textProperty().setValue(String.valueOf(selectedItemDefinition.modelId));
                        itemMaleEquip1.textProperty().setValue(String.valueOf(selectedItemDefinition.getMaleWornModelId1()));
                        itemMaleEquip2.textProperty().setValue(String.valueOf(selectedItemDefinition.getMaleWornModelId2()));
                        itemMaleEquip3.textProperty().setValue(String.valueOf(selectedItemDefinition.getMaleWornModelId3()));
                        itemFemaleEquip1.textProperty().setValue(String.valueOf(selectedItemDefinition.getFemaleWornModelId1()));
                        itemFemaleEquip2.textProperty().setValue(String.valueOf(selectedItemDefinition.getFemaleWornModelId2()));
                        itemFemaleEquip3.textProperty().setValue(String.valueOf(selectedItemDefinition.getFemaleWornModelId3()));
                        itemInvenZoom.textProperty().setValue(String.valueOf(selectedItemDefinition.getModelZoom()));
                        itemModelRot1.textProperty().setValue(String.valueOf(selectedItemDefinition.getModelRotation1()));
                        itemModelRot2.textProperty().setValue(String.valueOf(selectedItemDefinition.getModelRotation2()));
                        itemModelOSX.textProperty().setValue(String.valueOf(selectedItemDefinition.getModelOffset1()));
                        itemModelOSY.textProperty().setValue(String.valueOf(selectedItemDefinition.getModelOffset2()));
                        itemStackIds.textProperty().setValue(Optional.ofNullable(selectedItemDefinition.getStackIds()).map(ids -> Arrays.stream(ids)                   
                            .boxed()                      
                            .map(String::valueOf)         
                            .collect(Collectors.joining(";"))).orElse("") );
                        itemStackAmounts.textProperty().setValue(Optional.ofNullable(selectedItemDefinition.getStackAmounts()).map(ids -> Arrays.stream(ids)                   
                            .boxed()                      
                            .map(String::valueOf)         
                            .collect(Collectors.joining(";"))).orElse("") );
                        itemInvenOptions.textProperty().setValue(String.join(";",selectedItemDefinition.getInventoryOptions()));
                        itemGroundOptions.textProperty().setValue(String.join(";",selectedItemDefinition.getGroundOptions()));

                        System.out.println("Selected Item Definition: " + selectedItemDefinition.getName());
                        // For example, you could update the UI with more details about the item
                        // updateItemDetails(selectedItemDefinition);
                    } else {
                        System.out.println("Item not found in definitions.");
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Error parsing selected item: " + e.getMessage());
                }
            }
        });
    }
    
    public static Map<Integer, ItemDefinitions> fetchAllItemDefinitions() {
        Map<Integer, ItemDefinitions> allItemDefinitions = new HashMap<>();
        
        for (int itemId = 0; itemId < Utils.getItemDefinitionsSize(); itemId++) { //
            ItemDefinitions itemDef = ItemDefinitions.getItemDefinitions(itemId);
            allItemDefinitions.put(itemId, itemDef);
            if (itemDef.isLoaded()) {
                
            }
        }
        return allItemDefinitions;
    }

    private void refreshTextFieldData() {
        Map<Integer, ItemDefinitions> itemDefinitions = fetchAllItemDefinitions();
        String selectedItem = listview.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                // Parse the selected item to extract the ID
                String[] parts = selectedItem.split(":");
                int itemId = Integer.parseInt(parts[0].trim());
    
                // Fetch updated data for the selected item
                ItemDefinitions updatedItemDefinition = itemDefinitions.get(itemId);
    
                if (updatedItemDefinition != null) {
                    // Refresh TextField values with the updated data
                    itemName.textProperty().setValue(updatedItemDefinition.getName());
                    itemValue.textProperty().setValue(String.valueOf(updatedItemDefinition.getValue()));
    
                    itemInvenModel.textProperty().setValue(String.valueOf(updatedItemDefinition.modelId));
                    itemMaleEquip1.textProperty().setValue(String.valueOf(updatedItemDefinition.getMaleWornModelId1()));
                    itemMaleEquip2.textProperty().setValue(String.valueOf(updatedItemDefinition.getMaleWornModelId2()));
                    itemMaleEquip3.textProperty().setValue(String.valueOf(updatedItemDefinition.getMaleWornModelId3()));
                    itemFemaleEquip1.textProperty().setValue(String.valueOf(updatedItemDefinition.getFemaleWornModelId1()));
                    itemFemaleEquip2.textProperty().setValue(String.valueOf(updatedItemDefinition.getFemaleWornModelId2()));
                    itemFemaleEquip3.textProperty().setValue(String.valueOf(updatedItemDefinition.getFemaleWornModelId3()));
                    itemInvenZoom.textProperty().setValue(String.valueOf(updatedItemDefinition.getModelZoom()));
                    itemModelRot1.textProperty().setValue(String.valueOf(updatedItemDefinition.getModelRotation1()));
                    itemModelRot2.textProperty().setValue(String.valueOf(updatedItemDefinition.getModelRotation2()));
                    itemModelOSX.textProperty().setValue(String.valueOf(updatedItemDefinition.getModelOffset1()));
                    itemModelOSY.textProperty().setValue(String.valueOf(updatedItemDefinition.getModelOffset2()));
                    itemStackIds.textProperty().setValue(Optional.ofNullable(updatedItemDefinition.getStackIds())
                        .map(ids -> Arrays.stream(ids)
                            .boxed()
                            .map(String::valueOf)
                            .collect(Collectors.joining(";")))
                        .orElse(""));
                    itemStackAmounts.textProperty().setValue(Optional.ofNullable(updatedItemDefinition.getStackAmounts())
                        .map(ids -> Arrays.stream(ids)
                            .boxed()
                            .map(String::valueOf)
                            .collect(Collectors.joining(";")))
                        .orElse(""));
                    itemInvenOptions.textProperty().setValue(String.join(";", updatedItemDefinition.getInventoryOptions()));
                    itemGroundOptions.textProperty().setValue(String.join(";", updatedItemDefinition.getGroundOptions()));
    
                    System.out.println("Refreshed Item Definition: " + updatedItemDefinition.getName());
                } else {
                    System.out.println("Item definition not found for refresh.");
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Error parsing selected item during refresh: " + e.getMessage());
            }
        } else {
            System.out.println("No item selected for refresh.");
        }
    }

    //Repack the item into the itemdef index
    private void saveEditedFields(Map<Integer, ItemDefinitions> itemDefinitions) throws IOException {

        String selectedItem = listview.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                String[] parts = selectedItem.split(":");
                int itemId = Integer.parseInt(parts[0].trim());
                ItemDefinitions selectedItemDef = itemDefinitions.get(itemId);

                if (selectedItemDef != null) {
                    byte[] data = Cache.STORE.getIndexes()[Constants.ITEM_DEFINITIONS_INDEX].getFile(selectedItemDef.getArchiveId(), selectedItemDef.getFileId());
                    if (data == null) {
                        //System.out.println("Failed loading Item " + id+".");
                        return;
                    }
                    readOpcodeValues(new InputStream(data), data);
                    
                    System.out.println("Item definition updated successfully!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format while saving the item: " + e.getMessage());
            }
        }
    }
    
    private void readOpcodeValues(InputStream stream, byte[] data) throws IOException {
        Map<Integer, ItemDefinitions> itemDefinitions = fetchAllItemDefinitions();
        OutputStream buffer = new OutputStream(data);
        String selectedItem = listview.getSelectionModel().getSelectedItem();
        String[] parts = selectedItem.split(":");
        int itemId = Integer.parseInt(parts[0].trim());
        ItemDefinitions selectedItemDef = itemDefinitions.get(itemId);
        int archiveId = selectedItemDef.getArchiveId();
        while (true) {
            int opcode = stream.readUnsignedByte();
            if (opcode == 0)
                break;
            else if (opcode == 2) {
                String origName = stream.readString();  // Read the original string
                String newName = itemName.getText();  // Get the new name from the UI
    
                System.out.println("Original Item Name: " + origName);
                System.out.println("New Item Name: " + newName);
    
                if (!origName.equals(newName)) {
                    // Find the position of the original string in the buffer
                    int origPosition = findPositionOfStringInBuffer(data, origName);
    
                    // If the new string is the same length as the original one, just replace it
                    if (newName.length() == origName.length()) {
                        // Directly replace the string at the position
                        replaceStringAtPosition(buffer, origPosition, newName);
                    } else {
                        // If the new string is longer or shorter, handle accordingly
                        // Replace the original string with the new one
                        replaceStringAtPosition(buffer, origPosition, newName);
                        
                        // Ensure buffer accommodates the new string (resize if needed)
                        checkCapacityForNewData(buffer, origPosition + newName.length());
                    }
                }
                
                System.out.println("Buffer after modification: " + new String(buffer.getBuffer()));
                buffer.setOffset(stream.getOffset() - (newName.length() + 1));
                Cache.STORE.getIndexes()[Constants.ITEM_DEFINITIONS_INDEX].putFile(archiveId, selectedItemDef.getFileId(), buffer.getBuffer());
            }
        }
    }

        // Helper method to find the position of the original string in the buffer
    private int findPositionOfStringInBuffer(byte[] data, String origName) {
        // Convert the original name to bytes
        byte[] origNameBytes = origName.getBytes();
        for (int i = 0; i < data.length - origNameBytes.length; i++) {
            boolean found = true;
            for (int j = 0; j < origNameBytes.length; j++) {
                if (data[i + j] != origNameBytes[j]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return i;  // Return the position where the original string starts
            }
        }
        return -1;  // Return -1 if the string wasn't found
    }

    // Helper method to replace a string at a given position in the buffer
    private void replaceStringAtPosition(OutputStream buffer, int position, String newName) {
        byte[] newNameBytes = newName.getBytes();

        // Write the new name at the given position in the buffer
        for (int i = 0; i < newNameBytes.length; i++) {
            buffer.writeByte(newNameBytes[i], position + i);  // Write byte-by-byte
        }

        // Optionally, add a null terminator at the end of the string (if needed)
        buffer.writeByte(0, position + newNameBytes.length);
    }

    // Helper method to ensure that the buffer has enough capacity for new data
    private void checkCapacityForNewData(OutputStream buffer, int newPosition) {
        buffer.checkCapacityPosition(newPosition);  // Ensure buffer has enough space
    }

}
