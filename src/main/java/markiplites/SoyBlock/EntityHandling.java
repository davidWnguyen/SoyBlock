package markiplites.SoyBlock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.iridium.iridiumcolorapi.IridiumColorAPI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class EntityHandling implements Listener {
	public static HashMap<UUID, HashMap<String, Double>> entityAttributes = new HashMap<>();
	public static HashMap<UUID, HashMap<String, Double>> projectileAttributes = new HashMap<>();
	public static HashMap<UUID, String> customNames = new HashMap<>();
	public EntityHandling() {
		init();
	}
	public void init() {}
	//Reset IFrames
	static void resetIFramesDelayed(LivingEntity en)
	{
		new BukkitRunnable(){public void run(){//Start of Delay
			en.setNoDamageTicks(0);
		}}.runTaskLater(Main.getInstance(), 1);
	}
	static void resetIFrames(LivingEntity en)
	{
		en.setNoDamageTicks(0);
	}
	public static double getRandomDouble(double min, double max) {
	    Random r = new Random();
		return min + (max - min) * r.nextDouble();
	}
	//Damage Hologram
	public static void spawnIndicator(Location loc, String format, Entity en) {
	    loc.add(getRandomDouble(-0.5, 0.5),getRandomDouble(-0.5, 0.5),getRandomDouble(-0.5, 0.5));
	    //armorStand :D
        ArmorStand hologram = (ArmorStand) en.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        hologram.setVisible(false);
        hologram.setBasePlate(false);
        hologram.setCollidable(false);
        hologram.setArms(false);
        hologram.setSmall(true);
        hologram.setSilent(true);
        hologram.setCanPickupItems(false);
        hologram.setGliding(true);
        hologram.setLeftLegPose(EulerAngle.ZERO.add(180, 0, 0));
        hologram.setRightLegPose(EulerAngle.ZERO.add(180, 0, 0));
        hologram.setInvulnerable(true);
        hologram.setCustomNameVisible(true);
        hologram.setCustomName(format);
        hologram.setGravity(false);
		new BukkitRunnable(){public void run(){
			hologram.remove();
		}}.runTaskLater(Main.getInstance(), 15);

	}
	//Deal Damage
	public static void dealDamageToEntity(LivingEntity en, LivingEntity damager, double damageDealt, boolean isCrit, int damageType){
		if(en.isDead())
			return;

		en.damage(0.0);
		UUID id = en.getUniqueId();
		
		if(entityAttributes.containsKey(id))
		{
			double absorption = entityAttributes.get(id).getOrDefault("Absorption", 0.0);
			damageDealt *= (100.0-absorption)/100.0;
			String damageCharacter = damageType == 0 ? "⚔":"★";
			String finaldamage = "";

			if(damageType == 0) {
				if (isCrit) {
					en.getWorld().spawnParticle(Particle.CRIT, en.getLocation().add(0, 0.5, 0), 15);
					finaldamage = IridiumColorAPI.process(String.format("<GRADIENT:cc0000>- %.0f %s</GRADIENT:ff9933>",damageDealt,damageCharacter));
				}else {
					finaldamage = IridiumColorAPI.process(String.format("<SOLID:ffc000>- %.0f %s",damageDealt,damageCharacter));
				}
			}
			else if(damageType == 1) {
				if(isCrit) {
					en.getWorld().spawnParticle(Particle.CRIT_MAGIC, en.getLocation().add(0, 0.5, 0), 15);
					finaldamage = IridiumColorAPI.process(String.format("<GRADIENT:45a6ff>- %.0f %s</GRADIENT:9b31ff>",damageDealt,damageCharacter));
				}else {
					finaldamage = IridiumColorAPI.process(String.format("<SOLID:2986cc>- %.0f %s",damageDealt,damageCharacter));
				}
			}
			spawnIndicator(en.getLocation(), finaldamage, en);
			entityAttributes.get(id).put("Health", entityAttributes.get(id).get("Health") - damageDealt);
			if(entityAttributes.get(id).get("Health") > 0.0) {
				en.setHealth(entityAttributes.get(id).get("Health")/entityAttributes.get(id).get("MaxHealth") * en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				if(customNames.get(id) != null) setNameHealth(en);
			}else {
				en.setHealth(0.0);
				Bukkit.getServer().getPluginManager().callEvent(new EntityDamageByEntityEvent(damager, en, DamageCause.CUSTOM, -1.0));
			}
			resetIFrames(en);
		}
		else if(en instanceof Player && Main.getAttributes().containsKey(en.getUniqueId()))
		{
			UUID uuid = en.getUniqueId();
			double absorption = Main.getAttributes().get(uuid).getOrDefault("Absorption", 0.0);
			damageDealt *= (100.0-absorption)/100.0;
			
			String colorFormat = "§x§d§3§0§0§0§0";
			String damageCharacter = "❤";
	
			if(damageType == 0)
			{
				if(isCrit)
					en.getWorld().spawnParticle(Particle.CRIT, en.getLocation().add(0, 0.5,0), 15);
			}else if(damageType == 1) {
				colorFormat = "§b";
				if(isCrit)
					en.getWorld().spawnParticle(Particle.CRIT_MAGIC, en.getLocation().add(0, 0.5,0), 15);
			}
			String finaldamage = String.format("%s - %.0f %s",colorFormat,damageDealt,damageCharacter);
			spawnIndicator(en.getLocation(), finaldamage, en);
			Main.getAttributes().get(uuid).put("Health", Main.getAttributes().get(uuid).get("Health") - damageDealt);
			if(Main.getAttributes().get(uuid).get("Health") > 0.0) {
				en.setHealth(Main.getAttributes().get(uuid).get("Health")/Main.getAttributes().get(uuid).get("MaxHealth") * en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			}else {
				en.setHealth(0.0);
			}
			resetIFrames(en);
		}
	}
	@EventHandler
	public void onChunkLoaded(ChunkLoadEvent event)
	{
		for (Entity ent : event.getChunk().getEntities()) {
			if (ent instanceof Player)
				continue;
			if(!(ent instanceof LivingEntity))
				continue;
			if (ent.getType() == EntityType.ARMOR_STAND)
				{ent.remove();continue;}
			UUID id = ent.getUniqueId();
			if(entityAttributes.containsKey(id))
				continue;

			//Only give them randomized stats if they're not defined.
			//Replace later with mob attribute system.
			int mobLevel = new Random().nextInt(10 - 1 + 1) + 1;
			HashMap<String, Double> attributes = new HashMap<>();
			attributes.put("BaseDamage", 5.0 + (mobLevel * 2));
			attributes.put("Health", 40.0 + (mobLevel * 10));
			attributes.put("MaxHealth", 40.0 + (mobLevel * 10));
			attributes.put("Absorption", 0.0 + (mobLevel * 2.0));
			attributes.put("Intelligence", 0.0);
			attributes.put("IntelligenceScaling", 0.0);
			attributes.put("Strength", 0.0);
			attributes.put("StrengthScaling", 0.0);
			attributes.put("Dexterity", 0.0);
			attributes.put("DexterityScaling", 0.0);
			entityAttributes.put(id, attributes);
		}
	}
	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent event)
	{	
		if(event.getEntityType() != EntityType.ARMOR_STAND)
		{//Implement configs for mobs later, now will just have static randomized levels.
				int mobLevel = new Random().nextInt(10 - 1 + 1) + 1;
				HashMap<String, Double> attributes = new HashMap<>();
				attributes.put("BaseDamage", 5.0 + (mobLevel*2));
				attributes.put("Health", 40.0 + (mobLevel*10));
				attributes.put("MaxHealth", attributes.get("Health"));
				//attributes.put("Absorption", 0.0 + (mobLevel*2.0));
				attributes.put("Intelligence", 0.0);
				attributes.put("IntelligenceScaling", 0.0);
				attributes.put("Strength", 0.0);
				attributes.put("StrengthScaling", 0.0);
				attributes.put("Dexterity", 0.0);
				attributes.put("DexterityScaling", 0.0);
				entityAttributes.put(event.getEntity().getUniqueId(), attributes);
		}
		
	}
	@EventHandler
	public void onEntityRemoved(EntityRemoveFromWorldEvent event)
	{
		entityAttributes.remove(event.getEntity().getUniqueId());
		customNames.remove(event.getEntity().getUniqueId());
	}
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		UUID id = e.getEntity().getUniqueId();
		if(Ent.nameTags.containsKey(id)){
			Ent.nameTags.get(id).remove();
			Ent.nameTags.remove(id);
		}
	}
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player p = event.getEntity();
		UUID uuid = p.getUniqueId();
		Main.getAttributes().get(uuid).put("Health", Main.getAttributes().get(uuid).get("MaxHealth"));
		p.setHealth(Main.getAttributes().get(uuid).get("Health")/Main.getAttributes().get(uuid).get("MaxHealth") * p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
	}
	@EventHandler
	public void onProjectileCollide(ProjectileHitEvent e){
		Projectile i = e.getEntity();
		if(i == null)
			return;
		Entity v = e.getHitEntity();
		if(!(v instanceof LivingEntity))
			return;

		ProjectileSource s = i.getShooter();
		//Mobs cannot hit eachother, and player cannot hit eachother.
		if(s instanceof Mob && v instanceof Mob)
			return;
		if(s instanceof Player && v instanceof Player)
			return;

		UUID projID = i.getUniqueId();
		if(projectileAttributes.containsKey(projID))
		{
			double customDamage = projectileAttributes.get(projID).getOrDefault("Damage", 0.0);
			int damageType = (int) Math.round(projectileAttributes.get(projID).getOrDefault("DamageType", 0.0));
			boolean critBoolean = (projectileAttributes.get(projID).getOrDefault("CriticalAttack", 0.0))==1.0;
			if(s instanceof LivingEntity)
				dealDamageToEntity((LivingEntity)v, (LivingEntity) s, customDamage, critBoolean, damageType);
			else
				dealDamageToEntity((LivingEntity)v, null, customDamage, critBoolean, damageType);
			resetIFrames((LivingEntity)v);

			double knockback = projectileAttributes.get(projID).getOrDefault("Knockback", 0.0);
			if(knockback > 0.0)
				dealKnockback(i, v, knockback, 0.2*knockback);

			e.setCancelled(true);
			i.remove();
		}
	}
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e) {
		
		double damage = e.getDamage();
		if(damage == -1.0) return;
		double customDamage = damage;
		Entity victim = e.getEntity();
		
		if(damage == 0.0)
			{resetIFramesDelayed((LivingEntity)victim);return;}
		if(victim.getType() == EntityType.ARMOR_STAND)
			return;
		if(!(victim instanceof LivingEntity))
			return;

		UUID uuid = victim.getUniqueId();
		if(entityAttributes.containsKey(uuid) || (victim instanceof Player && Main.playerAttributes.containsKey(uuid)) )
		{
			//Custom Damage Handling
			if(e instanceof EntityDamageByEntityEvent)
			{
				Entity attacker = ((EntityDamageByEntityEvent)e).getDamager();
				//If the "attacker" is an projectile, make the attacker instead the shooter.
				if(attacker instanceof Projectile && ((Projectile)attacker).getShooter() instanceof LivingEntity)
					attacker = (Entity) ((Projectile)attacker).getShooter();

				UUID attackerID = attacker.getUniqueId();
				if(attacker instanceof Player)
				{
					if(e.getCause() == DamageCause.ENTITY_ATTACK)
					{
						e.setDamage(0);
						e.setCancelled(true);
						resetIFramesDelayed((LivingEntity)victim);
						resetIFrames((LivingEntity)victim);
				        return;
					}
					//((Player)attacker).sendMessage(String.format("You dealt %.1f damage.", customDamage));
				}
				else if(attacker instanceof LivingEntity && entityAttributes.containsKey(attackerID) && (e.getCause() == DamageCause.ENTITY_ATTACK || e.getCause() == DamageCause.PROJECTILE))
				{
					double critChance = entityAttributes.get(attackerID).getOrDefault("CritChance", 0.0);
					customDamage = CustomAttributes.getDamageModified(attackerID, Math.random() < critChance);
					e.setDamage(0);
					damage = 0;
					resetIFrames((LivingEntity)victim);
				}
			}
			//Damage Indicators
			//If Victim is a Player
			if (victim instanceof Player)
			{
				double absorption = Main.getAttributes().get(uuid).getOrDefault("Absorption", 0.0);
				customDamage *= (100.0-absorption)/100.0;
				if(damage != 0)//Environmental stuff deals pctHP stuff
				{
					customDamage = Main.getAttributes().get(uuid).get("MaxHealth")/20.0 * damage;
					e.setDamage(0);
				}
				Main.getAttributes().get(uuid).put("Health", Main.getAttributes().get(uuid).get("Health") - customDamage);
				
				String finaldamage = String.format("§x§d§3§0§0§0§0- %.0f ❤",customDamage);
				spawnIndicator(victim.getLocation(), finaldamage, victim);
				if(Main.getAttributes().get(uuid).get("Health") > 0.0)
				{
					((LivingEntity) victim).setHealth(Main.getAttributes().get(uuid).get("Health")/Main.getAttributes().get(uuid).get("MaxHealth") *((LivingEntity) victim).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				}else{
					((LivingEntity) victim).setHealth(0.0);
				}
				//((Player)victim).sendMessage(String.format("You took %.1f damage.", customDamage));
			}
			else//If victim is a mob
			{
				double absorption = entityAttributes.get(uuid).getOrDefault("Absorption", 0.0);
				customDamage *= (100.0-absorption)/100.0;
				
				entityAttributes.get(uuid).put("Health", entityAttributes.get(uuid).get("Health") - customDamage);
				String finaldamage = String.format("§x§f§b§9§7§0§0- %.0f ⚔",customDamage);
				spawnIndicator(victim.getLocation(), finaldamage, victim);
				if(entityAttributes.get(uuid).get("Health") > 0.0) {
					((LivingEntity) victim).setHealth(entityAttributes.get(uuid).get("Health")/entityAttributes.get(uuid).get("Health") *
							((LivingEntity) victim).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				}else {
					((LivingEntity) victim).setHealth(0.0);
				}
				resetIFramesDelayed((LivingEntity)victim);
			}
		}

	}

	@EventHandler
	public static void entityHitByEntity(EntityDamageByEntityEvent e) {
		if(e.getDamage() == -1.0 && e.getCause().equals(DamageCause.CUSTOM) && e.getEntity().isDead()) {
			if((Player)e.getDamager() instanceof Player && entityAttributes.get(e.getEntity().getUniqueId()).containsKey("CombatExp")) {
				Skills.addSkillExp(((Player)e.getDamager()).getUniqueId(), "Combat", entityAttributes.get(e.getEntity().getUniqueId()).get("CombatExp"));
			}
		}
	}
	public static void dealAOEAngledDamage(Player p, double degrees, double range, double damageDealt, boolean isCrit, int damageType) {
		Vector angleVector = p.getLocation().getDirection();
		Vector playerVector = p.getLocation().toVector();

		for(LivingEntity entity : p.getWorld().getLivingEntities()) {
			if (entity instanceof Player || entity.getType() == EntityType.ARMOR_STAND)
				continue;
			if (!entityAttributes.containsKey(entity.getUniqueId()))
				continue;

			Vector targetLocation = entity.getLocation().toVector();
			if (targetLocation.distance(playerVector) > range)
				continue;

			Vector targetVector = playerVector.clone().subtract(targetLocation).normalize();
			if(getAngleBetweenVector(targetVector, angleVector) > degrees)
				continue;

			dealDamageToEntity(entity, p, damageDealt, isCrit, damageType);
		}
	}
	public static void dealKnockback(Entity attacker, Entity victim, double knockback, double upwardsVelocity)
	{
		UUID uuid = victim.getUniqueId();
		if(victim instanceof Player && Main.playerAttributes.containsKey(uuid))
			knockback /= Main.playerAttributes.get(uuid).getOrDefault("KnockbackResistance", 1.0);
		else if(attacker instanceof LivingEntity && entityAttributes.containsKey(uuid))
			knockback /= entityAttributes.get(uuid).getOrDefault("KnockbackResistance", 1.0);

		Vector addedKB = attacker.getLocation().subtract(victim.getLocation()).toVector().normalize().setY(-upwardsVelocity).normalize().multiply(-knockback);
		victim.setVelocity(addedKB.add(victim.getVelocity()));
	}
	public static double getAngleBetweenVector(Vector vec1, Vector vec2){
		return 180-(180/Math.PI*(Math.acos( vec1.dot(vec2)  / (vec1.length() * vec2.length() ))));
	}
	public static void putEntityAttributes(UUID uuid, HashMap<String, Double> attributes, String customName) {
		entityAttributes.put(uuid, attributes);
		customNames.put(uuid, customName);
	}
	public static void setNameHealth(Entity ent) {
		UUID entityID = ent.getUniqueId();

		String color;
		if(entityAttributes.get(entityID).get("Health") >= (entityAttributes.get(entityID).get("MaxHealth")*0.66))
			color = "44E90B"; //green
		else if(entityAttributes.get(entityID).get("Health") >= entityAttributes.get(entityID).get("MaxHealth")*0.33)
			color = "E9DB0B"; //yellow
		else
			color = "EF320E"; //red
		if(Ent.nameTags.containsKey(entityID))
			Ent.nameTags.get(entityID).setCustomName(customNames.get(entityID) + ": " + IridiumColorAPI.process(String.format("<SOLID:%s> %.0f/%.0f", color, entityAttributes.get(entityID).get("Health"), entityAttributes.get(entityID).get("MaxHealth"))));
	}
}