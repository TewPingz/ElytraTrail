package me.tewpingz.elytratrail.elytratrail;

import lombok.Getter;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

@Getter
public class ElytraTrailItem {
    private final int entityId;
    private final long spawnTime;
    private final PacketPlayOutSpawnEntity spawnEntityPacket;
    private final PacketPlayOutEntityMetadata entityMetadataPacket;
    private final PacketPlayOutEntityDestroy entityDestroyPacket;

    public ElytraTrailItem(ElytraTrailReflection reflection, Location location, Material material) throws IllegalAccessException {
        /*
          The only reason this was done completely packet based rather than creating the entity using NMS was because
          if I had used EntityItem this would call RandomSource in the entity class that has a single thread function inside which would
          disallow me from creating entities async and crashing the server. So I had constructed the packet from completely nothing, so
          I can send the packets on an asynchronous thread without any issues. This also would not conflict with the entity ids of
          the spigot as it uses the same atomic integer.
         */

        this.spawnTime = System.currentTimeMillis();
        this.entityId = reflection.incrementAndGetEntityId();
        this.spawnEntityPacket = reflection.createSpawnEntityPacket(this.entityId, location);
        this.entityMetadataPacket = reflection.createEntityMetadataPacket(this.entityId,material);
        this.entityDestroyPacket = new PacketPlayOutEntityDestroy(this.entityId);
    }

    public void spawnItem(Player player) {
        ((CraftPlayer)player).getHandle().b.a(this.spawnEntityPacket);
        ((CraftPlayer)player).getHandle().b.a(this.entityMetadataPacket);
    }

    public void sendSpawnToAll() {
        Bukkit.getOnlinePlayers().forEach(this::spawnItem);
    }

    public void destroyItem(Player player) {
        ((CraftPlayer)player).getHandle().b.a(this.entityDestroyPacket);
    }

    public void sendDestroyToAll() {
        Bukkit.getOnlinePlayers().forEach(this::destroyItem);
    }
}
