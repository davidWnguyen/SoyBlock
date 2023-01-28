package markiplites.SoyBlock.ItemClasses;

import markiplites.SoyBlock.Item;
import markiplites.SoyBlock.attr;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;

public class Boots extends Item
{
	public Boots(String itemID, String itemName, Material mat, HashMap<attr, Double> attributes, String lore, String rgb)
	{
		super(itemID,itemName,mat,attributes,lore,103.0);
		ItemMeta meta = getItemMeta();
		ItemStack stack = getItemStack();
		if(rgb != null)
		{
			int it = 0;
			int red = 0;
			int green = 0;
			int blue = 0;
			for(String color: rgb.split(" "))
			{
				if(it == 0)
				{
					red = Integer.parseInt(color);
				}
				else if(it == 1)
				{
					green = Integer.parseInt(color);
				}
				else if(it == 2)
				{
					blue = Integer.parseInt(color);
				}
				it++;
			}

			Color rgbColor = Color.fromRGB(red, green, blue);
			Bukkit.getLogger().info("Set " + itemID + " RGB value to " + rgbColor.asRGB());
			((LeatherArmorMeta) meta).setColor(rgbColor);
		}
		super.setItemMeta(meta);
		super.setItemStack(stack);
		Bukkit.getLogger().info("Added Boots: " + itemID + " to item dictionary.");
		this.finalizeItem(itemID);
	}
}
