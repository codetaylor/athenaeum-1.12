package com.codetaylor.mc.athenaeum.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * https://github.com/BluSunrize/ImmersiveEngineering/blob/master/src/main/java/blusunrize/immersiveengineering/common/crafting/IngredientFluidStack.java
 */
public class IngredientFluidStack
    extends Ingredient {

  private final FluidStack fluid;
  private ItemStack[] cachedStacks;

  public IngredientFluidStack(FluidStack fluid) {

    super(0);
    this.fluid = fluid;
  }

  public IngredientFluidStack(Fluid fluid, int amount) {

    this(new FluidStack(fluid, amount));
  }

  public FluidStack getFluid() {

    return fluid;
  }

  @Nonnull
  @Override
  public ItemStack[] getMatchingStacks() {

    if (this.cachedStacks == null) {
      this.cachedStacks = new ItemStack[]{FluidUtil.getFilledBucket(this.fluid)};
    }
    return this.cachedStacks;
  }

  @Override
  public boolean apply(@Nullable ItemStack stack) {

    if (stack == null) {
      return false;

    } else {
      FluidStack fs = FluidUtil.getFluidContained(stack);
      return fs == null
          && this.fluid == null
          || fs != null
          && fs.containsFluid(fluid);
    }
  }

  /**
   * https://github.com/BluSunrize/ImmersiveEngineering/blob/master/src/main/java/blusunrize/immersiveengineering/common/crafting/IngredientFactoryFluidStack.java#L38
   */
  public static class Factory
      implements IIngredientFactory {

    @Nonnull
    @Override
    public Ingredient parse(JsonContext context, JsonObject json) {

      String name = JsonUtils.getString(json, "fluid");
      int amount = JsonUtils.getInt(json, "amount", 1000);
      Fluid fluid = FluidRegistry.getFluid(name);

      if (fluid == null) {
        throw new JsonSyntaxException("Fluid with name " + name + " could not be found");
      }

      return new IngredientFluidStack(fluid, amount);
    }
  }
}
