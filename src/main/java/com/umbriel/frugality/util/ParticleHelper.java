package com.umbriel.frugality.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParticleHelper {
    public static void spawnCraftingParticles(Level worldIn, double vectorMul, float height, BlockPos pos, ItemStack stack, int count) {
        for (int i = 0; i < count; ++i) {
            Vec3 vec3d = new Vec3(((double) worldIn.random.nextFloat() - 0.5D) * 0.1D
                    ,Math.random() * 0.1D + 0.1D, ((double) worldIn.random.nextFloat() - 0.5D) * 0.1D).scale(vectorMul);
            if (worldIn instanceof ServerLevel) {
                ((ServerLevel) worldIn).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack)
                        , pos.getX() + 0.5F, pos.getY() + height, pos.getZ() + 0.5F, 1, vec3d.x, vec3d.y + 0.2D, vec3d.z, 0.0D);
            } else {
                worldIn.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack)
                        , pos.getX() + 0.5F, pos.getY() + height, pos.getZ() + 0.5F, vec3d.x, vec3d.y +  0.2D, vec3d.z);
            }
        }
    }

    public static void spawnWaterParticles(Level worldIn, float height, BlockPos pos, int count) {
        for (int i = 0; i < count; ++i) {
            Vec3 vec3d = new Vec3(((double) worldIn.random.nextFloat() - 0.5D) * 0.1D
                    , Math.random() * 0.2D + 0.1D, ((double) worldIn.random.nextFloat() - 0.5D) * 0.1D);
            if (worldIn instanceof ServerLevel) {
                ((ServerLevel) worldIn).sendParticles(ParticleTypes.SPLASH
                        , pos.getX() + 0.5F, pos.getY() + height, pos.getZ() + 0.5F, 1, vec3d.x, vec3d.y + 0.5D, vec3d.z, 0.0D);
            } else {
                worldIn.addParticle(ParticleTypes.SPLASH
                        , pos.getX() + 0.5F, pos.getY() + height, pos.getZ() + 0.5F, vec3d.x, vec3d.y +  0.5D, vec3d.z);
            }
        }
    }
}
