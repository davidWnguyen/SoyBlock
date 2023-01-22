package markiplites.SoyBlock;
import java.util.HashMap;
import java.util.Random;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class EntityHandling implements Listener {
	public static HashMap<LivingEntity, HashMap<String, Double>> entityAttributes = new HashMap<>();
	public static HashMap<Entity, HashMap<String, Double>> projectileAttributes = new HashMap<>();
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
	public static void dealDamageToEntity(LivingEntity en, double damageDealt, boolean isCrit, int damageType){
		en.damage(0.0);
		if(entityAttributes.containsKey(en))
		{
			double absorption = entityAttributes.get(en).getOrDefault("Absorption", 0.0);
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
			entityAttributes.get(en).put("Health", entityAttributes.get(en).get("Health") - damageDealt);
			if(entityAttributes.get(en).get("Health") > 0.0) {
				en.setHealth(entityAttributes.get(en).get("Health")/entityAttributes.get(en).get("MaxHealth") * en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			}else {
				en.setHealth(0.0);
			}
			resetIFrames(en);
		}
		else if(Main.getAttributes().containsKey(en))
		{
			double absorption = Main.getAttributes().get(en).getOrDefault("Absorption", 0.0);
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
			Main.getAttributes().get(en).put("Health", Main.getAttributes().get(en).get("Health") - damageDealt);
			if(Main.getAttributes().get(en).get("Health") > 0.0) {
				en.setHealth(Main.getAttributes().get(en).get("Health")/Main.getAttributes().get(en).get("MaxHealth") * en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
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
				continue;
			if(entityAttributes.containsKey((LivingEntity)ent))
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

			entityAttributes.put((LivingEntity) ent, attributes);
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
			attributes.put("MaxHealth", 40.0 + (mobLevel*10));
			//attributes.put("Absorption", 0.0 + (mobLevel*2.0));
			attributes.put("Intelligence", 0.0);
			attributes.put("IntelligenceScaling", 0.0);
			attributes.put("Strength", 0.0);
			attributes.put("StrengthScaling", 0.0);
			attributes.put("Dexterity", 0.0);
			attributes.put("DexterityScaling", 0.0);
			
			entityAttributes.put(event.getEntity(), attributes);
		}
	}
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player p = event.getEntity();
		
		Main.getAttributes().get(p).put("Health", Main.getAttributes().get(p).get("MaxHealth"));
		
		p.setHealth(Main.getAttributes().get(p).get("Health")/Main.getAttributes().get(p).get("MaxHealth") * p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
	}
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e) {
		double damage = e.getDamage();
		double customDamage = damage;
		Entity victim = e.getEntity();
		if(damage == 0.0)
		{
			resetIFramesDelayed((LivingEntity)victim);
			return;
		}
		if(victim.getType() == EntityType.ARMOR_STAND) {
			return;
		}
		if(victim instanceof LivingEntity && (entityAttributes.containsKey((LivingEntity)victim) ||(victim instanceof Player && Main.playerAttributes.containsKey((Player)victim))))
		{
			//Custom Damage Handling
			if(e instanceof EntityDamageByEntityEvent)
			{
				Entity attacker = ((EntityDamageByEntityEvent)e).getDamager();
				Entity inflictor = attacker;
				//If the "attacker" is an arrow, make the attacker instead the shooter.
				if(attacker instanceof Arrow && ((Arrow)attacker).getShooter() instanceof LivingEntity)
				{
					attacker = (Entity) ((Arrow)attacker).getShooter();
				}
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
					else if(e.getCause() == DamageCause.PROJECTILE)
					{
						if(projectileAttributes.containsKey(inflictor))
						{
							customDamage = projectileAttributes.get(inflictor).getOrDefault("Damage", 0.0);
							boolean critBoolean = (projectileAttributes.get(inflictor).getOrDefault("CriticalAttack", 0.0))==1.0;
							dealDamageToEntity((LivingEntity)victim,customDamage, critBoolean, 0);
						}
						e.setDamage(0);
						e.setCancelled(true);
						resetIFramesDelayed((LivingEntity)victim);
						resetIFrames((LivingEntity)victim);
				        return;
					}
					//((Player)attacker).sendMessage(String.format("You dealt %.1f damage.", customDamage));
				}
				else if(attacker instanceof LivingEntity && entityAttributes.containsKey((LivingEntity)attacker) && (e.getCause() == DamageCause.ENTITY_ATTACK || e.getCause() == DamageCause.PROJECTILE))
				{
					double baseDMG = entityAttributes.get((LivingEntity) attacker).getOrDefault("BaseDamage", 5.0);
					
					double dex = entityAttributes.get((LivingEntity) attacker).getOrDefault("Dexterity", 0.0);
					double dexScaling = entityAttributes.get((LivingEntity) attacker).getOrDefault("DexterityScaling", 0.0);
					
					double str = entityAttributes.get((LivingEntity) attacker).getOrDefault("Strength", 0.0);
					double strScaling = entityAttributes.get((LivingEntity) attacker).getOrDefault("StrengthScaling", 0.0);
					
					double intel = entityAttributes.get((LivingEntity) attacker).getOrDefault("Intelligence", 0.0);
					double intelScaling = entityAttributes.get((LivingEntity) attacker).getOrDefault("IntelligenceScaling", 0.0);
					customDamage = baseDMG * Math.pow((1+(dex)/100.0), dexScaling) * Math.pow((1+(str)/100.0), strScaling) * Math.pow((1+(intel)/100.0), intelScaling);
					e.setDamage(0);
					damage = 0;
					resetIFrames((LivingEntity)victim);
				}
			}
			//Damage Indicators
			//If Victim is a Player
			if (victim instanceof Player)
			{
				double absorption = Main.getAttributes().get((Player) victim).getOrDefault("Absorption", 0.0);
				customDamage *= (100.0-absorption)/100.0;
				if(damage != 0)//Environmental stuff deals pctHP stuff
				{
					customDamage = Main.getAttributes().get((Player)victim).get("MaxHealth")/20.0 * damage;
					e.setDamage(0);
				}
				Main.getAttributes().get(victim).put("Health", Main.getAttributes().get((Player)victim).get("Health") - customDamage);
				
				String finaldamage = String.format("§x§d§3§0§0§0§0- %.0f ❤",customDamage);
				spawnIndicator(victim.getLocation(), finaldamage, victim);
				if(Main.getAttributes().get((Player)victim).get("Health") > 0.0)
				{
					((LivingEntity) victim).setHealth(Main.getAttributes().get((Player)victim).get("Health")/Main.getAttributes().get((Player)victim).get("MaxHealth") *((LivingEntity) victim).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				}else{
					((LivingEntity) victim).setHealth(0.0);
				}
				//((Player)victim).sendMessage(String.format("You took %.1f damage.", customDamage));
			}
			else//If victim is a mob
			{
				double absorption = entityAttributes.get((LivingEntity) victim).getOrDefault("Absorption", 0.0);
				customDamage *= (100.0-absorption)/100.0;
				
				entityAttributes.get((LivingEntity)victim).put("Health", entityAttributes.get((LivingEntity)victim).get("Health") - customDamage);
				String finaldamage = String.format("§x§f§b§9§7§0§0- %.0f ⚔",customDamage);
				spawnIndicator(victim.getLocation(), finaldamage, victim);
				if(entityAttributes.get((LivingEntity)victim).get("Health") > 0.0) {
					((LivingEntity) victim).setHealth(entityAttributes.get((LivingEntity)victim).get("Health")/entityAttributes.get((LivingEntity)victim).get("Health") *((LivingEntity) victim).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				}else {
					((LivingEntity) victim).setHealth(0.0);
				}
				resetIFramesDelayed((LivingEntity)victim);
			}
		}
	}
}