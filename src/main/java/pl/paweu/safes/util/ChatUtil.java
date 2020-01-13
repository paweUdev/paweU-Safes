package pl.paweu.safes.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class ChatUtil {

    public static String fixColor(String text){
        if(text == null || text.isEmpty()) return "";
        return ChatColor.translateAlternateColorCodes('&', text
                .replace("%>", "\u00bb")
                .replace("%O", "\u25cf"));
    }

    public static List<String> fixColor(List<String> list) {
        List<String> fixed = new ArrayList<>();
        if (list == null || list.isEmpty()) return fixed;
        for (final String s : list) fixed.add(fixColor(s));
        return fixed;
    }

    public static boolean sendMessage(CommandSender sender, String message){
        sender.sendMessage(fixColor(message));
        return true;
    }

    public static boolean sendMessage(Player player, String message){
        player.sendMessage(fixColor(message));
        return true;
    }
}
