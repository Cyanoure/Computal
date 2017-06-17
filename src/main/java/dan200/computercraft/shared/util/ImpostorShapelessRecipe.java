/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2016. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.util;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;

public class ImpostorShapelessRecipe extends ShapelessRecipes {

    public ImpostorShapelessRecipe(String p_i47500_1_, ItemStack p_i47500_2_, NonNullList<Ingredient> p_i47500_3_) {
        super(p_i47500_1_, p_i47500_2_, p_i47500_3_);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting _inventory) {
        return null;
    }
}
