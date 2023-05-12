package markiplites.SoyBlock;

import markiplites.SoyBlock.Configs.skillsConfig;
import markiplites.SoyBlock.Lists.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener{
	private static Main instance;
	ItemListHandler itemListHandler;
	public static HashMap<UUID, HashMap<String, Double>> playerAttributes = new HashMap<>();
	public static HashMap<String, NamespacedKey> attributeKeys = new HashMap<>();
	public static HashMap<String, NamespacedKey> modifierKeys = new HashMap<>();
	
	@Override
	public void onEnable() {
		instance = this;
		//Cache all attribute namespaced keys
		for(attr attrName : attr.values()){
			attributeKeys.put(attrName.name(), new NamespacedKey(this, attrName.name()));
		}
		//Same for modifiers!
		for(modifiers mod : modifiers.values()){
			modifierKeys.put(mod.name(), new NamespacedKey(this, mod.name()));
		}
		//For all of the non-attribute stringy stuff
		attributeKeys.put("itemID", new NamespacedKey(this, "itemID"));
		attributeKeys.put("additionalLore", new NamespacedKey(this, "additionalLore"));
		attributeKeys.put("UUID", new NamespacedKey(this, "UUID"));
		attributeKeys.put("talismanFamily", new NamespacedKey(this, "talismanFamily"));
		attributeKeys.put("blockID", new NamespacedKey(this, "blockID"));
		attributeKeys.put("itemUUID", new NamespacedKey(this, "itemUUID"));

		Bukkit.clearRecipes();

		Bukkit.getPluginManager().registerEvents(this, this); // registers eventlistener for events
		Bukkit.getPluginManager().registerEvents(new CustomAttributes(), this);
		Bukkit.getPluginManager().registerEvents(new HitDetection(), this);
		Bukkit.getPluginManager().registerEvents(new EntityHandling(), this);
		Bukkit.getPluginManager().registerEvents(new itemList(), this);
		Bukkit.getPluginManager().registerEvents(new recipeList(), this);
		Bukkit.getPluginManager().registerEvents(new ClickableItems(), this);
		Bukkit.getPluginManager().registerEvents(new MiningSpeed(this), this);

		new entityList();
		new blockList();
		new miscList();
		new skillsConfig();
		itemListHandler = new ItemListHandler();
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
			CustomAttributes.getUpdatedPlayerAttributes(p, attributes, true);
			playerAttributes.put(p.getUniqueId(), attributes);
			attributes.put("Health", attributes.get("MaxHealth"));
			attributes.put("Mana", attributes.get("MaxMana"));
		}}.runTaskLater(Main.getInstance(), 10);
		
		ItemStack mainMenu = ItemListHandler.generateItem("SBMENU");
		p.getInventory().setItem(8, mainMenu);

		p.setResourcePack("http://136.57.191.195/fastdl/SoyblockTextures.zip","d3a619a6c29b0b092cf21741ebc46faedd6cb3d0");
		//10s later
		new BukkitRunnable(){public void run(){
			if(p.getResourcePackStatus() != PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED)
				p.sendMessage(IridiumColorAPI.process("<SOLID:800000>please just accept the texture pack"));
		}}.runTaskLater(Main.getInstance(), 200);
	}
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e)
	{
		UUID id = e.getPlayer().getUniqueId();
		Skills.skillLevels.remove(id);
		Skills.skillExperience.remove(id);
		itemList.ability_cooldown.remove(id);
		playerAttributes.remove(id);
	}
	public static Main getInstance() {
		return instance;
	}
	public static HashMap<UUID, HashMap<String, Double>> getAttributes() {
		return playerAttributes;
	}
	public static Vector getRightVector(Location loc){Location temp = loc.clone();temp.setYaw(temp.getYaw()+90.0F); return temp.getDirection();}
	public static Vector getUpVector(Location loc){Location temp = loc.clone();temp.setPitch(temp.getPitch()-90.0F); return temp.getDirection();}
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
	public static int countStringArray(String[] array, String compare){
		int c=0;
		for (String s : array) {
			if (s != null && s.equals(compare))
				c++;
		}
		Bukkit.getLogger().info("appeared "+c+" times");
		return c;
	}
	public static String getRomanNumber(int number) {
		return "I".repeat(number)
				.replace("IIIII", "V")
				.replace("IIII", "IV")
				.replace("VV", "X")
				.replace("VIV", "IX")
				.replace("XXXXX", "L")
				.replace("XXXX", "XL")
				.replace("LL", "C")
				.replace("LXL", "XC")
				.replace("CCCCC", "D")
				.replace("CCCC", "CD")
				.replace("DD", "M")
				.replace("DCD", "CM");
	}
	public enum ItemProperties{
		weaponType,
		itemAction
	}
}