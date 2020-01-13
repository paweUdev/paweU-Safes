package pl.paweu.safes.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.paweu.safes.config.Settings;
import pl.paweu.safes.manager.SafeManager;

import static pl.paweu.safes.util.ChatUtil.sendMessage;
import static pl.paweu.safes.util.IntegerUtil.isInteger;

public class SafeCommand implements CommandExecutor {

    private final Settings settings;
    private final SafeManager safeManager;

    public SafeCommand(Settings settings, SafeManager safeManager){
        this.settings = settings;
        this.safeManager = safeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("safes.cmd.admin")) return sendMessage(sender, "&4&lBlad: &cNie posiadasz permisji &8(&6safes.cmd.admin&8)");
        if(args.length != 3) return sendMessage(sender, this.settings.safeCommandUsage);
        if(args[0].equalsIgnoreCase("sejf")){
            String forwho = args[1];
            String amount = args[2];
            if(!isInteger(amount)) return sendMessage(sender, this.settings.integerError);
            final int howmany = Integer.parseInt(amount);
            if(!forwho.equalsIgnoreCase("all")){
                Player target = Bukkit.getPlayer(forwho);
                if(target == null) return sendMessage(sender, this.settings.playerError);
                for(int i = 0; i < howmany; i++) safeManager.createSafe(safeManager.getNextSafeId(), forwho, "&cBrak", new ItemStack[this.settings.safeSize], target);
                return sendMessage(sender, this.settings.givenSafeMessageForSpecificPlayer.replace("{player}", forwho)
                .replace("{amount}", amount));
            } else {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    for (int i = 0; i < howmany; i++)
                        safeManager.createSafe(safeManager.getNextSafeId(), player.getName(), "&cBrak", new ItemStack[this.settings.safeSize], player);
                });
                return sendMessage(sender, this.settings.givenSafeMessageForOnlinePlayers.replace("{amount}", amount));
            }
        }
        if(args[0].equalsIgnoreCase("lom")){
            String forwho = args[1];
            String amount = args[2];
            if(!isInteger(amount)) return sendMessage(sender, this.settings.integerError);
            final int howmany = Integer.parseInt(amount);
            if(!forwho.equalsIgnoreCase("all")){
                Player target = Bukkit.getPlayer(forwho);
                if(target == null) return sendMessage(sender, this.settings.playerError);
                safeManager.createCrowbar(target, howmany);
                return sendMessage(sender, this.settings.givenCrowbarMessageForSpecificPlayer.replace("{player}", forwho)
                        .replace("{amount}", amount));
            } else {
                Bukkit.getOnlinePlayers().forEach(players -> safeManager.createCrowbar(players, howmany));
                return sendMessage(sender, this.settings.givenCrowbarMessageForOnlinePlayers.replace("{amount}", amount));
            }
        }
        return true;
    }
}
