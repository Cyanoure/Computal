/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2016. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.util;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ImpostorRecipe extends ShapedRecipes {
    public ImpostorRecipe(String group, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
        super(group, width, height, ingredients, result);
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting _inventory) {
        return ItemStack.EMPTY;
    }
}
