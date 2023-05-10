package markiplites.SoyBlock.SubMenus.Crafting;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import markiplites.SoyBlock.ItemListHandler;
import markiplites.SoyBlock.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class AnvilSubMenu
{
    public static void anvil_menu(Player p) {/*
        String[] guiSetup = {
                "bbbbbbbbb",
                "b       b",
                "b i+I=o b",
                "b       b",
                "bbbbbbbbb"
        };
        InventoryGui gui = new InventoryGui(Main.getInstance(), p, "Anvil", guiSetup);
        gui.setFiller(new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1)); // fill the empty slots with this
        //Output
        gui.addElement(new DynamicGuiElement('o', (viewer) -> {
            return new StaticGuiElement('o', new ItemStack (Material.CLOCK),
                    click -> {
                        click.getGui().draw(); // Update the GUI
                        return true;
                    },
                    "Output");
        }));
        //addition symbol
        gui.addElement(new StaticGuiElement('+',
                ItemListHandler.getItemForDisplay(Material.ARROW, 1000000),
                1,
                click -> {
                    return true; // returning true will cancel the click event and stop taking the item
                },
                " "
        ));
        //equals symbol
        gui.addElement(new StaticGuiElement('=',
                ItemListHandler.getItemForDisplay(Material.ARROW, 1000001),
                1,
                click -> {
                    return true; // returning true will cancel the click event and stop taking the item
                },
                " "
        ));

        gui.addElement(new StaticGuiElement('b',
                ItemListHandler.getItemForDisplay(Material.BLACK_STAINED_GLASS_PANE),
                1,
                click -> {
                    return true; // returning true will cancel the click event and stop taking the item
                },
                " "
        ));
        gui.show(p);
        */
    }
}
