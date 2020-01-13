package pl.paweu.safes.object;

import org.bukkit.inventory.ItemStack;

import static pl.paweu.safes.util.ItemUtil.*;
import static pl.paweu.safes.util.StringUtil.replace;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Safe {

    private boolean changes;

    private int id;
    private String owner;
    private String lore;
    private ItemStack[] items;

    public Safe(int id, String owner, String lore, ItemStack[] items){
        this.id = id;
        this.owner = owner;
        this.lore = lore;
        this.items = items;
        this.changes();
    }

    public Safe(ResultSet rs) throws SQLException, IOException {
        this.id = rs.getInt("id");
        this.owner = rs.getString("owner");
        this.lore = rs.getString("lore");
        this.items = itemStackArrayFromBase64(rs.getString("items"));
        this.setChanges(false);
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
        this.changes();
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
        this.changes();
    }

    public void setOwner(String owner){
        this.owner = owner;
        this.changes();
    }

    public boolean isChanges(){
        return this.changes;
    }

    public void setChanges(boolean b){
        this.changes = b;
    }

    private void changes(){
        this.changes = true;
    }

    public String getSQL() {
        String s = "INSERT INTO `paweU-Safes` VALUES(" +
                "%id%," +
                "'%owner%'," +
                "'%lore%'," +
                "'%items%'" +
                ") ON DUPLICATE KEY UPDATE " +
                "owner='%owner%'," +
                "lore='%lore%'," +
                "items='%items%';";
        s = replace(s, "%id%", String.valueOf(this.id));
        s = replace(s, "%owner%", this.owner);
        s = replace(s, "%lore%", this.lore);
        s = replace(s, "%items%", itemStackArrayToBase64(this.items));
        return s;
    }
}
