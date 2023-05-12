package markiplites.SoyBlock.SubMenus.Crafting;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import markiplites.SoyBlock.MenuItems.MainMenu.AnvilMenu;
import markiplites.SoyBlock.MenuItems.MainMenu.CraftingTable;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class CraftingMainMenu {
    public static void open(Player p){
		String[] guiSetup = {
            "bbbbbbbbb",
            "bca.....b",
            "b.......b",
            "b.......b",
            "bbbbbbbbb"
    };
    Gui gui = Gui.normal()
            .setStructure(guiSetup)
            .addIngredient('b', new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)))
            .addIngredient('c', new CraftingTable())
            .addIngredient('a', new AnvilMenu())
            .build();

    Window window = Window.single()
            .setViewer(p)
            .setTitle("Crafting Main Menu")
            .setGui(gui)
            .build();
    
    window.open();
    }
}
