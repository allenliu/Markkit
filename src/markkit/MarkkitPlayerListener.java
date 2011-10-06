package markkit;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
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
        BlockState state = block.getState();
        if (state instanceof Sign) {
            player.sendMessage(ChatColor.DARK_AQUA + "clicked on " + block.toString());
            player.sendMessage(event.getAction().toString()); 
        }
    }
}
