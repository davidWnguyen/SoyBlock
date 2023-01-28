package markiplites.SoyBlock.Lists;

import markiplites.SoyBlock.*;
import markiplites.SoyBlock.ItemClasses.*;
import org.bukkit.*;
import org.bukkit.Particle.DustTransition;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Predicate;

public class itemList implements Listener
{
	private final HashMap<String, Boolean> ability_cooldown = new HashMap<String, Boolean>();
	
	public itemList() {
		//cooldown booleans
		ability_cooldown.put("MURASAMA", true);
		ability_cooldown.put("JUMP_ROD", true);
		ability_cooldown.put("YAMATO", true);
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
		new Sword("MURASAMA", "Murasama", Material.IRON_SWORD, attributes, "fuck da media!");

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

		attributes.clear();

		attributes.put(attr.toolType, 1.0);
		attributes.put(attr.miningHardness, 10.0);
		attributes.put(attr.miningSpeed, 4.0);
		attributes.put(attr.miningFortune, 20.0);
		attributes.put(attr.itemType, 5.0);
		attributes.put(attr.rarity, 6.0);
		new Drill("MINOR", "Minor", Material.PRISMARINE_SHARD, attributes, "smiley face");

		attributes.clear();

		attributes.put(attr.blockDurability, 40.0);
		attributes.put(attr.blockHardness, 5.0);
		attributes.put(attr.blockTool, 1.0);
		attributes.put(attr.blockExp, 5.0);
		attributes.put(attr.rarity, 6.0);
		ItemStack[] blockLoot = {nigger.getItemStack().clone(),nigger.getItemStack().clone(),nigger.getItemStack().clone()};
		Block steel_block = new Block("STEEL_BLOCK", "Steel Block", Material.IRON_BLOCK, attributes, "i am a dwarf and diggy dig hole :###",blockLoot);

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

		attributes.clear();
		attributes.put(attr.regenerationBonus, 100.0);
		attributes.put(attr.dexterityBonusRaw, 300.0);
		attributes.put(attr.itemType, 200.0);
		attributes.put(attr.rarity, 3.0);
		Talisman talismanExample = new Talisman("soulRing", "<GRADIENT:02e494>Soules Ring</GRADIENT:0252e4>",
				Material.BLAZE_POWDER, attributes, "hand it over, that thing. your dark soul.","soulTalismans");

		attributes.clear();
		attributes.put(attr.regenerationBonus, 500.0);
		attributes.put(attr.dexterityBonusRaw, 800.0);
		attributes.put(attr.itemType, 200.0);
		attributes.put(attr.rarity, 4.0);
		Talisman talismanExample2 = new Talisman("soulRingRare", "<GRADIENT:02e494>Darkest Soules Ring</GRADIENT:0252e4>",
				Material.BLAZE_POWDER, attributes, "hand it over, that thing. your dark soul.","soulTalismans");

		attributes.clear();
		attributes.put(attr.moveSpeed, 1.0);
		attributes.put(attr.dexterityBonusRaw, -100.0);
		attributes.put(attr.itemType, 200.0);
		attributes.put(attr.rarity, 5.0);
		Talisman talismanExample3 = new Talisman("waterAffinityFeather", "<GRADIENT:02e494>Buoyancy Feather</GRADIENT:0252e4>",
				Material.FEATHER, attributes, "Gives bonus swag.","waterAffinity");

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

		attributes.clear();
		attributes.put(attr.moveSpeed, 0.15);
		attributes.put(attr.intelligenceBonusRaw, 100.0);
		Spell jump_rod = new Spell("JUMP_ROD", "Jump Rod", Material.STICK, attributes, "Move like a rabbit");
		
		attributes.clear();
		attributes.put(attr.intelligenceBonusRaw, 1000000.0);
		attributes.put(attr.intelligenceScaling, 2.0);
		Chestplate storm = new Chestplate("STORM_CP", "Storm Chestplate", Material.LEATHER_CHESTPLATE, attributes, "I am the storm that is approaching. Provoking, black clouds in isolation. I am reclaimour on my name!! Born in flames, I have been blessed. My family chest is a demon of death!!!!", "9 170 189");

		attributes.clear();
		attributes.put(attr.intelligenceBonusRaw, 500.0);
		attributes.put(attr.intelligenceScaling, 1.4);
		Spell elden_bong = new Spell("ELDEN_STAR", "Elden Star", Material.GOLDEN_SWORD, attributes, "Elden bong lol XD x3 :3333");

		attributes.clear();
		attributes.put(attr.baseDamage, 150.0);
		attributes.put(attr.intelligenceBonusRaw, 500.0);
		attributes.put(attr.intelligenceScaling, 1.5);
		attributes.put(attr.strengthBonusRaw, 500.0);
		attributes.put(attr.strengthScaling, 1.4);
		attributes.put(attr.dexterityBonusRaw, 400.0);
		attributes.put(attr.dexterityScaling, 1.35);
		attributes.put(attr.attackReachBonusRaw, 7.5);
		attributes.put(attr.rarity, 6.0);
		Sword yamato = new Sword("YAMATO", "Yamato", Material.IRON_SWORD, attributes, "WOOOOOOO DO THE VERGIL!!!!\n\nRight click: Judgement Cut\n");
	}



	@EventHandler
	public void PlayerInteractAtEntityEvent(PlayerInteractEvent e) {
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
			case "JUMP_ROD" -> {if(rightClick) jump_ability(e);}
			case "ELDEN_STAR" -> {if(rightClick) star_ability(e);}
			case "YAMATO" -> {if(rightClick) judgement_cut(e);}
		}
	}

	private void judgement_cut(PlayerInteractEvent e) {
		if(!check_ready(e.getPlayer(), "YAMATO", 75.0, 10)) return;

		Player p = e.getPlayer();
		Vector vec = traceToEntity(e, 15.0);
		Predicate<Entity> ignoreList = f -> (f != e.getPlayer() && f instanceof LivingEntity && !f.isDead() && f.getType() != EntityType.ARMOR_STAND);
		Collection<Entity> entities = e.getPlayer().getWorld().getNearbyEntities(vec.toLocation(e.getPlayer().getWorld()), 3.1, 3.1, 3.1, ignoreList);
		Location loc = vec.toLocation(e.getPlayer().getWorld());

		for(double i = 0;i <= Math.PI;i += Math.PI/15) {
			double radius = Math.sin(i);
			double y = Math.cos(i);
			for(double j = 0;j < Math.PI*2;j+= Math.PI / 15) {
				Location temp = loc.clone();
				double x = Math.cos(j) * radius;
				double z = Math.sin(j) * radius;
				temp.add(5*x, 5*y, 5*z);
				Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(255-((int)Math.round(i*20.0)), 0, 255), 3.0f);
				loc.getWorld().spawnParticle(Particle.REDSTONE, temp, 1, 0.0, 0.0, 0.0, dust);
			}
		}
		for(Entity entity : entities) {
			loc.getWorld().spawnParticle(Particle.CRIT_MAGIC, entity.getLocation(), 30, 0.0, 0.0, 0.0);
			EntityHandling.dealDamageToEntity((LivingEntity)entity, CustomAttributes.getDamageModified(p, false), false, 1);
		}
		Vector speed = p.getLocation().getDirection().multiply(1.15);
		speed = p.getVelocity().add(speed);
		p.setVelocity(speed.add(new Vector(0, 0.001, 0)));
	}

	private void star_ability(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		loc.setY(loc.getY()+5);
		ArrayList<ArmorStand> projectiles = new ArrayList<>();
		for(int i = 0;i < 15;i++) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
				ArmorStand ent = (ArmorStand)p.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
				ent.setVisible(false);
				ent.setBasePlate(false);
				ent.setCollidable(false);
				ent.setArms(false);
				ent.setSmall(false);
				ent.setSilent(true);
				ent.setCustomName("Armor Stand");
				ent.setCustomNameVisible(false);
				ent.setCanPickupItems(false);
				ent.setGliding(false);
				ent.setInvulnerable(true);
				ent.setCustomNameVisible(true);
				ent.setGravity(true);
				projectiles.add(ent);
				p.getWorld().playEffect(loc, Effect.BLAZE_SHOOT, 0, 30);
			}, 10);
		}
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
			for(ArmorStand proj : projectiles) {
				Predicate<Entity> ignoreList = f -> (f != p && f instanceof LivingEntity && !f.isDead() && f.getType() != EntityType.ARMOR_STAND);
				Collection<Entity> entities = p.getWorld().getNearbyEntities(proj.getLocation(), 15, 15, 15, ignoreList);
				if(!entities.isEmpty()) {
					p.getWorld().spawnParticle(Particle.FLAME, proj.getLocation(), 0);
					Entity closest = (Entity)entities.toArray()[0];
					for(Entity ent : entities) {
						if ( proj.getLocation().distance(ent.getLocation()) < proj.getLocation().distance(closest.getLocation()) ) {
						closest = ent;
						}
					}
					proj.setVelocity(closest.getLocation().subtract(proj.getLocation()).toVector().multiply(0.2));
					Collection<Entity> check = p.getWorld().getNearbyEntities(proj.getLocation(), 0.5, 0.5, 0.5, ignoreList);
					if(!check.isEmpty()) {
						Entity ent = (Entity)check.toArray()[0];
						EntityHandling.dealDamageToEntity((LivingEntity)ent, CustomAttributes.getDamageModified(p, false), false, 1);
						proj.remove();
					}
				}
				else {
					proj.remove();
				}
			}
		}, 20, 5);

	}


	private void jump_ability(PlayerInteractEvent e) {
		if(!check_ready(e.getPlayer(), "JUMP_ROD", 25.0, 20)) return;

		Player p = e.getPlayer();
		Vector vector = p.getEyeLocation().getDirection();
		vector.multiply(1.01);
		vector = p.getVelocity().add(vector);
		p.setVelocity(vector.add(new Vector(0, 1, 0)));
	}

	private void murasama_ability(PlayerInteractEvent e, Player p) {
		if (!check_ready(e.getPlayer(), "MURASAMA", 100.0, 20)) return;
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

			for (int i = -15; i < 16; i++) {
				final int finalI = i;
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
					Vector tmpForward = forward.clone().multiply(4.0 - Math.pow(Math.abs(finalI * 0.1), 2.1));
					Vector tmpRight = right.clone().multiply(finalI * 0.15);
					Vector tmpUp = up.clone().multiply(finalI * 0.07);
					Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 70 + finalI * 3, 0), 1.2F);

					Vector finalVec = new Vector();
					finalVec.add(p.getEyeLocation().toVector());
					finalVec.add(tmpForward);
					finalVec.add(tmpRight);
					finalVec.add(tmpUp);
					p.spawnParticle(Particle.REDSTONE, finalVec.toLocation(p.getWorld()), 20, dustOptions);
				}, (int) ((i + 15) / 6));
			}

			EntityHandling.dealAOEAngledDamage(p, 60.0, 6.0, CustomAttributes.getDamageModified(p, true) * 3.5, true, 0);
		}, 5);
	}

	private Vector traceToEntity(PlayerInteractEvent e, double range) {
		Player p = e.getPlayer();
		Predicate<Entity> ignoreList = i -> (i != p && i instanceof LivingEntity && !i.isDead() && i.getType() != EntityType.ARMOR_STAND);
		RayTraceResult trace = p.getWorld().rayTrace(p.getEyeLocation(), p.getEyeLocation().getDirection(), range, FluidCollisionMode.NEVER, true, 0.5, ignoreList);
		if(trace == null ) {
			Vector vec = p.getEyeLocation().getDirection().normalize();
			vec.multiply(range);
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
			switch(face){
				case WEST -> vec.add(new Vector(-0.5, 0, 0));
				case EAST -> vec.add(new Vector(0.5, 0, 0));
				case UP -> vec.add(new Vector(0, 0.5, 0));
				case DOWN -> vec.add(new Vector(0, -0.5, 0));
				case SOUTH -> vec.add(new Vector(0, 0, .05));
				case NORTH -> vec.add(new Vector(0, 0, -0.5));
			}
			return vec;
		}

		return null;
	}



	private boolean check_ready(Player p, String itemID, double manaCost, int delay) { //ability check / set x333
		if(!ability_cooldown.get(itemID)) {p.sendMessage("§4Ability on cooldown."); return false;}
		UUID uuid = p.getUniqueId();

		double currentMana = Main.getAttributes().get(uuid).get("Mana");
		if(!(currentMana - manaCost >= 0)) {p.sendMessage("§4You do not have enough mana for this!");return false;}
		Main.getAttributes().get(uuid).replace("Mana", (currentMana - manaCost));
		ability_cooldown.replace(itemID, false);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {ability_cooldown.replace(itemID, true);}, delay);
		return true;
	}	


}