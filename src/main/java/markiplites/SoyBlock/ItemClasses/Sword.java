package markiplites.SoyBlock.ItemClasses;

import java.util.HashMap;

import markiplites.SoyBlock.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;


public class Sword extends Item
{
	public Sword(String itemID, String itemName, Material mat, HashMap<String, Double> attributes, String lore)
	{
		super(itemID,itemName,mat,attributes,lore);
		Bukkit.getLogger().info("Added Sword: " + itemID + " to item dictionary.");
	}
}
