package pl.paweu.safes.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.paweu.safes.config.Settings;
import pl.paweu.safes.manager.SafeManager;
import pl.paweu.safes.object.Safe;

import static pl.paweu.safes.util.ChatUtil.sendMessage;
import static pl.paweu.safes.util.IntegerUtil.getIntegerFromString;

public class LoreCommand implements CommandExecutor {

    private final Settings settings;
    private final SafeManager safeManager;

    public LoreCommand(Settings settings, SafeManager safeManager){
        this.settings = settings;
        this.safeManager = safeManager;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) 
            return sendMessage(sender, "Tej komendy nie mozna uzyc z poziomu konsoli");
        
        Player player = (Player) sender;
        
        if (!player.hasPermission("safes.cmd.lore")) 
            return sendMessage(player, "&4&lBlad: &cNie posiadasz permisji &8(&6safes.cmd.lore&8)");
        
        if (args.length != 1) 
            return sendMessage(sender, this.settings.loreCommandUsage);
        
        String lore = args[0];
        
        if (lore.length() < 2 || lore.length() > 16) 
            return sendMessage(sender, this.settings.loreLengthError);
        
        ItemStack itemStack = player.getItemInHand();
        
        if (!this.safeManager.isSafe(itemStack)) 
            return sendMessage(player, this.settings.isNotSafeItem);
        
        ItemMeta itemMeta = itemStack.getItemMeta();
        String name = ChatColor.stripColor(itemMeta.getDisplayName());
        int id = getIntegerFromString(name);
        
        Safe safe = this.safeManager.getSafeFromId(id);
        safe.setLore(lore);
        this.safeManager.updateSafe(safe, itemStack);
        
        player.updateInventory();
        return sendMessage(player, this.settings.loreChangeMessage.replace("{lore}", safe.getLore()));
    }
}
