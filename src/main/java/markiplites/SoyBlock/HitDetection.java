package markiplites.SoyBlock;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
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

		int weaponType = container.has(new NamespacedKey(Main.getInstance(), "weaponType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "weaponType"), PersistentDataType.DOUBLE)) : 0;
		switch (weaponType) {
			case 1 -> {
				double projSpeed = container.has(new NamespacedKey(Main.getInstance(), "projectileSpeed"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "projectileSpeed"), PersistentDataType.DOUBLE) : 1.0;
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