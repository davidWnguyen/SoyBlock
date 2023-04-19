package markiplites.SoyBlock.Lists;

import markiplites.SoyBlock.ItemClasses.Block;
import markiplites.SoyBlock.attr;
import org.bukkit.Material;
import java.util.HashMap;

public class blockList {
    public static final HashMap<String, HashMap<attr, Double>> block_attributes = new HashMap<>();
    public static final HashMap<String, String[]> block_loot = new HashMap<>();
    public blockList() {
        init();
    }
    public void init() {
        HashMap<attr, Double> attributes = new HashMap<>();
        //All of the woods
        attributes.put(attr.blockDurability, 60.0);
        attributes.put(attr.blockHardness, 1.0);
        attributes.put(attr.blockTool, 2.0);
        attributes.put(attr.blockForagingExp, 5.0);
        attributes.put(attr.blockRegenerationTime, 3.0);
        attributes.put(attr.stackable, 1.0);
        attributes.put(attr.rarity, 1.0);
        //wood
        new Block("OAK_LOG", "Oak Log", Material.OAK_LOG, attributes,
                "mine diamond :3",new String[]{"OAK_LOG"});
        new Block("BIRCH_LOG", "Birch Log", Material.BIRCH_LOG, attributes,
                "<SOLID:7a7a7a>Wood lel",new String[]{"BIRCH_LOG"});
        new Block("ACACIA_LOG", "ACACIA Log", Material.ACACIA_LOG, attributes,
                "<SOLID:7a7a7a>Wood lel",new String[]{"ACACIA_LOG"});
        new Block("JUNGLE_LOG", "Jungle Log", Material.JUNGLE_LOG, attributes,
                "<SOLID:7a7a7a>Wood lel",new String[]{"JUNGLE_LOG"});
        new Block("MANGROVE_LOG", "Mangrove Log", Material.MANGROVE_LOG, attributes,
                "<SOLID:7a7a7a>Wood lel",new String[]{"MANGROVE_LOG"});
        new Block("SPRUCE_LOG", "Spruce Log", Material.SPRUCE_LOG, attributes,
                "<SOLID:7a7a7a>Wood lel",new String[]{"SPRUCE_LOG"});
        new Block("DARK_OAK_LOG", "Dark Oak Log", Material.DARK_OAK_LOG, attributes,
                "<SOLID:7a7a7a>Wood lel",new String[]{"DARK_OAK_LOG"});
        
        //stone
        attributes.replace(attr.blockTool, 1.0);
        attributes.remove(attr.blockForagingExp);
        attributes.put(attr.blockMiningExp, 5.0);
        new Block("STONE", "Stone", Material.STONE, attributes,
                "<SOLID:61737A>The bong method has worked out so far",new String[]{"COBBLESTONE"});
        new Block("COBBLESTONE", "Cobblestone", Material.COBBLESTONE, attributes,
                "<SOLID:61737A>The bong method has worked out so far",new String[]{"COBBLESTONE"});
        new Block("ANDESITE", "Andesite", Material.ANDESITE, attributes,
                "<SOLID:61737A>The bong method has worked out so far",new String[]{"ANDESITE"});
        new Block("DIORITE", "Diorite", Material.DIORITE, attributes,
                "<SOLID:61737A>The bong method has worked out so far",new String[]{"DIORITE"});
        new Block("GRANITE", "Granite", Material.GRANITE, attributes,
                "<SOLID:61737A>The bong method has worked out so far",new String[]{"GRANITE"});       
        new Block("DEEPSLATE", "Deepslate", Material.DEEPSLATE, attributes,
                "<SOLID:61737A>The bong method has worked out so far",new String[]{"DEEPSLATE"});
        new Block("DRIPSTONE_BLOCK", "Dripstone", Material.DRIPSTONE_BLOCK, attributes,
                "<SOLID:61737A>The bong method has worked out so far",new String[]{"DRIPSTONE_BLOCK"});
        new Block("SANDSTONE", "Sandstone", Material.SANDSTONE, attributes,
                "<SOLID:61737A>The bong method has worked out so far",new String[]{"SANDSTONE"});


        attributes.replace(attr.blockMiningExp, 60.0);
        new Block("COAL_ORE", "Coal Ore", Material.COAL_ORE, attributes,
                "ayyy lmao",new String[]{"COAL"});
        new Block("IRON_ORE", "Iron Ore", Material.IRON_ORE, attributes,
                "dub",new String[]{"IRON"});
        new Block("COPPER_ORE", "Copper Ore", Material.COPPER_ORE, attributes,
                "yeah",new String[]{"COPPER"});
        new Block("REDSTONE_ORE", "Granite", Material.REDSTONE_ORE, attributes,
                "reddy stones",new String[]{"REDSTONE","REDSTONE","REDSTONE"});
        attributes.replace(attr.blockMiningExp, 120.0);
        new Block("GOLD_ORE", "Gold Ore", Material.GOLD_ORE, attributes,
                "blingy soules",new String[]{"GOLD_INGOT"});
        new Block("LAPIS_ORE", "Lapis Lazuli Ore", Material.LAPIS_ORE, attributes,
                "bluey stones",new String[]{"LAPIS_LAZULI","LAPIS_LAZULI","LAPIS_LAZULI","LAPIS_LAZULI","LAPIS_LAZULI"});
        new Block("AMETHYST_CLUSTER", "Amethyst Cluster", Material.AMETHYST_CLUSTER, attributes,
                "purple purple",new String[]{"AMETHYST_SHARD","AMETHYST_SHARD"});

        attributes.replace(attr.blockMiningExp, 350.0);
        new Block("DIAMOND_ORE", "Diamond Ore", Material.DIAMOND_ORE, attributes,
                "swag",new String[]{"DIAMOND"});
        new Block("EMERALD_ORE", "Emerald Ore", Material.EMERALD_ORE, attributes,
                "dripped out",new String[]{"EMERALD"});

        attributes.clear();
        attributes.put(attr.blockDurability, 500.0);
        attributes.put(attr.blockHardness, 3.0);
        attributes.put(attr.blockTool, 2.0);
        attributes.put(attr.blockExp, 50.0);
        attributes.put(attr.blockForagingExp, 50.0);
        attributes.put(attr.blockRegenerationTime, 10.0);
        attributes.put(attr.stackable, 1.0);
        attributes.put(attr.rarity, 3.0);
        new Block("GIANT_REED", "Giant Reed", Material.MELON, attributes,
                "<SOLID:7a7a7a>Giant strands of hard and dense reed.",new String[]{"GIANT_REED"});

        attributes.clear();
        attributes.put(attr.blockDurability, 1.0);
        attributes.put(attr.blockFarmingExp, 5.0);
        attributes.put(attr.blockRegenerationTime, 10.0);
        attributes.put(attr.stackable, 1.0);
        attributes.put(attr.rarity, 1.0);
        new Block("SUGAR_CANE", "Sugar Cane", Material.SUGAR_CANE, attributes,
                "<SOLID:7a7a7a>Can be used to craft armor.",new String[]{"SUGAR_CANE"});
        new Block("OAK_LEAVES", "Oak Leaves", Material.OAK_LEAVES, attributes,
                "",new String[]{"STICK"});
        new Block("BIRCH_LEAVES", "Birch Leaves", Material.BIRCH_LEAVES, attributes,
                "",new String[]{"STICK"});
        new Block("JUNGLE_LEAVES", "Jungle Leaves", Material.JUNGLE_LEAVES, attributes,
                "",new String[]{"STICK"});
        new Block("ACACIA_LEAVES", "Acacia Leaves", Material.ACACIA_LEAVES, attributes,
                "",new String[]{"STICK"});
        new Block("DARK_OAK_LEAVES", "Dark Oak Leaves", Material.DARK_OAK_LEAVES, attributes,
                "",new String[]{"STICK"});
    }
}
