package me.tewpingz.elytratrail.elytratrail;

import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

@Getter
public class ElytraTrailConfig {

    private final boolean usePermission;
    private final String permission;
    private final List<Material> trailItems;

    public ElytraTrailConfig(ElytraTrail elytraTrail) {
        elytraTrail.getConfig().options().copyDefaults();
        elytraTrail.saveDefaultConfig();
        this.usePermission = elytraTrail.getConfig().getBoolean("use-permission");
        this.permission = elytraTrail.getConfig().getString("permission");
        this.trailItems = new ArrayList<>();
        elytraTrail.getConfig().getStringList("trail-items").forEach(material -> {
            try {
                this.trailItems.add(Material.valueOf(material.toUpperCase(Locale.ROOT)));
            } catch (Exception e) {
                elytraTrail.getLogger().log(Level.WARNING, "Failed to load %s because its not a valid material".formatted(material));
                e.printStackTrace();
            }
        });
    }

    public Material getRandomMaterial() {
        int random = ThreadLocalRandom.current().nextInt(this.trailItems.size());
        return this.trailItems.get(random);
    }
}
