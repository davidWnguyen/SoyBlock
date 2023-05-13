package markiplites.SoyBlock;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class BowHandler implements Listener {
	public static HashMap<UUID, Object[]> useTime = new HashMap<>();
	
	public BowHandler() {
		new BukkitRunnable(){
			@Override
			public void run()
			{
				for (Player p : Main.getInstance().getServer().getOnlinePlayers()) {
					UUID uuid = p.getUniqueId();
					if (!useTime.containsKey(uuid))
						continue;
					
					Object[] status = useTime.get(uuid);
					if((boolean)status[1])
						continue;

					ItemStack item = p.getInventory().getItemInMainHand();
					if(item == null)
						return;
					ItemMeta meta = item.getItemMeta();
					if(meta == null)
						return;
					PersistentDataContainer container = meta.getPersistentDataContainer();
					double timer = container.getOrDefault(Main.attributeKeys.get( "drawTime"), PersistentDataType.DOUBLE, 0.0);
					
					if(System.currentTimeMillis() - (long)status[0] >= timer*1000){
						onFullyDrawn(p, item, meta, container);
						useTime.replace(uuid, new Object[]{status[0], true});
					}
				}
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 20, 1);
	}

	@EventHandler
	public void onDraw(PlayerInteractEvent e) {
		if(e.getItem() != null && e.getItem().getType() == Material.BOW
		&& (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) ) {
			Player p = e.getPlayer();
			useTime.put(p.getUniqueId(), new Object[]{System.currentTimeMillis(), false});
		}
	}
	@EventHandler
	public void onShoot(EntityShootBowEvent e){
		if(!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();

		if(!useTime.containsKey(p.getUniqueId()))
			return;

		ItemStack item = e.getBow();
		if(item == null)
			return;
		ItemMeta meta = item.getItemMeta();
		if(meta == null)
			return;
		PersistentDataContainer container = meta.getPersistentDataContainer();
		String itemID = container.get(Main.attributeKeys.get( "itemID"), PersistentDataType.STRING);
		if(itemID == null || itemID.isEmpty()) return;

		//If fully charged:
		if((boolean)useTime.get(p.getUniqueId())[1]){
			switch(itemID){
				case "LIGHTNING_GREATBOW"->{
					e.getProjectile().remove();
					Location eyeLocation = p.getEyeLocation();
					Predicate<Entity> ignoreList = i -> (i != p && i instanceof LivingEntity && !i.isDead() && i.getType() != EntityType.ARMOR_STAND);
					RayTraceResult traceResult = p.getWorld().rayTrace(eyeLocation, eyeLocation.getDirection(), 50.0, FluidCollisionMode.NEVER, true,0.25, ignoreList);
					if(traceResult != null)
					{
						Vector hitLocation = traceResult.getHitPosition();
						p.getWorld().strikeLightningEffect(hitLocation.toLocation(p.getWorld()));

						Collection<Entity> entities = p.getWorld().getNearbyEntities(hitLocation.toLocation(p.getWorld()), 6.0, 6.0, 6.0, ignoreList);
						for(Entity entity : entities) {
							EntityHandling.dealDamageToEntity((LivingEntity)entity, p, 2.0*CustomAttributes.getDamageModified(p.getUniqueId(), true), true, 1);
						}
					}
				}
			}
		}
		BowHandler.useTime.remove(p.getUniqueId());
	}
	public void onFullyDrawn(Player p, ItemStack item, ItemMeta meta, PersistentDataContainer container){
		String itemID = container.get(Main.attributeKeys.get( "itemID"), PersistentDataType.STRING);
		if(itemID == null || itemID.isEmpty()) return;

		switch(itemID){
			default ->{
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.75f,1f);
				p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation(), 50);
			}
		}
	}
}