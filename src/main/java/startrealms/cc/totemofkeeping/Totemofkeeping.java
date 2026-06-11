package startrealms.cc.totemofkeeping;

import org.bukkit.plugin.java.JavaPlugin;
import startrealms.cc.totemofkeeping.listener.DeathListener;
import startrealms.cc.totemofkeeping.command.TotemCommand;

public final class Totemofkeeping extends JavaPlugin {

    private static Totemofkeeping instance;

    public static Totemofkeeping getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new DeathListener(), this);

        // Register commands
        getCommand("totemgive").setExecutor(new TotemCommand());
        getCommand("totemreload").setExecutor(new TotemCommand());

        getLogger().info("TotemOfKeeping enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("TotemOfKeeping disabled!");
    }
}