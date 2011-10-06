package markkit;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Markkit extends JavaPlugin {

    
    public Logger log = Logger.getLogger("Minecraft");
    
    @Override
    public void onDisable() {
        log.info("Markkit - Plugin Disabled.");        
    }

    @Override
    public void onEnable() {
        log.info("Markkit - Plugin Enabled.");
    }

}
