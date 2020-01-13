package pl.paweu.safes;

import org.bukkit.plugin.java.JavaPlugin;
import pl.paweu.safes.command.LoreCommand;
import pl.paweu.safes.command.SafeCommand;
import pl.paweu.safes.config.Settings;
import pl.paweu.safes.database.MySQL;
import pl.paweu.safes.listener.InventoryClickListener;
import pl.paweu.safes.listener.InventoryCloseListener;
import pl.paweu.safes.listener.PlayerInteractListener;
import pl.paweu.safes.listener.PlayerJoinListener;
import pl.paweu.safes.manager.SafeManager;
import pl.paweu.safes.task.MysqlTask;

public final class SafesPlugin extends JavaPlugin  {

    private Settings settings;
    private SafeManager safeManager;
    private MySQL mySQL;

    @Override
    public void onEnable() {
        final long start = System.currentTimeMillis();
        loadConfig();
        registerManagers();
        loadMySQL();
        registerCommands();
        registerListeners();
        registerTasks();
        System.out.println("[paweU-Safes] -> Plugin zostal wlaczony w " + (System.currentTimeMillis() - start) + "ms");
        System.out.println("[paweU-Safes] -> Discord: paweU#5651");
    }

    @Override
    public void onDisable() {
        this.mySQL.save();
    }

    private void registerCommands(){
        getCommand("sejf").setExecutor(new SafeCommand(this.settings, this.safeManager));
        getCommand("opis").setExecutor(new LoreCommand(this.settings, this.safeManager));
    }

    private void registerListeners(){
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this.safeManager), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this.safeManager, this.settings), this);
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(this.safeManager, this.settings), this);
        this.getServer().getPluginManager().registerEvents(new InventoryCloseListener(this.safeManager, this.settings), this);
    }

    private void registerManagers(){
        this.safeManager = new SafeManager(this.settings);
    }

    private void registerTasks(){
        new MysqlTask(this.mySQL, this).start();
    }

    private void loadConfig(){
        this.settings = new Settings(this);
        this.settings.load();
    }

    private void loadMySQL(){
        this.mySQL = new MySQL(this.settings, this.safeManager);
        this.mySQL.load();
    }
}
