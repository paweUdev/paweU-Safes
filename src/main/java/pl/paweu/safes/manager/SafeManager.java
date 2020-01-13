package pl.paweu.safes.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.paweu.safes.builder.ItemBuilder;
import pl.paweu.safes.config.Settings;
import pl.paweu.safes.object.Safe;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import static pl.paweu.safes.util.ChatUtil.fixColor;
import static pl.paweu.safes.util.ItemUtil.addItem;
import static pl.paweu.safes.util.StringUtil.getStringWithoutInteger;

public class SafeManager {

    private final Settings settings;

    private final HashMap<Integer, Safe> safes = new HashMap<>();

    public SafeManager(Settings settings){
        this.settings = settings;
    }

    public void updateSafe(Safe safe, ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(this.settings.safeItemLore.stream().map(s -> fixColor(s.replace("{owner}", safe.getOwner())
                .replace("{lore}", safe.getLore()))).collect(Collectors.toList()));
        itemStack.setItemMeta(itemMeta);
    }

    public void firstJoinGive(Player player){
        if(!player.hasPlayedBefore()) {
            for(int i = 0; i < this.settings.firstJoinSafeAmount; i++)
                this.createSafe(this.getNextSafeId(), player.getName(), "&cBrak", new ItemStack[this.settings.safeSize], player);
            player.sendMessage(this.settings.firstJoinMessage);
        }
    }

    public void createCrowbar(Player player, int amount){
        ItemBuilder crowbar = new ItemBuilder(Material.TRIPWIRE_HOOK).setName(this.settings.crowbarItemName);
        crowbar.setLore(this.settings.crowbarItemLore);
        crowbar.setAmount(amount);
        addItem(player, crowbar.build());
    }

    public void createSafe(int id, String owner, String lore, ItemStack[] itemStacks, Player player){
        Safe safe = new Safe(id, owner, lore, itemStacks);
        if(!this.safes.containsKey(safe.getId())) this.safes.put(safe.getId(), safe);
        ItemBuilder safe1 = new ItemBuilder(Material.CHEST).setName(this.getSafeName().replace("{id}", String.valueOf(id)));
        safe1.setLore(this.getSafeLore().stream().map(s -> s.replace("{owner}", safe.getOwner())
        .replace("{lore}", safe.getLore())).collect(Collectors.toList()));
        addItem(player, safe1.build());
    }


    public void openSafe(Player player, int id){
        Inventory inventory = Bukkit.createInventory(null, this.settings.safeSize, this.settings.safeItemName.replace("{id}", String.valueOf(id)));
        Safe safe = getSafeFromId(id);
        if(!safe.getOwner().equals(player.getName())){
            player.sendMessage(this.settings.youAreNotTheOwnerError);
            return;
        }
        inventory.setContents(safe.getItems());
        player.openInventory(inventory);
    }

    public void openTakeOverSafe(Player player){
        Inventory inventory = Bukkit.createInventory(null, 54, getCrowbarName());
        ItemStack krzak = new ItemBuilder(Material.VINE).setName("&cXXX").build();
        for(int i = 0; i < 31; i++) inventory.setItem(i, krzak);
        for(int i = 32; i < 54; i++) inventory.setItem(i, krzak);
        player.openInventory(inventory);
    }

    public void loadSafe(Safe safe){
        if(!this.safes.containsKey(safe.getId())) this.safes.put(safe.getId(), safe);
    }

    public Safe getSafeFromId(int id){
        for(Safe safe : this.safes.values()){
            if(safe.getId() == id) return safe;
        }
        return null;
    }

    public boolean isSafe(ItemStack itemStack){
        String config = getStringWithoutInteger(ChatColor.stripColor(this.settings.safeItemName.replace("{id}", "")));
        return itemStack.getType().equals(Material.CHEST) &&
                itemStack.hasItemMeta() &&
                itemStack.getItemMeta().hasLore() &&
                itemStack.getItemMeta().hasDisplayName() &&
                itemStack.getItemMeta().getDisplayName().contains(config);
    }

    public HashMap<Integer, Safe> getSafes(){
        return this.safes;
    }

    public int getNextSafeId(){
        return this.safes.size() + 1;
    }

    private String getSafeName(){
        return this.settings.safeItemName;
    }

    private List<String> getSafeLore(){
        return this.settings.safeItemLore;
    }

    private String getCrowbarName(){
        return this.settings.crowbarItemName;
    }
}
