package Me.menutils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {

    private final Menutils plugin;

    public MenuListener(Menutils plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Menutils Menu")) return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        if (!player.isOp()) {
            player.sendMessage("You need OP to use this feature.");
            return;
        }
        int slot = event.getSlot();
        String action = "";
        switch (slot) {
            case 0:
                action = "nightvision";
                break;
            case 1:
                action = "nofall";
                break;
            case 2:
                action = "nofire";
                break;
            case 3:
                action = "nodrown";
                break;
            case 4:
                action = "nohunger";
                break;
            case 5:
                action = "ban";
                break;
            case 6:
                action = "warn";
                break;
            case 7:
                action = "kick";
                break;
            case 8:
                action = "mute";
                break;
        }
        if (!action.isEmpty()) {
            plugin.getWaitingForAction().put(player.getUniqueId(), action);
            player.sendMessage("Type the target player name in chat:");
            player.closeInventory();
        }
    }
}
