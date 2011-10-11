package markkit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Markkit extends JavaPlugin {

    public static final String VERSION = "0.8";
    public static final String MARKET_SIGN = ChatColor.DARK_AQUA + "[Market]";

    public static Map<Player, Material> playerTrade = new HashMap<Player, Material>();
    protected static MarkkitConfig config;

    private final MarkkitCommandExecutor commandExecutor = new MarkkitCommandExecutor(this);
    private final MarkkitPlayerListener playerListener = new MarkkitPlayerListener(this);
    private final MarkkitBlockListener blockListener = new MarkkitBlockListener(this);

    public Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onDisable() {
        config.save();
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
        pluginManager.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Normal, this);
    }
    
    public static TradeTuple getRate(Material material) {
        Integer factor = MarkkitConfig.trade.get(material);
        if (factor == null) {
            return null;
        }
        if (factor >= 0) {
            return new TradeTuple((float) 1.0, (float) ((0.3 * factor) + Math.pow(1.08, factor)), factor);
        } else {
            return new TradeTuple((float) ((0.3 * -factor) + Math.pow(1.08, -factor)), (float) 1.0, factor);
        }
    }

}
