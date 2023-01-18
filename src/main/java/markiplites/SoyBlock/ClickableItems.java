package markiplites.SoyBlock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import de.themoep.inventorygui.*;

public class ClickableItems implements Listener {
	public ClickableItems() {
		init();
	}
	public void init() {	
	
	}
	@EventHandler
    public void PlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
        if (player != null && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
        {
    		ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
    		if(meta == null)
    			return;
    		PersistentDataContainer container = meta.getPersistentDataContainer();
    		
    		int itemActionType = container.has(new NamespacedKey(Main.getInstance(), "itemAction"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "itemAction"), PersistentDataType.DOUBLE)) : -1;
			switch (itemActionType) {
				case 1 -> {
					String[] guiSetup = {
							"    s    ",
							"         ",
							"         "
					};
					InventoryGui gui = new InventoryGui(Main.getInstance(), player, "Soyblock Menu", guiSetup);
					gui.setFiller(new ItemStack(Material.GRAY_STAINED_GLASS, 1)); // fill the empty slots with this

					gui.addElement(new StaticGuiElement('s',
							new ItemStack(Material.REDSTONE),
							69,
							click -> {
								click.getRawEvent().getWhoClicked().sendMessage(ChatColor.RED + "lel!");
								return true; // returning true will cancel the click event and stop taking the item
							},
							"Soyblock Stats",
							CustomAttributes.getPlayerStatsFormat(player)
					));
					gui.show(player);
				}
			}
        }
	}
	@EventHandler
	public void PlayerInventoryInteract(InventoryInteractEvent e)
	{

	}
}
