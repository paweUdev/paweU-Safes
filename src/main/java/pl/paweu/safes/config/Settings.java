package pl.paweu.safes.config;

import org.bukkit.configuration.file.FileConfiguration;
import pl.paweu.safes.SafesPlugin;

import java.util.List;
import static pl.paweu.safes.util.ChatUtil.fixColor;

public class Settings {

    private final SafesPlugin plugin;

    //values
    public int mysqlPort;
    public int firstJoinSafeAmount;
    public int safeSize;

    //lists
    public List<String> safeItemLore;
    public List<String> crowbarItemLore;

    //strings
    public String mysqlHost, mysqlUsername, mysqlPassword,mysqlDatabase;
    public String safeCommandUsage;
    public String loreCommandUsage;
    public String integerError;
    public String playerError;
    public String safeItemName;
    public String crowbarItemName;
    public String givenSafeMessageForOnlinePlayers;
    public String givenCrowbarMessageForOnlinePlayers;
    public String givenSafeMessageForSpecificPlayer;
    public String givenCrowbarMessageForSpecificPlayer;
    public String firstJoinMessage;
    public String tookOverTheSafe;
    public String youAreOwnerError;
    public String youAreNotTheOwnerError;
    public String isNotSafeItem;
    public String loreChangeMessage;
    public String loreLengthError;

    public Settings(SafesPlugin plugin){
        this.plugin = plugin;
    }

    public void load(){
        this.plugin.saveDefaultConfig();
        FileConfiguration config = this.plugin.getConfig();
        //mysql

        mysqlHost = config.getString("mysql.host");
        mysqlDatabase = config.getString("mysql.database");
        mysqlUsername = config.getString("mysql.username");
        mysqlPassword = config.getString("mysql.password");

        //integers
        mysqlPort = config.getInt("mysql.port");
        safeSize = config.getInt("values.safeSize");
        firstJoinSafeAmount = config.getInt("values.firstJoinSafeAmount");

        //strings
        safeCommandUsage = fixColor(config.getString("messages.safeCommandUsage"));
        loreCommandUsage = fixColor(config.getString("messages.loreCommandUsage"));
        integerError = fixColor(config.getString("messages.integerError"));
        playerError = fixColor(config.getString("messages.playerError"));
        safeItemName = fixColor(config.getString("items.safe.name"));
        crowbarItemName = fixColor(config.getString("items.crowbar.name"));
        givenSafeMessageForSpecificPlayer = fixColor(config.getString("messages.givenSafeMessageForSpecificPlayer"));
        givenSafeMessageForOnlinePlayers = fixColor(config.getString("messages.givenSafeMessageForOnlinePlayers"));
        givenCrowbarMessageForSpecificPlayer = fixColor(config.getString("messages.givenCrowbarMessageForSpecificPlayer"));
        givenCrowbarMessageForOnlinePlayers = fixColor(config.getString("messages.givenCrowbarMessageForOnlinePlayers"));
        firstJoinMessage = fixColor(config.getString("messages.firstJoinMessage"));
        tookOverTheSafe = fixColor(config.getString("messages.tookOverTheSafe"));
        youAreOwnerError = fixColor(config.getString("messages.youAreOwnerError"));
        youAreNotTheOwnerError = fixColor(config.getString("messages.youAreNotTheOwnerError"));
        isNotSafeItem = fixColor(config.getString("messages.isNotSafeItem"));
        loreChangeMessage = fixColor(config.getString("messages.loreChangeMessage"));
        loreLengthError = fixColor(config.getString("messages.loreLengthError"));

        //lists
        safeItemLore = fixColor(config.getStringList("items.safe.lore"));
        crowbarItemLore = fixColor(config.getStringList("items.crowbar.lore"));
    }
}
