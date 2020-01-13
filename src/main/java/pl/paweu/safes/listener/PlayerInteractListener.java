package pl.paweu.safes.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.paweu.safes.config.Settings;
import pl.paweu.safes.manager.SafeManager;

import static pl.paweu.safes.util.IntegerUtil.getIntegerFromString;

public class PlayerInteractListener implements Listener {

    private final SafeManager safeManager;
    private final Settings settings;

    public PlayerInteractListener(SafeManager safeManager, Settings settings){
        this.safeManager = safeManager;
        this.settings = settings;
    }

    @EventHandler
    public void onCrowbar(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        Material type = itemStack.getType();
        if(!type.equals(Material.TRIPWIRE_HOOK)) return;
        if(!itemStack.hasItemMeta()) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(!itemMeta.hasDisplayName()) return;
        String name = itemMeta.getDisplayName();
        String config = this.settings.crowbarItemName;
        if(!name.equals(config)) return;
        event.setCancelled(true);
        this.safeManager.openTakeOverSafe(player);
    }

    @EventHandler
    public void onSafe(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        if(!this.safeManager.isSafe(itemStack)) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        String name = ChatColor.stripColor(itemMeta.getDisplayName());
        int id = getIntegerFromString(name);
        event.setCancelled(true);
        this.safeManager.openSafe(player, id);
    }
}
