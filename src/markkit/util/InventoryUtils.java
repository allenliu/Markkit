package markkit.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

    public static boolean hasMaterials(Inventory inventory, Map<Material, Integer> materials, Map<Material, Integer> missingMaterials) {
        boolean missing = false;
        for (Material material : materials.keySet()) {
            int difference = getMaterialAmount(inventory, material) - materials.get(material);
            missingMaterials.put(material, difference);
            if (difference < 0) {
                missing = true;
            }
        }
        return missing;
    }
    
    public static int getMaterialAmount(Inventory inventory, Material material) {
        Map<Integer, ? extends ItemStack> slots = inventory.all(material);
        int amount = 0;
        for (ItemStack itemStack : slots.values()) {
            amount += itemStack.getAmount();
        }
        return amount;
    }

    public static Map<Material, Integer> getMaterialDifference(Inventory inventory, Map<Material, Integer> materials) {
        Map<Material, Integer> differences = new HashMap<Material, Integer>();
        for (Material material : materials.keySet()) {
            differences.put(material, getMaterialAmount(inventory, material) - materials.get(material));
        }
        return differences;
    }

    public static boolean containsSufficientMaterials(Inventory inventory, Map<Material, Integer> materials) {
        Map<Material, Integer> differences = getMaterialDifference(inventory, materials);
        for (Integer amount : differences.values()) {
            if (amount < 0) {
                return false;
            }
        }
        return true;
    }

    public static ItemStack[] materialDifference(Map<Material, Integer> materials, ItemStack[] leftovers) {
        ItemStack[] difference = new ItemStack[leftovers.length];
        for (int i = 0; i < leftovers.length; i++) {
            difference[i] = new ItemStack(leftovers[i].getType(), materials.get(leftovers[i].getType()) - leftovers[i].getAmount());
        }
        return difference;
    }
    
    public static boolean addMaterials(Inventory inventory, Map<Material, Integer> materials) {
        ItemStack[] itemStacks = new ItemStack[materials.keySet().size()];
        int i = 0;
        for (Material material : materials.keySet()) {
            itemStacks[i] = new ItemStack(material, materials.get(material));
            i++;
        }
        Map<Integer, ItemStack> leftovers = inventory.addItem(itemStacks);
        if (!leftovers.isEmpty()) {
            itemStacks = new ItemStack[leftovers.size()];
            i = 0;
            for (ItemStack itemStack : leftovers.values()) {
                itemStacks[i] = itemStack;
                i++;
            }
            inventory.removeItem(materialDifference(materials, itemStacks));
            return false;
        }
        return true;
    }
    
    public static boolean removeMaterials(Inventory inventory, Map<Material, Integer> materials) {
        ItemStack[] itemStacks = new ItemStack[materials.keySet().size()];
        int i = 0;
        for (Material material : materials.keySet()) {
            itemStacks[i] = new ItemStack(material, materials.get(material));
            i++;
        }
        Map<Integer, ItemStack> leftovers = inventory.removeItem(itemStacks);
        if (!leftovers.isEmpty()) {
            itemStacks = new ItemStack[leftovers.size()];
            i = 0;
            for (ItemStack itemStack : leftovers.values()) {
                itemStacks[i] = itemStack;
                i++;
            }
            inventory.addItem(materialDifference(materials, itemStacks));
            return false;
        }
        return true;
    }
}
