package aplini.versionupdateprompt.hook;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static aplini.versionupdateprompt.VersionUpdatePrompt.sendVersionMessages;

public class hookBukkit implements Listener {

    // 玩家加入事件
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        sendVersionMessages(event.getPlayer());
    }
}
