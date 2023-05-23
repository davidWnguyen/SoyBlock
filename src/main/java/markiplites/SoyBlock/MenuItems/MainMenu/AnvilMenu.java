package markiplites.SoyBlock.MenuItems.MainMenu;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public class AnvilMenu extends AbstractItem {
    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.ANVIL).setDisplayName(IridiumColorAPI.process("<SOLID:ff9933>Anvil"))
            .addLoreLines(IridiumColorAPI.process("<SOLID:ffcc00>Standard Anvil"));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        player.openInventory(Bukkit.createInventory(player, InventoryType.ANVIL));
    }
}
