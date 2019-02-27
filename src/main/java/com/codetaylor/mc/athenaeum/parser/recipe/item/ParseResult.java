package com.codetaylor.mc.athenaeum.parser.recipe.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by codetaylor on 11/17/2016.
 */
public class ParseResult {

  public static final ParseResult NULL = new ParseResult();

  private String domain;
  private String path;
  private int meta;
  private int quantity;

  public ParseResult() {
    //
  }

  public ParseResult(String domain, String path, int meta) {

    this(domain, path, meta, 1);
  }

  public ParseResult(String domain, String path, int meta, int quantity) {

    this.domain = domain;
    this.path = path;
    this.meta = meta;
    this.quantity = quantity;
  }

  public void setDomain(String domain) {

    this.domain = domain;
  }

  public void setPath(String path) {

    this.path = path;
  }

  public void setMeta(int meta) {

    this.meta = meta;
  }

  public void setQuantity(int quantity) {

    this.quantity = quantity;
  }

  public String getDomain() {

    return domain;
  }

  public String getPath() {

    return path;
  }

  public int getMeta() {

    return meta;
  }

  public int getQuantity() {

    return quantity;
  }

  @Override
  public String toString() {

    if (this == ParseResult.NULL) {
      return "null";
    }

    return this.domain + ":" + this.path + (("ore".equals(this.domain)) ? "" : ":" + this.meta + ((this.quantity != 1) ? " * " + this.quantity : ""));
  }

  public boolean matches(ItemStack itemStack, boolean ignoreQuantity) {

    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.getDomain(), this.getPath()));

    if (itemStack.getItem() != item) {
      return false;
    }

    if (this.getMeta() != OreDictionary.WILDCARD_VALUE
        && this.getMeta() != itemStack.getMetadata()) {
      return false;
    }

    return (ignoreQuantity) || (this.getQuantity() == itemStack.getCount());
  }
}
