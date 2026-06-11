package startrealms.cc.totemofkeeping.util;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import startrealms.cc.totemofkeeping.Totemofkeeping;

import java.util.Arrays;

public class TotemItem {

    private static final NamespacedKey KEY =
            new NamespacedKey(Totemofkeeping.getInstance(), "totem_of_keeping");

//    public static ItemStack createTotem() {
//        ItemStack item = new ItemStack(Material.TOTEM_OF_UNDYING);
//        ItemMeta meta = item.getItemMeta();
//
//        if (meta == null) return item;
//
//        meta.setDisplayName("§dTotem of Keeping");
//        meta.setLore(Arrays.asList(
//                "§7Prevents item loss on death",
//                "§7Works only in PvP"
//        ));
//
//        meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, 1);
//
//        item.setItemMeta(meta);
//        return item;
//    }
public static ItemStack createTotem() {
    ItemStack item = new ItemStack(Material.TOTEM_OF_UNDYING);
    ItemMeta meta = item.getItemMeta();

    if (meta == null) return item;

    meta.setDisplayName("§dTotem of Keeping");
    meta.setLore(Arrays.asList(
            "§7Prevents item loss on death",
            "§7Works only in PvP"
    ));

    // Glow effect
    meta.addEnchant(Enchantment.UNBREAKING, 1, true);
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

    meta.getPersistentDataContainer().set(KEY, PersistentDataType.INTEGER, 1);

    item.setItemMeta(meta);
    return item;
}

    public static boolean isTotem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().has(KEY, PersistentDataType.INTEGER);
    }
}