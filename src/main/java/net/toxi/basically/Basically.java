package net.toxi.basically;

import net.fabricmc.api.ModInitializer;

import net.toxi.basically.blocks.ModBlocks;
import net.toxi.basically.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Basically implements ModInitializer {
	public static final String MOD_ID = "basically";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlock();
		ModBlocks.registerRenderLayers();
	}
}