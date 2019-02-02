package com.codetaylor.mc.athenaeum.inventory;

import com.codetaylor.mc.athenaeum.util.StackHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class LargeDynamicStackHandler
    extends DynamicStackHandler {

  public LargeDynamicStackHandler(int initialSize) {

    super(initialSize);
  }

  @Override
  public NBTTagCompound serializeNBT() {

    NBTTagList nbtTagList = new NBTTagList();

    for (int i = 0; i < this.stacks.size(); i++) {

      if (!this.stacks.get(i).isEmpty()) {
        NBTTagCompound itemTag = new NBTTagCompound();
        itemTag.setInteger("Slot", i);
        StackHelper.writeLargeItemStack(this.stacks.get(i), itemTag);
        nbtTagList.appendTag(itemTag);
      }
    }

    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setTag("Items", nbtTagList);
    nbt.setInteger("Size", this.stacks.size());
    return nbt;
  }

  @Override
  public void deserializeNBT(NBTTagCompound nbt) {

    setSize(nbt.hasKey("Size", Constants.NBT.TAG_INT) ? nbt.getInteger("Size") : this.stacks.size());
    NBTTagList tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);

    for (int i = 0; i < tagList.tagCount(); i++) {
      NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
      int slot = itemTags.getInteger("Slot");

      if (slot >= 0 && slot < this.stacks.size()) {
        this.stacks.set(slot, StackHelper.readLargeItemStack(itemTags));
      }
    }
    this.onLoad();
  }
}
