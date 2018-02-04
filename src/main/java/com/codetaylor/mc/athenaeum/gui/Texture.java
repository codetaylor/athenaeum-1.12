package com.codetaylor.mc.athenaeum.gui;

import net.minecraft.util.ResourceLocation;

/**
 * Created by codetaylor on 5/26/2017.
 */
public class Texture {

  private ResourceLocation resourceLocation;
  private int positionX;
  private int positionY;
  private int width;
  private int height;

  /**
   * Defines a texture.
   *
   * @param resourceLocation the location of the texture
   * @param positionX        the x position of the element
   * @param positionY        the y position of the element
   * @param width            the width of the texture
   * @param height           the height of the texture
   */
  public Texture(
      ResourceLocation resourceLocation,
      int positionX,
      int positionY,
      int width,
      int height
  ) {

    this.resourceLocation = resourceLocation;
    this.positionX = positionX;
    this.positionY = positionY;
    this.width = width;
    this.height = height;
  }

  public ResourceLocation getResourceLocation() {

    return this.resourceLocation;
  }

  public int getPositionX() {

    return this.positionX;
  }

  public int getPositionY() {

    return this.positionY;
  }

  public int getWidth() {

    return this.width;
  }

  public int getHeight() {

    return this.height;
  }
}
