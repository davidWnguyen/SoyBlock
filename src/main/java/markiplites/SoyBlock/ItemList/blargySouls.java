package markiplites.SoyBlock.ItemList;

import markiplites.SoyBlock.*;
import markiplites.SoyBlock.ItemClasses.*;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.HashMap;

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
		HashMap<attr, Double> attributes = new HashMap<>();
		attributes.put(attr.baseDamage, 1000.0);
		attributes.put(attr.baseAttackSpeed, 3.0);
		attributes.put(attr.attackReachBonusRaw, 5.0);
		attributes.put(attr.critChance, 0.3);
		attributes.put(attr.moveSpeed, 0.5);
		attributes.put(attr.strengthScaling, 1.2);
		attributes.put(attr.dexterityScaling, 1.35);
		attributes.put(attr.strengthBonusRaw, 100.0);
		attributes.put(attr.dexterityBonusRaw, 250.0);
		attributes.put(attr.rarity, 6.0);
		Sword murasama = new Sword("MURASAMA", "Murasama", Material.IRON_SWORD, attributes, "fuck da media!");
		
		attributes.clear();
		
		
		attributes.put(attr.baseDamage, 150.0);
		attributes.put(attr.baseAttackSpeed, 1.0);
		attributes.put(attr.attackReachBonusRaw, 1.5);
		attributes.put(attr.critChance, 0.5);
		attributes.put(attr.moveSpeed, 0.1);
		attributes.put(attr.strengthScaling, 1.5);
		attributes.put(attr.dexterityScaling, 0.9);
		attributes.put(attr.strengthBonusRaw, 75.0);
		attributes.put(attr.dexterityBonusRaw, 25.0);
		attributes.put(attr.itemType, 1.0);
		attributes.put(attr.rarity, 1.0);
		Sword nigger = new Sword("NIGGER", "Nigger", Material.WOODEN_SWORD, attributes, "niggar get out of china niggar :3333");
		nigger.finalizeItem("NIGGER");

		attributes.clear();

		attributes.put(attr.toolType, 1.0);
		attributes.put(attr.miningHardness, 10.0);
		attributes.put(attr.miningSpeed, 4.0);
		attributes.put(attr.miningFortune, 20.0);
		attributes.put(attr.itemType, 5.0);
		attributes.put(attr.rarity, 6.0);
		Drill minor = new Drill("MINOR", "Minor", Material.PRISMARINE_SHARD, attributes, "smiley face");
		minor.finalizeItem("MINOR");

		attributes.clear();

		attributes.put(attr.blockDurability, 40.0);
		attributes.put(attr.blockHardness, 5.0);
		attributes.put(attr.blockTool, 1.0);
		attributes.put(attr.blockExp, 5.0);
		attributes.put(attr.rarity, 6.0);
		ItemStack[] blockLoot = {nigger.getItemStack().clone(),nigger.getItemStack().clone(),nigger.getItemStack().clone()};
		Block steel_block = new Block("STEEL_BLOCK", "Steel Block", Material.IRON_BLOCK, attributes, "i am a dwarf and diggy dig hole :###",blockLoot);
		steel_block.finalizeItem("STEEL_BLOCK");

		attributes.clear();
		attributes.put(attr.critChance, 0.3);
		attributes.put(attr.moveSpeed, 0.5);
		attributes.put(attr.strengthBonusRaw, 100.0);
		attributes.put(attr.dexterityBonusRaw, 250.0);
		attributes.put(attr.intelligenceBonusRaw, 900.0);
		attributes.put(attr.itemType, 101.0);
		attributes.put(attr.rarity, 5.0);
		Chestplate cp = new Chestplate("chestplateOfDoom", "<GRADIENT:02e494>FAMILY CHEST DEATH DOOM CREST</GRADIENT:0252e4>",
				Material.LEATHER_CHESTPLATE, attributes, "carry on my wayward son","2 195 228");
		cp.finalizeItem("chestplateOfDoom");

		attributes.clear();
		attributes.put(attr.regenerationBonus, 100.0);
		attributes.put(attr.dexterityBonusRaw, 300.0);
		attributes.put(attr.itemType, 200.0);
		attributes.put(attr.rarity, 3.0);
		Talisman talismanExample = new Talisman("soulRing", "<GRADIENT:02e494>Soules Ring</GRADIENT:0252e4>",
				Material.BLAZE_POWDER, attributes, "hand it over, that thing. your dark soul.","soulTalismans");
		talismanExample.finalizeItem("soulRing");

		attributes.clear();
		attributes.put(attr.regenerationBonus, 500.0);
		attributes.put(attr.dexterityBonusRaw, 800.0);
		attributes.put(attr.itemType, 200.0);
		attributes.put(attr.rarity, 4.0);
		Talisman talismanExample2 = new Talisman("soulRingRare", "<GRADIENT:02e494>Darkest Soules Ring</GRADIENT:0252e4>",
				Material.BLAZE_POWDER, attributes, "hand it over, that thing. your dark soul.","soulTalismans");
		talismanExample2.finalizeItem("soulRingRare");

		attributes.clear();
		attributes.put(attr.moveSpeed, 1.0);
		attributes.put(attr.dexterityBonusRaw, -100.0);
		attributes.put(attr.itemType, 200.0);
		attributes.put(attr.rarity, 5.0);
		Talisman talismanExample3 = new Talisman("waterAffinityFeather", "<GRADIENT:02e494>Buoyancy Feather</GRADIENT:0252e4>",
				Material.FEATHER, attributes, "Gives bonus swag.","waterAffinity");
		talismanExample3.finalizeItem("waterAffinityFeather");

		attributes.clear();
		attributes.put(attr.itemAction, 1.0);
		attributes.put(attr.itemType, 0.0);
		attributes.put(attr.rarity, 6.0);
		Item menu = new Item("SBMENU", "Main Menu", Material.BELL, attributes, "<SOLID:e40252>RIGHT CLICK:View your in-game stats.",0.0);
		menu.finalizeItem("SBMENU");

		attributes.clear();
		attributes.put(attr.baseDamage, 300.0);
		attributes.put(attr.baseAttackSpeed, 2.0);
		attributes.put(attr.critChance, 0.6);
		attributes.put(attr.critDamage, 0.5);
		attributes.put(attr.dexterityScaling, 1.9);
		attributes.put(attr.weaponType, 1.0);
		attributes.put(attr.projectileSpeed, 2.0);
		attributes.put(attr.itemType, 1.0);
		attributes.put(attr.rarity, 2.0);
		Bow sploch = new Bow("SPLOCH", "<GRADIENT:cc00ff>SPLOCH! goes the bug</GRADIENT:6666ff>",
				Material.FISHING_ROD, attributes, "<SOLID:0066ff>Primary Attack: Shoots out an arrow at high velocity.");
		sploch.finalizeItem("SPLOCH");
	}



	@EventHandler
	public void PlayerInteract(PlayerInteractEvent e) {
		if(e.getHand() != null && !e.getHand().equals(EquipmentSlot.HAND)) return; //fix double fire

		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		if(item == null)
			return;

		ItemMeta meta = item.getItemMeta();
		if(meta == null)
			return;

		PersistentDataContainer p = meta.getPersistentDataContainer();
		String itemID = p.get(new NamespacedKey(Main.getInstance(), "itemID"), PersistentDataType.STRING);
		if(itemID == null) return;

		Action a = e.getAction();
		boolean leftClick = a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK;
		boolean rightClick = a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK;

		switch(itemID) {
			case "MURASAMA" -> {if(rightClick) murasama_ability(e,e.getPlayer());}
		}
	}


	private void murasama_ability(PlayerInteractEvent e, Player p) {
		if(!ability_cooldown.get("MURASAMA"))
			{e.getPlayer().sendMessage("§4Ability on cooldown.");return;}

		double currentMana = Main.getAttributes().get(e.getPlayer()).get("Mana");
		if(currentMana - 25 < 0)
			{e.getPlayer().sendMessage("§4You do not have enough mana for this!");return;}

		//PART 1
		//Particle
		Vector addVec = p.getEyeLocation().getDirection();
		addVec.multiply(0.2);
		p.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, p.getEyeLocation(),
				1, addVec.getX(), addVec.getY(), addVec.getZ());
		//Sound
		p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1.0F, 1.0F);
		//PART 2: Delayed slash
		//HOLY SHIT
		Location loc = p.getEyeLocation();
		Vector forward = loc.getDirection();
		Vector right = Main.getRightVector(loc);
		Vector up = Main.getUpVector(loc);
		//WHAT THE FUCK BOOOOOOOM
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
			p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0F, 1.0F);

			for(int i=-15;i<16;i++) {
				final int finalI = i;
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
					Vector tmpForward = forward.clone().multiply(4.0 - Math.pow(Math.abs(finalI * 0.1), 2.1));
					Vector tmpRight = right.clone().multiply(finalI * 0.15);
					Vector tmpUp = up.clone().multiply(finalI * 0.07);
					Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 70+finalI*3, 0), 1.2F);

					Vector finalVec = new Vector();
					finalVec.add(p.getEyeLocation().toVector());
					finalVec.add(tmpForward);
					finalVec.add(tmpRight);
					finalVec.add(tmpUp);
					p.spawnParticle(Particle.REDSTONE, finalVec.toLocation(p.getWorld()), 20, dustOptions);
				},(int)((i+15)/6));
			}

			EntityHandling.dealAOEAngledDamage(p, 60.0, 6.0, CustomAttributes.getDamageModified(p,true)*3.5, true, 0);
			}, 5);

		Main.getAttributes().get(e.getPlayer()).replace("Mana", (currentMana - 25));
		ability_cooldown.replace("MURASAMA", false);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {ability_cooldown.replace("MURASAMA", true);}, 60); //1 second delay
	}
}