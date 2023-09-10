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
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
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
					if(status == null || (boolean)status[1])
						continue;

					ItemStack item = p.getInventory().getItemInMainHand();
					if(item == null)
						return;
					ItemMeta meta = item.getItemMeta();
					if(meta == null)
						return;
					PersistentDataContainer container = meta.getPersistentDataContainer();
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
		if(e.getItem() == null || !(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) || e.getPlayer().hasCooldown(e.getItem().getType()))
			return;
		
		Player p = e.getPlayer();
		switch(e.getItem().getType())
		{
			case BOW,CROSSBOW ->{
				useTime.put(p.getUniqueId(), new Object[]{System.currentTimeMillis(), false, true});
			}
			case TRIDENT->{
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
				progress.setVisible(true);
				progress.addPlayer(p);

				useTimeBar.put(p.getUniqueId(), progress);
			}
			default -> {

			}
		}
	}
	@EventHandler
	public void onArrowCalc(PlayerReadyArrowEvent e) {
		Player p = e.getPlayer();
		if(!p.getInventory().contains(Material.ARROW))
			return;

		if(!(boolean)useTime.getOrDefault(p.getUniqueId(), new Object[]{0, false, false})[2])
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
		progress.setVisible(true);
		progress.addPlayer(p);

		useTimeBar.put(p.getUniqueId(), progress);
	}

	@EventHandler
	public void onShoot(ProjectileLaunchEvent e){
		Projectile ent = e.getEntity();

		if(!(ent.getShooter() instanceof Player))
			return;

		Player p = (Player) ent.getShooter();
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

		double chargePct = container.getOrDefault(Main.attributeKeys.get("chargePct"), PersistentDataType.DOUBLE, 0.0);

		if(chargePct == 0.0){
			Object[] status = useTime.get(uuid);
			if(status == null)
				return;

			double timer = container.getOrDefault(Main.attributeKeys.get( "drawTime"), PersistentDataType.DOUBLE, 0.0);
			long timeLeft = System.currentTimeMillis() - (long)status[0];
			chargePct = 1-(timer - timeLeft/1000.0)/timer;
		}

		container.set(Main.attributeKeys.get("chargePct"), PersistentDataType.DOUBLE, 0.0);

		item.setItemMeta(meta);
		//If fully charged:
		if(chargePct >= 1.0){
			switch(itemID){
				case "LIGHTNING_GREATBOW","LIGHTNING_TRIDENT"->{
					ent.remove();
					Location eyeLocation = p.getEyeLocation();
					Predicate<Entity> ignoreList = i -> (i != p && i instanceof LivingEntity && !i.isDead() && i.getType() != EntityType.ARMOR_STAND);
					RayTraceResult traceResult = p.getWorld().rayTrace(eyeLocation, eyeLocation.getDirection(), 100.0, FluidCollisionMode.NEVER, true,0.25, ignoreList);
					if(traceResult != null)
					{
						Vector hitLocation = traceResult.getHitPosition();
						p.getWorld().strikeLightningEffect(hitLocation.toLocation(p.getWorld()));

						Collection<Entity> entities = p.getWorld().getNearbyEntities(hitLocation.toLocation(p.getWorld()), 6.0, 6.0, 6.0, ignoreList);
						for(Entity entity : entities) {
							EntityHandling.dealDamageToEntity((LivingEntity)entity, p, 2.0*CustomAttributes.getDamageModified(uuid, true), true, 1);
						}
					}
				}
				case "REPEATING_CROSSBOW" ->{
					ent.remove();
					new BukkitRunnable() {
						int timerCount = 0;
						@Override
						public void run() {
							if(timerCount > 15){
								cancel();
								return;
							}
							double projSpeed = 3.0*container.getOrDefault(Main.attributeKeys.get( "projectileSpeed"), PersistentDataType.DOUBLE, 1.0);
							Vector playerDirection = p.getLocation().getDirection();
							Arrow shortbowArrow = p.launchProjectile(Arrow.class, playerDirection.multiply(projSpeed));
							if (shortbowArrow == null)
								return;

							shortbowArrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);

							HashMap<String, Double> attributes = new HashMap<>();

							double critChance = Main.getAttributes().get(uuid).getOrDefault("CritChance", 0.0);
							boolean critBoolean = Math.random() < critChance;
							double customDamage = CustomAttributes.getDamageModified(p.getUniqueId(), critBoolean);

							attributes.put("Damage", customDamage);
							attributes.put("CriticalAttack", critBoolean ? 1.0 : 0.0);//lol

							EntityHandling.projectileAttributes.put(shortbowArrow.getUniqueId(), attributes);
							p.getWorld().playSound(p.getLocation(), Sound.ITEM_CROSSBOW_SHOOT, 0.8f,1f);
							timerCount++;
						}
					}.runTaskTimer(Main.getInstance(), 1, 3);
				}
			}
		}else if (chargePct <= 1.0){
			switch(itemID){
				case "LIGHTNING_GREATBOW","LIGHTNING_TRIDENT","REPEATING_CROSSBOW"->{
					((AbstractArrow)ent).setCritical(false);
					((AbstractArrow)ent).setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
					((AbstractArrow)ent).setPierceLevel(((AbstractArrow)ent).getPierceLevel() + 4);

					HashMap<String, Double> attributes = new HashMap<>();

					double critChance = Main.getAttributes().get(uuid).getOrDefault("CritChance", 0.0);
					boolean critBoolean = Math.random() < critChance;
					double customDamage = (0.5+chargePct)*CustomAttributes.getDamageModified(uuid, critBoolean);

					attributes.put("Damage", customDamage);
					attributes.put("CriticalAttack", critBoolean ? 1.0 : 0.0);//lol

					EntityHandling.projectileAttributes.put(ent.getUniqueId(), attributes);
					new BukkitRunnable() {
						int timerCount = 0;
						@Override
						public void run() {
							if(timerCount > 5 || ent.isDead() || ((AbstractArrow)ent).isInBlock()){
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
	}
	public void onFullyDrawn(Player p, ItemStack item, ItemMeta meta, PersistentDataContainer container){
		String itemID = container.get(Main.attributeKeys.get( "itemID"), PersistentDataType.STRING);
		if(itemID == null || itemID.isEmpty()) return;

		switch(itemID){
			case "LIGHTNING_GREATBOW","LIGHTNING_TRIDENT"->{
				p.getWorld().playSound(p.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 0.8f,1f);
				p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation(), 50);
			}
			default ->{
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.75f,1f);
				p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation(), 50);
			}
		}
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
	public static void finishCrossbowDraw(Player p){
		UUID id = p.getUniqueId();

		ItemStack item = p.getInventory().getItemInMainHand();
		if(item == null)
			return;

		ItemMeta meta = item.getItemMeta();
		if(meta == null)
			return;

		PersistentDataContainer data = meta.getPersistentDataContainer();
		if(data == null)
			return;

		String itemID = data.get(Main.attributeKeys.get( "itemID"), PersistentDataType.STRING);
		if(itemID == null) return;

		double timer = data.getOrDefault(Main.attributeKeys.get( "drawTime"), PersistentDataType.DOUBLE, 0.0);
		long timeLeft = System.currentTimeMillis() - (long)useTime.get(id)[0];
		double chargePct = 1-(timer - timeLeft/1000.0)/timer;

		data.set(Main.attributeKeys.get("chargePct"), PersistentDataType.DOUBLE, chargePct);
		item.setItemMeta(meta);

		if(useTimeBar.containsKey(id)){
			BossBar bar = useTimeBar.get(id);
			bar.removeAll();
			useTimeBar.remove(id);
		}
		useTime.remove(id);
	}
}