package markkit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Markkit extends JavaPlugin {

    public static final String VERSION = "0.1";
    public static final String MARKET_SIGN = ChatColor.DARK_AQUA + "[Market]";

    public static Map<Player, Integer> playerTrade = new HashMap<Player, Integer>();
    protected static MarkkitConfig config;

    private final MarkkitCommandExecutor commandExecutor = new MarkkitCommandExecutor(this);
    private final MarkkitPlayerListener playerListener = new MarkkitPlayerListener(this);
    private final MarkkitBlockListener blockListener = new MarkkitBlockListener(this);

    public Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onDisable() {
        log.info("Markkit " + VERSION + " disabled.");        
    }

    @Override
    public void onEnable() {
        log.info("Markkit " + VERSION + " enabled.");
        config = new MarkkitConfig(new File(getDataFolder().getAbsolutePath() + File.separator + "config.yml"));
        getCommand("market").setExecutor(commandExecutor);
        registerEvents();
    }
    
    private void registerEvents() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
        pluginManager.registerEvent(Event.Type.SIGN_CHANGE, blockListener, Event.Priority.Normal, this);
    }

}
