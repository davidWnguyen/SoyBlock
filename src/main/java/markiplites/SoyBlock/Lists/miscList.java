package markiplites.SoyBlock.Lists;

import markiplites.SoyBlock.Item;
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

        attributes.put(attr.rarity, 5.0);
        new Item("HEART_OF_THE_SEA", "<GRADIENT:0099ff>Heart of the Sea</GRADIENT:00cc66>", Material.HEART_OF_THE_SEA, attributes, 
                "<SOLID:ffffff>Dropped by <SOLID:669999>Elder Guardians, <SOLID:0099ff>Applies +1 Splishy Splash to weapons which:, <SOLID:ccccff>Splashes +5%/level damage within 3 blocks. Applies wet debuff for 1s/level.", 0.0);

        attributes.replace(attr.rarity, 4.0);
        new Item("SCULK_CATALYST", "<GRADIENT:003366>Sculk Catalyst</GRADIENT:0099ff>", Material.SCULK_CATALYST, attributes, 
                "<SOLID:ffffff>Dropped by <SOLID:003366>Boss Wardens, <SOLID:0099ff>Applies +1 Arcane Mastery to armor which:, <SOLID:ccccff>Increases INT scaling by <SOLID:ffcc00>+10%<SOLID:ccccff> and boosts ability damage by <SOLID:ffcc00>+15%.", 0.0);

        attributes.replace(attr.rarity, 3.0);
        new Item("PERFECT_DIAMOND", "Perfect Diamond", Material.DIAMOND, attributes, 
                "<SOLID:ffffff>Applies +1 level of Sharpness which, <SOLID:ccccff>Increases melee damage dealt from the weapon by +10%.", 0.0);
        new Item("ECHO_SHARD", "<GRADIENT:003366>Echo Shard</GRADIENT:0099ff>", Material.ECHO_SHARD, attributes, 
                "<SOLID:ffffff>Dropped by <SOLID:003366>Wardens, <SOLID:0099ff>Applies +1 level of clockwork to an item which:, <SOLID:ccccff>Reduces cooldown by <SOLID:ffcc00>-15%", 0.0);

        attributes.replace(attr.rarity, 2.0);
        new Item("DIAMOND", "Diamond", Material.DIAMOND, attributes,"", 0.0);
        new Item("EMERALD", "Emerald", Material.EMERALD, attributes,"", 0.0);

        attributes.replace(attr.rarity, 1.0);

        new Item("COPPER_INGOT", "Copper Ingot", Material.COPPER_INGOT, attributes, "", 0.0);
        new Item("IRON_INGOT", "Iron Ingot", Material.IRON_INGOT, attributes,"", 0.0);
        new Item("GOLD_INGOT", "Gold Ingot", Material.GOLD_INGOT, attributes, "", 0.0);
        new Item("LAPIS_LAZULI", "Lapis Lazuli", Material.LAPIS_LAZULI, attributes,"", 0.0);
        new Item("REDSTONE", "Redstone", Material.REDSTONE, attributes,"", 0.0);
        new Item("AMETHYST_SHARD", "Amethyst Shard", Material.AMETHYST_SHARD, attributes,"", 0.0);
        new Item("STICK", "Stick", Material.STICK, attributes,"", 0.0);

        attributes.replace(attr.rarity, 1.0);

        new Item("STICK", "Stick", Material.STICK, attributes,"", 0.0);
    }
}
