package net.toxi.basically;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class FallingBlockVelocityHandler {
    private static final Map<FallingBlockEntity, DimensionData> trackedEntities = new HashMap<>();

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(FallingBlockVelocityHandler::onWorldTick);
    }
    private static class DimensionData {
        Direction direction;
        ServerWorld world;

        DimensionData(Direction direction, ServerWorld world) {
            this.direction = direction;
            this.world = world;
        }
    }

    public static void track(FallingBlockEntity entity, Direction direction, ServerWorld world) {
        trackedEntities.put(entity, new DimensionData(direction, world));
    }
    private static BlockPos nextDirection(BlockPos pos, Direction direction){
        return switch (direction){
            case WEST -> pos.add(1,0,0);
            case EAST -> pos.add(-1,0,0);
            case NORTH -> pos.add(0,0,1);
            case SOUTH -> pos.add(0,0,-1);
            case UP -> pos.add(0,-1,0);
            case DOWN -> pos.add(0,1,0);
        };
    }
    private static void onWorldTick(ServerWorld world) {
        Iterator<Map.Entry<FallingBlockEntity, DimensionData>> iterator = trackedEntities.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<FallingBlockEntity, DimensionData> entry = iterator.next();
            FallingBlockEntity entity = entry.getKey();
            DimensionData data = entry.getValue();
            world.getServer().execute(() -> {
                entity.setNoGravity(true);
            });

            BlockPos pos = entity.getBlockPos();
            BlockPos nextPos = nextDirection(pos,data.direction); // Prochaine position selon la direction
            System.out.println("Entity at " + pos + " moving to " + nextPos);
            if (entity.isRemoved() || !entity.isAlive()) {
                System.out.println("L'entité a été supprimée avant d'appliquer la vitesse !");
                iterator.remove(); // Supprime les entités mortes
                continue;
            }
            System.out.println("Tracking " + trackedEntities.size() + " entities.");

            boolean hasCollision = data.world.getBlockState(nextPos).isFullCube(data.world,nextPos);
            if (hasCollision) {
                // L'entité touche un mur, elle doit être transformée en bloc
                    System.out.println("Collision detected at " + nextPos + " -> " + world.getBlockState(nextPos));

                    world.setBlockState(pos, entity.getBlockState(), 3);
                    System.out.println("Trying to place block at " + pos + ", state: " + world.getBlockState(pos));
                    entity.discard(); // Supprime l'entité
                    iterator.remove(); // Retire l'entité de la liste suivie
            } else {
                // Applique la vitesse pour continuer la chute
                switch (data.direction) {
                    case EAST->entity.addVelocity(-0.02, 0.0, 0.0);
                    case WEST->entity.addVelocity(0.02, 0.0, 0.0);
                    case NORTH->entity.addVelocity(0.0, 0.0, 0.02);
                    case SOUTH->entity.addVelocity(0.0, 0.0, -0.02);
                    case UP->entity.addVelocity(0.0, -0.02, 0.0);
                    case DOWN->entity.addVelocity(0.0, 0.02, 0.0);
                }
            }
            Box entityBox = entity.getBoundingBox().expand(0.1); // Étend légèrement la zone de collision
            List<Entity> entities = world.getEntitiesByClass(Entity.class, entityBox, e -> !e.equals(entity));

            for (Entity e : entities) {
                e.addVelocity(entity.getVelocity().x*1.5,entity.getVelocity().y*1.5,entity.getVelocity().z*1.5);
            }

            }
        }
    }

