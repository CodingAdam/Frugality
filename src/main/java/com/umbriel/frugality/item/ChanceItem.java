package com.umbriel.frugality.item;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class ChanceItem {

    private static final Random ran = new Random();
    private final ItemStack item;
    private final float chance;

    public ChanceItem(ItemStack item, float chance ){
        this.item = item;
        this.chance = chance;
    }

    public ItemStack getStack() {
        return item;
    }

    public float getChance() {
        return chance;
    }

    public ItemStack rollOutput() {
        int outputAmount = item.getCount();
        for (int roll = 0; roll < item.getCount(); roll++)
            if (ran.nextFloat() > chance) {
                outputAmount--;
            }
        if (outputAmount == 0) {
            return ItemStack.EMPTY;
        }
        ItemStack out = item.copy();
        out.setCount(outputAmount);
        return out;
    }

    public JsonElement toJson(){
        JsonObject json = new JsonObject();
        json.addProperty("item", item.getItem().getRegistryName().toString());
        int count = item.getCount();
        if(count > 1){
            json.addProperty("count", count);
        }
        if(chance < 1){
            json.addProperty("chance", chance);
        }
        return json;
    }

    public static ChanceItem fromJson(JsonElement element){
        JsonObject json = element.getAsJsonObject();
        String itemID = GsonHelper.getAsString(json, "item");
        int count = GsonHelper.getAsInt(json, "count", 1);
        float chance = GsonHelper.isValidNode(json, "chance") ? GsonHelper.getAsFloat(json, "chance") : 1;
        ItemStack itemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemID)), count);

        return new ChanceItem(itemStack, chance);
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeItem(getStack());
        buf.writeFloat(getChance());
    }

    public static ChanceItem read(FriendlyByteBuf buf) {
        return new ChanceItem(buf.readItem(), buf.readFloat());
    }

}
