package pl.paweu.safes.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.paweu.safes.manager.SafeManager;

public class PlayerJoinListener implements Listener {

    private final SafeManager safeManager;

    public PlayerJoinListener(SafeManager safeManager){
        this.safeManager = safeManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        this.safeManager.firstJoinGive(player);
    }
}
