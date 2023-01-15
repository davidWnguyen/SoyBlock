package markiplites.SoyBlock.ItemList;

import markiplites.SoyBlock.Item;

import markiplites.SoyBlock.ItemListHandler;
import markiplites.SoyBlock.Main;
import markiplites.SoyBlock.ItemClasses.Block;
import markiplites.SoyBlock.ItemClasses.Chestplate;
import markiplites.SoyBlock.ItemClasses.Sword;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


import java.util.ArrayList;
import java.util.HashMap;

public class blargySouls implements Listener
{
	public blargySouls() {
		init();
	}
	public void init() {
		//splurgy souls lel
		HashMap<String, Double> attributes = new HashMap<>();
		attributes.put("baseDamage", 1000.0);
		attributes.put("baseAttackSpeed", 3.0);
		attributes.put("attackReachBonusRaw", 5.0);
		attributes.put("critChance", 0.3);
		attributes.put("moveSpeed", 0.5);
		attributes.put("strengthScaling", 1.2);
		attributes.put("dexterityScaling", 1.35);
		attributes.put("strengthBonusRaw", 100.0);
		attributes.put("dexterityBonusRaw", 250.0);
		attributes.put("itemType", 1.0);
		attributes.put("rarity", 6.0);
		Sword murasama = new Sword("MURASAMA", "Murasama", Material.IRON_SWORD, attributes, "fuck da media!");
		murasama.finalizeItem("MURASAMA");
		
		attributes.clear();
		
		
		attributes.put("baseDamage", 150.0);
		attributes.put("baseAttacKSpeed", 1.0);
		attributes.put("attackReachBonusRaw", 1.5);
		attributes.put("critChance", 0.5);
		attributes.put("moveSpeed", 0.1);
		attributes.put("strengthScaling", 1.5);
		attributes.put("dexterityScaling", 0.9);
		attributes.put("strengthBonusRaw", 75.0);
		attributes.put("dexterityBonusRaw", 25.0);
		attributes.put("itemType", 1.0);
		attributes.put("rarity", 1.0);
		Sword nigger = new Sword("NIGGER", "Nigger", Material.WOODEN_SWORD, attributes, "niggar get out of china niggar :3333");
		nigger.finalizeItem("NIGGER");

		attributes.clear();

		attributes.put("toolType", 1.0);
		attributes.put("miningHardness", 10.0);
		attributes.put("miningSpeed", 4.0);
		attributes.put("miningFortune", 20.0);
		attributes.put("itemType", 5.0);
		attributes.put("rarity", 6.0);
		Item minor = new Item("MINOR", "Minor", Material.PRISMARINE_SHARD, attributes, "smiley face");
		minor.finalizeItem("MINOR");

		attributes.clear();

		attributes.put("blockDurability", 40.0);
		attributes.put("blockHardness", 5.0);
		attributes.put("blockTool", 1.0);
		attributes.put("blockExp", 5.0);
		attributes.put("rarity", 6.0);
		ItemStack[] blockLoot = {nigger.getItemStack().clone(),nigger.getItemStack().clone(),nigger.getItemStack().clone()};
		Block steel_block = new Block("STEEL_BLOCK", "Steel Block", Material.IRON_BLOCK, attributes, "i am a dwarf and diggy dig hole :###",blockLoot);
		steel_block.finalizeItem("STEEL_BLOCK");


		attributes.put("critChance", 0.3);
		attributes.put("moveSpeed", 0.5);
		attributes.put("strengthBonusRaw", 100.0);
		attributes.put("dexterityBonusRaw", 250.0);
		attributes.put("itemType", 101.0);
		attributes.put("rarity", 5.0);
		Chestplate cp = new Chestplate("chestplateOfDoom", "<GRADIENT:02e494>FAMILY CHEST DEATH DOOM CREST</GRADIENT:0252e4>",
				Material.LEATHER_CHESTPLATE, attributes, "carry on my wayward son","2 195 228");
		cp.finalizeItem("chestplateOfDoom");
	}



	@EventHandler
	public void PlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
		Bukkit.getLogger().info("SOYBLOCK: PlayerInteractEntityEvent triggered");
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		if(!item.equals(null)) {
			ItemMeta meta = item.getItemMeta();
			if(meta == null) return;
			PersistentDataContainer p = meta.getPersistentDataContainer();
			if(p == null) return;
			String itemID = p.get(new NamespacedKey(Main.getInstance(), "itemID"), PersistentDataType.STRING);
			if(itemID == null) return;
			switch(itemID) {
				case "MURASAMA" -> murasama_ability();
				
			}
		}
	}


	private void murasama_ability() {

	}
}