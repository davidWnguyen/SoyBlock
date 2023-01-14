package markiplites.SoyBlock;
import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	private static Main instance;
	ItemList itemList;
	public static HashMap<Player, HashMap<String, Double>> playerAttributes = new HashMap<>();
	
	FileConfiguration weaponConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "weaponConfig.yml"));
	FileConfiguration armorConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "armorConfig.yml"));
	FileConfiguration blockConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "blockConfig.yml"));
	FileConfiguration toolConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "toolConfig.yml"));
	
	@Override
	public void onEnable() {
		instance = this;
		itemList = new ItemList();
		Bukkit.getPluginManager().registerEvents(this, this); // registers eventlistener for events
		Bukkit.getPluginManager().registerEvents(new CustomAttributes(), this);
		Bukkit.getPluginManager().registerEvents(new HitDetection(), this);
		Bukkit.getPluginManager().registerEvents(new EntityHandling(), this);
		Bukkit.getPluginManager().registerEvents(new ClickableItems(), this);
		Bukkit.getPluginManager().registerEvents(new MiningSpeed(this), this);

		blargySouls testItem = new blargySouls();
		//Timers
		HUDTimer.run(instance);
		//Commands
		this.getCommand("sbgive").setExecutor(new Commands());
		this.getCommand("sbgive").setTabCompleter(new CommandsTabCompletion());
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) { //straight lollin' 
		Player p = e.getPlayer();
		p.setHealth(p.getMaxHealth());
		HashMap<String, Double> attributes = CustomAttributes.defaultStats();
		
		//Standard Procedure to calculate stats
		for(ItemStack checkItem : p.getInventory()) {
			if(checkItem != null && checkItem.equals(p.getItemInHand())) {
				ItemMeta meta = checkItem.getItemMeta();
				if(meta == null)
					return;
				PersistentDataContainer container = meta.getPersistentDataContainer();
				int itemType = container.has(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE)) : 1;
				if(itemType < 100)
				{
					CustomAttributes.giveItemStats(checkItem,attributes);
				}
			}
		}
		for(ItemStack checkItem: p.getEquipment().getArmorContents()) {
			if(checkItem != null) {
				ItemMeta meta = checkItem.getItemMeta();
				if(meta == null)
					return;
				PersistentDataContainer container = meta.getPersistentDataContainer();
				int itemType = container.has(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE)) : 1;
				if(itemType > 100)
					CustomAttributes.giveItemStats(checkItem,attributes);
			}
		}
		attributes.put("Health", attributes.get("MaxHealth"));
		playerAttributes.put(p, attributes);
	
	}
	public static Main getInstance() {
		return instance;
	}
	public static HashMap<Player, HashMap<String, Double>> getAttributes() {
		return playerAttributes;
	}
	
}