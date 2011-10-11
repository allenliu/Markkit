package markkit;

import markkit.util.MaterialUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MarkkitCommandExecutor implements CommandExecutor {

    public static Markkit plugin;

    public MarkkitCommandExecutor(Markkit instance) {
        plugin = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("market")) {
            Material material = MaterialUtils.findMaterial(args[0]);
            if (material == null) {
                sender.sendMessage(ChatColor.RED + args[0] + " is not a valid resource.");
                return true;
            }
            TradeTuple trade = Markkit.getRate(material);
            if (trade == null) {
                sender.sendMessage(ChatColor.RED + MaterialUtils.cleanName(material) + " can't be traded.");
                return true;
            }
            if (sender instanceof Player) {
                Markkit.playerTrade.put((Player) sender, material);
                sender.sendMessage(ChatColor.YELLOW + "You will now trade for " + MaterialUtils.cleanName(material) + ".");
            }
            int gold = Math.round(trade.gold);
            int other = Math.round(trade.other);
            if (trade.value > 0) {
                other = Math.round((float) 0.95 * trade.other);
            } else if (trade.value < 0) {
                gold = Math.round((float) 1.05 * trade.gold);
            }
            sender.sendMessage(ChatColor.YELLOW + "Trade " + gold + " gold ingot for " + other + " " + MaterialUtils.cleanName(material) + ".");
            if (trade.value > 0) {
                other = Math.round((float) 1.05 * trade.other);
            } else if (trade.value < 0) {
                gold = Math.round((float) 0.95 * trade.gold);
            }
            sender.sendMessage(ChatColor.YELLOW + "Trade " + other + " " + MaterialUtils.cleanName(material) + " for " + gold + " gold ingot.");
            return true;
        }
        return false;
    }

}
