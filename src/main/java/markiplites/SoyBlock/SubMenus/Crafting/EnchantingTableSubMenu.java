package markiplites.SoyBlock.SubMenus.Crafting;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import markiplites.SoyBlock.ItemListHandler;
import markiplites.SoyBlock.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantingTableSubMenu
{
    public static void enchanting_table_menu(Player p) {/*
        //oh god this will be a pain in the ass
        String[] guiSetup = {
                "bbbbbbbbb",
                "b       b",
                "b   n   b",
                "b       b",
                "bbbbbbbbb"
        };
        InventoryGui gui = new InventoryGui(Main.getInstance(), p, "Enchanting Table", guiSetup);
        gui.setFiller(new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1)); // fill the empty slots with this

        gui.addElement(new StaticGuiElement('b',
                ItemListHandler.getItemForDisplay(Material.BLACK_STAINED_GLASS_PANE),
                1,
                click -> {
                    return true; // returning true will cancel the click event and stop taking the item
                },
                " "
        ));

        gui.addElement(new StaticGuiElement('n',
                ItemListHandler.getItemForDisplay(Material.WOODEN_SWORD),
                1,
                click -> {
                    return true; // returning true will cancel the click event and stop taking the item
                },
                IridiumColorAPI.process("<SOLID:846D49>NIGGER!!!!!")
        ));

        gui.show(p);
        */
    }
}
