package markkit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.util.config.Configuration;

public class MarkkitConfig {

    private Configuration config;
    
    public static int totalTrades;
    
    public static Map<Material, Integer> cost = new HashMap<Material, Integer>();
    public static Map<Material, Integer> trade = new HashMap<Material, Integer>();
    public static Map<Material, String[]> nameList = new HashMap<Material, String[]>();
    public static Map<String, Material> commonNames = new HashMap<String, Material>();
    
    public MarkkitConfig(File configFile) {
        config = new Configuration(configFile);
              
        if (!configFile.exists()) {
            setDefaults();
            
            save();
        } else {
            config.load();
            
            totalTrades = config.getInt("stats.totalTrades", 0);
            
            for (String key : config.getKeys("cost")) {
                cost.put(Material.getMaterial(key), config.getInt("cost." + key, 0));
            }
            for (String key : config.getKeys("trade")) {
                trade.put(Material.getMaterial(key), config.getInt("trade." + key, 0));
            }
            for (String key : config.getKeys("alias")) {
                nameList.put(Material.getMaterial(key), config.getStringList("alias." + key, new ArrayList<String>()).toArray(new String[] {}));
            }
        }
        
        for (Material material : nameList.keySet()) {
            String[] names = nameList.get(material);
            for (int i = 0; i < names.length; i++) {
                commonNames.put(names[i], material);
            }
        }
    }
    
    public void save() {
        config.setProperty("stats.totalTrades", totalTrades);
        
        for (Material key : cost.keySet()) {
            config.setProperty("cost." + key.toString(), cost.get(key));
        }
        for (Material key : trade.keySet()) {
            config.setProperty("trade." + key.toString(), trade.get(key));
        }
        for (Material key : nameList.keySet()) {
            config.setProperty("alias." + key.toString(), nameList.get(key));
        }
        config.save();
    }
    
    private void setDefaults() {
        totalTrades = 0;
        
        cost.put(Material.WOOD, 10);
        cost.put(Material.IRON_INGOT, 5);
        cost.put(Material.GOLD_INGOT, 5);      
        
        trade.put(Material.APPLE, 20);
        trade.put(Material.BONE, 20);
        trade.put(Material.BROWN_MUSHROOM, 40);
        trade.put(Material.CACTUS, 60);
        trade.put(Material.CLAY_BALL, 50);
        trade.put(Material.COAL, 50);
        trade.put(Material.COBBLESTONE, 80);
        trade.put(Material.DIAMOND, -20);
        trade.put(Material.DIRT, 75);
        trade.put(Material.EGG, 20);
        trade.put(Material.ENDER_PEARL, 10);
        trade.put(Material.FEATHER, 30);
        trade.put(Material.FLINT, 30);
        trade.put(Material.GLOWSTONE_DUST, 30);
        trade.put(Material.GOLD_RECORD, -20);
        trade.put(Material.GRAVEL, 70);
        trade.put(Material.GREEN_RECORD, -20);
        trade.put(Material.ICE, 10);
        trade.put(Material.INK_SACK, 30);
        trade.put(Material.IRON_INGOT, 20);
        trade.put(Material.LEATHER, 30);
        trade.put(Material.LEAVES, 70);
        trade.put(Material.MELON, 40);
        trade.put(Material.MOSSY_COBBLESTONE, 10);
        trade.put(Material.NETHERRACK, 40);
        trade.put(Material.OBSIDIAN, 0);
        trade.put(Material.PORK, 20);
        trade.put(Material.PUMPKIN, 40);
        trade.put(Material.RAW_BEEF, 20);
        trade.put(Material.RAW_CHICKEN, 20);
        trade.put(Material.RAW_FISH, 20);
        trade.put(Material.REDSTONE, 40);
        trade.put(Material.RED_MUSHROOM, 40);
        trade.put(Material.RED_ROSE, 30);
        trade.put(Material.ROTTEN_FLESH, 50);
        trade.put(Material.SADDLE, -30);
        trade.put(Material.SAND, 50);
        trade.put(Material.SAPLING, 60);
        trade.put(Material.SLIME_BALL, 10);
        trade.put(Material.SNOW_BALL, 50);
        trade.put(Material.SOUL_SAND, 20);
        trade.put(Material.STRING, 10);
        trade.put(Material.SUGAR_CANE, 70);
        trade.put(Material.SULPHUR, 10);
        trade.put(Material.VINE, 70);
        trade.put(Material.WEB, 10);
        trade.put(Material.WHEAT, 70);
        trade.put(Material.WOOD, 70);
        trade.put(Material.WOOL, 30);
        trade.put(Material.YELLOW_FLOWER, 30);

        nameList.put(Material.BROWN_MUSHROOM , new String[] {"brownmushroom"});
        nameList.put(Material.CLAY_BALL, new String[] {"clay", "clayball"});
        nameList.put(Material.COBBLESTONE, new String[] {"cobble"});
        nameList.put(Material.ENDER_PEARL, new String[] {"enderpearl"});
        nameList.put(Material.GLOWSTONE_DUST, new String[] {"glowstone", "brightstone", "glowstonedust"});
        nameList.put(Material.GOLD_RECORD, new String[] {"goldrecord", "record"});
        nameList.put(Material.GREEN_RECORD, new String[] {"greenrecord"});
        nameList.put(Material.INK_SACK, new String[] {"ink", "inksack"});
        nameList.put(Material.IRON_INGOT, new String[] {"iron", "ironingot"});
        nameList.put(Material.MOSSY_COBBLESTONE, new String[] {"mossy", "mossycobble", "mossycobblestone"});
        nameList.put(Material.RAW_BEEF, new String[] {"beef"});
        nameList.put(Material.RAW_CHICKEN, new String[] {"chicken"});
        nameList.put(Material.RAW_FISH, new String[] {"fish"});
        nameList.put(Material.REDSTONE, new String[] {"redstonedust"});
        nameList.put(Material.RED_MUSHROOM, new String[] {"redmushroom", "mushroom"});
        nameList.put(Material.RED_ROSE, new String[] {"rose", "redrose"});
        nameList.put(Material.ROTTEN_FLESH, new String[] {"flesh", "rottingflesh", "zombiemeat", "zombieflesh", "rottenflesh"});
        nameList.put(Material.SADDLE, new String[] {"pigsaddle"});
        nameList.put(Material.SLIME_BALL, new String[] {"slime", "slimeball"});
        nameList.put(Material.SNOW_BALL, new String[] {"snow", "snowball"});
        nameList.put(Material.SOUL_SAND, new String[] {"soulsand"});
        nameList.put(Material.SUGAR_CANE, new String[] {"reed", "cane", "sugarcane"});
        nameList.put(Material.YELLOW_FLOWER, new String[] {"flower", "yellowflower"});
    }
}
