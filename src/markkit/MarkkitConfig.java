package markkit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.util.config.Configuration;

public class MarkkitConfig {

    private Configuration config;
    public static Map<Material, Integer> cost = new HashMap<Material, Integer>();
    public static Map<Material, Integer> trade = new HashMap<Material, Integer>();
    
    public MarkkitConfig(File configFile) {
        config = new Configuration(configFile);
        
        cost.put(Material.WOOD, 32);
        cost.put(Material.DIAMOND, 4);
        cost.put(Material.IRON_INGOT, 16);
        cost.put(Material.GOLD_INGOT, 8);
        
        
        trade.put(Material.DIRT, 0);
        trade.put(Material.COBBLESTONE, 500);
        trade.put(Material.SAND, 200);
        trade.put(Material.GRAVEL, 0);
        
        if (!configFile.exists()) {
            for (Material key : cost.keySet()) {
                config.setProperty("cost." + key.toString(), cost.get(key));
            }
            for (Material key : trade.keySet()) {
                config.setProperty("trade." + key.toString(), trade.get(key));
            }
            config.save();
        } else {
            config.load();
            for (String key : config.getKeys("cost")) {
                cost.put(Material.getMaterial(key), config.getInt("cost." + key, 0));
            }
            System.out.println(cost);
            for (String key : config.getKeys("trade")) {
                trade.put(Material.getMaterial(key), config.getInt("trade." + key, 0));
            }
            System.out.println(trade);
        }
    }
}
