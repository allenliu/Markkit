package markkit;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

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
                            player.sendMessage("trade rates for " + material);
                        } else {
                            player.sendMessage("would be trading " + material);
                        }
                    }
                }
            }
        }
    }
}
