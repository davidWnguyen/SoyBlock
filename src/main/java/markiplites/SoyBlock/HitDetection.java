package markiplites.SoyBlock;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class HitDetection implements Listener {
	public HitDetection() {
		init();
	}
	public void init() {	
	
	}
	@EventHandler
	public void PlayerLeftClick(PlayerAnimationEvent event) {
		if (event.getAnimationType() != PlayerAnimationType.ARM_SWING)
			return;

		Player player = event.getPlayer();
		leftClickAttack(player);
    }

	public void leftClickAttack(Player player)
	{
		UUID id = player.getUniqueId();
		if(player.getCooldown(player.getInventory().getItemInMainHand().getType()) != 0)
			return;

		ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();

		if (meta == null)
			return;
		PersistentDataContainer container = meta.getPersistentDataContainer();
		if (container == null)
			return;

		int weaponType = container.has(Main.attributeKeys.get( "weaponType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(Main.attributeKeys.get( "weaponType"), PersistentDataType.DOUBLE)) : 0;
		switch (weaponType) {
			//Rocket Launchers
			case 2 -> {
				double projSpeed = container.getOrDefault(Main.attributeKeys.get( "projectileSpeed"), PersistentDataType.DOUBLE, 1.0);
				double blastRadius = container.getOrDefault(Main.attributeKeys.get( "blastRadius"), PersistentDataType.DOUBLE, 1.0);
				double blastFalloff = container.getOrDefault(Main.attributeKeys.get( "blastFalloff"), PersistentDataType.DOUBLE, 0.5);

				Vector playerDirection = player.getLocation().getDirection();
				Snowball rocket = player.launchProjectile(Snowball.class, playerDirection.multiply(projSpeed));
				if (rocket == null)
					return;
				rocket.setGravity(false);
				rocket.setItem(new ItemStack(Material.TNT));
				rocket.getWorld().playSound(rocket.getLocation(), Sound.ENTITY_WARDEN_ATTACK_IMPACT, 1.0f,1.0f);
				HashMap<String, Double> attributes = new HashMap<>();

				double critChance = Main.getAttributes().get(id).getOrDefault("CritChance", 0.0);
				boolean critBoolean = Math.random() < critChance;
				double customDamage = CustomAttributes.getDamageModified(player.getUniqueId(), critBoolean);

				EntityHandling.projectileAttributes.put(rocket.getUniqueId(), attributes);
				Predicate<Entity> ignoreList = f -> (f instanceof LivingEntity && !f.isDead() && f.getType() != EntityType.ARMOR_STAND && !(f instanceof Player)) || (f == player);
				new BukkitRunnable() {
					int timerCount = 0;
					@Override
					public void run() {
						if(timerCount > 66)//Lifespan of 10s
							rocket.remove();

						if(rocket.isDead())
						{
							rocket.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, rocket.getLocation(), 10);
							rocket.getWorld().playSound(rocket.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.2f,0.7f);
							Collection<Entity> entities = rocket.getWorld().getNearbyEntities(rocket.getLocation(), blastRadius, blastRadius, blastRadius, ignoreList);
							for(Entity ent : entities){
								double falloffRatio = Math.min(Math.max(1.0 - ( rocket.getLocation().distance(ent.getLocation()) / blastRadius )*blastFalloff , 0.5), 1.0);
								EntityHandling.dealDamageToEntity((LivingEntity) ent, player, falloffRatio*customDamage, critBoolean, 0);
								EntityHandling.dealKnockback(rocket, ent, 2.0, 0.2);
							}
							cancel();
							return;
						}
						timerCount++;
					}
				}.runTaskTimer(Main.getInstance(), 0, 3);
				double attackSpeed = Main.getAttributes().get(id).getOrDefault("AttackSpeed", 4.0);
				player.setCooldown(player.getInventory().getItemInMainHand().getType(), (int) Math.round(20.0 / attackSpeed));
			}
			//Shortbows
			case 1 -> {
				double projSpeed = container.has(Main.attributeKeys.get( "projectileSpeed"), PersistentDataType.DOUBLE) ? container.get(Main.attributeKeys.get( "projectileSpeed"), PersistentDataType.DOUBLE) : 1.0;
				Vector playerDirection = player.getLocation().getDirection();
				Arrow shortbowArrow = player.launchProjectile(Arrow.class, playerDirection.multiply(projSpeed));
				if (shortbowArrow == null)
					return;

				shortbowArrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);

				HashMap<String, Double> attributes = new HashMap<>();

				double critChance = Main.getAttributes().get(id).getOrDefault("CritChance", 0.0);
				boolean critBoolean = Math.random() < critChance;
				double customDamage = CustomAttributes.getDamageModified(player.getUniqueId(), critBoolean);

				attributes.put("Damage", customDamage);
				attributes.put("CriticalAttack", critBoolean ? 1.0 : 0.0);//lol

				EntityHandling.projectileAttributes.put(shortbowArrow.getUniqueId(), attributes);

				double attackSpeed = Main.getAttributes().get(id).getOrDefault("AttackSpeed", 4.0);

				player.setCooldown(player.getInventory().getItemInMainHand().getType(), (int) Math.round(20.0 / attackSpeed));
			}
			default ->//Normal Swords
			{
				LivingEntity victim = (LivingEntity) hitTraceResult(player);
				if (victim == null)
					return;

				double critChance = Main.getAttributes().get(id).getOrDefault("CritChance", 0.0);
				boolean critBoolean = Math.random() < critChance;
				double customDamage = CustomAttributes.getDamageModified(player.getUniqueId(), critBoolean);

				EntityHandling.dealDamageToEntity(victim, player, customDamage, critBoolean, 0);

				double attackSpeed = Main.getAttributes().get(id).getOrDefault("AttackSpeed", 4.0);

				player.setCooldown(player.getInventory().getItemInMainHand().getType(), (int) Math.round(20.0 / attackSpeed));
			}
		}
	}
    private Entity hitTraceResult(Player player) {
        double attackRange = Main.getAttributes().get(player.getUniqueId()).getOrDefault("AttackRange", 3.0);
        //List<Entity> entities = (List<Entity>) player.getWorld().getNearbyEntities(player.getLocation(), attackRange, attackRange, attackRange);
        //^ will be used for cleaving
        Location goFuckYourself = player.getEyeLocation();
        //goFuckYourself.add(player.getLocation().getDirection().toBlockVector().multiply(0.1));
        //player.sendMessage(String.format("%.1f,%.1f,%.1f",goFuckYourself.getX(),goFuckYourself.getY(),goFuckYourself.getZ()  ));
        //^confirmation that this does actually work
        Predicate<Entity> ignoreList = i -> (i != player && i instanceof LivingEntity && !i.isDead() && i.getType() != EntityType.ARMOR_STAND);
        RayTraceResult traceResult = player.getWorld().rayTrace(goFuckYourself, player.getEyeLocation().getDirection(), attackRange+0.2, FluidCollisionMode.NEVER, true,0.25, ignoreList);
        if(traceResult != null)
        {
        	return traceResult.getHitEntity();
        }
        return null;
    }
}