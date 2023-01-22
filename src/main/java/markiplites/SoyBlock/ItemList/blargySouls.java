package markiplites.SoyBlock.ItemList;

import markiplites.SoyBlock.Item;
import markiplites.SoyBlock.ItemClasses.Block;
import markiplites.SoyBlock.ItemClasses.Chestplate;
import markiplites.SoyBlock.ItemClasses.Sword;
import markiplites.SoyBlock.ItemClasses.Talisman;
import markiplites.SoyBlock.Main;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.function.Predicate;

public class blargySouls implements Listener
{
	private HashMap<String, Boolean> ability_cooldown = new HashMap<String, Boolean>();
	
	public blargySouls() {
		//cooldown booleans
		ability_cooldown.put("MURASAMA", true);


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

		attributes.clear();
		attributes.put("critChance", 0.3);
		attributes.put("moveSpeed", 0.5);
		attributes.put("strengthBonusRaw", 100.0);
		attributes.put("dexterityBonusRaw", 250.0);
		attributes.put("itemType", 101.0);
		attributes.put("rarity", 5.0);
		Chestplate cp = new Chestplate("chestplateOfDoom", "<GRADIENT:02e494>FAMILY CHEST DEATH DOOM CREST</GRADIENT:0252e4>",
				Material.LEATHER_CHESTPLATE, attributes, "carry on my wayward son","2 195 228");
		cp.finalizeItem("chestplateOfDoom");

		attributes.clear();
		attributes.put("regenerationBonus", 100.0);
		attributes.put("dexterityBonusRaw", 300.0);
		attributes.put("itemType", 200.0);
		attributes.put("rarity", 3.0);
		Talisman talismanExample = new Talisman("soulRing", "<GRADIENT:02e494>Soules Ring</GRADIENT:0252e4>",
				Material.BLAZE_POWDER, attributes, "hand it over, that thing. your dark soul.","soulTalismans");
		talismanExample.finalizeItem("soulRing");

		attributes.clear();
		attributes.put("regenerationBonus", 500.0);
		attributes.put("dexterityBonusRaw", 800.0);
		attributes.put("itemType", 200.0);
		attributes.put("rarity", 4.0);
		Talisman talismanExample2 = new Talisman("soulRingRare", "<GRADIENT:02e494>Darkest Soules Ring</GRADIENT:0252e4>",
				Material.BLAZE_POWDER, attributes, "hand it over, that thing. your dark soul.","soulTalismans");
		talismanExample2.finalizeItem("soulRingRare");

		attributes.clear();
		attributes.put("moveSpeed", 1.0);
		attributes.put("dexterityBonusRaw", -100.0);
		attributes.put("itemType", 200.0);
		attributes.put("rarity", 5.0);
		Talisman talismanExample3 = new Talisman("waterAffinityFeather", "<GRADIENT:02e494>Buoyancy Feather</GRADIENT:0252e4>",
				Material.FEATHER, attributes, "Gives bonus swag.","waterAffinity");
		talismanExample3.finalizeItem("waterAffinityFeather");

		attributes.clear();
		attributes.put("itemAction", 1.0);
		attributes.put("itemType", 0.0);
		attributes.put("rarity", 6.0);
		Item menu = new Item("SBMENU", "Main Menu", Material.BELL, attributes, "<SOLID:e40252>RIGHT CLICK:View your in-game stats.");
		menu.finalizeItem("SBMENU");

		attributes.clear();
		attributes.put("moveSpeed", 0.15);
		attributes.put("intelligenceBonusRaw", 100.0);
		Item jump_rod = new Item("JUMP_ROD", "Jump Rod", Material.STICK, attributes, "Move like a rabbit");
		jump_rod.finalizeItem("JUMP_ROD");
		
	}



	@EventHandler
	public void PlayerInteractAtEntityEvent(PlayerInteractEvent e) {
		if(e.getHand() != null && !e.getHand().equals(EquipmentSlot.HAND)) return; //fix double fire
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		if(!item.equals(null)) {
			ItemMeta meta = item.getItemMeta();
			if(meta == null) return;
			PersistentDataContainer p = meta.getPersistentDataContainer();
			if(p == null) return;
			String itemID = p.get(new NamespacedKey(Main.getInstance(), "itemID"), PersistentDataType.STRING);
			if(itemID == null) return;
			switch(itemID) {
				case "MURASAMA" -> murasama_ability(e);
				case "JUMP_ROD" -> jump_ability(e);
			}
		}
	}

	private void jump_ability(PlayerInteractEvent e) {

	}

	private void murasama_ability(PlayerInteractEvent e) {
		if(ability_cooldown.get("MURASAMA")) {
		double currentMana = Main.getAttributes().get(e.getPlayer()).get("Mana");
		if(currentMana - 25 >= 0) {
			Vector vec = traceToEntity(e);
			vec.add(new Vector(0, 0.5, 0));
			e.getPlayer().getWorld().spawnEntity(vec.toLocation(e.getPlayer().getWorld()), EntityType.PRIMED_TNT);
			Main.getAttributes().get(e.getPlayer()).replace("Mana", (currentMana - 25));
			ability_cooldown.replace("MURASAMA", false);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {ability_cooldown.replace("MURASAMA", true);}, 20); //1 second delay
		}
		else {
			e.getPlayer().sendMessage("§4You do not have enough mana for this!");
		}
		} else {
			e.getPlayer().sendMessage("§4Ability on cooldown.");
		}	
	}

	private Vector traceToEntity(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Predicate<Entity> ignoreList = i -> (i != p && i instanceof LivingEntity && !i.isDead() && i.getType() != EntityType.ARMOR_STAND);
		RayTraceResult trace = p.getWorld().rayTrace(p.getEyeLocation(), p.getEyeLocation().getDirection(), Main.getAttributes().get(p).getOrDefault("AttackRange", 3.0), FluidCollisionMode.NEVER, true, 0.5, ignoreList);
		if(trace == null ) {
			Vector vec = p.getEyeLocation().getDirection().normalize();
			vec.multiply(Main.getAttributes().get(p).getOrDefault("AttackRange", 3.0));
			vec.add(p.getLocation().toVector());
			return vec;
		}
		else if(trace.getHitEntity() != null) return trace.getHitEntity().getLocation().toVector();
		else if(trace.getHitBlock() != null) {
			DecimalFormat f = new DecimalFormat("#");
			Vector vec = trace.getHitBlock().getLocation().toVector();
			vec.setX(Double.parseDouble(f.format(vec.getX())));
			vec.setY(Double.parseDouble(f.format(vec.getY())));
			vec.setZ(Double.parseDouble(f.format(vec.getZ())));
			BlockFace face = trace.getHitBlockFace();
			if(face == BlockFace.WEST) vec.add(new Vector(-0.5, 0, 0));
			else if(face == BlockFace.EAST) vec.add(new Vector(0.5, 0, 0));
			else if(face == BlockFace.UP) vec.add(new Vector(0, 0.5, 0));
			else if(face == BlockFace.DOWN) vec.add(new Vector(0, -0.5, 0));
			else if(face == BlockFace.SOUTH) vec.add(new Vector(0, 0, .05));
			else if(face == BlockFace.NORTH) vec.add(new Vector(0, 0, -0.5));
			return vec;
		}

		return null;
	}
}