package markkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import markkit.util.InventoryUtils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MarkkitBlockListener extends BlockListener {

    public static Markkit plugin;

    public MarkkitBlockListener(Markkit instance) {
        plugin = instance;
    }
    
    public void onSignChange(SignChangeEvent event) {
        String signText = event.getLine(0).trim();
        if (signText.equalsIgnoreCase("[market]")) {
            Player player = event.getPlayer();
            if(createMarket(player)) {              
                event.setLine(0, Markkit.MARKET_SIGN);
                Location eventLocation = event.getBlock().getLocation();
                eventLocation.setY(eventLocation.getY() - 1);
                Block chestBlock = eventLocation.getBlock();
                if (chestBlock.getType() != Material.CHEST) {
                    chestBlock.setType(Material.CHEST);
                }
                player.sendMessage(ChatColor.DARK_AQUA + "Successfully established a market.");
            } else {
                event.getBlock().setType(Material.AIR);
                ItemStack item = new ItemStack(Material.SIGN, 1);
                player.getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
            }
        }
    }
    
    private boolean createMarket(Player player) {
        Inventory inventory = player.getInventory();
        Map<Material, Integer> missingMaterials = new HashMap<Material, Integer>();
        boolean missing = InventoryUtils.hasMaterials(inventory, MarkkitConfig.cost, missingMaterials);
        if (missing) {
            player.sendMessage(ChatColor.RED + "Insufficient resources to establish a market.");
            
            StringBuilder out = new StringBuilder(ChatColor.RED + "You are short");
            List<Material> list = new ArrayList<Material>();
            for (Material material : missingMaterials.keySet()) {
                if (missingMaterials.get(material) < 0) {
                    list.add(material);
                }
            }
           
            for (int i = 0; i < list.size() - 1; i++) {
                Material material = list.get(i);
                out.append(" ").append(Math.abs(missingMaterials.get(material))).append(" ").append(material).append(",");
            }
            out.append(" and ").append(Math.abs(missingMaterials.get(list.get(list.size() - 1)))).append(" ").append(list.get(list.size() - 1)).append(".");
       
            player.sendMessage(out.toString());
            return false;
        }
        for (Material material : MarkkitConfig.cost.keySet()) {
            player.getInventory().removeItem(new ItemStack(material, MarkkitConfig.cost.get(material)));  
        }
        return true;
    }
}
