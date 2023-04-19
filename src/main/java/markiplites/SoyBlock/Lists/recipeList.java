package markiplites.SoyBlock.Lists;

import markiplites.SoyBlock.ItemListHandler;
import markiplites.SoyBlock.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class recipeList implements Listener {
    public recipeList() {
        init();
    }
    public void init() {
        ItemStack item = ItemListHandler.generateItem("DIAMONDSWORD");
        NamespacedKey key = new NamespacedKey(Main.getInstance(), "diamond_sword");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape(" D ", " D ", " S ");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('S', Material.STICK);

        Bukkit.addRecipe(recipe);

        item = ItemListHandler.generateItem("NIGGER");
        key = new NamespacedKey(Main.getInstance(), "nigger");
        recipe = new ShapedRecipe(key, item);
        recipe.shape(" D ", " D ", " S ");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('S', Material.STICK);

        Bukkit.addRecipe(recipe);
    }
    @EventHandler
    public void prepareCraft(PrepareItemCraftEvent e){
        if(e.isRepair()) return;
        CraftingInventory inv = e.getInventory();
        ItemStack item = inv.getResult();

        if(item == null) return;
        ItemMeta meta = item.getItemMeta();

        if(meta == null) return;
        PersistentDataContainer p = meta.getPersistentDataContainer();

        if(p == null) return;
        String itemID = p.get(Main.attributeKeys.get( "itemID"), PersistentDataType.STRING);

        ItemStack[] items = inv.getMatrix();
        String[] itemIDs = new String[9];
        for(int i = 0;i < items.length; i++){
            if(items[i] == null) continue;
            ItemMeta m = items[i].getItemMeta();
            if(m == null) continue;
            PersistentDataContainer c = m.getPersistentDataContainer();
            if(c == null) continue;
            itemIDs[i] = c.get(Main.attributeKeys.get( "itemID"), PersistentDataType.STRING);
        }

        boolean failure = true;
        switch(itemID) {
            case "NIGGER":{//Diamond Swords
                if(Main.countStringArray(itemIDs,"PERFECT_DIAMOND") == 2)
                    failure = false;

                else if(Main.countStringArray(itemIDs,"DIAMOND") == 2)
                {inv.setResult(ItemListHandler.generateItem("DIAMONDSWORD"));failure = false;}
                break;
            }
            default:{
                failure = false;
            }
        }
        if(failure)
            inv.setResult(new ItemStack(Material.AIR));
    }
}
