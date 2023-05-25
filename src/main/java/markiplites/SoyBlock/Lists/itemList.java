package markiplites.SoyBlock.Lists;
import markiplites.SoyBlock.*;
import markiplites.SoyBlock.Item;
import markiplites.SoyBlock.ItemClasses.*;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.*;
import java.util.function.Predicate;

public class itemList implements Listener
{
	public static final HashMap<UUID, HashMap<String, Double>> ability_cooldown = new HashMap<>();
	
	public itemList() {
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
		attributes.put(attr.critChance, 0.3);
		attributes.put(attr.moveSpeed, 0.5);
		attributes.put(attr.strengthBonusRaw, 100.0);
		attributes.put(attr.dexterityBonusRaw, 250.0);
		attributes.put(attr.intelligenceBonusRaw, 900.0);
		attributes.put(attr.itemType, 101.0);
		attributes.put(attr.rarity, 5.0);
		new Chestplate("CHESTPLATE_OF_DOOM", "<GRADIENT:02e494>FAMILY CHEST DEATH DOOM CREST</GRADIENT:0252e4>",
				Material.LEATHER_CHESTPLATE, attributes, "carry on my wayward son","2 195 228");

		attributes.clear();
		attributes.put(attr.regenerationBonus, 100.0);
		attributes.put(attr.dexterityBonusRaw, 300.0);
		attributes.put(attr.itemType, 200.0);
		attributes.put(attr.rarity, 3.0);
		new Talisman("SOUL_RING", "<GRADIENT:02e494>Soules Ring</GRADIENT:0252e4>",
				Material.BLAZE_POWDER, attributes, "hand it over, that thing. your dark soul.","soulTalismans");

		attributes.clear();
		attributes.put(attr.regenerationBonus, 500.0);
		attributes.put(attr.dexterityBonusRaw, 800.0);
		attributes.put(attr.itemType, 200.0);
		attributes.put(attr.rarity, 4.0);
		new Talisman("SOUL_RING_RARE", "<GRADIENT:02e494>Darkest Soules Ring</GRADIENT:0252e4>",
				Material.BLAZE_POWDER, attributes, "hand it over, that thing. your dark soul.","soulTalismans");

		attributes.clear();
		attributes.put(attr.moveSpeed, 1.0);
		attributes.put(attr.dexterityBonusRaw, -100.0);
		attributes.put(attr.itemType, 200.0);
		attributes.put(attr.rarity, 5.0);
		new Talisman("WATER_AFFINITY_FEATHER", "<GRADIENT:02e494>Buoyancy Feather</GRADIENT:0252e4>",
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
		new Bow("SPLOCH", "<GRADIENT:cc00ff>SPLOCH! goes the bug</GRADIENT:6666ff>",
				Material.FISHING_ROD, attributes, "<SOLID:0066ff>Primary Attack: Shoots out an arrow at high velocity.");

		attributes.clear();
		attributes.put(attr.moveSpeed, 0.15);
		attributes.put(attr.intelligenceBonusRaw, 100.0);
		new Spell("JUMP_ROD", "Jump Rod", Material.STICK, attributes, "Move like a rabbit");
		
		attributes.clear();
		attributes.put(attr.intelligenceBonusRaw, 150.0);
		attributes.put(attr.intelligenceScaling, 1.3);
		attributes.put(attr.absorptionBonusRaw, 15.0);
		attributes.put(attr.healthBonusRaw, 250.0);
		new Chestplate("STORM_CP", "Storm Chestplate", Material.LEATHER_CHESTPLATE, attributes, "I am the storm that is approaching. Provoking, black clouds in isolation. I am reclaimour on my name!! Born in flames, I have been blessed. My family chest is a demon of death!!!!", "9 170 189");

		attributes.clear();
		attributes.put(attr.baseDamage, 15.0);
		attributes.put(attr.intelligenceBonusRaw, 500.0);
		attributes.put(attr.intelligenceScaling, 1.4);
		new Spell("ELDEN_STAR", "Elden Star", Material.GOLDEN_SWORD, attributes, "Elden bong lol XD x3 :3333");

		attributes.clear();
		attributes.put(attr.baseDamage, 150.0);
		attributes.put(attr.baseAttackSpeed, 4.0);
		attributes.put(attr.intelligenceBonusRaw, 500.0);
		attributes.put(attr.intelligenceScaling, 1.5);
		attributes.put(attr.strengthBonusRaw, 500.0);
		attributes.put(attr.strengthScaling, 1.4);
		attributes.put(attr.dexterityBonusRaw, 400.0);
		attributes.put(attr.dexterityScaling, 1.35);
		attributes.put(attr.attackReachBonusRaw, 7.5);
		attributes.put(attr.itemAbilityTotalCooldown, 2.0);
		attributes.put(attr.itemTexture, 1000001.0);
		attributes.put(attr.rarity, 6.0);
		new Sword("YAMATO", "Yamato", Material.IRON_SWORD, attributes, "WOOOOOOO DO THE VERGIL!!!!\n\nRight click: Judgement Cut\n");
		
		attributes.clear();
		new Spell("SPAWNER_ZOMBIEKING", "Spawn Zombie_King", Material.STICK, attributes, "zmb");

		new Spell("SPAWNER_SKELETONKING", "Spawn Skeleton_King", Material.STICK, attributes, ":skull: meme");
		new Spell("SPAWNER_TARGETDUMMY", "Spawn Target Dummy", Material.STICK, attributes, "Hitting P100");

		attributes.put(attr.baseDamage, 15.0);
		attributes.put(attr.attackReachBonusRaw, 2.1);
		attributes.put(attr.strengthBonusRaw, 10.0);
		attributes.put(attr.dexterityBonusRaw, 15.0);
		new Sword("DIAMONDSWORD", "Sword", Material.DIAMOND_SWORD, attributes, "Do you like my sword, sword?");

		attributes.clear();

		attributes.put(attr.baseDamage, 35.0);
		attributes.put(attr.attackReachBonusRaw, 3.0);
		attributes.put(attr.baseAttackSpeed, 1.0);
		attributes.put(attr.strengthBonusRaw, 15.0);
		attributes.put(attr.strengthScaling, 0.9);
		attributes.put(attr.toolType, 2.0);
		attributes.put(attr.miningSpeed, 6.0);
		attributes.put(attr.miningHardness, 4.0);
		attributes.put(attr.miningFortune, 30.0);
		new Axe("NETHERITE_AXE", "Netherite Axe", Material.NETHERITE_AXE, attributes, "");

		attributes.clear();

		attributes.put(attr.baseDamage, 10.0);
		attributes.put(attr.attackReachBonusRaw, 3.0);
		attributes.put(attr.baseAttackSpeed, 4.0);
		attributes.put(attr.dexterityBonusRaw, 15.0);
		attributes.put(attr.dexterityScaling, 0.9);
		attributes.put(attr.toolType, 3.0);
		attributes.put(attr.miningSpeed, 5.0);
		attributes.put(attr.miningHardness, 4.0);
		attributes.put(attr.miningFortune, 30.0);
		new Hoe("NETHERITE_HOE", "Netherite Hoe", Material.NETHERITE_HOE, attributes, "");

		attributes.clear();

		attributes.put(attr.baseDamage, 5000.0);
		attributes.put(attr.weaponType, 2.0);
		attributes.put(attr.baseAttackSpeed, 2.0);
		attributes.put(attr.strengthBonusRaw, 200.0);
		attributes.put(attr.strengthScaling, 2.0);
		attributes.put(attr.projectileSpeed, 1.5);
		attributes.put(attr.blastRadius, 3.25);
		attributes.put(attr.blastFalloff, 0.35);
		attributes.put(attr.rarity, 6.0);
		attributes.put(attr.itemTexture, 1000000.0);
		new Explosives("BLUNT_LAUNCHER", "<GRADIENT:cc9900>Blunt Launcher</GRADIENT:009933>", Material.WHEAT, attributes, "rawket lawnchair");

		attributes.clear();

		attributes.put(attr.baseDamage, 5000.0);
		attributes.put(attr.baseAttackSpeed, 0.3);
		attributes.put(attr.strengthBonusRaw, 200.0);
		attributes.put(attr.strengthScaling, 3.0);
		attributes.put(attr.projectileSpeed, 1.5);
		attributes.put(attr.drawTime, 3.0);
		attributes.put(attr.rarity, 6.0);
		new Bow("LIGHTNING_GREATBOW", "<GRADIENT:ffff00>Lightning Greatbow</GRADIENT:ff9999>", Material.BOW, attributes, "");
		new Sword("LIGHTNING_TRIDENT", "<GRADIENT:ffff00>Lightning Trident</GRADIENT:ff9999>", Material.TRIDENT, attributes, "");
		new Bow("REPEATER_CROSSBOW", "<GRADIENT:ffff00>Repeater Crossbow</GRADIENT:ff9999>", Material.CROSSBOW, attributes, "");
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
		String itemID = p.get(Main.attributeKeys.get( "itemID"), PersistentDataType.STRING);
		if(itemID == null) return;

		Action a = e.getAction();
		boolean leftClick = a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK;
		boolean rightClick = a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK;

		switch(itemID) {
			case "MURASAMA" -> {if(rightClick) murasama_ability(e,meta);}
			case "JUMP_ROD" -> {if(rightClick) jump_ability(e,meta);}
			case "ELDEN_STAR" -> {if(rightClick) star_ability(e,meta);}
			case "YAMATO" -> {if(rightClick) judgement_cut(e,meta);}
			case "SPAWNER_ZOMBIEKING" -> {if(rightClick) spawn(e, "ZOMBIE_KING");}
			case "SPAWNER_SKELETONKING" -> {if(rightClick) spawn(e, "SKELETON_KING");}
			case "SPAWNER_TARGETDUMMY" -> {if(rightClick) spawn(e, "TARGET_DUMMY");}
			case "NIGGER" -> {if(rightClick) kill(e);}
		}
	}


	private void kill(PlayerInteractEvent e) {
		e.getPlayer().sendMessage("NIGGER: Killed you.");
		EntityHandling.dealDamageToEntity(e.getPlayer(), null, Main.getAttributes().get(e.getPlayer().getUniqueId()).get("MaxHealth"), false, 1);
	}

	private void spawn(PlayerInteractEvent e, String id) {
		Vector vec = traceToEntity(e, 10.0);
		Ent.spawnCustomEntity(id, vec.toLocation(e.getPlayer().getWorld()));
	}

	private void judgement_cut(PlayerInteractEvent e, ItemMeta meta) {
		if(!check_ready(e.getPlayer(), "YAMATO", 75.0, 0.5, meta)) return;

		Player p = e.getPlayer();
		Vector vec = traceToEntity(e, 15.0);
		Predicate<Entity> ignoreList = f -> ( f instanceof LivingEntity && !(f instanceof Player) && !f.isDead() && f.getType() != EntityType.ARMOR_STAND);
		Collection<Entity> entities = e.getPlayer().getWorld().getNearbyEntities(vec.toLocation(e.getPlayer().getWorld()), 4.1, 4.1, 4.1, ignoreList);
		Location loc = vec.toLocation(e.getPlayer().getWorld());
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1.75f,1f);

		//Run particles multithreaded so performance doesn't suck ass
		for(int i = 0;i< 16;i++) {
			int finalI = i;
			new BukkitRunnable() {
				final double random = Math.random()-0.5;
				final double randomX = 3.5*(Math.random()-0.5);
				final double randomY = 3.5*(Math.random()-0.5);
				final double randomZ = 3.5*(Math.random()-0.5);
				@Override
				public void run() {
					Location temp = loc.clone();
					loc.setYaw((float) (random*360));
					loc.setPitch((float) (random*180));
					Vector a = temp.getDirection();
					a.multiply(-4);
					temp.add(a);
					Vector addDir = temp.getDirection();
					Vector randVec = new Vector(randomX, randomY, randomZ);
					for (int i = 0; i < 40; i++ ) {
						Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(255, 255, 255), 0.7f);
						loc.getWorld().spawnParticle(Particle.REDSTONE, temp.clone().add(addDir.clone().multiply(i*0.2)).add(randVec), 0, 0.0, 0.0, 0.0, dust);
						dust = new Particle.DustOptions(Color.fromRGB(150, 230, 255), 0.7f);
						loc.getWorld().spawnParticle(Particle.REDSTONE, temp.clone().add(addDir.clone().multiply(i*0.2)).add(randVec), 0, 0.0, 0.0, 0.0, dust);
					}
					if((finalI & 1) == 1) {
						p.getWorld().playSound(loc, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 0.5f, (float) (1 + finalI * 0.035));
						for(Entity entity : entities) {
							loc.getWorld().spawnParticle(Particle.CRIT_MAGIC, entity.getLocation(), 30, 0.0, 0.0, 0.0);
							EntityHandling.dealDamageToEntity((LivingEntity)entity, p, 0.1*CustomAttributes.getDamageModified(p.getUniqueId(), false), false, 1);
						}
					}
				}
			}.runTaskLater(Main.getInstance(), i+2);
		}
		new BukkitRunnable() {
			@Override
			public void run() {
				int particleCount = 40;
				for (double i = 0; i <= Math.PI; i += Math.PI / particleCount) {
					double radius = Math.sin(i);
					double y = Math.cos(i);
					for (double j = 0; j < Math.PI * 2; j += Math.PI / particleCount) {
						Location temp = loc.clone();
						double x = Math.cos(j) * radius;
						double z = Math.sin(j) * radius;
						temp.add(4 * x, 4 * y, 4 * z);
						Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(255 - ((int) Math.round(i * 81)), 0, 255), 1.0f);
						loc.getWorld().spawnParticle(Particle.REDSTONE, temp, 0, 0.0, 0.0, 0.0, dust);
					}
				}
				p.getWorld().playSound(loc, Sound.ENTITY_EVOKER_FANGS_ATTACK, 2.0f,1f);
				for(Entity entity : entities) {
					loc.getWorld().spawnParticle(Particle.CRIT_MAGIC, entity.getLocation(), 30, 0.0, 0.0, 0.0);
					EntityHandling.dealDamageToEntity((LivingEntity)entity, p, 2.0*CustomAttributes.getDamageModified(p.getUniqueId(), true), true, 1);
				}
			}
		}.runTaskLater(Main.getInstance(), 20);

		Vector speed = p.getLocation().getDirection().multiply(1.05).setY(0);
		speed = p.getVelocity().add(speed);
		p.setVelocity(speed.add(new Vector(0, 0.15, 0)));
	}

	private void star_ability(PlayerInteractEvent e, ItemMeta meta) {
		if(!check_ready(e.getPlayer(), "ELDEN_STAR", 30.0, 2.5, meta)) return;

		Player p = e.getPlayer();
		Location loc = p.getLocation();
		loc.setY(loc.getY()+5);
		ArrayList<Snowball> projectiles = new ArrayList<>();
		Snowball mainStar = p.launchProjectile(Snowball.class, p.getLocation().getDirection().multiply(0.2));
		final double damageDealt = CustomAttributes.getDamageModified(p.getUniqueId(),false)*0.1;
		mainStar.setGravity(false);
		mainStar.setItem(new ItemStack(Material.GLOWSTONE));
		mainStar.getWorld().playSound(mainStar.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR, 1.0f,1.0f);

		Predicate<Entity> ignoreList = f -> (f != p && f instanceof LivingEntity && !f.isDead() && f.getType() != EntityType.ARMOR_STAND);
		new BukkitRunnable() {
			int timerCount = 0;
			@Override
			public void run() {
				if(timerCount > 20)
				{
					for(Snowball star : projectiles) {
						star.remove();
					}
					mainStar.remove();
					cancel();
					return;
				}
				if(mainStar.isDead())
				{
					//When it collides/dies, cause an explosion.
					mainStar.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, mainStar.getLocation(), 20);
					mainStar.getWorld().playSound(mainStar.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.2f,0.7f);
					Collection<Entity> entities = p.getWorld().getNearbyEntities(mainStar.getLocation(), 7, 7, 7, ignoreList);
					for(Entity ent : entities){
						EntityHandling.dealDamageToEntity((LivingEntity) ent, p, damageDealt*20.0, true, 1);
						EntityHandling.dealKnockback(mainStar, ent, 5.0, 0.3);
					}
					for(Snowball star : projectiles) {
						star.remove();
					}
					cancel();
					return;
				}

				Location starLocation = mainStar.getLocation();

				mainStar.getWorld().spawnParticle(Particle.GLOW, starLocation, 0);
				mainStar.getWorld().playSound(starLocation, Sound.ENTITY_BLAZE_SHOOT, 1.0f,1.0f);

				for(int i = 0;i<4;i++) {
					//Shoot out stars from each cardinal direction (from main star)
					Location temp = starLocation.clone();
					temp.setYaw(temp.getYaw()+(90.0F*i));
					temp.setPitch(0.0f);

					Snowball subStar = p.launchProjectile(Snowball.class, temp.getDirection());
					subStar.teleport(temp);
					subStar.setItem(new ItemStack(Material.GLOWSTONE_DUST));
					subStar.setGravity(false);

					projectiles.add(subStar);

					HashMap<String,Double> projAttrib = new HashMap<>();
					projAttrib.put("Damage", damageDealt);
					projAttrib.put("DamageType", 1.0);
					EntityHandling.projectileAttributes.put(subStar.getUniqueId(), projAttrib);
				}

				for(Snowball star : projectiles)
				{
					if(star == null)
						{projectiles.remove(star);continue;}

					Location subStarLocation = star.getLocation();
					p.getWorld().spawnParticle(Particle.GLOW, star.getLocation(), 3);

					Collection<Entity> entities = p.getWorld().getNearbyEntities(subStarLocation, 6, 6, 6, ignoreList);
					if(!entities.isEmpty()) {
						Entity closest = (Entity) entities.toArray()[0];
						for (Entity ent : entities) {
							if (subStarLocation.distance(ent.getLocation()) < subStarLocation.distance(closest.getLocation())) {
								closest = ent;
							}
						}

						double baseSpeed = star.getVelocity().length()*0.2;
						Vector endResult = subStarLocation.toVector().subtract(closest.getLocation().toVector());
						Vector projVector = endResult.add(star.getVelocity()).normalize();
						double[] angles = new double[2];
						Main.VectorAngles(projVector, angles);
						double newSpeed = -(baseSpeed*5);
						projVector.multiply(newSpeed);
						star.setVelocity(projVector);
						star.setRotation((float)angles[0],(float)angles[1]);
					}
				}
				timerCount++;
			}
		}.runTaskTimer(Main.getInstance(), 1L, 5L);

	}


	private void jump_ability(PlayerInteractEvent e, ItemMeta meta) {
		if(!check_ready(e.getPlayer(), "JUMP_ROD", 20.0, 0.75, meta)) return;

		Player p = e.getPlayer();
		Vector vector = p.getEyeLocation().getDirection();
		vector.multiply(1.01);
		vector = p.getVelocity().add(vector);
		p.setVelocity(vector.add(new Vector(0, 1, 0)));
	}

	private void murasama_ability(PlayerInteractEvent e, ItemMeta meta) {
		Player p = e.getPlayer();
		if (!check_ready(e.getPlayer(), "MURASAMA", 100.0, 5.0, meta)) return;
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
		new BukkitRunnable(){
			public void run()
			{
				p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0F, 1.0F);
				for (int i = -19; i < 19; i++) {
					final int finalI = i;
					new BukkitRunnable(){
						public void run(){
							double fwdFactor = Math.pow(Math.abs(finalI * 0.1), 2.1);
							for(int i = 0; i < 15;i++) {
								Vector tmpForward = forward.clone().multiply(4.0 - fwdFactor - i*0.12);
								Vector tmpRight = right.clone().multiply(finalI * 0.15);
								Vector tmpUp = up.clone().multiply(finalI * 0.07);
								Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255 - i*15, Math.max(70 + finalI*3 - i*10,0), 0), 1F);
								Vector finalVec = new Vector();
								finalVec.add(p.getEyeLocation().toVector());
								finalVec.add(tmpForward);
								finalVec.add(tmpRight);
								finalVec.add(tmpUp);
								p.spawnParticle(Particle.REDSTONE, finalVec.toLocation(p.getWorld()), 0, dustOptions);
							}
						}
					}.runTaskLaterAsynchronously(Main.getInstance(), (i + 15) / 10);
				}
				EntityHandling.dealAOEAngledDamage(p, 75.0, 6.0, CustomAttributes.getDamageModified(p.getUniqueId(), true) * 3.5, true, 0);
			}
		}.runTaskLater(Main.getInstance(), 5);
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

	private boolean check_ready(Player p, String itemID, double manaCost, double delay, ItemMeta meta) { //ability check / set x333
		if(p.getCooldown(p.getInventory().getItemInMainHand().getType()) != 0)
			return false;

		UUID uuid = p.getUniqueId();
		if(!ability_cooldown.containsKey(uuid) || !ability_cooldown.get(uuid).containsKey(itemID)) {
			HashMap<String, Double> map = new HashMap<>();
			map.put(itemID, (double)System.currentTimeMillis());
			ability_cooldown.put(uuid, map);
		}
		
		if(System.currentTimeMillis() < ability_cooldown.get(uuid).get(itemID)) {
			HUDTimer.playerStatusCooldown.put(uuid, ability_cooldown.get(uuid).get(itemID));
			return false;
		}

		double currentMana = Main.getAttributes().get(uuid).get("Mana");
		if(!(currentMana - manaCost >= 0)) {p.sendMessage("§4You do not have enough mana for this!");return false;}
		Main.getAttributes().get(uuid).replace("Mana", (currentMana - manaCost));

		int clockworkValue = meta.getPersistentDataContainer().getOrDefault(Main.modifierKeys.get( "clockwork"), PersistentDataType.INTEGER, 0);
		if(clockworkValue > 0)
			delay *= 1.0-(clockworkValue*0.15);

		ability_cooldown.get(uuid).replace(itemID, (1000.0*delay) + System.currentTimeMillis());


		String buffer = String.format("§6Used %s§f! §b-%.0f★",
				Normalizer.normalize(meta.getDisplayName(),Normalizer.Form.NFKC),manaCost);
		HUDTimer.playerStatusAbility.put(uuid, new Object[]{buffer,System.currentTimeMillis()+1000});

		double cd = meta.getPersistentDataContainer().getOrDefault(Main.attributeKeys.get("itemAbilityTotalCooldown"), PersistentDataType.DOUBLE,0.0);
		if(cd > 0.0)
			p.setCooldown(p.getInventory().getItemInMainHand().getType(), (int) (cd*20.0));

		return true;
	}	


}