package net.toxi.basically.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.toxi.basically.FallingBlockVelocityHandler;
public class BobBlock extends FallingBlock {
    public BobBlock(AbstractBlock.Settings settings) {
        super(settings);
    }
    @Override
    protected MapCodec<? extends FallingBlock> getCodec() {
        return null;
    }



    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.setNoGravity(true);
    }
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Direction direction = hit.getSide();
        if (world.isClient||direction== null) {
            return ActionResult.SUCCESS;
        } else {
                FallingBlockEntity fallingBlock = FallingBlockEntity.spawnFromBlock(world, pos, state);

                if (fallingBlock != null) {
                    fallingBlock.setNoGravity(true);
                    fallingBlock.velocityDirty = true;

                    if (world instanceof ServerWorld serverWorld) {
                        FallingBlockVelocityHandler.track(fallingBlock, direction, serverWorld);
                    }
                }

                    return ActionResult.CONSUME;
        }
    }
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    }

}
