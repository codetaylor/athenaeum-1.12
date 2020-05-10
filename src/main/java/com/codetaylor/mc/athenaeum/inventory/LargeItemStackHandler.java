package com.codetaylor.mc.athenaeum.inventory;

import com.codetaylor.mc.athenaeum.util.StackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Serializes the stack count with an extra int in order to exceed the default
 * limitation of a byte: [-127,127].
 */
public class LargeItemStackHandler
    extends ItemStackHandler {

  public LargeItemStackHandler() {
    //
  }

  public LargeItemStackHandler(int size) {

    super(size);
  }

  public LargeItemStackHandler(NonNullList<ItemStack> stacks) {

    super(stacks);
  }

  @Override
  public CompoundNBT serializeNBT() {

    ListNBT nbtTagList = new ListNBT();

    for (int i = 0; i < this.stacks.size(); i++) {

      if (!this.stacks.get(i).isEmpty()) {
        CompoundNBT itemTag = new CompoundNBT();
        itemTag.putInt("Slot", i);
        StackHelper.writeLargeItemStack(this.stacks.get(i), itemTag);
        nbtTagList.add(itemTag);
      }
    }

    CompoundNBT nbt = new CompoundNBT();
    nbt.put("Items", nbtTagList);
    nbt.putInt("Size", this.stacks.size());
    return nbt;
  }

  @Override
  public void deserializeNBT(CompoundNBT tag) {

    setSize(tag.contains("Size", Constants.NBT.TAG_INT) ? tag.getInt("Size") : this.stacks.size());
    ListNBT tagList = tag.getList("Items", Constants.NBT.TAG_COMPOUND);

    for (int i = 0; i < tagList.size(); i++) {
      CompoundNBT itemTags = tagList.getCompound(i);
      int slot = itemTags.getInt("Slot");

      if (slot >= 0 && slot < this.stacks.size()) {
        this.stacks.set(slot, StackHelper.readLargeItemStack(itemTags));
      }
    }
    this.onLoad();
  }
}
