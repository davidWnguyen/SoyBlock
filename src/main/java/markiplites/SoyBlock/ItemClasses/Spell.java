package markiplites.SoyBlock.ItemClasses;

import markiplites.SoyBlock.Item;
import markiplites.SoyBlock.attr;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class Spell extends Item implements Listener
{
	public Spell(String itemID, String itemName, Material mat, HashMap<attr, Double> attributes, String lore)
	{
		super(itemID,itemName,mat,attributes,lore,21.0);
		Bukkit.getLogger().info("Added Spell: " + itemID + " to item dictionary.");
		this.finalizeItem(itemID);
	}
}
