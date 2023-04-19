package markiplites.SoyBlock.Lists;

import markiplites.SoyBlock.Item;
import markiplites.SoyBlock.ItemListHandler;
import markiplites.SoyBlock.attr;
import org.bukkit.Material;

import java.util.HashMap;

public class miscList {
    public miscList() {
        init();
    }
    public void init() {
        HashMap<attr, Double> attributes = new HashMap<>();
        attributes.put(attr.stackable, 1.0);

        attributes.put(attr.rarity, 3.0);
        new Item("PERFECT_DIAMOND", "Perfect Diamond", Material.DIAMOND, attributes,
                "", 0.0);


        attributes.replace(attr.rarity, 2.0);
        new Item("DIAMOND", "Diamond", Material.DIAMOND, attributes,
                "", 0.0);
        new Item("EMERALD", "Emerald", Material.EMERALD, attributes,
                "", 0.0);

        attributes.replace(attr.rarity, 1.0);

        new Item("COPPER_INGOT", "Copper Ingot", Material.COPPER_INGOT, attributes,
                "", 0.0);
        new Item("IRON_INGOT", "Iron Ingot", Material.IRON_INGOT, attributes,
                "", 0.0);
        new Item("GOLD_INGOT", "Gold Ingot", Material.GOLD_INGOT, attributes,
                "", 0.0);
        new Item("LAPIS_LAZULI", "Lapis Lazuli", Material.LAPIS_LAZULI, attributes,
                "", 0.0);
        new Item("REDSTONE", "Redstone", Material.REDSTONE, attributes,
                "", 0.0);
        new Item("AMETHYST_SHARD", "Amethyst Shard", Material.AMETHYST_SHARD, attributes,
                "", 0.0);
        new Item("STICK", "Stick", Material.STICK, attributes,
                "", 0.0);
    }
}
