package pl.paweu.safes.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.paweu.safes.config.Settings;
import pl.paweu.safes.manager.SafeManager;
import pl.paweu.safes.object.Safe;
import static pl.paweu.safes.util.ItemUtil.*;

import static pl.paweu.safes.util.IntegerUtil.getIntegerFromString;

public class InventoryClickListener implements Listener {

    private final SafeManager safeManager;
    private final Settings settings;

    public InventoryClickListener(SafeManager safeManager, Settings settings){
        this.safeManager = safeManager;
        this.settings = settings;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        if(inventory == null) return;
        InventoryView inventoryView = event.getView();
        String title = inventoryView.getTitle();
        String config = this.settings.crowbarItemName;
        if(!title.equals(config)) return;
        ItemStack itemStack = event.getCurrentItem();
        int slot = event.getSlot();
        if(slot != 31 && !itemStack.getType().equals(Material.AIR) && !this.safeManager.isSafe(itemStack)){
            event.setCancelled(true);
            return;
        }
        Material type = itemStack.getType();
        if(!type.equals(Material.CHEST)) return;
        if(!itemStack.hasItemMeta()) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(!itemMeta.hasDisplayName()) return;
        String name = itemMeta.getDisplayName();
        if(!itemMeta.hasLore()) return;
        if(slot != 31) return;
        int id = getIntegerFromString(ChatColor.stripColor(name));
        Safe safe = this.safeManager.getSafeFromId(id);
        if(safe.getOwner().equals(player.getName())){
            event.setCancelled(true);
            addItem(player, itemStack);
            player.closeInventory();
            player.sendMessage(this.settings.youAreOwnerError);
            return;
        }
        removeItemInHand(player);
        safe.setOwner(event.getWhoClicked().getName());
        this.safeManager.updateSafe(safe, itemStack);
        player.closeInventory();
        addItem(player, itemStack);
        player.sendMessage(this.settings.tookOverTheSafe.replace("{player}", safe.getOwner()));
    }
}
