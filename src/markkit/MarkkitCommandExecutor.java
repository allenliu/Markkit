package markkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MarkkitCommandExecutor implements CommandExecutor {

    public static Markkit plugin;

    public MarkkitCommandExecutor(Markkit instance) {
        plugin = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("market")) {
            sender.sendMessage("command executed");            
        }
        return false;
    }

}
