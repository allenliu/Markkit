package markkit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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
            if(createMarket(player.getInventory())) {
                event.setLine(0, ChatColor.DARK_AQUA + "[Market]");
                player.sendMessage(ChatColor.DARK_AQUA + "Successfully established a market.");
            } else {
                event.getBlock().setType(Material.AIR);
                ItemStack item = new ItemStack(Material.SIGN, 1);
                player.getWorld().dropItemNaturally(player.getLocation(), item);
                player.sendMessage(ChatColor.RED + "Insufficient resources to establish a market.");
            }            
        }
    }
    
    private boolean createMarket(Inventory inventory) {
        return true;
    }
}
