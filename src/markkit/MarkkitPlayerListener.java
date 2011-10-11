package markkit;

import markkit.util.InventoryUtils;
import markkit.util.MaterialUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MarkkitPlayerListener extends PlayerListener {

    public static Markkit plugin;

    public MarkkitPlayerListener(Markkit instance) {
        plugin = instance;
    }
    
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (block != null) {
            BlockState state = event.getClickedBlock().getState();
            if (state instanceof Sign) {
                if (((Sign) state).getLine(0).equals(Markkit.MARKET_SIGN)) {
                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) { 
                        event.setCancelled(true);
                    }
                    Material material = event.getMaterial();
                    if (material != Material.AIR) {
                        if (event.getAction() == Action.LEFT_CLICK_BLOCK) { 
                            player.sendMessage("trade rates for " + MaterialUtils.cleanName(material));
                        } else {
                            if (material == Material.GOLD_INGOT) {
                                tradeFor(player, event);
                            } else {
                                tradeWith(player, material, event);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void onPlayerQuit(PlayerQuitEvent event) {
        Markkit.playerTrade.remove(event.getPlayer());
    }
    
    @SuppressWarnings("deprecation")
    private void tradeFor(Player player, PlayerInteractEvent event) {
        if (Markkit.playerTrade.containsKey(player)) {
            Material material = Markkit.playerTrade.get(player);
            TradeTuple trade = Markkit.getRate(material);
            if (trade == null) {
                // this should never happen because of how playerTrade works
                player.sendMessage(ChatColor.RED + MaterialUtils.cleanName(material) + " can't be traded.");
                return;
            }
            int gold = Math.round(trade.gold);
            int other = Math.round(trade.other);
            if (trade.value > 0) {
                other = Math.round((float) 0.95 * trade.other);
            } else if (trade.value < 0) {
                gold = Math.round((float) 1.05 * trade.gold);
            }
            int goldAmount = InventoryUtils.getMaterialAmount(player.getInventory(), Material.GOLD_INGOT);
            if (gold > goldAmount) {
                player.sendMessage(ChatColor.RED + "You need " + (gold - goldAmount) + " more gold ingot.");
                return;
            }
            player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, gold));
            player.updateInventory();
            player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(material, other));
            player.sendMessage(ChatColor.DARK_AQUA + "You traded " + gold + " gold ingot for " + other + " " + MaterialUtils.cleanName(material) + "!");
            MarkkitConfig.trade.put(material, MarkkitConfig.trade.get(material) - 1);
        } else {
            player.sendMessage(ChatColor.RED + "You don't have a trade resource set.");
        }
    }
    
    @SuppressWarnings("deprecation")
    private void tradeWith(Player player, Material material, PlayerInteractEvent event) {
        
        TradeTuple trade = Markkit.getRate(material);
        if (trade == null) {
            player.sendMessage(ChatColor.RED + MaterialUtils.cleanName(material) + " can't be traded.");
            return;
        }
        int gold = Math.round(trade.gold);
        int other = Math.round(trade.other);
        if (trade.value > 0) {
            other = Math.round((float) 1.05 * trade.other);
        } else if (trade.value < 0) {
            gold = Math.round((float) 0.95 * trade.gold);
        }
        int otherAmount = InventoryUtils.getMaterialAmount(player.getInventory(), material);
        if (other > otherAmount) {
            player.sendMessage(ChatColor.RED + "You need " + (other - otherAmount) + " more " + MaterialUtils.cleanName(material) +  ".");
            return;
        }
        player.getInventory().removeItem(new ItemStack(material, other));
        player.updateInventory();
        player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, gold));
        player.sendMessage(ChatColor.DARK_AQUA + "You traded " + other + " " + MaterialUtils.cleanName(material) + " for " + gold + " gold ingot!");
        MarkkitConfig.trade.put(material, MarkkitConfig.trade.get(material) + 1);
    }
    
}
