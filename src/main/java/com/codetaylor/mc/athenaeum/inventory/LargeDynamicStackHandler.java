package com.codetaylor.mc.athenaeum.inventory;

import com.codetaylor.mc.athenaeum.util.StackHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;

public class LargeDynamicStackHandler
    extends DynamicStackHandler {

  public LargeDynamicStackHandler(int initialSize) {

    super(initialSize);
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
  public void deserializeNBT(CompoundNBT nbt) {

    setSize(nbt.contains("Size", Constants.NBT.TAG_INT) ? nbt.getInt("Size") : this.stacks.size());
    ListNBT tagList = nbt.getList("Items", Constants.NBT.TAG_COMPOUND);

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
