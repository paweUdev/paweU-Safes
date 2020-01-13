package pl.paweu.safes.task;

import org.bukkit.Bukkit;
import pl.paweu.safes.SafesPlugin;
import pl.paweu.safes.database.MySQL;

public class MysqlTask implements Runnable {

    private final MySQL mySQL;
    private final SafesPlugin plugin;

    public MysqlTask(MySQL mySQL, SafesPlugin plugin){
        this.mySQL = mySQL;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        this.mySQL.save();
    }

    public void start(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, this, 100L, 6000L);
    }
}
