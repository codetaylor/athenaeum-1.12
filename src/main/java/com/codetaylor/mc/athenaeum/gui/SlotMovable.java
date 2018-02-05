package com.codetaylor.mc.athenaeum.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * Created by codetaylor on 5/27/2017.
 */
public class SlotMovable
    extends
    Slot {

  private int positionXOrigin;
  private int positionYOrigin;

  public SlotMovable(IInventory inventoryIn, int index, int xPosition, int yPosition) {
    super(inventoryIn, index, xPosition, yPosition);
    this.positionXOrigin = this.xPos;
    this.positionYOrigin = this.yPos;
  }

  public void slotMove(int positionX, int positionY) {
    this.xPos = positionX;
    this.yPos = positionY;
  }

  public void slotMoveToOrigin() {
    this.xPos = this.positionXOrigin;
    this.yPos = this.positionYOrigin;
  }

}
