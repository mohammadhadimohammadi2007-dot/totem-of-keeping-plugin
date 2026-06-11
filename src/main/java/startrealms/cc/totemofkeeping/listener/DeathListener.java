package startrealms.cc.totemofkeeping.listener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import startrealms.cc.totemofkeeping.Totemofkeeping;
import startrealms.cc.totemofkeeping.util.TotemItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DeathListener implements Listener {

    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final Map<UUID, UUID> lastDamager = new HashMap<>();
    private final Map<UUID, UUID> lastKillPair = new HashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player victim && e.getDamager() instanceof Player attacker) {
            lastDamager.put(victim.getUniqueId(), attacker.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer == null) {
            UUID last = lastDamager.get(victim.getUniqueId());
            if (last != null) {
                killer = Bukkit.getPlayer(last);
            }
        }

        boolean endOnly = Totemofkeeping.getInstance().getConfig().getBoolean("end-only");
        boolean inEnd = victim.getWorld().getEnvironment() == World.Environment.THE_END;
        boolean canActivate = !endOnly || inEnd;


        if (killer != null && killer != victim && canActivate) {

            ItemStack[] contents = victim.getInventory().getContents();

            for (int i = 0; i < contents.length; i++) {
                ItemStack item = contents[i];

                if (TotemItem.isTotem(item)) {

                    item.setAmount(item.getAmount() - 1);
                    if (item.getAmount() <= 0) {
                        contents[i] = null;
                    }

                    event.setKeepInventory(true);
                    event.setKeepLevel(true);
                    event.getDrops().clear();

                    ItemStack[] finalContents = contents;
                    Bukkit.getScheduler().runTask(Totemofkeeping.getInstance(), () ->
                            victim.getInventory().setContents(finalContents)
                    );

                    String msg = Totemofkeeping.getInstance()
                            .getConfig().getString("messages.activated", "§dTotem activated!");
                    victim.sendMessage(msg);

                    return;
                }
            }
        }


        if (killer == null || killer == victim) return;
        if (!victim.hasPermission("rank.sponsor")) return;

        if (lastKillPair.containsKey(victim.getUniqueId()) &&
                lastKillPair.get(victim.getUniqueId()).equals(killer.getUniqueId())) {
            return;
        }

        long now = System.currentTimeMillis();
        long cooldown = Totemofkeeping.getInstance().getConfig().getLong("cooldown") * 1000;

        if (cooldowns.containsKey(victim.getUniqueId())) {
            long last = cooldowns.get(victim.getUniqueId());
            if ((now - last) < cooldown) return;
        }

        double chance = Totemofkeeping.getInstance().getConfig().getDouble("drop-chance");

        if (Math.random() <= chance) {

            ItemStack totem = TotemItem.createTotem();

            Map<Integer, ItemStack> leftover = killer.getInventory().addItem(totem);

            if (!leftover.isEmpty()) {
                for (ItemStack item : leftover.values()) {
                    killer.getWorld().dropItemNaturally(killer.getLocation(), item);
                }
            }

            String msg = Totemofkeeping.getInstance()
                    .getConfig().getString("messages.received", "§aYou received a Totem!");
            killer.sendMessage(msg);

            cooldowns.put(victim.getUniqueId(), now);
            lastKillPair.put(victim.getUniqueId(), killer.getUniqueId());
        }
    }
}