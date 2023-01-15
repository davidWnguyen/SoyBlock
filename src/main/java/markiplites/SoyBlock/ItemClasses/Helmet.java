package markiplites.SoyBlock.ItemClasses;

import markiplites.SoyBlock.Item;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;
import java.util.UUID;

public class Helmet extends Item implements Listener
{
	public Helmet(String itemID, String itemName, Material mat, HashMap<String, Double> attributes, String lore, String cosmetic)
	{
		super(itemID,itemName,mat,attributes,lore);
		ItemMeta meta = getItemMeta();
		ItemStack stack = getItemStack();
		if(mat == Material.PLAYER_HEAD && cosmetic != null)
		{
			UUID hashAsId = new UUID(cosmetic.hashCode(), cosmetic.hashCode());
			Bukkit.getUnsafe().modifyItemStack(stack,
					"{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + cosmetic + "\"}]}}}"
			);
			Bukkit.getLogger().info("Set " + itemID + " head value to " + cosmetic);
		}
		else if(cosmetic != null) {
			int it = 0;
			int red = 0;
			int green = 0;
			int blue = 0;
			for (String color : cosmetic.split(" ")) {
				if (it == 0) {
					red = Integer.parseInt(color);
				} else if (it == 1) {
					green = Integer.parseInt(color);
				} else if (it == 2) {
					blue = Integer.parseInt(color);
				}
				it++;
			}

			Color cosmeticColor = Color.fromRGB(red, green, blue);
			Bukkit.getLogger().info("Set " + itemID + " cosmetic value to " + cosmeticColor.asRGB());
			((LeatherArmorMeta) meta).setColor(cosmeticColor);
		}

		super.setItemMeta(meta);
		super.setItemStack(stack);
		Bukkit.getLogger().info("Added Boots: " + itemID + " to item dictionary.");
	}
}
