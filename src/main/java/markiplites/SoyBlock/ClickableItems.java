package markiplites.SoyBlock;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import markiplites.SoyBlock.MenuItems.MainMenu.CraftingMenu;
import markiplites.SoyBlock.MenuItems.MainMenu.SkillsMenu;

import org.bukkit.Material;
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
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;


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

			if(!container.has(Main.attributeKeys.get( "itemAction"), PersistentDataType.DOUBLE))
				return;

			int itemActionType =  (int) Math.round(container.get(Main.attributeKeys.get( "itemAction"), PersistentDataType.DOUBLE));
			switch (itemActionType) {
				case 1 -> Menu_SBMainMenu(p);
			}
			event.setCancelled(true);

        }
	}

	@EventHandler
	public void PlayerInventoryInteract(InventoryClickEvent e) {
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

		if(!container.has(Main.attributeKeys.get( "itemAction"), PersistentDataType.DOUBLE))
			return;

		int itemActionType = (int) Math.round(container.get(Main.attributeKeys.get( "itemAction"), PersistentDataType.DOUBLE));
		e.setCancelled(true);
		p.closeInventory();
		switch (itemActionType) {
			case 1 -> Menu_SBMainMenu((Player)p);
		}
	}

	@EventHandler
	public void onPlayerSwapHands(PlayerSwapHandItemsEvent e) {
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

		if(container.has(Main.attributeKeys.get( "itemAction"), PersistentDataType.DOUBLE))
			e.setCancelled(true);
	}

	public static void Menu_SBMainMenu(Player player) {
		String[] guiSetup = {
				"bbbbbbbbb",
				"b...v...b",
				"b..tsr..b",
				"b..aie..b",
				"b..cp...b",
				"bbbbbbbbb",
		};
		Gui gui = Gui.normal()
				.setStructure(guiSetup)
				.addIngredient('b', new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)))

				.addIngredient('s', new SimpleItem(new ItemBuilder(Material.PLAYER_HEAD).setDisplayName(IridiumColorAPI.process("<SOLID:6699ff>Soyblock Stats"))
					.addLoreLines(CustomAttributes.getPlayerStatsFormat(player)) ))

				.addIngredient('p', new SimpleItem(new ItemBuilder(Material.COMMAND_BLOCK).setDisplayName(IridiumColorAPI.process("<SOLID:9999ff>Client Preferences"))
					.addLoreLines(IridiumColorAPI.process("<SOLID:cc66ff>Change your settings here."))) )

				.addIngredient('v', new SimpleItem(new ItemBuilder(Material.ENDER_CHEST).setDisplayName(IridiumColorAPI.process("<SOLID:6666ff>Vault"))
					.addLoreLines(IridiumColorAPI.process("<SOLID:4d4dff>Store & access items."))) )

				.addIngredient('r', new SimpleItem(new ItemBuilder(Material.IRON_CHESTPLATE).setDisplayName(IridiumColorAPI.process("<SOLID:33ccff>Armory"))
					.addLoreLines(IridiumColorAPI.process("<SOLID:33cccc>See your collection of items."))) )

				.addIngredient('t', new SimpleItem(new ItemBuilder(Material.ENDER_PEARL).setDisplayName(IridiumColorAPI.process("<SOLID:00cc66>Teleport"))
					.addLoreLines(IridiumColorAPI.process("<SOLID:00994d>Teleport to previously visited places."))) )

				.addIngredient('a', new SimpleItem(new ItemBuilder(Material.RED_BANNER).setDisplayName(IridiumColorAPI.process("<SOLID:ff3399>Achievements"))
					.addLoreLines(IridiumColorAPI.process("<SOLID:e60073>Collect rewards from milestones."))) )

				.addIngredient('i', new SkillsMenu())

				.addIngredient('e', new SimpleItem(new ItemBuilder(Material.IRON_SWORD).setDisplayName(IridiumColorAPI.process("<SOLID:ff9933>Equipment"))
					.addLoreLines(IridiumColorAPI.process("<SOLID:ffcc00>Change out your currently worn gear."))) )

				.addIngredient('i', new CraftingMenu())
				.build();

		Window window = Window.single()
				.setViewer(player)
				.setTitle("Soyblock Main Menu")
				.setGui(gui)
				.build();
		
		window.open();
	}/*
	private static void crafting_menu(Player p) {
		String[] guiSetup = {
				"bbbbbbbbb",
				"bcae....b",
				"b.......b",
				"b.......b",
				"bbbbbbbbb"
		};
		InventoryGui gui = new InventoryGui(Main.getInstance(), p, "Crafting Menus", guiSetup);
		gui.setFiller(new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1)); // fill the empty slots with this

		gui.addElement(new StaticGuiElement('b',
				ItemListHandler.getItemForDisplay(Material.BLACK_STAINED_GLASS_PANE),
				1,
				click -> {
					return true; // returning true will cancel the click event and stop taking the item
				},
				" "
		));

		gui.addElement(new StaticGuiElement('c',
				ItemListHandler.getItemForDisplay(Material.CRAFTING_TABLE),
				1,
				click -> {
					p.closeInventory();
					p.closeInventory();
					p.openWorkbench(null, true);
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:cc9900>Crafting Table")
		));

		gui.addElement(new StaticGuiElement('a',
				ItemListHandler.getItemForDisplay(Material.ANVIL),
				1,
				click -> {
					AnvilSubMenu.anvil_menu(p);
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<GRADIENT:3399ff>Anvil</GRADIENT:ccffff>")
		));

		gui.addElement(new StaticGuiElement('e',
				ItemListHandler.getItemForDisplay(Material.ENCHANTING_TABLE),
				1,
				click -> {
					EnchantingTableSubMenu.enchanting_table_menu(p);
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<GRADIENT:ff6699>Enchanting Table</GRADIENT:00ffcc>")
		));

		gui.show(p);
	}
	private static void skills(Player p) {
		String[] guiSetup = {
				"bbbbbbbbb",
				"b...m...b",
				"b..fch..b",
				"b...a...b",
				"bbbbbbbbb"
		};
		InventoryGui gui = new InventoryGui(Main.getInstance(), p, "Skills Menu", guiSetup);
		gui.setFiller(new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1)); // fill the empty slots with this

		int skillLevel = Skills.getLevel(p.getUniqueId(), "Combat");
		gui.addElement(new StaticGuiElement('c',
				ItemListHandler.getItemForDisplay(Material.IRON_SWORD),
				Math.min(skillLevel, 64),
				click -> {
					SkillSubMenus.combat_menu(p);
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:1652C3>Combat Skill Menu"),
				String.format("%d Skill Points Available", skillLevel),
				String.format("%.1f/%.1f EXP", Skills.getPlayerEXP(p.getUniqueId(),"Combat")-skillsConfig.skillEXPRequirementsCumulative.get("Combat")[skillLevel-1], skillsConfig.skillEXPRequirements.get("Combat")[skillLevel])
		));

		skillLevel = Skills.getLevel(p.getUniqueId(), "Foraging");
		gui.addElement(new StaticGuiElement('f',
				ItemListHandler.getItemForDisplay(Material.SPRUCE_SAPLING),
				Math.min(skillLevel, 64),
				click -> {
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:0C8536>Foraging Skill Menu"),
				String.format("%d Skill Points Available", skillLevel),
				String.format("%.1f/%.1f EXP", Skills.getPlayerEXP(p.getUniqueId(),"Foraging")-skillsConfig.skillEXPRequirementsCumulative.get("Foraging")[skillLevel-1], skillsConfig.skillEXPRequirements.get("Foraging")[skillLevel])
		));
		skillLevel = Skills.getLevel(p.getUniqueId(), "Alchemy");
		gui.addElement(new StaticGuiElement('h',
				ItemListHandler.getItemForDisplay(Material.BREWING_STAND),
				Math.min(skillLevel, 64),
				click -> {
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:C0D9F1>Alchemy Skill Menu"),
				String.format("%d Skill Points Available", skillLevel),
				String.format("%.1f/%.1f EXP", Skills.getPlayerEXP(p.getUniqueId(),"Alchemy")-skillsConfig.skillEXPRequirementsCumulative.get("Alchemy")[skillLevel-1], skillsConfig.skillEXPRequirements.get("Alchemy")[skillLevel])
		));

		skillLevel = Skills.getLevel(p.getUniqueId(), "Mining");
		gui.addElement(new StaticGuiElement('m',
				ItemListHandler.getItemForDisplay(Material.IRON_PICKAXE),
				Math.min(skillLevel, 64),
				click -> {
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:7B2EDB>Mining Skill Menu"),
				String.format("%d Skill Points Available", skillLevel),
				String.format("%.1f/%.1f EXP", Skills.getPlayerEXP(p.getUniqueId(),"Mining")-skillsConfig.skillEXPRequirementsCumulative.get("Mining")[skillLevel-1], skillsConfig.skillEXPRequirements.get("Mining")[skillLevel])
		));

		skillLevel = Skills.getLevel(p.getUniqueId(), "Farming");
		gui.addElement(new StaticGuiElement('a',
				ItemListHandler.getItemForDisplay(Material.IRON_HOE),
				Math.min(skillLevel, 64),
				click -> {
					return true; // returning true will cancel the click event and stop taking the item
				},
				IridiumColorAPI.process("<SOLID:666633>Farming Skill Menu"),
				String.format("%d Skill Points Available", skillLevel),
				String.format("%.1f/%.1f EXP", Skills.getPlayerEXP(p.getUniqueId(),"Farming")-skillsConfig.skillEXPRequirementsCumulative.get("Farming")[skillLevel-1], skillsConfig.skillEXPRequirements.get("Farming")[skillLevel])
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
	}*/
}

