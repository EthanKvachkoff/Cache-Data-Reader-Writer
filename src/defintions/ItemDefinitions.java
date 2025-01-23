package defintions;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import alex.io.InputStream;
import alex.io.OutputStream;
import alex.store.Index;
import alex.utils.Constants;
import cache.Cache;
import cache.Utils;

@SuppressWarnings("unused")
public final class ItemDefinitions {

    private static final ItemDefinitions[] itemsDefinitions;

    static { // that's why this is here
        itemsDefinitions = new ItemDefinitions[Utils.getItemDefinitionsSize()];
    }

    public int id;
    private boolean loaded;

    public int modelId;
    private String name;

    // model size information
    private int modelZoom;
    private int modelRotation1;
    private int modelRotation2;
    private int modelOffset1;
    private int modelOffset2;

    // extra information
    private int stackable;
    private int value;
    private boolean membersOnly;

    // wearing model information
    private int maleEquip1;
    private int femaleEquip1;
    private int maleEquip2;
    private int femaleEquip2;

    // options
    private String[] groundOptions;
    public String[] inventoryOptions;

    // model information
    public int[] originalModelColors;
    public int[] modifiedModelColors;
    public short[] originalTextureColors;
    private short[] modifiedTextureColors;
    private byte[] unknownArray1;
    private int[] unknownArray2;
    // extra information, not used for newer items
    private boolean unnoted;

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

    // extra added
    private boolean noted;
    private boolean lended;

    private HashMap<Integer, Object> clientScriptData;
    private HashMap<Integer, Integer> itemRequiriments;

    public static ItemDefinitions getItemDefinitions(int itemId) {
        if (itemId < 0 || itemId >= itemsDefinitions.length)
            itemId = 0;
        ItemDefinitions def = itemsDefinitions[itemId];
        if (def == null)
            itemsDefinitions[itemId] = def = new ItemDefinitions(itemId);
        return def;
    }

    public static void clearItemsDefinitions() {
        for (int i = 0; i < itemsDefinitions.length; i++)
            itemsDefinitions[i] = null;
    }

    public ItemDefinitions(int id) {
        this.id = id;
        setDefaultsVariableValues();
        setDefaultOptions();
        loadItemDefinitions();
    }

    public boolean isLoaded() {
        return loaded;
    }

    private void loadItemDefinitions() {
        byte[] data = Cache.STORE.getIndexes()[Constants.ITEM_DEFINITIONS_INDEX].getFile(getArchiveId(), getFileId());
        if (data == null) {
            //System.out.println("Failed loading Item " + id+".");
            return;
        }
        readOpcodeValues(new InputStream(data));	
        if (certTemplateId != -1)
            //toNote();
        if (lendTemplateId != -1)
            toLend();
        if (unknownValue1 != -1)
            //toLendBind();
        loaded = true;
    }

    /*private void toNote() {
        // ItemDefinitions noteItem; //certTemplateId
        ItemDefinitions realItem = getItemDefinitions(certId);
        membersOnly = realItem.membersOnly;
        value = realItem.value;
        name = realItem.name;
        stackable = 1;
        noted = true;
    }*/

    public static ItemDefinitions forName(String name) {
        for (ItemDefinitions definition : itemsDefinitions) {
            if (definition.name.equalsIgnoreCase(name)) {
                return definition;
            }
        }
        return null;
    }

    private void toLendBind() {
        //ItemDefinitions lendItem; //lendTemplateId
        ItemDefinitions realItem = getItemDefinitions(unknownValue2);
        originalModelColors = realItem.originalModelColors;
        maleEquipModelId3 = realItem.maleEquipModelId3;
        femaleEquipModelId3 = realItem.femaleEquipModelId3;
        teamId = realItem.teamId;
        value = 0;
        membersOnly = realItem.membersOnly;
        name = realItem.name;
        inventoryOptions = new String[5];
        groundOptions = realItem.groundOptions;
        if (realItem.inventoryOptions != null)
            for (int optionIndex = 0; optionIndex < 4; optionIndex++)
                inventoryOptions[optionIndex] = realItem.inventoryOptions[optionIndex];
        inventoryOptions[4] = "Discard";
        maleEquip1 = realItem.maleEquip1;
        maleEquip2 = realItem.maleEquip2;
        femaleEquip1 = realItem.femaleEquip1;
        femaleEquip2 = realItem.femaleEquip2;
        clientScriptData = realItem.clientScriptData;
        lended = true;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private void toLend() {
        ItemDefinitions lendItem; //lendTemplateId
        ItemDefinitions realItem = getItemDefinitions(lendId);
        originalModelColors = realItem.originalModelColors;
        maleEquipModelId3 = realItem.maleEquipModelId3;
        femaleEquipModelId3 = realItem.femaleEquipModelId3;
        teamId = realItem.teamId;
        value = 0;
        membersOnly = realItem.membersOnly;
        name = realItem.name;
        inventoryOptions = new String[5];
        groundOptions = realItem.groundOptions;
        if (realItem.inventoryOptions != null)
            for (int optionIndex = 0; optionIndex < 4; optionIndex++)
                inventoryOptions[optionIndex] = realItem.inventoryOptions[optionIndex];
        inventoryOptions[4] = "Discard";
        maleEquip1 = realItem.maleEquip1;
        maleEquip2 = realItem.maleEquip2;
        femaleEquip1 = realItem.femaleEquip1;
        femaleEquip2 = realItem.femaleEquip2;
        clientScriptData = realItem.clientScriptData;
        lended = true;
    }

    public int getArchiveId() {
        return id >>> 8;
    }

    public int getFileId() {
        return 0xff & id;
    }

    public boolean isDestroyItem() {
        if (inventoryOptions == null)
            return false;
        for (String option : inventoryOptions) {
            if (option == null)
                continue;
            if (option.equalsIgnoreCase("destroy"))
                return true;
        }
        return false;
    }

    public boolean isWearItem() {
        if (inventoryOptions == null)
            return false;
        for (String option : inventoryOptions) {
            if (option == null)
                continue;
            if (option.equalsIgnoreCase("wield")
                    || option.equalsIgnoreCase("wear")
                    || option.equalsIgnoreCase("equip"))
                return true;
        }
        return false;
    }

    public boolean hasSpecialBar() {
        if (clientScriptData == null)
            return false;
        Object specialBar = clientScriptData.get(686);
        return specialBar != null && specialBar instanceof Integer && (Integer) specialBar == 1;
    }

    public int getRenderAnimId() {
        if (clientScriptData == null)
            return 1426;
        Object animId = clientScriptData.get(644);
        if (animId != null && animId instanceof Integer)
            return (Integer) animId;
        return 1426;
    }

    public int getQuestId() {
        if (clientScriptData == null)
            return -1;
        Object questId = clientScriptData.get(861);
        if (questId != null && questId instanceof Integer)
            return (Integer) questId;
        return -1;
    }

    public HashMap<Integer, Integer> getCreateItemRequirements() {
        if (clientScriptData == null)
            return null;
        HashMap<Integer, Integer> items = new HashMap<>();
        int requiredId = -1;
        int requiredAmount = -1;
        for (Map.Entry<Integer, Object> entry : clientScriptData.entrySet()) {
            int key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String)
                continue;
            if (key >= 538 && key <= 770) {
                if (key % 2 == 0)
                    requiredId = (Integer) value;
                else
                    requiredAmount = (Integer) value;
                if (requiredId != -1 && requiredAmount != -1) {
                    items.put(requiredAmount, requiredId);
                    requiredId = -1;
                    requiredAmount = -1;
                }
            }
        }
        return items;
    }

    public HashMap<Integer, Object> getClientScriptData() {
        return clientScriptData;
    }

    /*
     * public HashMap<Integer, Integer> getWearingSkillRequiriments() { if
     * (clientScriptData == null) return null; HashMap<Integer, Integer> skills
     * = new HashMap<Integer, Integer>(); int nextLevel = -1; int nextSkill =
     * -1; for (int key : clientScriptData.keySet()) { Object value =
     * clientScriptData.get(key); if (value instanceof String) continue; if(key
     * == 277) { skills.put((Integer) value, id == 19709 ? 120 : 99); }else if
     * (key == 23 && id == 15241) { skills.put(4, (Integer) value);
     * skills.put(11, 61); } else if (key >= 749 && key < 797) { if (key % 2 ==
     * 0) nextLevel = (Integer) value; else nextSkill = (Integer) value; if
     * (nextLevel != -1 && nextSkill != -1) { skills.put(nextSkill, nextLevel);
     * nextLevel = -1; nextSkill = -1; } }
     *
     * } return skills; }
     */

    private void setDefaultOptions() {
        groundOptions = new String[]{null, null, "take", null, null};
        inventoryOptions = new String[]{null, null, null, null, "drop"};
    }

    private void setDefaultsVariableValues() {
        name = "null";
        maleEquip1 = -1;
        maleEquip2 = -1;
        femaleEquip1 = -1;
        femaleEquip2 = -1;
        modelZoom = 2000;
        lendId = -1;
        lendTemplateId = -1;
        certId = -1;
        certTemplateId = -1;
        unknownInt9 = 128;
        value = 1;
        maleEquipModelId3 = -1;
        femaleEquipModelId3 = -1;
        unknownValue1 = -1;
        unknownValue2 = -1;

    }
	
    int opcode18;

    private final void readValues(InputStream stream, int opcode) {
		//System.out.println(opcode);
		if (opcode == 1){
			modelId = stream.readBigSmart();
			//System.out.println("opcode1 = " + modelId);
		}	
		else if (opcode == 2){
			name = stream.readString();
			//System.out.println(name);
		}	
		else if (opcode == 4){
			modelZoom = stream.readUnsignedShort();
			//System.out.println("opcode4 = " + modelZoom);
		}
		else if (opcode == 5){
			modelRotation1 = stream.readUnsignedShort();
			//System.out.println("opcode5 = " + modelRotation1);
		}
		else if (opcode == 6){
			modelRotation2 = stream.readUnsignedShort();
			//System.out.println("opcode6 = " + modelRotation1);
		}
		else if (opcode == 7) {
			modelOffset1 = stream.readUnsignedShort();
			if (modelOffset1 > 32767)
				modelOffset1 -= 65536;
			modelOffset1 <<= 0;
		} else if (opcode == 8) {
			modelOffset2 = stream.readUnsignedShort();
			if (modelOffset2 > 32767)
				modelOffset2 -= 65536;
			modelOffset2 <<= 0;
		} else if (opcode == 11){
			stackable = 1;
			//System.out.println("opcode11 = " + stackable);
		}	
		else if (opcode == 12){
			value = stream.readInt();
			//System.out.println("opcode12 = " + value);
		}	
		else if (opcode == 117)
			opcode117 = stream.readUnsignedByte();
		else if (opcode == 211)
			opcode211 = stream.readUnsignedByte();
		else if (opcode == 255)
			opcode255 = stream.readUnsignedByte();
		else if (opcode == 75)
			opcode75 = stream.readUnsignedByte();
		else if (opcode == 87)
			opcode87 = stream.readUnsignedByte();
		else if (opcode == 68)
			opcode68 = stream.readUnsignedByte();
		else if (opcode == 118)
			opcode118 = stream.readUnsignedByte();
		else if (opcode == 83)
			opcode83 = stream.readUnsignedByte();
		else if (opcode == 254)
			opcode254 = stream.readUnsignedByte();
		else if (opcode == 156)
			opcode156 = stream.readUnsignedByte();
		else if (opcode == 232)
			opcode232 = stream.readUnsignedByte();
		else if (opcode == 199)
			opcode199 = stream.readUnsignedByte();
		else if (opcode == 253)
			opcode253 = stream.readUnsignedByte();
		else if (opcode == 223)
			opcode223 = stream.readUnsignedByte();
		else if (opcode == 198)
			opcode198 = stream.readUnsignedByte();
		else if (opcode == 186)
			opcode186 = stream.readUnsignedByte();
		else if (opcode == 29)
			opcode29 = stream.readUnsignedByte();
		else if (opcode == 238)
			opcode238 = stream.readUnsignedByte();
		else if (opcode == 153)
			opcode153 = stream.readUnsignedByte();
		else if (opcode == 155)
			opcode155 = stream.readUnsignedByte();
		else if (opcode == 99)
			opcode99 = stream.readUnsignedByte();
		else if (opcode == 251)
			opcode251 = stream.readUnsignedByte();
		else if (opcode == 22)
			opcode22 = stream.readUnsignedByte();
		else if (opcode == 192)
			opcode192 = stream.readUnsignedByte();
		else if (opcode == 245)
			opcode245 = stream.readUnsignedByte();
		else if (opcode == 45)
			opcode45 = stream.readUnsignedByte();
		else if (opcode == 56)
			opcode56 = stream.readUnsignedByte();
		else if (opcode == 248)
			opcode248 = stream.readUnsignedByte();
		else if (opcode == 237)
			opcode237 = stream.readUnsignedByte();
		else if (opcode == 243)
			opcode243 = stream.readUnsignedByte();
		else if (opcode == 185)
			opcode185 = stream.readUnsignedByte();
		else if (opcode == 221)
			opcode221 = stream.readUnsignedByte();
		else if (opcode == 240)
			opcode240 = stream.readUnsignedByte();
		else if (opcode == 154)
			opcode154 = stream.readUnsignedByte();
		else if (opcode == 158)
			opcode158 = stream.readUnsignedByte();
		else if (opcode == 137)
			opcode137 = stream.readUnsignedByte();
		else if (opcode == 143)
			opcode143 = stream.readUnsignedByte();
		else if (opcode == 61)
			opcode61 = stream.readUnsignedByte();
		else if (opcode == 80)
			opcode80 = stream.readUnsignedByte();
		else if (opcode == 196)
			opcode196 = stream.readUnsignedByte();
		else if (opcode == 85)
			opcode85 = stream.readUnsignedByte();
		else if (opcode == 239)
			opcode239 = stream.readUnsignedByte();
		else if (opcode == 177)
			opcode177 = stream.readUnsignedByte();
		else if (opcode == 163)
			opcode163 = stream.readUnsignedByte();
		else if (opcode == 150)
			opcode150 = stream.readUnsignedByte();
		else if (opcode == 152)
			opcode152 = stream.readUnsignedByte();
		else if (opcode == 135)
			opcode135 = stream.readUnsignedByte();
		else if (opcode == 120)
			opcode120 = stream.readUnsignedByte();
		else if (opcode == 204)
			opcode204 = stream.readUnsignedByte();
		else if (opcode == 81)
			opcode81 = stream.readUnsignedByte();
		else if (opcode == 208)
			opcode208 = stream.readUnsignedByte();
		else if (opcode == 242)
			opcode242 = stream.readUnsignedByte();
		else if (opcode == 15)
			opcode15 = stream.readUnsignedByte();
		else if (opcode == 233)
			opcode233 = stream.readUnsignedByte();
		else if (opcode == 213)
			opcode213 = stream.readUnsignedByte();
		else if (opcode == 207)
			opcode207 = stream.readUnsignedByte();
		else if (opcode == 216)
			opcode216 = stream.readUnsignedByte();
		else if (opcode == 206)
			opcode206 = stream.readUnsignedByte();
		else if (opcode == 50)
			opcode50 = stream.readUnsignedByte();
		else if (opcode == 193)
			opcode193 = stream.readUnsignedByte();
		else if (opcode == 71)
			opcode71 = stream.readUnsignedByte();
		else if (opcode == 10)
			opcode10 = stream.readUnsignedByte();
		else if (opcode == 55)
			opcode55 = stream.readUnsignedByte();
		else if (opcode == 144)
			opcode144 = stream.readUnsignedByte();
		else if (opcode == 235)
			opcode235 = stream.readUnsignedByte();
		else if (opcode == 188)
			opcode188 = stream.readUnsignedByte();
		else if (opcode == 241)
			opcode241 = stream.readUnsignedByte();
		else if (opcode == 236)
			opcode236 = stream.readUnsignedByte();
		else if (opcode == 182)
			opcode182 = stream.readUnsignedByte();
		else if (opcode == 169)
			opcode169 = stream.readUnsignedByte();
		else if (opcode == 190)
			opcode190 = stream.readUnsignedByte();
		else if (opcode == 178)
			opcode178 = stream.readUnsignedByte();
		else if (opcode == 88)
			opcode88 = stream.readUnsignedByte();
		else if (opcode == 200)
			opcode200 = stream.readUnsignedByte();
		else if (opcode == 184)
			opcode184 = stream.readUnsignedByte();
		else if (opcode == 176)
			opcode176 = stream.readUnsignedByte();
		else if (opcode == 197)
			opcode197 = stream.readUnsignedByte();
		else if (opcode == 247)
			opcode247 = stream.readUnsignedByte();
		else if (opcode == 218)
			opcode218 = stream.readUnsignedByte();
		else if (opcode == 250)
			opcode250 = stream.readUnsignedByte();
		else if (opcode == 174)
			opcode174 = stream.readUnsignedByte();
		else if (opcode == 210)
			opcode210 = stream.readUnsignedByte();
		else if (opcode == 164)
			opcode164 = stream.readUnsignedByte();
		else if (opcode == 142)
			opcode142 = stream.readUnsignedByte();
		else if (opcode == 148)
			opcode148 = stream.readUnsignedByte();
		else if (opcode == 133)
			opcode133 = stream.readUnsignedByte();
		else if (opcode == 222)
			opcode222 = stream.readUnsignedByte();
		else if (opcode == 138)
			opcode138 = stream.readUnsignedByte();
		else if (opcode == 194)
			opcode194 = stream.readUnsignedByte();
		else if (opcode == 119)
			opcode119 = stream.readUnsignedByte();
		else if (opcode == 202)
			opcode202 = stream.readUnsignedByte();
		else if (opcode == 149)
			opcode149 = stream.readUnsignedByte();
		else if (opcode == 64)
			opcode64 = stream.readUnsignedByte();
		else if (opcode == 147)
			opcode147 = stream.readUnsignedByte();
		else if (opcode == 214)
			opcode214 = stream.readUnsignedByte();
		else if (opcode == 74)
			opcode74 = stream.readUnsignedByte();
		else if (opcode == 86)
			opcode86 = stream.readUnsignedByte();
		else if (opcode == 167)
			opcode167 = stream.readUnsignedByte();
		else if (opcode == 161)
			opcode161 = stream.readUnsignedByte();
		else if (opcode == 58)
			opcode58 = stream.readUnsignedByte();
		else if (opcode == 59)
			opcode59 = stream.readUnsignedByte();
		else if (opcode == 187)
			opcode187 = stream.readUnsignedByte();
		else if (opcode == 77)
			opcode77 = stream.readUnsignedByte();
		else if (opcode == 229)
			opcode229 = stream.readUnsignedByte();
		else if (opcode == 230)
			opcode230 = stream.readUnsignedByte();
		else if (opcode == 17)
			opcode17 = stream.readUnsignedByte();
		else if (opcode == 67)
			opcode67 = stream.readUnsignedByte();
		else if (opcode == 131)
			opcode131 = stream.readUnsignedByte();
		else if (opcode == 225)
			opcode225 = stream.readUnsignedByte();
		else if (opcode == 203)
			opcode203 = stream.readUnsignedByte();
		else if (opcode == 19)
			opcode19 = stream.readUnsignedByte();
		else if (opcode == 43)
			opcode43 = stream.readUnsignedByte();
		else if (opcode == 168)
			opcode168 = stream.readUnsignedByte();
		else if (opcode == 46)
			opcode46 = stream.readUnsignedByte();
		else if (opcode == 209)
			opcode209 = stream.readUnsignedByte();
		else if (opcode == 166)
			opcode166 = stream.readUnsignedByte();
		else if (opcode == 54)
			opcode54 = stream.readUnsignedByte();
		else if (opcode == 21)
			opcode21 = stream.readUnsignedByte();
		else if (opcode == 73)
			opcode73 = stream.readUnsignedByte();
		else if (opcode == 159)
			opcode159 = stream.readUnsignedByte();
		else if (opcode == 123)
			opcode123 = stream.readUnsignedByte();
		else if (opcode == 146)
			opcode146 = stream.readUnsignedByte();
		else if (opcode == 180)
			opcode180 = stream.readUnsignedByte();
		else if (opcode == 20)
			opcode20 = stream.readUnsignedByte();
		else if (opcode == 165)
			opcode165 = stream.readUnsignedByte();
		else if (opcode == 84)
			opcode84 = stream.readUnsignedByte();
		else if (opcode == 28)
			opcode28 = stream.readUnsignedByte();
		else if (opcode == 175)
			opcode175 = stream.readUnsignedByte();
		else if (opcode == 141)
			opcode141 = stream.readUnsignedByte();
		else if (opcode == 205)
			opcode205 = stream.readUnsignedByte();
		else if (opcode == 220)
			opcode220 = stream.readUnsignedByte();
		else if (opcode == 136)
			opcode136 = stream.readUnsignedByte();
		else if (opcode == 212)
			opcode212 = stream.readUnsignedByte();
		else if (opcode == 49)
			opcode49 = stream.readUnsignedByte();
		else if (opcode == 69)
			opcode69 = stream.readUnsignedByte();
		else if (opcode == 72)
			opcode72 = stream.readUnsignedByte();
		else if (opcode == 60)
			opcode60 = stream.readUnsignedByte();
		else if (opcode == 62)
			opcode62 = stream.readUnsignedByte();
		else if (opcode == 219)
			opcode219 = stream.readUnsignedByte();
		else if (opcode == 44)
			opcode44 = stream.readUnsignedByte();
		else if (opcode == 227)
			opcode227 = stream.readUnsignedByte();
		else if (opcode == 76)
			opcode76 = stream.readUnsignedByte();
		else if (opcode == 234)
			opcode234 = stream.readUnsignedByte();
		else if (opcode == 57)
			opcode57 = stream.readUnsignedByte();
		else if (opcode == 51)
			opcode51 = stream.readUnsignedByte();
		else if (opcode == 124)
			opcode124 = stream.readUnsignedByte();
		else if (opcode == 70)
			opcode70 = stream.readUnsignedByte();
		else if (opcode == 231)
			opcode231 = stream.readUnsignedByte();
		else if (opcode == 162)
			opcode162 = stream.readUnsignedByte();
		else if (opcode == 160)
			opcode160 = stream.readUnsignedByte();
		else if (opcode == 181)
			opcode181 = stream.readUnsignedByte();
		else if (opcode == 183)
			opcode183 = stream.readUnsignedByte();
		else if (opcode == 191)
			opcode191 = stream.readUnsignedByte();
		else if (opcode == 189)
			opcode189 = stream.readUnsignedByte();
		else if (opcode == 179)
			opcode179 = stream.readUnsignedByte();
		else if (opcode == 173)
			opcode173 = stream.readUnsignedByte();
		else if (opcode == 48)
			opcode48 = stream.readUnsignedByte();
		else if (opcode == 172)
			opcode172 = stream.readUnsignedByte();
		else if (opcode == 42)
			opcode42 = stream.readUnsignedByte();
		else if (opcode == 47)
			opcode47 = stream.readUnsignedByte();
		else if (opcode == 246)
			opcode246 = stream.readUnsignedByte();
		else if (opcode == 89)
			opcode89 = stream.readUnsignedByte();
		else if (opcode == 195)
			opcode195 = stream.readUnsignedByte();
		else if (opcode == 145)
			opcode145 = stream.readUnsignedByte();
		else if (opcode == 224)
			opcode224 = stream.readUnsignedByte();
		else if (opcode == 63)
			opcode63 = stream.readUnsignedByte();
		else if (opcode == 94)
			opcode94 = stream.readUnsignedByte();
		else if (opcode == 201)
			opcode201 = stream.readUnsignedByte();
		else if (opcode == 217)
			opcode217 = stream.readUnsignedByte();
		else if (opcode == 252)
			opcode252 = stream.readUnsignedByte();
		else if (opcode == 228)
			opcode228 = stream.readUnsignedByte();
		else if (opcode == 82)
			opcode82 = stream.readUnsignedByte();
		else if (opcode == 13)
			opcode13 = stream.readUnsignedByte();
		else if (opcode == 14)
			opcode14 = stream.readUnsignedByte();
		else if (opcode == 9)
			opcode9 = stream.readUnsignedByte();
		else if (opcode == 27)
			opcode27 = stream.readUnsignedByte();
		else if (opcode == 66)
			opcode66 = stream.readUnsignedByte();
		else if (opcode == 116)
			opcode116 = stream.readUnsignedByte();
		else if (opcode == 157)
			opcode157 = stream.readUnsignedByte();
		else if (opcode == 244)
			opcode244 = stream.readUnsignedByte();
		else if (opcode == 53)
			opcode53 = stream.readUnsignedByte();
		else if (opcode == 215)
			opcode215 = stream.readUnsignedByte();
		else if (opcode == 171)
			opcode171 = stream.readUnsignedByte();
		else if (opcode == 3)
			opcode3 = stream.readUnsignedByte();
		else if (opcode == 170)
			opcode170 = stream.readUnsignedByte();
		else if (opcode == 226)
			opcode226 = stream.readUnsignedByte();
		else if (opcode == 52)
			opcode52 = stream.readUnsignedByte();
		else if (opcode == 151)
			opcode151 = stream.readUnsignedByte();//		14 66 116 157 244 170 151 9 27
		else if (opcode == 16){
			membersOnly = true;
			//System.out.println("opcode16 = " + membersOnly);
		}
			
		else if (opcode == 18) // added
			stream.readUnsignedShort();
		else if (opcode == 23)
			maleEquip1 = stream.readBigSmart();
		else if (opcode == 24)
			maleEquip2 = stream.readBigSmart();
		else if (opcode == 25)
			femaleEquip1 = stream.readBigSmart();
		else if (opcode == 26)
			femaleEquip2 = stream.readBigSmart();
		else if (opcode >= 30 && opcode < 35)
			groundOptions[opcode - 30] = stream.readString();
		else if (opcode >= 35 && opcode < 40){
			inventoryOptions[opcode - 35] = stream.readString();
			String invenOptions = inventoryOptions[opcode - 35] = stream.readString();
			//System.out.println("opcode35 = " + invenOptions);
		}
			
		else if (opcode == 40) {
			int length = stream.readUnsignedByte();
			originalModelColors = new int[length];
			modifiedModelColors = new int[length];
			for (int index = 0; index < length; index++) {
				originalModelColors[index] = stream.readUnsignedShort();
				modifiedModelColors[index] = stream.readUnsignedShort();
			}
		} else if (opcode == 41) {
			int length = stream.readUnsignedByte();
			originalTextureColors = new short[length];
			modifiedTextureColors = new short[length];
			for (int index = 0; index < length; index++) {
				originalTextureColors[index] = (short) stream
						.readUnsignedShort();
				modifiedTextureColors[index] = (short) stream
						.readUnsignedShort();
			}
		} else if (opcode == 42) {
			int length = stream.readUnsignedByte();
			unknownArray1 = new byte[length];
			for (int index = 0; index < length; index++)
				unknownArray1[index] = (byte) stream.readByte();
		} else if (opcode == 65){
			unnoted = true;
			//System.out.println("opcode65 = " + unnoted);
		}
		else if (opcode == 78)
			maleEquipModelId3 = stream.readBigSmart();
		else if (opcode == 79)
			femaleEquipModelId3 = stream.readBigSmart();
		else if (opcode == 90)
			unknownInt1 = stream.readBigSmart();
		else if (opcode == 91)
			unknownInt2 = stream.readBigSmart();
		else if (opcode == 92)
			unknownInt3 = stream.readBigSmart();
		else if (opcode == 93)
			unknownInt4 = stream.readBigSmart();
		else if (opcode == 95)
			unknownInt5 = stream.readUnsignedShort();
		else if (opcode == 96)
			unknownInt6 = stream.readUnsignedByte();
		else if (opcode == 97){
			certId = stream.readUnsignedShort();
			//System.out.println("opcode97 = " + certId);
		}
		else if (opcode == 98){
			certTemplateId = stream.readUnsignedShort();
			//System.out.println("opcode98 = " + certTemplateId);
		}
		else if (opcode >= 100 && opcode < 110) {
			if (stackIds == null) {
				stackIds = new int[10];
				stackAmounts = new int[10];
			}
			stackIds[opcode - 100] = stream.readUnsignedShort();
			stackAmounts[opcode - 100] = stream.readUnsignedShort();
		} else if (opcode == 110)
			unknownInt7 = stream.readUnsignedShort();
		else if (opcode == 111)
			unknownInt8 = stream.readUnsignedShort();
		else if (opcode == 112)
			unknownInt9 = stream.readUnsignedShort();
		else if (opcode == 113)
			unknownInt10 = stream.readByte();
		else if (opcode == 114)
			unknownInt11 = stream.readByte() * 5;
		else if (opcode == 115)
			teamId = stream.readUnsignedByte();
		else if (opcode == 121)
			lendId = stream.readUnsignedShort();
		else if (opcode == 122)
			lendTemplateId = stream.readUnsignedShort();
		else if (opcode == 125) {
			unknownInt12 = stream.readByte() << 0;
			unknownInt13 = stream.readByte() << 0;
			unknownInt14 = stream.readByte() << 0;
		} else if (opcode == 126) {
			unknownInt15 = stream.readByte() << 0;
			unknownInt16 = stream.readByte() << 0;
			unknownInt17 = stream.readByte() << 0;
		} else if (opcode == 127) {
			unknownInt18 = stream.readUnsignedByte();
			unknownInt19 = stream.readUnsignedShort();
			//System.out.println("opcode127 = " + unknownInt18);
			//System.out.println("opcode127 = " + unknownInt19);
		} else if (opcode == 128) {
			unknownInt20 = stream.readUnsignedByte();
			unknownInt21 = stream.readUnsignedShort();
		} else if (opcode == 129) {
			unknownInt20 = stream.readUnsignedByte();
			unknownInt21 = stream.readUnsignedShort();
			//System.out.println("opcode129 = " + unknownInt20);
			//System.out.println("opcode129 = " + unknownInt21);
		} else if (opcode == 130) {
			unknownInt22 = stream.readUnsignedByte();
			unknownInt23 = stream.readUnsignedShort();
		} else if (opcode == 132) {
			int length = stream.readUnsignedByte();
			unknownArray2 = new int[length];
			for (int index = 0; index < length; index++)
				unknownArray2[index] = stream.readUnsignedShort();
		} else if (opcode == 134) {
			int unknownValue = stream.readUnsignedByte();
		} else if (opcode == 139) {
			unknownValue2 = stream.readUnsignedShort();
		} else if (opcode == 140) {
			unknownValue1 = stream.readUnsignedShort();
		} else if (opcode == 249) {
			int length = stream.readUnsignedByte();
			if (clientScriptData == null)
				clientScriptData = new HashMap<Integer, Object>(length);
			for (int index = 0; index < length; index++) {
				boolean stringInstance = stream.readUnsignedByte() == 1;
				int key = stream.read24BitInt();
				Object value = stringInstance ? stream.readString() : stream
						.readInt();
				clientScriptData.put(key, value);
			}

		//System.out.println("End of the opcode for this item.");
		} else
			throw new RuntimeException("MISSING OPCODE " + opcode
					+ " FOR ITEM " + id);
	}

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

    private void readOpcodeValues(InputStream stream) {
        while (true) {
            int opcode = stream.readUnsignedByte();
            if (opcode == 0)
                break;
            readValues(stream, opcode);
        }
    }

    public String getName() {
        return name;
    }
	public void setName(String itemName) {
		name = itemName;
    }
	public int getModelZoom() {
        return modelZoom;
    }
	public String[] getInventoryOptions() {
        return inventoryOptions;
    }
	public String[] getGroundOptions() {
        return groundOptions;
    }

	public int[] getStackIds() {
        return stackIds;
    }
	public int[] getStackAmounts() {
        return stackAmounts;
    }
	public int getModelRotation1() {
        return modelRotation1;
    }
	public int getModelRotation2() {
        return modelRotation2;
    }
	public int getModelOffset1() {
        return modelOffset1;
    }
	public int getModelOffset2() {
        return modelOffset2;
    }
    public int getFemaleWornModelId1() {
        return femaleEquip1;
    }

    public int getFemaleWornModelId2() {
        return femaleEquip2;
    }

    public int getMaleWornModelId1() {
        return maleEquip1;
    }

    public int getMaleWornModelId2() {
        return maleEquip2;
    }

	public int getFemaleWornModelId3() {
        return femaleEquipModelId3;
    }

    public int getMaleWornModelId3() {
        return maleEquipModelId3;
    }

    public boolean isLended() {
        return lended;
    }

    public boolean isStackable() {
        return stackable == 1;
    }

    public boolean isNoted() {
        return noted;
    }

    public int getLendId() {
        return lendId;
    }

    public int getCertId() {
        return certId;
    }

    public int getValue() {
        return value;
    }

}