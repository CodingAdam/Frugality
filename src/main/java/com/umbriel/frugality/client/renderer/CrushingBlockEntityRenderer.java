package com.umbriel.frugality.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.umbriel.frugality.block.workstation.CrushingBlock;
import com.umbriel.frugality.block.entity.CrushingBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class CrushingBlockEntityRenderer implements BlockEntityRenderer<CrushingBlockEntity> {

    public CrushingBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CrushingBlockEntity entity, float ticks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Direction direction = entity.getBlockState().getValue(CrushingBlock.FACING).getOpposite();
        ItemStack item = entity.getStoredItem();
        int i = (int)entity.getBlockPos().asLong();

        if(!item.isEmpty()){
            poseStack.pushPose();
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            boolean isBlock = itemRenderer.getModel(item, entity.getLevel(), null, 0).isGui3d();


            if(isBlock){
                renderBlock(poseStack, direction);
            }
            else{
                renderItem(poseStack, direction);
            }


            Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.FIXED, light, overlay, poseStack, bufferSource, i);
            poseStack.popPose();
        }
    }
    public void renderItem(PoseStack poseStack ,Direction direction){
        poseStack.translate(0.5D, 0.6D, 0.5D);
        float f = -direction.toYRot();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(f));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));

        poseStack.scale(0.7F, 0.7F, 0.7F);
    }

    public void renderBlock(PoseStack poseStack, Direction direction) {
        poseStack.translate(0.5D, 0.7D, 0.5D);
        float f = -direction.toYRot();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(f));

        poseStack.scale(0.9F, 0.9F, 0.9F);
    }
}
