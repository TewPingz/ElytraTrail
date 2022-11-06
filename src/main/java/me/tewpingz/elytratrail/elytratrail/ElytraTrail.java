package me.tewpingz.elytratrail.elytratrail;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class ElytraTrail extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getLogger().info("Initializing reflections for elytra trails");
        ElytraTrailReflection reflection;
        try {
            reflection = new ElytraTrailReflection();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            this.getLogger().log(Level.SEVERE, "Failed to load server as something wrong has occurred while loading reflections.");
            Bukkit.shutdown();
            throw new RuntimeException(e);
        }
        this.getLogger().info("Successfully initialized reflections for elytra trails");
        this.getLogger().info("Initializing the configuration file");
        ElytraTrailConfig config = new ElytraTrailConfig(this);
        this.getLogger().info("Successfully initialized the configuration file");
        this.getLogger().info("Initializing the runnable for elytra trails");
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new ElytraTrailRunnable(reflection, config), 1L, 1L);
        this.getLogger().info("Successfully initialized the elytra trail");
    }
}
