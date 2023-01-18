package markiplites.SoyBlock;

import markiplites.SoyBlock.ItemList.blargySouls;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class Main extends JavaPlugin implements Listener{
	private static Main instance;
	ItemListHandler itemListHandler;
	public static HashMap<Player, HashMap<String, Double>> playerAttributes = new HashMap<>();
	
	FileConfiguration weaponConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "weaponConfig.yml"));
	FileConfiguration armorConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "armorConfig.yml"));
	FileConfiguration blockConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "blockConfig.yml"));
	FileConfiguration toolConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "toolConfig.yml"));
	
	@Override
	public void onEnable() {
		instance = this;
		itemListHandler = new ItemListHandler();
		Bukkit.getPluginManager().registerEvents(this, this); // registers eventlistener for events
		Bukkit.getPluginManager().registerEvents(new CustomAttributes(), this);
		Bukkit.getPluginManager().registerEvents(new HitDetection(), this);
		Bukkit.getPluginManager().registerEvents(new EntityHandling(), this);
		Bukkit.getPluginManager().registerEvents(new blargySouls(), this);
		Bukkit.getPluginManager().registerEvents(new ClickableItems(), this);
		Bukkit.getPluginManager().registerEvents(new MiningSpeed(this), this);

		blargySouls testItem = new blargySouls();
		//Timers
		HUDTimer.run(instance);
		//Commands
		this.getCommand("sbgive").setExecutor(new Commands());
		this.getCommand("sbgive").setTabCompleter(new CommandsTabCompletion());
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
		CustomAttributes.getUpdatedPlayerAttributes(p, attributes);
		attributes.put("Health", attributes.get("MaxHealth"));
		attributes.put("Mana", attributes.get("MaxMana"));
		playerAttributes.put(p, attributes);
	}
	public static Main getInstance() {
		return instance;
	}
	public static HashMap<Player, HashMap<String, Double>> getAttributes() {
		return playerAttributes;
	}
	
}