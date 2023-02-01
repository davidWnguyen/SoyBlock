package markiplites.SoyBlock;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import markiplites.SoyBlock.Lists.entityList;
import markiplites.SoyBlock.Lists.itemList;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener{
	private static Main instance;
	ItemListHandler itemListHandler;
	public static HashMap<UUID, HashMap<String, Double>> playerAttributes = new HashMap<>();
	
	@Override
	public void onEnable() {
		instance = this;
		itemListHandler = new ItemListHandler();
		Bukkit.getPluginManager().registerEvents(this, this); // registers eventlistener for events
		Bukkit.getPluginManager().registerEvents(new CustomAttributes(), this);
		Bukkit.getPluginManager().registerEvents(new HitDetection(), this);
		Bukkit.getPluginManager().registerEvents(new EntityHandling(), this);
		Bukkit.getPluginManager().registerEvents(new itemList(), this);
		Bukkit.getPluginManager().registerEvents(new ClickableItems(), this);
		new entityList();
		//Bukkit.getPluginManager().registerEvents(new MiningSpeed(this), this);
		//Timers
		HUDTimer.run(instance);
		//Commands
		this.getCommand("sbgive").setExecutor(new Commands());
		this.getCommand("sbgive").setTabCompleter(new CommandsTabCompletion());


		for(World world : Bukkit.getWorlds())
		{
			world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
			world.setGameRule(GameRule.DO_INSOMNIA, false);
			world.setGameRule(GameRule.KEEP_INVENTORY, true);
			world.setGameRule(GameRule.NATURAL_REGENERATION, false);
			world.setGameRule(GameRule.FALL_DAMAGE, false);
		}
	}
	@EventHandler
	public void onHungerChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) { //straight lollin' 
		Player p = e.getPlayer();
		p.setHealth(p.getMaxHealth());
		HashMap<String, Double> attributes = CustomAttributes.defaultStats();
		//Standard Procedure to calculate stats
		new BukkitRunnable(){public void run(){
			CustomAttributes.getUpdatedPlayerAttributes(p, attributes, false);
			playerAttributes.put(p.getUniqueId(), attributes);
			attributes.put("Health", attributes.get("MaxHealth"));
			attributes.put("Mana", attributes.get("MaxMana"));
		}}.runTaskLater(Main.getInstance(), 10);
		
		ItemStack mainMenu = ItemListHandler.generateItem("SBMENU");
		p.getInventory().setItem(8, mainMenu);
	}
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e)
	{
		playerAttributes.remove(e.getPlayer().getUniqueId());
	}
	public static Main getInstance() {
		return instance;
	}
	public static HashMap<UUID, HashMap<String, Double>> getAttributes() {
		return playerAttributes;
	}
	public static Vector getRightVector(Location loc){Location temp = loc.clone();temp.setYaw(temp.getYaw()+90.0F); return temp.getDirection();}
	public static Vector getUpVector(Location loc){Location temp = loc.clone();temp.setPitch(temp.getPitch()-90.0F);; return temp.getDirection();}
	public static void VectorAngles(Vector forward, double[] angle)
	{
		double tmp, yaw, pitch;

		if (forward.getX() == 0 && forward.getY() == 0)
		{
			yaw = 0;
			if (forward.getZ() > 0)
				pitch = 270;
			else
				pitch = 90;
		}
		else
		{
			yaw = (Math.atan2(forward.getY(), forward.getX()) * 180 / Math.PI);
			if (yaw < 0)
				yaw += 360;

			tmp =  Math.sqrt(forward.getX()*forward.getX() + forward.getY()*forward.getY());
			pitch = (Math.atan2(-forward.getZ(), tmp) * 180 / Math.PI);
			if (pitch < 0)
				pitch += 360;
		}

		angle[0] = pitch;
		angle[1] = yaw;
	}

	public enum ItemProperties{
		weaponType,
		itemAction
	}
}