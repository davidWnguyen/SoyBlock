package markiplites.SoyBlock.MenuItems.MainMenu;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import markiplites.SoyBlock.SubMenus.Skills.SkillMainMenu;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public class SkillsMenu extends AbstractItem {
    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.ENCHANTED_BOOK).setDisplayName(IridiumColorAPI.process("<SOLID:ff9999>Skills"))
            .addLoreLines(IridiumColorAPI.process("<SOLID:ff9966>See your skill progression."));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        SkillMainMenu.open(player);
        notifyWindows(); // this will update the ItemStack that is displayed to the player
    }

}
