package startrealms.cc.totemofkeeping.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import startrealms.cc.totemofkeeping.Totemofkeeping;
import startrealms.cc.totemofkeeping.util.TotemItem;

import java.util.Map;

public class TotemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("totem.admin")) {
            sender.sendMessage("§cNo permission.");
            return true;
        }


        if (command.getName().equalsIgnoreCase("totemgive")) {

            if (args.length < 1) {
                sender.sendMessage("§cUsage: /totemgive <player> [amount]");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("§cPlayer not found.");
                return true;
            }

            int amount = 1;

            if (args.length >= 2) {
                try {
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§cInvalid amount.");
                    return true;
                }
            }

            ItemStack totem = TotemItem.createTotem();
            totem.setAmount(amount);

            Map<Integer, ItemStack> leftover = target.getInventory().addItem(totem);

            if (!leftover.isEmpty()) {
                leftover.values().forEach(item ->
                        target.getWorld().dropItemNaturally(target.getLocation(), item)
                );
            }

            sender.sendMessage("§aGave " + amount + " Totem(s) to " + target.getName());
            target.sendMessage("§dYou received " + amount + " Totem of Keeping!");

            return true;
        }

        if (command.getName().equalsIgnoreCase("totemreload")) {

            Totemofkeeping.getInstance().reloadConfig();
            sender.sendMessage("§aTotemOfKeeping config reloaded.");

            return true;
        }

        return false;
    }
}