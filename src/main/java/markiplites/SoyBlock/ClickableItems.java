package markiplites.SoyBlock;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
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
		Player p = event.getPlayer();
        if (p != null && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
        {
    		ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
    		if(meta == null)
    			return;
    		PersistentDataContainer container = meta.getPersistentDataContainer();

			if(!container.has(new NamespacedKey(Main.getInstance(), "itemAction"), PersistentDataType.DOUBLE))
				return;

    		int itemActionType =  (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "itemAction"), PersistentDataType.DOUBLE));
			switch (itemActionType) {
				case 1 -> Menu_SBMainMenu(p);
			}
			event.setCancelled(true);

        }
	}
	@EventHandler
	public void PlayerInventoryInteract(InventoryClickEvent e)
	{
		HumanEntity p = e.getWhoClicked();
		if(p == null || !(p instanceof Player))
			return;
		ItemStack itemStack = e.getCurrentItem();
		if(itemStack == null)
			return;
		ItemMeta meta = itemStack.getItemMeta();
		if(meta == null)
			return;

		PersistentDataContainer container = meta.getPersistentDataContainer();

		if(!container.has(new NamespacedKey(Main.getInstance(), "itemAction"), PersistentDataType.DOUBLE))
			return;

		int itemActionType = (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "itemAction"), PersistentDataType.DOUBLE));
		e.setCancelled(true);
		p.closeInventory();
		switch (itemActionType) {
			case 1 -> Menu_SBMainMenu((Player)p);
		}
	}
	@EventHandler
	public void onPlayerSwapHands(PlayerSwapHandItemsEvent e)
	{
		HumanEntity p = e.getPlayer();
		if(p == null)
			return;
		ItemStack itemStack = e.getOffHandItem();
		if(itemStack == null)
			return;
		ItemMeta meta = itemStack.getItemMeta();
		if(meta == null)
			return;
		PersistentDataContainer container = meta.getPersistentDataContainer();

		if(container.has(new NamespacedKey(Main.getInstance(), "itemAction"), PersistentDataType.DOUBLE))
			e.setCancelled(true);
	}
	public static void Menu_SBMainMenu(Player player)
	{
		String[] guiSetup = {
				"bbbbbbbbb",
				"b   v   b",
				"b  tsr  b",
				"b  aie  b",
				"b   p   b",
				"bbbbbbbbb",
		};
		InventoryGui gui = new InventoryGui(Main.getInstance(), player, "Soyblock Main Menu", guiSetup);
		gui.setFiller(new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1)); // fill the empty slots with this

		gui.addElement(new StaticGuiElement('s',
				ItemListHandler.getPlayerHead(player),
				1,
				click -> {
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:6699ff>Soyblock Stats"),
				CustomAttributes.getPlayerStatsFormat(player)
		));
		gui.addElement(new StaticGuiElement('p',
				ItemListHandler.getItemForDisplay(Material.COMMAND_BLOCK),
				1,
				click -> {
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:9999ff>Client Preferences"),
				IridiumColorAPI.process("<SOLID:cc66ff>Change your settings here.")
		));
		gui.addElement(new StaticGuiElement('v',
				ItemListHandler.getItemForDisplay(Material.ENDER_CHEST),
				1,
				click -> {
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:6666ff>Vault"),
				IridiumColorAPI.process("<SOLID:4d4dff>Store & access items.")
		));
		gui.addElement(new StaticGuiElement('r',
				ItemListHandler.getItemForDisplay(Material.IRON_CHESTPLATE),
				1,
				click -> {
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:33ccff>Armory"),
				IridiumColorAPI.process("<SOLID:33cccc>See your collection of items.")
		));
		gui.addElement(new StaticGuiElement('t',
				ItemListHandler.getItemForDisplay(Material.ENDER_EYE),
				1,
				click -> {
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:00cc66>Teleport"),
				IridiumColorAPI.process("<SOLID:00994d>Teleport to previously visited places.")
		));
		gui.addElement(new StaticGuiElement('a',
				ItemListHandler.getItemForDisplay(Material.RED_BANNER),
				1,
				click -> {
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:ff3399>Achievements"),
				IridiumColorAPI.process("<SOLID:e60073>Collect rewards from milestones.")
		));
		gui.addElement(new StaticGuiElement('i',
				ItemListHandler.getItemForDisplay(Material.ENCHANTED_BOOK),
				1,
				click -> {
					skills(player);
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:ff9999>Skills"),
				IridiumColorAPI.process("<SOLID:ff9966>See your skill progression.")
		));
		gui.addElement(new StaticGuiElement('e',
				ItemListHandler.getItemForDisplay(Material.IRON_SWORD),
				1,
				click -> {
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:ff9933>Equipment"),
				IridiumColorAPI.process("<SOLID:ffcc00>Change out your currently worn gear.")
		));
		gui.addElement(new StaticGuiElement('b',
				new ItemStack(Material.BLACK_STAINED_GLASS_PANE),
				1,
				click -> {
					return true; // returning true will cancel the click event and stop taking the item
				},
				" "
		));
		gui.show(player);
	}
private static void skills(Player p) {
	String[] guiSetup = {
		"bbbbbbbbb",
		"b   m   b",
		"b  fch  b",
		"b       b",
		"bbbbbbbbb"
	};
	InventoryGui gui = new InventoryGui(Main.getInstance(), p, "Soyblock Main Menu", guiSetup);
	gui.setFiller(new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1)); // fill the empty slots with this

	gui.addElement(new StaticGuiElement('c',
	ItemListHandler.getItemForDisplay(Material.IRON_SWORD),
	Math.min(Skills.getLevel(p.getUniqueId(), "Combat"), 64),
	click -> {
		combat_menu(p);
		return true; // returning true will cancel the click event and stop taking the item
	},
	IridiumColorAPI.process("<SOLID:1652C3>Combat Skill Menu"),
	String.format("+%d Base Damage", (5 * (Skills.getLevel(p.getUniqueId(), "Combat")-1))),
	String.format("%.1f/%.1f EXP", Skills.getPlayerEXP(p.getUniqueId(),"Combat"),Skills.getRequiredEXP(p.getUniqueId(), "Combat", 1))
	));

	gui.addElement(new StaticGuiElement('f',
	ItemListHandler.getItemForDisplay(Material.SPRUCE_SAPLING),
	Math.min(Skills.getLevel(p.getUniqueId(), "Foraging"), 64),
	click -> {
		return true; // returning true will cancel the click event and stop taking the item
	},
	IridiumColorAPI.process("<SOLID:0C8536>Foraging Skill Menu"),
	String.format("+%d Strength", (5 * (Skills.getLevel(p.getUniqueId(), "Foraging")-1))),
	String.format("%.1f/%.1f EXP", Skills.getPlayerEXP(p.getUniqueId(),"Foraging"),Skills.getRequiredEXP(p.getUniqueId(), "Foraging", 1))
	));

	gui.addElement(new StaticGuiElement('h',
	ItemListHandler.getItemForDisplay(Material.BREWING_STAND),
	Math.min(Skills.getLevel(p.getUniqueId(), "Alchemy"), 64),
	click -> {
		return true; // returning true will cancel the click event and stop taking the item
	},
	IridiumColorAPI.process("<SOLID:C0D9F1>Alchemy Skill Menu"),
	String.format("+%d Iteglicen", (5 * (Skills.getLevel(p.getUniqueId(), "Alchemy")-1))),
	String.format("%.1f/%.1f EXP", Skills.getPlayerEXP(p.getUniqueId(),"Alchemy"),Skills.getRequiredEXP(p.getUniqueId(), "Alchemy", 1))
	));

	gui.addElement(new StaticGuiElement('m',
	ItemListHandler.getItemForDisplay(Material.IRON_PICKAXE),
	Math.min(Skills.getLevel(p.getUniqueId(), "Mining"), 64),
	click -> {
		return true; // returning true will cancel the click event and stop taking the item
	},
	IridiumColorAPI.process("<SOLID:7B2EDB>Mining Skill Menu"),
	String.format("+%d%% Absorption", (1 * (Skills.getLevel(p.getUniqueId(), "Mining")-1))),
	String.format("%.1f/%.1f EXP", Skills.getPlayerEXP(p.getUniqueId(),"Mining"),Skills.getRequiredEXP(p.getUniqueId(), "Mining", 1))
	));

	gui.addElement(new StaticGuiElement('b',
	ItemListHandler.getItemForDisplay(Material.BLACK_STAINED_GLASS_PANE),
	1,
	click -> {
		return true; // returning true will cancel the click event and stop taking the item
	},
	" "
	));

	gui.show(p);
}


	private static void combat_menu(Player p) {
		String[] guiSetup = {
			"bbbbbbbbb",
			"b       b",
			"b   n   b",
			"b       b",
			"bbbbbbbbb"
		};
		InventoryGui gui = new InventoryGui(Main.getInstance(), p, "Soyblock Main Menu", guiSetup);
		gui.setFiller(new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1)); // fill the empty slots with this
		
		gui.addElement(new StaticGuiElement('b',
		ItemListHandler.getItemForDisplay(Material.BLACK_STAINED_GLASS_PANE),
		1,
		click -> {
			return true; // returning true will cancel the click event and stop taking the item
		},
		" "
		));

		gui.addElement(new StaticGuiElement('n',
		ItemListHandler.getItemForDisplay(Material.WOODEN_SWORD),
		1,
		click -> {
			return true; // returning true will cancel the click event and stop taking the item
		},
		IridiumColorAPI.process("<SOLID:846D49>NIGGER!!!!!")
		));

		gui.show(p);
	}




}

