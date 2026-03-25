package Me.menutils;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Instant;

public class EffectListener implements Listener {

    private final Menutils plugin;

    public EffectListener(Menutils plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL && plugin.isToggled(player, plugin.getNoFall())) {
            event.setCancelled(true);
        } else if ((event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA) && plugin.isToggled(player, plugin.getNoFire())) {
            event.setCancelled(true);
        } else if (event.getCause() == EntityDamageEvent.DamageCause.DROWNING && plugin.isToggled(player, plugin.getNoDrown())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();
        if (plugin.isToggled(player, plugin.getNoHunger())) {
            event.setCancelled(true);
            player.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.getWaitingForAction().containsKey(player.getUniqueId())) {
            event.setCancelled(true);
            String action = plugin.getWaitingForAction().get(player.getUniqueId());
            plugin.getWaitingForAction().remove(player.getUniqueId());
            String name = event.getMessage();
            Player target = Bukkit.getPlayer(name);
            if (target == null) {
                player.sendMessage("Player not found.");
                return;
            }
            switch (action) {
                case "nightvision":
                    plugin.toggle(target, plugin.getNightVision());
                    if (plugin.isToggled(target, plugin.getNightVision())) {
                        target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
                    } else {
                        target.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    }
                    player.sendMessage("Toggled night vision for " + target.getName());
                    break;
                case "nofall":
                    plugin.toggle(target, plugin.getNoFall());
                    player.sendMessage("Toggled no fall damage for " + target.getName());
                    break;
                case "nofire":
                    plugin.toggle(target, plugin.getNoFire());
                    player.sendMessage("Toggled no fire damage for " + target.getName());
                    break;
                case "nodrown":
                    plugin.toggle(target, plugin.getNoDrown());
                    player.sendMessage("Toggled no drowning for " + target.getName());
                    break;
                case "nohunger":
                    plugin.toggle(target, plugin.getNoHunger());
                    player.sendMessage("Toggled no hunger for " + target.getName());
                    break;
                case "ban":
                    plugin.toggle(target, plugin.getBanned());
                    BanList<String> banList = Bukkit.getBanList(BanList.Type.NAME);
                    if (plugin.isToggled(target, plugin.getBanned())) {
                        banList.addBan(target.getName(), "Banned by " + player.getName(), (Instant) null, (String) null);
                        target.kickPlayer("You have been banned!");
                    } else {
                        ((BanList) banList).pardon(target.getName());
                        target.sendMessage("You have been unbanned!");
                    }
                    player.sendMessage("Toggled ban for " + target.getName());
                    break;
                case "warn":
                    plugin.toggle(target, plugin.getWarned());
                    if (plugin.isToggled(target, plugin.getWarned())) {
                        target.sendMessage("You have been warned by " + player.getName() + "!");
                    } else {
                        target.sendMessage("Warning removed by " + player.getName() + ".");
                    }
                    player.sendMessage("Toggled warn for " + target.getName());
                    break;
                case "kick":
                    plugin.toggle(target, plugin.getKicked());
                    if (plugin.isToggled(target, plugin.getKicked())) {
                        target.kickPlayer("Kicked by " + player.getName());
                    }
                    player.sendMessage("Toggled kick for " + target.getName());
                    break;
                case "mute":
                    plugin.toggle(target, plugin.getMuted());
                    if (plugin.isToggled(target, plugin.getMuted())) {
                        target.sendMessage("You have been muted by " + player.getName() + "!");
                    } else {
                        target.sendMessage("You have been unmuted by " + player.getName() + "!");
                    }
                    player.sendMessage("Toggled mute for " + target.getName());
                    break;
            }
        }
        if (plugin.isToggled(player, plugin.getMuted())) {
            event.setCancelled(true);
            player.sendMessage("You are muted!");
        }
    }
}
