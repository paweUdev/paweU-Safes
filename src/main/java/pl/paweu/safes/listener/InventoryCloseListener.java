package pl.paweu.safes.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import pl.paweu.safes.config.Settings;
import pl.paweu.safes.manager.SafeManager;
import pl.paweu.safes.object.Safe;

import static pl.paweu.safes.util.StringUtil.*;
import static pl.paweu.safes.util.IntegerUtil.*;

public class InventoryCloseListener implements Listener {

    private final SafeManager safeManager;
    private final Settings settings;

    public InventoryCloseListener(SafeManager safeManager, Settings settings){
        this.safeManager = safeManager;
        this.settings = settings;

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Inventory inventory = event.getInventory();
        InventoryView inventoryView = event.getView();
        String title = inventoryView.getTitle();
        String config = getStringWithoutInteger(ChatColor.stripColor(this.settings.safeItemName.replace("{id}", "")));
        if(!title.contains(config)) return;
        int id = getIntegerFromString(ChatColor.stripColor(title));
        Safe safe = this.safeManager.getSafeFromId(id);
        safe.setItems(inventory.getContents());
    }
}
