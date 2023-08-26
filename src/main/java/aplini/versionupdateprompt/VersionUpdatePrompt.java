package aplini.versionupdateprompt;

import aplini.versionupdateprompt.hook.hookAuthMe;
import aplini.versionupdateprompt.hook.hookBukkit;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class VersionUpdatePrompt extends JavaPlugin implements Listener {

    static VersionUpdatePrompt plugin;
    static private ViaAPI via;
    static String serverVerName;
    static int serverVer;

    @Override // 插件加载
    public void onLoad() {
        plugin = this;
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        plugin.getConfig();
    }

    @Override
    public void onEnable() {
        via = Via.getAPI();

        // 获取服务器协议版本
        serverVer = via.getServerVersion().highestSupportedVersion();
        // 获取服务器版本名称
        serverVerName = ProtocolVersion.getProtocol(serverVer).getName();

        // 使用配置覆盖自动检查版本
        if(plugin.getConfig().getInt("server.serverVer", -1) != -1){
            serverVer = plugin.getConfig().getInt("server.serverVer");
        }
        if(!plugin.getConfig().getString("server.serverVerName", "").isEmpty()){
            serverVerName = plugin.getConfig().getString("server.serverVerName");
        }

        getLogger().info("服务器版本: "+ serverVerName +"["+ serverVer +"]");

        // 是否启用 AuthMe
        if(plugin.getConfig().getBoolean("server.hook_AuthMe", false)){
            getLogger().info("连接到 AuthMe 插件");
            getServer().getPluginManager().registerEvents(new hookAuthMe(), this);
        }else{
            getServer().getPluginManager().registerEvents(new hookBukkit(), this);
        }
    }

    @Override
    public void onDisable() {}


    // 发送版本消息
    public static void sendVersionMessages(Player player) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(plugin.getConfig().getInt("server.delay", 4000));
            } catch (InterruptedException ignored) {}

            // 如果玩家已经离线, 则不运行
            if(!player.isOnline()){return;}

            // 协议版本和版本名称
            int playerVer = via.getPlayerVersion(player.getUniqueId());
            String playerVerName = ProtocolVersion.getProtocol(playerVer).getName();
//            getLogger().info(player.getName() +": "+ playerVerName +"["+ playerVer +"]");

            if(playerVer < serverVer){
                if(plugin.getConfig().getString("message.lower", "").isEmpty()){return;}
                player.sendMessage(plugin.getConfig().getString("message.lower", "")
                        .replace("%playerVersion%", playerVerName)
                        .replace("%serverVersion%", serverVerName));
            }else if(playerVer == serverVer){
                if(plugin.getConfig().getString("message.equal", "").isEmpty()){return;}
                player.sendMessage(plugin.getConfig().getString("message.equal", "")
                        .replace("%playerVersion%", playerVerName)
                        .replace("%serverVersion%", serverVerName));
            }else{
                if(plugin.getConfig().getString("message.higher", "").isEmpty()){return;}
                player.sendMessage(plugin.getConfig().getString("message.higher", "")
                        .replace("%playerVersion%", playerVerName)
                        .replace("%serverVersion%", serverVerName));
            }
        });
        executor.shutdown();
    }
}
