package Me.menutils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Menutils extends JavaPlugin implements CommandExecutor {

    private Map<UUID, Boolean> nightVision = new HashMap<>();
    private Map<UUID, Boolean> noFall = new HashMap<>();
    private Map<UUID, Boolean> noFire = new HashMap<>();
    private Map<UUID, Boolean> noDrown = new HashMap<>();
    private Map<UUID, Boolean> noHunger = new HashMap<>();
    private Map<UUID, Boolean> banned = new HashMap<>();
    private Map<UUID, Boolean> warned = new HashMap<>();
    private Map<UUID, Boolean> kicked = new HashMap<>();
    private Map<UUID, Boolean> muted = new HashMap<>();
    private Map<UUID, String> waitingForAction = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("menutils").setExecutor(this);
        getServer().getPluginManager().registerEvents(new MenuListener(this), this);
        getServer().getPluginManager().registerEvents(new EffectListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;
        openMenu(player);
        return true;
    }

    public void openMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, "Menutils Menu");

        // Night Vision
        ItemStack nightVisionItem = new ItemStack(Material.POTION);
        ItemMeta meta = nightVisionItem.getItemMeta();
        meta.setDisplayName("Night Vision");
        nightVisionItem.setItemMeta(meta);
        inv.setItem(0, nightVisionItem);

        // No Fall Damage
        ItemStack noFallItem = new ItemStack(Material.DIAMOND_BOOTS);
        meta = noFallItem.getItemMeta();
        meta.setDisplayName("No Fall Damage");
        noFallItem.setItemMeta(meta);
        inv.setItem(1, noFallItem);

        // No Fire Damage
        ItemStack noFireItem = new ItemStack(Material.BLAZE_POWDER);
        meta = noFireItem.getItemMeta();
        meta.setDisplayName("No Fire Damage");
        noFireItem.setItemMeta(meta);
        inv.setItem(2, noFireItem);

        // No Drowning
        ItemStack noDrownItem = new ItemStack(Material.WATER_BUCKET);
        meta = noDrownItem.getItemMeta();
        meta.setDisplayName("No Drowning");
        noDrownItem.setItemMeta(meta);
        inv.setItem(3, noDrownItem);

        // No Hunger
        ItemStack noHungerItem = new ItemStack(Material.BREAD);
        meta = noHungerItem.getItemMeta();
        meta.setDisplayName("No Hunger");
        noHungerItem.setItemMeta(meta);
        inv.setItem(4, noHungerItem);

        // Ban Player
        ItemStack banItem = new ItemStack(Material.BARRIER);
        meta = banItem.getItemMeta();
        meta.setDisplayName("Ban Player");
        banItem.setItemMeta(meta);
        inv.setItem(5, banItem);

        // Warn Player
        ItemStack warnItem = new ItemStack(Material.PAPER);
        meta = warnItem.getItemMeta();
        meta.setDisplayName("Warn Player");
        warnItem.setItemMeta(meta);
        inv.setItem(6, warnItem);

        // Kick Player
        ItemStack kickItem = new ItemStack(Material.STICK);
        meta = kickItem.getItemMeta();
        meta.setDisplayName("Kick Player");
        kickItem.setItemMeta(meta);
        inv.setItem(7, kickItem);

        // Mute Player
        ItemStack muteItem = new ItemStack(Material.BOOK);
        meta = muteItem.getItemMeta();
        meta.setDisplayName("Mute Player");
        muteItem.setItemMeta(meta);
        inv.setItem(8, muteItem);

        player.openInventory(inv);
    }

    public boolean isToggled(Player player, Map<UUID, Boolean> map) {
        return map.getOrDefault(player.getUniqueId(), false);
    }

    public void toggle(Player player, Map<UUID, Boolean> map) {
        UUID uuid = player.getUniqueId();
        boolean current = map.getOrDefault(uuid, false);
        map.put(uuid, !current);
    }

    // Getters for listeners
    public Map<UUID, Boolean> getNightVision() { return nightVision; }
    public Map<UUID, Boolean> getNoFall() { return noFall; }
    public Map<UUID, Boolean> getNoFire() { return noFire; }
    public Map<UUID, Boolean> getNoDrown() { return noDrown; }
    public Map<UUID, Boolean> getNoHunger() { return noHunger; }
    public Map<UUID, Boolean> getBanned() { return banned; }
    public Map<UUID, Boolean> getWarned() { return warned; }
    public Map<UUID, Boolean> getKicked() { return kicked; }
    public Map<UUID, Boolean> getMuted() { return muted; }
    public Map<UUID, String> getWaitingForAction() { return waitingForAction; }
}
