package net.toxi.basically.blocks;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.toxi.basically.Basically;

import javax.lang.model.element.Name;

public class ModBlocks {

    public static final Block BOB_BLOCK = registerBlock("bob_block", new TransparentBlock(AbstractBlock.Settings.create().strength(4f).nonOpaque()));
    public static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Basically.MOD_ID, name), block);
    }

    public static void registerRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(BOB_BLOCK, RenderLayer.getTranslucent());
    }
    public static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Basically.MOD_ID,name), new BlockItem(block, new Item.Settings()));
    }


    public static void registerModBlock() {
        Basically.LOGGER.info("Register blocks for "+ Basically.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries ->
                fabricItemGroupEntries.add(ModBlocks.BOB_BLOCK));

    }
}
