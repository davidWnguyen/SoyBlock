package markiplites.SoyBlock.Lists;

import markiplites.SoyBlock.ItemClasses.Block;
import markiplites.SoyBlock.attr;
import org.bukkit.Bukkit;
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
        attributes.put(attr.blockExp, 1.0);
        attributes.put(attr.blockRegenerationTime, 3.0);
        attributes.put(attr.stackable, 1.0);
        attributes.put(attr.rarity, 1.0);
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

        attributes.clear();
        attributes.put(attr.blockDurability, 500.0);
        attributes.put(attr.blockHardness, 3.0);
        attributes.put(attr.blockTool, 2.0);
        attributes.put(attr.blockExp, 50.0);
        attributes.put(attr.blockRegenerationTime, 10.0);
        attributes.put(attr.stackable, 1.0);
        attributes.put(attr.rarity, 3.0);
        new Block("GIANT_REED", "Giant Reed", Material.MELON, attributes,
                "<SOLID:7a7a7a>Giant strands of hard and dense reed.",new String[]{"GIANT_REED"});
        attributes.clear();
        attributes.put(attr.blockDurability, 1.0);
        attributes.put(attr.blockHardness, 1.0);
        attributes.put(attr.blockExp, 0.0);
        attributes.put(attr.blockRegenerationTime, 10.0);
        attributes.put(attr.stackable, 1.0);
        attributes.put(attr.rarity, 1.0);
        new Block("SUGAR_CANE", "Sugar Cane", Material.SUGAR_CANE, attributes,
                "<SOLID:7a7a7a>Can be used to craft armor.",new String[]{"SUGAR_CANE"});
    }
}
