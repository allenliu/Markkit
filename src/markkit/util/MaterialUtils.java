package markkit.util;

import markkit.MarkkitConfig;

import org.bukkit.Material;

public class MaterialUtils {

    public static String cleanName(Material material) {
        return material.toString().toLowerCase().replaceAll("_", " ");
    }
    
    public static Material findMaterial(String name) {
        Material material = MarkkitConfig.commonNames.get(name);
        if (material != null) {
            return material;
        }
        return Material.matchMaterial(name);
    }
    
}
