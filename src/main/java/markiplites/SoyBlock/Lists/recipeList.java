package markiplites.SoyBlock.Lists;

import markiplites.SoyBlock.CustomAttributes;
import markiplites.SoyBlock.ItemListHandler;
import markiplites.SoyBlock.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;


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

    @EventHandler
    public void prepareAnvilCraft(PrepareAnvilEvent e){
        AnvilInventory inv = e.getInventory();
        if(inv == null)
            return;

        //Player p = (Player) e.getViewers().get(0);

        int xpLevelCost = 0;
        int itemCost = 1;

        ItemStack receivingItem = inv.getItem(0);
        ItemStack consumedItem = inv.getItem(1);

        if(consumedItem == null || receivingItem == null) return;
        ItemMeta receivingMeta = consumedItem.getItemMeta();
        ItemMeta consumedMeta = consumedItem.getItemMeta();

        if(consumedMeta == null || receivingMeta == null) return;
        PersistentDataContainer receivingContainer = receivingMeta.getPersistentDataContainer();
        PersistentDataContainer consumedContainer = consumedMeta.getPersistentDataContainer();

        if(receivingContainer == null || consumedContainer == null) return;
        String consumedItemID = consumedContainer.get(Main.attributeKeys.get("itemID"), PersistentDataType.STRING);

        if(consumedItemID == null || consumedItemID.isEmpty()) return;

        ItemStack result = receivingItem.clone();
        ItemMeta resultMeta = result.getItemMeta();
        PersistentDataContainer resultContainer = resultMeta.getPersistentDataContainer();

        boolean change = false;
        switch(consumedItemID){
            case "PERFECT_DIAMOND" ->{
                int amount = resultContainer.getOrDefault(Main.modifierKeys.get("clockwork"), PersistentDataType.INTEGER, 0);
                if(amount < 3){
                    resultContainer.set(Main.modifierKeys.get("clockwork"), PersistentDataType.INTEGER, amount+1);
                    change = true;
                }
            }
            case "ECHO_SHARD" ->{
                int amount = resultContainer.getOrDefault(Main.modifierKeys.get("clockwork"), PersistentDataType.INTEGER, 0);
                if(amount < 3){
                    resultContainer.set(Main.modifierKeys.get("clockwork"), PersistentDataType.INTEGER, amount+1);
                    change = true;
                }
            }
        }
        if(change){
            result.setItemMeta(resultMeta);
            CustomAttributes.updateItem(result);
            e.setResult(result);
        }else{
            e.setResult(null);
        }
        Main.getInstance().getServer().getScheduler().runTask(Main.getInstance(), () -> inv.setRepairCostAmount(itemCost));
        Main.getInstance().getServer().getScheduler().runTask(Main.getInstance(), () -> inv.setRepairCost(xpLevelCost));
    }
}
