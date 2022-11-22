package markiplites.SoyBlock;
import java.util.HashMap;
import java.util.function.Predicate;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class HitDetection implements Listener {
	public HitDetection() {
		init();
	}
	public void init() {	
	
	}
	@EventHandler
    public void PlayerAnimation(PlayerAnimationEvent event) {
		Player player = event.getPlayer();
        if (event.getAnimationType() == PlayerAnimationType.ARM_SWING)
        {
        	double baseDMG = Main.getAttributes().get(player).getOrDefault("BaseDamage", 5.0);
	        if(baseDMG > 5.0)//fully charged & is weapon
	        {
	        	if(player.getCooldown(player.getInventory().getItemInMainHand().getType()) == 0)
	        	{
	        		ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
	        		if(meta == null)
	        			return;
	        		PersistentDataContainer container = meta.getPersistentDataContainer();
	        		if(container == null)
	        			return;
	        		int weaponType = container.has(new NamespacedKey(Main.getInstance(), "weaponType"), PersistentDataType.DOUBLE) ? (int)Math.round(container.get(new NamespacedKey(Main.getInstance(), "weaponType"), PersistentDataType.DOUBLE)) : 0;
					switch (weaponType) {
						case 1 -> {
							double projSpeed = container.has(new NamespacedKey(Main.getInstance(), "projectileSpeed"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "projectileSpeed"), PersistentDataType.DOUBLE) : 1.0;
							Vector playerDirection = player.getLocation().getDirection();
							Arrow shortbowArrow = player.launchProjectile(Arrow.class, playerDirection.multiply(projSpeed));
							if (shortbowArrow != null) {
								HashMap<String, Double> attributes = new HashMap<>();

								double dex = Main.getAttributes().get(player).containsKey("Dexterity") ? Math.max(Main.getAttributes().get(player).get("Dexterity"), 0.0) : 0.0;
								double dexScaling = Main.getAttributes().get(player).getOrDefault("DexterityScaling", 0.0);

								double str = Main.getAttributes().get(player).containsKey("Strength") ? Math.max(Main.getAttributes().get(player).get("Strength"), 0.0) : 0.0;
								double strScaling = Main.getAttributes().get(player).getOrDefault("StrengthScaling", 0.0);

								double intel = Main.getAttributes().get(player).containsKey("Intelligence") ? Math.max(Main.getAttributes().get(player).get("Intelligence"), 0.0) : 0.0;
								double intelScaling = Main.getAttributes().get(player).getOrDefault("IntelligenceScaling", 0.0);
								double customDamage = baseDMG * Math.pow((1 + (dex) / 100.0), dexScaling) * Math.pow((1 + (str) / 100.0), strScaling) * Math.pow((1 + (intel) / 100.0), intelScaling);

								double critChance = Main.getAttributes().get(player).getOrDefault("CritChance", 0.0);
								boolean critBoolean = false;
								if (Math.random() < critChance) {
									critBoolean = true;
									customDamage *= (Main.getAttributes().get(player).getOrDefault("CritDamage", 0.0)) + 1.35;
								}

								attributes.put("Damage", customDamage);
								attributes.put("CriticalAttack", critBoolean ? 1.0 : 0.0);//lol

								EntityHandling.projectileAttributes.put(shortbowArrow, attributes);

								double attackSpeed = Main.getAttributes().get(player).getOrDefault("AttackSpeed", 4.0);

								player.setCooldown(player.getInventory().getItemInMainHand().getType(), (int) Math.round(20.0 / attackSpeed));
							}
						}
						default ->//Normal Swords
						{
							LivingEntity victim = (LivingEntity) hitTraceResult(player);
							if (victim != null) {
								double dex = Main.getAttributes().get(player).containsKey("Dexterity") ? Math.max(Main.getAttributes().get(player).get("Dexterity"), 0.0) : 0.0;
								double dexScaling = Main.getAttributes().get(player).getOrDefault("DexterityScaling", 0.0);

								double str = Main.getAttributes().get(player).containsKey("Strength") ? Math.max(Main.getAttributes().get(player).get("Strength"), 0.0) : 0.0;
								double strScaling = Main.getAttributes().get(player).getOrDefault("StrengthScaling", 0.0);

								double intel = Main.getAttributes().get(player).containsKey("Intelligence") ? Math.max(Main.getAttributes().get(player).get("Intelligence"), 0.0) : 0.0;
								double intelScaling = Main.getAttributes().get(player).getOrDefault("IntelligenceScaling", 0.0);
								double customDamage = baseDMG * Math.pow((1 + (dex) / 100.0), dexScaling) * Math.pow((1 + (str) / 100.0), strScaling) * Math.pow((1 + (intel) / 100.0), intelScaling);

								double critChance = Main.getAttributes().get(player).getOrDefault("CritChance", 0.0);
								boolean critBoolean = false;
								if (Math.random() < critChance) {
									critBoolean = true;
									customDamage *= (Main.getAttributes().get(player).getOrDefault("CritDamage", 0.0)) + 1.35;
								}

								EntityHandling.dealDamageToEntity(victim, customDamage, critBoolean, 0);

								double attackSpeed = Main.getAttributes().get(player).getOrDefault("AttackSpeed", 4.0);

								player.setCooldown(player.getInventory().getItemInMainHand().getType(), (int) Math.round(20.0 / attackSpeed));
							} else {
								player.setCooldown(player.getInventory().getItemInMainHand().getType(), 1);
							}
						}
					}
	        	}
	        	else
	        	{
	        		event.setCancelled(true);
	        	}
	        }
        }
    }
	/*@EventHandler
    public void PlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
        if (event.)
        {
        	
        }
	}*/
    
    private Entity hitTraceResult(Player player) {
        double attackRange = Main.getAttributes().get(player).getOrDefault("AttackRange", 3.0);
        //List<Entity> entities = (List<Entity>) player.getWorld().getNearbyEntities(player.getLocation(), attackRange, attackRange, attackRange);
        //^ will be used for cleaving
        Location goFuckYourself = player.getEyeLocation();
        //goFuckYourself.add(player.getLocation().getDirection().toBlockVector().multiply(0.1));
        //player.sendMessage(String.format("%.1f,%.1f,%.1f",goFuckYourself.getX(),goFuckYourself.getY(),goFuckYourself.getZ()  ));
        //^confirmation that this does actually work
        Predicate<Entity> ignoreList = i -> (i != player && i instanceof LivingEntity && i.getType() != EntityType.ARMOR_STAND);
        RayTraceResult traceResult = player.getWorld().rayTrace(goFuckYourself, player.getEyeLocation().getDirection(), attackRange+0.2, FluidCollisionMode.NEVER, true,0.25, ignoreList);
        if(traceResult != null)
        {
        	return traceResult.getHitEntity();
        }
        return null;
    }
}