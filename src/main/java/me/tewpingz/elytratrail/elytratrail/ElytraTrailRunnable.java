package me.tewpingz.elytratrail.elytratrail;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ElytraTrailRunnable implements Runnable {

    private final ElytraTrailReflection reflection;
    private final ElytraTrailConfig config;
    private final List<ElytraTrailItem> droppedItems;

    public ElytraTrailRunnable(ElytraTrailReflection reflection, ElytraTrailConfig config) {
        this.reflection = reflection;
        this.config = config;
        this.droppedItems = new ArrayList<>();
    }

    @Override
    public void run() {
        // Loop through all the online players
        for (Player player : Bukkit.getOnlinePlayers()) {

            // Check if permissions is enabled and check if the player has the permission
            if (this.config.isUsePermission() && !player.hasPermission(this.config.getPermission())) {
                // Since the player does not have the permission continue to the next player.
                continue;
            }

            // Check if the player is wearing an elytra and is also gliding
            if (player.isGliding() && (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getType() == Material.ELYTRA)) {
                // Find a random number from 1 to 5
                int amount = ThreadLocalRandom.current().nextInt(5) + 1;
                // Generate up to 5 random items
                for (int i = 0; i < amount; i++) {
                    // Get the location and manipulate it to ensure the item doesn't spawn in the players face.
                    Location location = player.getLocation();
                    // Get the way they are facing and minus by 1.25 block
                    location.add(location.getDirection().normalize().multiply(-1.25));

                    try {
                        // Create the instance of the elytra item
                        ElytraTrailItem trailItem = new ElytraTrailItem(this.reflection, location, this.config.getRandomMaterial());
                        trailItem.sendSpawnToAll(); // Send the spawn packet to all
                        this.droppedItems.add(trailItem); // Add the item to the dropped item list
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Stream through all the elytra items
        List<ElytraTrailItem> items = this.droppedItems.stream().filter(elytraTrailItem -> {
            // Check how long the item has been alive for
            long aliveFor = System.currentTimeMillis() - elytraTrailItem.getSpawnTime();
            return aliveFor > 750; // If the item has been alive for 750 ms allow it to go through the filter
        }).toList();

        // Remove all the trail elytra items from the dropped items list
        this.droppedItems.removeAll(items);
        items.forEach(ElytraTrailItem::sendDestroyToAll);
    }
}
