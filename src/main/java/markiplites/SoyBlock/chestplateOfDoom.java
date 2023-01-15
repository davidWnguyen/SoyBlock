package markiplites.SoyBlock;

import org.bukkit.Material;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class chestplateOfDoom implements Listener
{
	private Chestplate item;
	public chestplateOfDoom() {
		init();
	}
	public void init() {
		//splurgy souls lel
		HashMap<String, Double> attributes = new HashMap<>();
		attributes.put("critChance", 0.3);
		attributes.put("moveSpeed", 0.5);
		attributes.put("strengthBonusRaw", 100.0);
		attributes.put("dexterityBonusRaw", 250.0);
		attributes.put("itemType", 101.0);
		attributes.put("rarity", 5.0);
		item = new Chestplate("chestplateOfDoom", "<GRADIENT:02e494>FAMILY CHEST DEATH DOOM CREST</GRADIENT:0252e4>",
				Material.LEATHER_CHESTPLATE, attributes, "carry on my wayward son","2 195 228");
	}
}
