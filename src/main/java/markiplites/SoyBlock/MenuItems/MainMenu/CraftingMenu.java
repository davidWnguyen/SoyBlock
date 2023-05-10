package markiplites.SoyBlock.MenuItems.MainMenu;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import markiplites.SoyBlock.SubMenus.Crafting.CraftingMainMenu;

public class CraftingMenu extends AbstractItem {
    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.ENCHANTED_BOOK).setDisplayName(IridiumColorAPI.process("<SOLID:ff9933>Crafting"))
            .addLoreLines(IridiumColorAPI.process("<SOLID:ffcc00>Craft using any unlocked station."));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        CraftingMainMenu.open(player);
        notifyWindows(); // this will update the ItemStack that is displayed to the player
    }

}
