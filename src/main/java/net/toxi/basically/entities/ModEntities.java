package net.toxi.basically.entities;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.toxi.basically.Basically;

public class ModEntities {

    public static void registerModEntities() {
        Basically.LOGGER.info("Registering mod entities for " + Basically.MOD_ID);
    }
}

