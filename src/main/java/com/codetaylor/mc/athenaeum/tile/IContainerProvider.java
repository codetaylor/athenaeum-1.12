package com.codetaylor.mc.athenaeum.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IContainerProvider<C extends Container, G extends GuiContainer> {

  C getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos);

  G getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos);

}
