package aplini.versionupdateprompt.hook;

import fr.xephi.authme.events.LoginEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import static aplini.versionupdateprompt.VersionUpdatePrompt.sendVersionMessages;

public class hookAuthMe implements Listener {

    // AuthMe 玩家登录事件
    @EventHandler(priority = EventPriority.MONITOR)
    public void onAuthMeLoginEvent(LoginEvent event) {
        sendVersionMessages(event.getPlayer());
    }
}
