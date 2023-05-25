package markiplites.SoyBlock;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Predicate;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;

import it.unimi.dsi.fastutil.Hash;

public class BowHandler implements Listener {
	public static HashMap<UUID, Object[]> useTime = new HashMap<>();
	public static HashMap<UUID, BossBar> useTimeBar = new HashMap<>();
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

					if((long)status[0] == 0)
						return;

					double timer = container.getOrDefault(Main.attributeKeys.get( "drawTime"), PersistentDataType.DOUBLE, 0.0);
					BossBar progress = useTimeBar.get(uuid);
					long timeLeft = System.currentTimeMillis() - (long)status[0];
					

					if(timeLeft >= timer*1000){
						onFullyDrawn(p, item, meta, container);
						useTime.replace(uuid, new Object[]{status[0], true, false});
						if(progress != null){
							progress.setProgress(1.0);
							progress.setTitle("Charge: Ready");
						}
					}else if(progress != null){
						progress.setProgress(1-(timer - timeLeft/1000.0)/timer);
						progress.setTitle("Charge: " + timeLeft/1000.0 + "s");
					}
				}
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 20, 1);
	}

	@EventHandler
	public void onDraw(PlayerInteractEvent e) {
		if(e.getItem() != null && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) ) {
			Player p = e.getPlayer();

			if(p.getCooldown(e.getItem().getType()) > 0)
				return;

			ItemMeta meta = e.getItem().getItemMeta();
			if(meta == null)
				return;

			PersistentDataContainer container = meta.getPersistentDataContainer();
			double drawTime = container.getOrDefault(Main.attributeKeys.get("drawTime"), PersistentDataType.DOUBLE, 0.0);
			if(drawTime == 0.0)
				return;

			if(e.getItem().getType() == Material.BOW || e.getItem().getType() == Material.CROSSBOW)
				useTime.put(p.getUniqueId(), new Object[]{0l, false, true});
			else{
				useTime.put(p.getUniqueId(), new Object[]{System.currentTimeMillis(), false, false});
		
				double timer = container.getOrDefault(Main.attributeKeys.get( "drawTime"), PersistentDataType.DOUBLE, 0.0);
				BossBar progress = Bukkit.createBossBar("Charge: " + timer + "s", BarColor.BLUE, BarStyle.SEGMENTED_20);
				progress.addPlayer(p);
		
				useTimeBar.put(p.getUniqueId(), progress);
			}
		}
	}
	@EventHandler
	public void onArrowCalc(PlayerReadyArrowEvent e) {
		Player p = e.getPlayer();
		if(!p.getInventory().contains(Material.ARROW))
			return;

		if(!(boolean)useTime.getOrDefault(p.getUniqueId(), new Object[]{0l, false, false})[2])
			return;

		useTime.put(p.getUniqueId(), new Object[]{System.currentTimeMillis(), false, false});

		ItemStack item = p.getInventory().getItemInMainHand();
		if(item == null)
			return;
		ItemMeta meta = item.getItemMeta();
		if(meta == null)
			return;
		PersistentDataContainer container = meta.getPersistentDataContainer();
		double timer = container.getOrDefault(Main.attributeKeys.get( "drawTime"), PersistentDataType.DOUBLE, 0.0);
		BossBar progress = Bukkit.createBossBar("Charge: " + timer + "s", BarColor.BLUE, BarStyle.SEGMENTED_20);
		progress.addPlayer(p);

		useTimeBar.put(p.getUniqueId(), progress);
	}

	@EventHandler
	public void onShoot(ProjectileLaunchEvent e){
		if(!(e.getEntity().getShooter() instanceof Player))
			return;

		Player p = (Player) e.getEntity().getShooter();
		UUID uuid = p.getUniqueId();
		if(!useTime.containsKey(uuid))
			return;

		ItemStack item = p.getInventory().getItemInMainHand();
		if(item == null)
			return;
		ItemMeta meta = item.getItemMeta();
		if(meta == null)
			return;
		PersistentDataContainer container = meta.getPersistentDataContainer();
		String itemID = container.get(Main.attributeKeys.get( "itemID"), PersistentDataType.STRING);
		if(itemID == null || itemID.isEmpty()) return;
		Object[] status = useTime.get(uuid);

		double crossbowCharge = container.getOrDefault(Main.attributeKeys.get("chargePct"), PersistentDataType.DOUBLE, 0.0);
		//If fully charged:
		if((boolean)status[1] || crossbowCharge >= 1.0){
			switch(itemID){
				case "LIGHTNING_GREATBOW","LIGHTNING_TRIDENT"->{
					e.getEntity().remove();
					Location eyeLocation = p.getEyeLocation();
					Predicate<Entity> ignoreList = i -> (i != p && i instanceof LivingEntity && !i.isDead() && i.getType() != EntityType.ARMOR_STAND);
					RayTraceResult traceResult = p.getWorld().rayTrace(eyeLocation, eyeLocation.getDirection(), 100.0, FluidCollisionMode.NEVER, true,0.25, ignoreList);
					if(traceResult != null)
					{
						Vector hitLocation = traceResult.getHitPosition();
						p.getWorld().strikeLightningEffect(hitLocation.toLocation(p.getWorld()));

						Collection<Entity> entities = p.getWorld().getNearbyEntities(hitLocation.toLocation(p.getWorld()), 6.0, 6.0, 6.0, ignoreList);
						double damageDealt = 2.0*CustomAttributes.getDamageModified(uuid, true);
						for(Entity entity : entities) {
							EntityHandling.dealDamageToEntity((LivingEntity)entity, p, damageDealt, true, 1);
						}
					}
				}
				case "REPEATER_CROSSBOW" ->{
					HashMap<String, Double> newProjectileAttributes = new HashMap<>();
					boolean isCrit = Math.random() < Main.getAttributes().get(uuid).getOrDefault("CritChance", 0.0);
					double customDamage = CustomAttributes.getDamageModified(uuid, isCrit)*crossbowCharge;
					newProjectileAttributes.put("Damage", customDamage);
					newProjectileAttributes.put("CriticalAttack", isCrit ? 1.0 : 0.0);
					
					EntityHandling.projectileAttributes.put(e.getEntity().getUniqueId(), newProjectileAttributes);
					p.setCooldown(item.getType(), 3);
					new BukkitRunnable() {
						int timerCount = 0;
						@Override
						public void run() {
							if(timerCount > 7){
								cancel();
								return;
							}
							if(!p.getInventory().getItemInMainHand().equals(item)){
								cancel();
								return;
							}

							boolean isCrit = Math.random() < Main.getAttributes().get(uuid).getOrDefault("CritChance", 0.0);
							double customDamage = CustomAttributes.getDamageModified(uuid, isCrit)*crossbowCharge;
							newProjectileAttributes.replace("Damage", customDamage);
							newProjectileAttributes.replace("CriticalAttack", isCrit ? 1.0 : 0.0);

							Arrow shortbowArrow = p.launchProjectile(Arrow.class, p.getLocation().getDirection().multiply(3.0));
							EntityHandling.projectileAttributes.put(shortbowArrow.getUniqueId(), newProjectileAttributes);
							p.getWorld().playSound(p.getLocation(), Sound.ITEM_CROSSBOW_SHOOT, 1.2f, 1.0f);
							p.setCooldown(item.getType(), 3);
							timerCount++;
						}
					}.runTaskTimer(Main.getInstance(), 3, 2);
				}
			}
		}else{//Non-charged:
			double timer = container.getOrDefault(Main.attributeKeys.get( "drawTime"), PersistentDataType.DOUBLE, 0.0);
			long timeLeft = System.currentTimeMillis() - (long)status[0];
			double chargePct = (1-(timer - timeLeft/1000.0)/timer);
			if(crossbowCharge > 0.0)
				chargePct = crossbowCharge > 1.0 ? 1.0 : crossbowCharge;

			switch(itemID){
				case "LIGHTNING_GREATBOW"->{
					Arrow ent = (Arrow) e.getEntity();
					ent.setCritical(false);
					ent.setPierceLevel(ent.getPierceLevel() + 4);
					boolean isCrit = Math.random() < Main.getAttributes().get(uuid).getOrDefault("CritChance", 0.0);
					double customDamage = CustomAttributes.getDamageModified(uuid, isCrit)*chargePct;
					HashMap<String, Double> attributes = new HashMap<>();
					attributes.put("Damage", customDamage);
					attributes.put("CriticalAttack", isCrit ? 1.0 : 0.0);

					EntityHandling.projectileAttributes.put(ent.getUniqueId(), attributes);
					//Particle
					new BukkitRunnable() {
						int timerCount = 0;
						@Override
						public void run() {
							if(timerCount > 5 || ent.isDead() || ent.isInBlock()){
								cancel();
								return;
							}
							ent.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, ent.getLocation(), 3);
							timerCount++;
						}
					}.runTaskTimer(Main.getInstance(), 0, 10);
				}
			}
		}
		if(item.getType() != Material.BOW)
			onCancelDraw(p);
		if(crossbowCharge > 0.0)
			{container.set(Main.attributeKeys.get("chargePct"), PersistentDataType.DOUBLE, 0.0);item.setItemMeta(meta);}
	}
	public void onFullyDrawn(Player p, ItemStack item, ItemMeta meta, PersistentDataContainer container){
		String itemID = container.get(Main.attributeKeys.get( "itemID"), PersistentDataType.STRING);
		if(itemID == null || itemID.isEmpty()) return;

		switch(itemID){
			case "LIGHTNING_GREATBOW","LIGHTNING_TRIDENT"->{
				p.getWorld().playSound(p.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 0.8f,1f);
				p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation(), 50);
			}
			case "REPEATER_CROSSBOW"->{
				p.getWorld().playSound(p.getLocation(), Sound.ITEM_CROSSBOW_LOADING_MIDDLE, 2.5f,0.5f);
				p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation(), 50);
			}
			default ->{
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.75f,1f);
				p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation(), 50);
			}
		}
	}
	public static void onCrossbowStopLoading(Player p){
		ItemStack item = p.getInventory().getItemInMainHand();
		if(item == null)
			return;
		ItemMeta meta = item.getItemMeta();
		if(meta == null)
			return;
		PersistentDataContainer container = meta.getPersistentDataContainer();
		double timer = container.getOrDefault(Main.attributeKeys.get( "drawTime"), PersistentDataType.DOUBLE, 0.0);
		long timeLeft = System.currentTimeMillis() - (long)useTime.get(p.getUniqueId())[0];
		container.set(Main.attributeKeys.get( "chargePct"), PersistentDataType.DOUBLE, (1-(timer - timeLeft/1000.0)/timer));
		item.setItemMeta(meta);
		onCancelDraw(p);
	}
	public static void onCancelDraw(Player p){
		UUID id = p.getUniqueId();

		if(useTimeBar.containsKey(id)){
			BossBar bar = useTimeBar.get(id);
			bar.removeAll();
			useTimeBar.remove(id);
		}
		useTime.remove(id);
	}
}