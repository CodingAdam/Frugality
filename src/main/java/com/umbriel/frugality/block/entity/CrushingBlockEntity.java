package com.umbriel.frugality.block.entity;

import com.umbriel.frugality.block.workstation.CrushingBlock;
import com.umbriel.frugality.init.ModBlockEntities;
import com.umbriel.frugality.init.ModRecipes;
import com.umbriel.frugality.util.recipes.CrushingBlockRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class CrushingBlockEntity extends BlockEntity {

    private final ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> input;
    private ResourceLocation recipeID;
    private int currentHits;

    private boolean isItemOnBlock;

    public CrushingBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRUSHING_BLOCK.get(), pos, state);
        inventory = createHandler();
        input = LazyOptional.of(() -> inventory);
        isItemOnBlock = false;
        currentHits = 0;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        isItemOnBlock = compound.getBoolean("IsItemOnBlock");
        inventory.deserializeNBT(compound.getCompound("Inventory"));
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", inventory.serializeNBT());
        compound.putBoolean("IsItemOnBlock", isItemOnBlock);
    }

    public boolean processItem(ItemStack tool, Player player){
        if(level == null)
            return false;
        Optional<CrushingBlockRecipe> crushRecipe = getRecipe(new RecipeWrapper(inventory), tool, player);

        crushRecipe.ifPresent(recipe -> {
            if(currentHits == 0){
                currentHits = recipe.getHits();
            }
            if((currentHits - 1) == 0){
                currentHits -= 1;
                List<ItemStack> results = recipe.rollOutputs();
                for (ItemStack resultStack : results) {
                    Direction direction = getBlockState().getValue(CrushingBlock.FACING).getCounterClockWise();
                    ItemEntity entity = new ItemEntity(level, worldPosition.getX() + 0.5 + (direction.getStepX() * 0.2), worldPosition.getY() + 0.2, worldPosition.getZ() + 0.5 + (direction.getStepZ() * 0.2), resultStack.copy());
                    entity.setDeltaMovement(direction.getStepX() * 0.2F, 0.0F, direction.getStepZ() * 0.2F);
                    level.addFreshEntity(entity);
                }
                removeItem();
            }
            else {
                currentHits -= 1;
            }
            if (player != null) {
                tool.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            } else {
                if (tool.hurt(1, level.random, null)) {
                    tool.setCount(0);
                }
            }
            level.playSound(null, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        });
        return crushRecipe.isPresent();

    }

    public Optional<CrushingBlockRecipe> getRecipe(RecipeWrapper wrapper, ItemStack tool, Player player){
        if(level == null)
            return Optional.empty();
        if (recipeID != null) {
            List<CrushingBlockRecipe> recipes = level.getRecipeManager()
                    .getAllRecipesFor(ModRecipes.crushingBlockRecipeType);
            CrushingBlockRecipe recipe = findRecipe(recipes, recipeID);
            if (recipe != null && recipe.matches(wrapper, level) && ((CrushingBlockRecipe) recipe).getTool().test(tool)) {
                return Optional.of((CrushingBlockRecipe) recipe);
            }
        }

        List<CrushingBlockRecipe> recipeList = level.getRecipeManager().getRecipesFor(ModRecipes.crushingBlockRecipeType, wrapper, level);
        if (recipeList.isEmpty()) {
            return Optional.empty();
        }
        Optional<CrushingBlockRecipe> recipe = recipeList.stream().filter(cuttingRecipe -> cuttingRecipe.getTool().test(tool)).findFirst();
        if (!recipe.isPresent()) {
            return Optional.empty();
        }
        recipeID = recipe.get().getId();
        return recipe;
    }

    @Nullable
    public static CrushingBlockRecipe findRecipe(List<CrushingBlockRecipe> recipes, ResourceLocation recipeID) {
        for (final CrushingBlockRecipe recipe : recipes) {
            if (recipe.getId() == recipeID) {
                return recipe;
            }
        }
        return null;
    }

    public boolean addItem(ItemStack itemStack) {
        if (isEmpty() && !itemStack.isEmpty()) {
            inventory.setStackInSlot(0, itemStack.split(1));
            isItemOnBlock = false;
            inventoryChanged();
            return true;
        }
        return false;
    }

    public boolean toolOnBoard(ItemStack tool) {
        if (addItem(tool)) {
            isItemOnBlock = true;
            return true;
        }
        return false;
    }

    public ItemStack removeItem() {
        if (!isEmpty()) {
            isItemOnBlock = false;
            ItemStack item = getStoredItem().split(1);
            inventoryChanged();
            return item;
        }
        return ItemStack.EMPTY;
    }

    public IItemHandler getInventory() {
        return inventory;
    }

    public ItemStack getStoredItem() {
        return inventory.getStackInSlot(0);
    }

    public boolean isEmpty() {
        return inventory.getStackInSlot(0).isEmpty();
    }

    public boolean isItemOnBlock() {
        return isItemOnBlock;
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return input.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        input.invalidate();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler()
        {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    protected void inventoryChanged() {
        super.setChanged();
        if (level != null) level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }
}


