package markiplites.SoyBlock;

import org.bukkit.Material;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class blargySouls implements Listener
{
	private Sword item;
	public blargySouls() {
		init();
	}
	public void init() {
		//splurgy souls lel
		item = new Sword();
		HashMap<String, Double> attributes = new HashMap<>();
		attributes.put("baseDamage", 1000.0);
		attributes.put("baseAttackSpeed", 3.0);
		attributes.put("attackReachBonusRaw", 5.0);
		attributes.put("critChance", 0.3);
		attributes.put("moveSpeed", 0.5);
		attributes.put("strengthScaling", 1.2);
		attributes.put("dexterityScaling", 1.35);
		attributes.put("strengthBonusRaw", 100.0);
		attributes.put("dexterityBonusRaw", 250.0);
		item.createItem("MURASAMA", "Murasama", Material.IRON_SWORD, attributes, "fuck da media!");
	}
}
