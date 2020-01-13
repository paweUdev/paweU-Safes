package pl.paweu.safes.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static pl.paweu.safes.util.ChatUtil.fixColor;

public class ItemBuilder {

    private int amount;
    private short data;
    private List<String> lore;
    private final Material mat;
    private String name;

    public ItemBuilder(final Material mat) {
        this(mat, 1);
    }

    private ItemBuilder(final Material mat, final int amount) {
        this(mat, amount, (short) 0);
    }

    private ItemBuilder(final Material mat, final int amount, final short data) {
        this.data = 0;
        this.lore = new ArrayList<>();
        this.name = null;
        this.lore = new ArrayList<>();
        this.mat = mat;
        this.amount = amount;
        this.data = data;
    }

    public ItemStack build() {
        final ItemStack is = new ItemStack(this.mat, this.amount, this.data);
        final ItemMeta im = is.getItemMeta();
        if (this.name != null) im.setDisplayName(fixColor(this.name));
        if (this.lore != null && !this.lore.isEmpty()) im.setLore(fixColor(this.lore));
        is.setItemMeta(im);
        return is;
    }

    public void setAmount(final int amount) {
        this.amount = amount;
    }

    public void setLore(final List<String> lore) {
        this.lore = lore;
    }

    public ItemBuilder setName(final String name) {
        this.name = name;
        return this;
    }
}


