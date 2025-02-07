package net.toxi.basically.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.toxi.basically.Basically;

public class ModItems {
    public static final Item BOB_ITEM = registerItem("bob_item", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Basically.MOD_ID, name), item);
    }

    public static  void registerModItems() {
        Basically.LOGGER.info("Registering Mod items for "+ Basically.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(BOB_ITEM);
        });
    }


}
