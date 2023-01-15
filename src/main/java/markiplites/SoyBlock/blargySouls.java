package markiplites.SoyBlock;

import org.bukkit.Material;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;

public class blargySouls implements Listener
{
	private ArrayList<Sword> item = new ArrayList<Sword>();
	public blargySouls() {
		init();
	}
	public void init() {
		//splurgy souls lel
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
		attributes.put("itemType", 1.0);
		attributes.put("rarity", 6.0);
		item.add(new Sword("MURASAMA", "Murasama", Material.IRON_SWORD, attributes, "fuck da media!"));
		
		attributes.clear();
		
		
		attributes.put("baseDamage", 150.0);
		attributes.put("baseAttacKSpeed", 1.0);
		attributes.put("attackReachBonusRaw", 1.5);
		attributes.put("critChance", 0.5);
		attributes.put("moveSpeed", 0.1);
		attributes.put("strengthScaling", 1.5);
		attributes.put("dexterityScaling", 0.9);
		attributes.put("strengthBonusRaw", 75.0);
		attributes.put("dexterityBonusRaw", 25.0);
		attributes.put("itemType", 1.0);
		attributes.put("rarity", 1.0);
		item.add(new Sword("NIGGER", "Nigger", Material.WOODEN_SWORD, attributes, "niggar get out of china niggar :3333"));
		
	}
}
