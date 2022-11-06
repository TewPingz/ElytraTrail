package me.tewpingz.elytratrail.elytratrail;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ElytraTrailReflection {
    // This is the current entity id, since it's an atomic int we have consist access to it.
    private final AtomicInteger entityIdAtomicInteger;

    private final Field entityMetadataListField;

    // DataWatcher objects
    private final DataWatcherObject<Byte> byteDataWatcherObject;
    private final DataWatcherObject<Integer> integerDataWatcherObject;
    private final DataWatcherObject<Optional<IChatBaseComponent>> optionalDataWatcherObject;
    private final DataWatcherObject<Boolean> booleanDataWatcherObject;
    private final DataWatcherObject<Boolean> booleanDataWatcherObject1;
    private final DataWatcherObject<Boolean> booleanDataWatcherObject2;
    private final DataWatcherObject<EntityPose> entityPoseDataWatcherObject;
    private final DataWatcherObject<Integer> integerDataWatcherObject1;
    private final DataWatcherObject<net.minecraft.world.item.ItemStack> itemStackDataWatcherObject;

    public ElytraTrailReflection() throws NoSuchFieldException, IllegalAccessException {
        // Byte data watcher object
        Field field = Entity.class.getDeclaredField("Z");
        field.setAccessible(true);
        this.byteDataWatcherObject = (DataWatcherObject<Byte>) field.get(null);
        // Int data watcher object
        field = Entity.class.getDeclaredField("aL");
        field.setAccessible(true);
        this.integerDataWatcherObject = (DataWatcherObject<Integer>) field.get(null);
        // Optional data watcher object
        field = Entity.class.getDeclaredField("aM");
        field.setAccessible(true);
        this.optionalDataWatcherObject = (DataWatcherObject<Optional<IChatBaseComponent>>) field.get(null);
        // boolean data watcher object
        field = Entity.class.getDeclaredField("aN");
        field.setAccessible(true);
        this.booleanDataWatcherObject = (DataWatcherObject<Boolean>) field.get(null);
        // boolean data watcher object
        field = Entity.class.getDeclaredField("aO");
        field.setAccessible(true);
        this.booleanDataWatcherObject1 = (DataWatcherObject<Boolean>) field.get(null);
        // boolean data watcher object
        field = Entity.class.getDeclaredField("aP");
        field.setAccessible(true);
        this.booleanDataWatcherObject2 = (DataWatcherObject<Boolean>) field.get(null);
        // entity pos data watcher object
        field = Entity.class.getDeclaredField("ad");
        field.setAccessible(true);
        this.entityPoseDataWatcherObject = (DataWatcherObject<EntityPose>) field.get(null);
        // int data watcher object
        field = Entity.class.getDeclaredField("aQ");
        field.setAccessible(true);
        this.integerDataWatcherObject1 = (DataWatcherObject<Integer>) field.get(null);
        // itemstack data watcher object
        field = EntityItem.class.getDeclaredField("c");
        field.setAccessible(true);
        this.itemStackDataWatcherObject = (DataWatcherObject<ItemStack>) field.get(null);
        // entity id atomic int object
        field = Entity.class.getDeclaredField("c");
        field.setAccessible(true);
        this.entityIdAtomicInteger = (AtomicInteger) field.get(null);
        // entity metadata list field
        field = PacketPlayOutEntityMetadata.class.getDeclaredField("b");
        field.setAccessible(true);
        this.entityMetadataListField = field;
    }

    public int incrementAndGetEntityId() {
        return this.entityIdAtomicInteger.incrementAndGet();
    }

    public PacketPlayOutSpawnEntity createSpawnEntityPacket(int entityId, Location location) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float pitch = MathHelper.d(location.getPitch() * 256.0F / 360.0F);
        float yaw = MathHelper.d(location.getYaw() * 256.0F / 360.0F);
        Vec3D velocity = new Vec3D(0, 0, 0);
        return new PacketPlayOutSpawnEntity(entityId, UUID.randomUUID(), x, y, z, pitch, yaw, EntityTypes.T, 0, velocity, 0);
    }
    public PacketPlayOutEntityMetadata createEntityMetadataPacket(int entityId, Material material) throws IllegalAccessException {
        PacketPlayOutEntityMetadata entityMetadata = new PacketPlayOutEntityMetadata(entityId, new DataWatcher(null), false);
        List<DataWatcher.Item<?>> items = new ArrayList<>();
        items.add(new DataWatcher.Item<>(byteDataWatcherObject, (byte)0));
        items.add(new DataWatcher.Item<>(integerDataWatcherObject, 0));
        items.add(new DataWatcher.Item<>(booleanDataWatcherObject, false));
        items.add(new DataWatcher.Item<>(optionalDataWatcherObject, Optional.empty()));
        items.add(new DataWatcher.Item<>(booleanDataWatcherObject1, false));
        items.add(new DataWatcher.Item<>(booleanDataWatcherObject2, false));
        items.add(new DataWatcher.Item<>(entityPoseDataWatcherObject, EntityPose.a));
        items.add(new DataWatcher.Item<>(integerDataWatcherObject1, 0));
        items.add(new DataWatcher.Item<>(itemStackDataWatcherObject, CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(material))));
        this.entityMetadataListField.set(entityMetadata, items);
        return entityMetadata;
    }
}
