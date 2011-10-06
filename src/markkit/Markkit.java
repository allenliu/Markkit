package markkit;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class Markkit extends JavaPlugin {

    public static final String VERSION = "0.1";

    protected static Configuration CONFIG;

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

        CONFIG = getConfiguration();        
        getCommand("market").setExecutor(commandExecutor);
        registerEvents();
    }

    private void registerEvents() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
        pluginManager.registerEvent(Event.Type.SIGN_CHANGE, blockListener, Event.Priority.Normal, this);
    }

}
