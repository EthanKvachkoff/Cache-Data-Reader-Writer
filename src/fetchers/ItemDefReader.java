package fetchers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import alex.io.InputStream;
import alex.io.Stream;
import alex.utils.Constants;
import cache.Cache;
import cache.Utils;
import defintions.ItemDefinitions;

public class ItemDefReader {
    private static ItemDefinitions[] itemsDefinitions;
    private BufferedWriter writer;
    
    private HashMap<Integer, Object> clientScriptData;
    public String[] inventoryOptions;

    // model information
    public int[] originalModelColors;
    public int[] modifiedModelColors;
    public short[] originalTextureColors;
    private short[] modifiedTextureColors;
    private byte[] unknownArray1;
    private int[] unknownArray2;

    private int unknownValue;
    private int unknownValue1;
    private int unknownValue2;
    private int opcode3;
	private int opcode199;
	private int opcode223;
	private int opcode198;
	private int opcode186;
	private int opcode29;
	private int opcode238;
	private int opcode153;
	private int opcode155;
	private int opcode99;
	private int opcode251;
	private int opcode22;
	private int opcode192;
	private int opcode245;
	private int opcode45;
	private int opcode56;
	private int opcode248;
	private int opcode237;
	private int opcode243;
	private int opcode185;
	private int opcode221;
	private int opcode240;
	private int opcode154;
	private int opcode158;
	private int opcode137;
	private int opcode143;
	private int opcode61;
	private int opcode80;
	private int opcode196;
	private int opcode85;
	private int opcode239;
	private int opcode177;
	private int opcode163;
	private int opcode150;
	private int opcode152;
	private int opcode135;
	private int opcode120;
	private int opcode204;
	private int opcode81;
	private int opcode208;
	private int opcode242;
	private int opcode15;
	private int opcode233;
	private int opcode213;
	private int opcode207;
	private int opcode216;
	private int opcode206;
	private int opcode50;
	private int opcode193;
	private int opcode71;
	private int opcode10;
	private int opcode55;
	private int opcode144;
	private int opcode235;
	private int opcode188;
	private int opcode241;
	private int opcode236;
	private int opcode182;
	private int opcode169;
	private int opcode190;
	private int opcode178;
	private int opcode88;
	private int opcode200;
	private int opcode184;
	private int opcode176;
	private int opcode197;
	private int opcode247;
	private int opcode218;
	private int opcode250;
	private int opcode174;
	private int opcode210;
	private int opcode164;
	private int opcode142;
	private int opcode148;
	private int opcode133;
	private int opcode222;
	private int opcode138;
	private int opcode194;
	private int opcode119;
	private int opcode202;
	private int opcode149;
	private int opcode64;
	private int opcode147;
	private int opcode214;
	private int opcode74;
	private int opcode86;
	private int opcode167;
	private int opcode161;
	private int opcode171;
	private int opcode58;
	private int opcode59;
	private int opcode187;
	private int opcode77;
	private int opcode229;
	private int opcode230;
	private int opcode17;
	private int opcode67;
	private int opcode131;
	private int opcode225;
	private int opcode203;
	private int opcode19;
	private int opcode43;
	private int opcode168;
	private int opcode46;
	private int opcode209;
	private int opcode166;
	private int opcode54;
	private int opcode21;
	private int opcode73;
	private int opcode159;
	private int opcode123;
	private int opcode146;
	private int opcode180;
	private int opcode20;
	private int opcode165;
	private int opcode84;
	private int opcode28;
	private int opcode175;
	private int opcode141;
	private int opcode205;
	private int opcode220;
	private int opcode136;
	private int opcode212;
	private int opcode49;
	private int opcode69;
	private int opcode72;
	private int opcode60;
	private int opcode62;
	private int opcode219;
	private int opcode44;
	private int opcode227;
	private int opcode76;
	private int opcode234;
	private int opcode57;
	private int opcode51;
	private int opcode124;
	private int opcode70;
	private int opcode231;
	private int opcode162;
	private int opcode160;
	private int opcode181;
	private int opcode183;
	private int opcode191;
	private int opcode189;
	private int opcode179;
	private int opcode173;
	private int opcode48;
	private int opcode172;
	private int opcode42;
	private int opcode47;
	private int opcode246;
	private int opcode89;
	private int opcode195;
	private int opcode145;
	private int opcode224;
	private int opcode63;
	private int opcode94;
	private int opcode201;
	private int opcode217;
    private int opcode13;
	private int opcode52;
	private int opcode53;
	private int opcode82;
	private int opcode83;
	private int opcode87;
	private int opcode117;
	private int opcode66;
	private int opcode116;
	private int opcode157;
	private int opcode68;
	private int opcode244;
	private int opcode170;
	private int opcode151;
	private int opcode75;
	private int opcode14;
	private int opcode27;
	private int opcode9;
	private int opcode232;
	private int opcode211;
	private int opcode254;
	private int opcode118;
	private int opcode228;
	private int opcode226;
	private int opcode255;
	private int opcode253;
	private int opcode252;
	private int opcode156;
	private int opcode215;
    private int maleEquipModelId3;
    private int femaleEquipModelId3;
    private int unknownInt1;
    private int unknownInt2;
    private int unknownInt3;
    private int unknownInt4;
    private int unknownInt5;
    private int unknownInt6;
    private int certId;
    private int certTemplateId;
    private int[] stackIds;
    private int[] stackAmounts;
    private int unknownInt7;
    private int unknownInt8;
    private int unknownInt9;
    private int unknownInt10;
    private int unknownInt11;
    private int teamId;
    private int lendId;
    private int lendTemplateId;
    private int unknownInt12;
    private int unknownInt13;
    private int unknownInt14;
    private int unknownInt15;
    private int unknownInt16;
    private int unknownInt17;
    private int unknownInt18;
    private int unknownInt19;
    private int unknownInt20;
    private int unknownInt21;
    private int unknownInt22;
    private int unknownInt23;
    private int maleEquip1;
    private int femaleEquip1;
    private int maleEquip2;
    private int femaleEquip2;


    static {
        // Defer initialization of itemsDefinitions to after Cache.STORE is set
        itemsDefinitions = null;
    }

    public ItemDefReader() {
        try {
            writer = new BufferedWriter(new FileWriter("itemdefs.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final void writeToFile(String content) {
        try {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final void closeFile() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ItemDefinitions getItemDefinitions(int itemId) {
        if (itemsDefinitions == null) {
            initializeItemDefinitions();
        }
        if (itemId < 0 || itemId >= itemsDefinitions.length) {
            itemId = 0;
        }
        ItemDefinitions def = itemsDefinitions[itemId];
        if (def == null) {
            itemsDefinitions[itemId] = def = new ItemDefinitions(itemId);
        }
        return def;
    }

    public static void clearItemsDefinitions() {
        if (itemsDefinitions != null) {
            for (int i = 0; i < itemsDefinitions.length; i++) {
                itemsDefinitions[i] = null;
            }
        }
    }

    private static void initializeItemDefinitions() {
        try {
            if (Cache.STORE == null) {
                throw new IllegalStateException("Cache.STORE is not initialized!");
            }
            itemsDefinitions = new ItemDefinitions[Utils.getItemDefinitionsSize()];
        } catch (Exception e) {
            e.printStackTrace();
            itemsDefinitions = new ItemDefinitions[0];
        }
    }

    public void loadItemDefinitions(int archiveId, int fileId) {
        try {
            byte[] data = Cache.STORE.getIndexes()[Constants.ITEM_DEFINITIONS_INDEX].getFile(archiveId, fileId);
            if (data == null) {
                System.out.println("Failed loading Item definition for archiveId: " + archiveId + ", fileId: " + fileId);
                return;
            }
            readOpcodeValues(new InputStream(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readOpcodeValues(InputStream stream) throws IOException {
        while (true) {
            int opcode = stream.readUnsignedByte();
            if (opcode == 0)
                break;
            readValues(stream, opcode);
        }
    }

    /*if (item.name != null) { // assuming default for name is null, if it's something like "null" then check for that instead
    buffer.p1(1); // opcode
    buffer.putString(item.name);
    }*/
    private void readValues(InputStream stream, int opcode) throws IOException {
        // All opcode cases
        if (opcode == 1) {
            int modelId = stream.readBigSmart();
            writeToFile("opcode1 (ModelId)= " + modelId);
        } else if (opcode == 2) {
            String name = stream.readString();
            writeToFile("opcode2  (Name)= " + name);
        } else if (opcode == 3){
            opcode3 = stream.readUnsignedByte();
            writeToFile("opcode3 = " + opcode3);
        } else if (opcode == 4) {
            int modelZoom = stream.readUnsignedShort();
            writeToFile("opcode4  (modelZoom)= " + modelZoom);
        } else if (opcode == 5) {
            int modelRotation1 = stream.readUnsignedShort();
            writeToFile("opcode5 (modelRotation1)= " + modelRotation1);
        } else if (opcode == 6) {
            int modelRotation2 = stream.readUnsignedShort();
            writeToFile("opcode6 (modelRotation2)= " + modelRotation2);
        } else if (opcode == 7) {
            int modelOffsetX = stream.readUnsignedShort();
            if (modelOffsetX > 32767) modelOffsetX -= 65536;
            writeToFile("opcode7 (modelOffsetX)= " + modelOffsetX);
        } else if (opcode == 8) {
            int modelOffsetY = stream.readUnsignedShort();
            if (modelOffsetY > 32767) modelOffsetY -= 65536;
            writeToFile("opcode8 (modelOffsetY)= " + modelOffsetY);
        } else if (opcode == 11) {
            writeToFile("opcode11 = 1");
        } else if (opcode == 12) {
            int value = stream.readInt();
            writeToFile("opcode12 (value)= " + value);
        } else if (opcode == 14) {
            int intVal1 = stream.readUnsignedByte();
            writeToFile("opcode14 (intVal1)= " + intVal1);
        } else if (opcode == 16) {
            Boolean isMembers = true;
            writeToFile("opcode16 (isMembers)= " + isMembers);
        } else if (opcode == 23){
            maleEquip1 = stream.readBigSmart();
            writeToFile("opcode23 (maleEquip1)"+ " = " + maleEquip1);
        }
        else if (opcode == 24){
            maleEquip2 = stream.readBigSmart();
            writeToFile("opcode24 (maleEquip2)" + " = " + maleEquip2);
        }    
        else if (opcode == 25){
            femaleEquip1 = stream.readBigSmart();
            writeToFile("opcode25 (femaleEquip1)" + " = " + femaleEquip1);
        }
        else if (opcode == 26){
            femaleEquip2 = stream.readBigSmart();
            writeToFile("opcode26 (femaleEquip2)" +  " = " + femaleEquip2);
        }     
        else if (opcode >= 30 && opcode < 35) {
            String groundAction = stream.readString();
            writeToFile("opcode (groundAction)" + opcode + " = " + groundAction);
        } else if (opcode == 40) {
            int colorCount = stream.readUnsignedByte();
            writeToFile("opcode40 colorCount = " + colorCount);
            for (int i = 0; i < colorCount; i++) {
                int originalColor = stream.readUnsignedShort();
                int replacementColor = stream.readUnsignedShort();
                writeToFile("originalColor = " + originalColor + ", replacementColor = " + replacementColor);
            }
        } else if (opcode == 41) {
            int textureCount = stream.readUnsignedByte();
            writeToFile("opcode41 textureCount = " + textureCount);
            for (int i = 0; i < textureCount; i++) {
                int originalTexture = stream.readUnsignedShort();
                int replacementTexture = stream.readUnsignedShort();
                writeToFile("originalTexture = " + originalTexture + ", replacementTexture = " + replacementTexture);
            }
        } else if (opcode == 57){
            opcode57 = stream.readUnsignedByte();
            writeToFile("opcode57 = " + opcode57);
        } else if (opcode == 65) {
            writeToFile("opcode65 = true");
        } else if (opcode == 78 || opcode == 79) {
            int secondaryModelId = stream.readBigSmart();
            writeToFile("opcode" + opcode + " (secondaryModelId)= " + secondaryModelId);
        } else if (opcode == 90 || opcode == 91 || opcode == 92 || opcode == 93) {
            int unknownModelId = stream.readBigSmart();
            writeToFile("opcode" + opcode + " (unknownModelId) = " + unknownModelId);
        } else if (opcode == 95) {
            int combatLevel = stream.readUnsignedShort();
            writeToFile("opcode95 (combatLevel) = " + combatLevel);
        } else if (opcode == 96){
            unknownInt6 = stream.readUnsignedByte();
            writeToFile("opcode96 = " + unknownInt6);
        } else if (opcode == 97) {
            int resizeX = stream.readUnsignedShort();
            writeToFile("opcode97 = " + resizeX);
        } else if (opcode == 98) {
            int resizeY = stream.readUnsignedShort();
            writeToFile("opcode98 = " + resizeY);
        } else if (opcode >= 100 && opcode < 110) {
            int stackId = stream.readUnsignedShort();
            int stackAmount = stream.readUnsignedShort();
            writeToFile("opcode" + opcode + " stackId = " + stackId + ", stackAmount = " + stackAmount);
        } else if (opcode == 110) {
            int resizeZ = stream.readUnsignedShort();
            writeToFile("opcode110 = " + resizeZ);
        } else if (opcode == 111) {
            int ambient = stream.readUnsignedShort();
            writeToFile("opcode111 = " + ambient);
        } else if (opcode == 112) {
            int contrast = stream.readUnsignedShort();
            writeToFile("opcode112 = " + contrast);
        } else if (opcode == 113) {
            int team = stream.readUnsignedByte();
            writeToFile("opcode113 = " + team);
        } else if (opcode == 114) {
            int optionId = stream.readUnsignedByte();
            writeToFile("opcode114 = " + optionId);
        } else if (opcode == 115) {
            writeToFile("opcode115 = true");
        }else if (opcode == 249) {
			int length = stream.readUnsignedByte();
			if (clientScriptData == null)
				clientScriptData = new HashMap<Integer, Object>(length);
			for (int index = 0; index < length; index++) {
				boolean stringInstance = stream.readUnsignedByte() == 1;
				int key = stream.read24BitInt();
				Object value = stringInstance ? stream.readString() : stream.readInt();
				clientScriptData.put(key, value);
                //writeToFile("key=" + key + ", value=" + value);
			}
		//System.out.println("End of the opcode for this item.");
		}else if (opcode == 127) {
			int unknownInt18 = stream.readUnsignedByte();
			int unknownInt19 = stream.readUnsignedShort();
            writeToFile("opcode127 = " + unknownInt18 + ", " + unknownInt19);
		} else if (opcode == 45){
            int opcode45 = stream.readUnsignedByte();
            writeToFile("opcode45 = " + opcode45); 
        }else if (opcode >= 35 && opcode < 40){
			inventoryOptions[opcode - 35] = stream.readString();
			String invenOptions = inventoryOptions[opcode - 35] = stream.readString();
            writeToFile("invenOptions = " + invenOptions);
		}else if (opcode == 68){
            opcode68 = stream.readUnsignedByte();
            writeToFile("opcode68 = " + opcode68);
        }else if (opcode == 121){
            lendId = stream.readUnsignedShort();
            writeToFile("lendId = " + lendId);
        }else if (opcode == 122){
            lendTemplateId = stream.readUnsignedShort();
            writeToFile("lendTemplateId = " + lendTemplateId);
        }else if (opcode == 82){
             opcode82 = stream.readUnsignedByte();
             writeToFile("opcode82 = " + opcode82);
        }else if (opcode == 162){
            opcode162 = stream.readUnsignedByte();
            writeToFile("opcode162 = " + opcode162);
        }else if (opcode == 126) {
			unknownInt15 = stream.readByte() << 0;
			unknownInt16 = stream.readByte() << 0;
			unknownInt17 = stream.readByte() << 0;
            writeToFile("opcode126 = " + unknownInt15 + ", " + unknownInt16 + ", " + unknownInt17);
		} else if (opcode == 245){
            opcode245 = stream.readUnsignedByte();
            writeToFile("opcode245 = " + opcode245);
        }else if (opcode == 49){
            opcode49 = stream.readUnsignedByte();
            writeToFile("opcode49 = " + opcode49);
        } else if (opcode == 255){
            opcode255 = stream.readUnsignedByte();
            writeToFile("opcode255 = " + opcode255);
        } else if (opcode == 145){
            opcode145 = stream.readUnsignedByte();
            writeToFile("opcode145 = " + opcode145);
        } else if (opcode == 220){
            opcode220 = stream.readUnsignedByte();
            writeToFile("opcode220 = " + opcode220);
        } else if (opcode == 178){
            opcode178 = stream.readUnsignedByte();
            writeToFile("opcode178 = " + opcode178);
        } else if (opcode == 219){
            opcode219 = stream.readUnsignedByte();
            writeToFile("opcode219 = " + opcode219);
        } else if (opcode == 184){
            opcode184 = stream.readUnsignedByte(); 
            writeToFile("opcode184 = " + opcode184);
        } else {
            writeToFile("Unknown opcode: " + opcode);
        }
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

    public static void main(String[] args) {
        try { 
            // Initialize Cache.STORE before accessing any dependent class
            Cache.STORE = new alex.store.Store("C:\\Users\\ethan\\OneDrive\\Desktop\\CodingScratchBook\\Cacheing\\Cache\\src\\realizationcache\\");
    
            // Define the maximum number of item definitions (you can adjust this as per the total number of items)
            int maxItemId = 100000;  // Adjust this based on your cache's item count
            Map<Integer, ItemDefinitions> itemDefinitions = fetchAllItemDefinitions();
            // Iterate through all item IDs and load the corresponding item definitions
            for (int itemId = 0; itemId < maxItemId; itemId++) {
                try {
                    // Instantiate the reader for each item
                    ItemDefReader reader = new ItemDefReader();
                    ItemDefinitions updatedItemDefinition = itemDefinitions.get(itemId);
                    // Load the item definition (you may need to adjust archiveId and fileId based on the cache structure)
                    reader.loadItemDefinitions(updatedItemDefinition.getArchiveId(), itemId);  // Replace with actual archiveId and itemId (itemId could be the fileId or id)
                    reader.closeFile();
                } catch (Exception e) {
                    // Print any errors for individual items (this is optional, but useful for debugging)
                    System.out.println("Error loading item ID " + itemId + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
