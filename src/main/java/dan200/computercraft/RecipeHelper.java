package dan200.computercraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.oredict.OreIngredient;

public class RecipeHelper {

    private static final String MODID = "computercraft";
    private static final String MODNAME = "Computality";
    private static int j = 0;

    /*
     * This adds the recipe to the list of crafting recipes.  Since who cares about names, it adds it as recipesX, where X is the current recipe you are adding.
     */
    public static void addRecipe(RegistryEvent.Register<IRecipe> register, int j, IRecipe rec) {
        if (rec.getRegistryName() == null)
            register.getRegistry().register(rec.setRegistryName(new ResourceLocation(MODID, "recipes" + j)));
        else register.getRegistry().register(rec);
    }

    /*
     * This adds the recipe to the list of crafting recipes.  Cares about names.
     */
    public static void addRecipe(RegistryEvent.Register<IRecipe> register, String name, IRecipe rec) {
        if (rec.getRegistryName() == null)
            register.getRegistry().register(rec.setRegistryName(new ResourceLocation(MODID, name)));
        else register.getRegistry().register(rec);
    }

    /*
     * Adds a shapeless recipe with X output using an array of inputs. Use Strings for OreDictionary support. This array is not ordered.
     */
    public static void addShapeless(RegistryEvent.Register<IRecipe> register, ItemStack output, Object... inputs) {
        addRecipe(register, j++, new ShapelessRecipes(MODID + ":" + j, output, createInput(inputs)));
    }

    public static void addShapeless(RegistryEvent.Register<IRecipe> register, Item output, Object... inputs) {
        addShapeless(register, new ItemStack(output), inputs);
    }

    public static void addShapeless(RegistryEvent.Register<IRecipe> register, Block output, Object... inputs) {
        addShapeless(register, new ItemStack(output), inputs);
    }

    /*
     * Adds a shapeless recipe with X output using an array of inputs. Use Strings for OreDictionary support. This array is not ordered.  This has a custom group.
     */
    public static void addShapeless(RegistryEvent.Register<IRecipe> register, String group, ItemStack output, Object... inputs) {
        addRecipe(register, j++, new ShapelessRecipes(MODID + ":" + group, output, createInput(inputs)));
    }

    public static void addShapeless(RegistryEvent.Register<IRecipe> register, String group, Item output, Object... inputs) {
        addShapeless(register, group, new ItemStack(output), inputs);
    }

    public static void addShapeless(RegistryEvent.Register<IRecipe> register, String group, Block output, Object... inputs) {
        addShapeless(register, group, new ItemStack(output), inputs);
    }

    /*
     * Adds a shapeless recipe with X output on a crafting grid that is W x H, using an array of inputs.  Use null for nothing, use Strings for OreDictionary support, this array must have a length of width * height.
     * This array is ordered, and items must follow from left to right, top to bottom of the crafting grid.
     */
    public static void addShaped(RegistryEvent.Register<IRecipe> register, ItemStack output, int width, int height, Object... input) {
        addRecipe(register, j++, genShaped(output, width, height, input));
    }

    public static void addShaped(RegistryEvent.Register<IRecipe> register, Item output, int width, int height, Object... input) {
        addShaped(register, new ItemStack(output), width, height, input);
    }

    public static void addShaped(RegistryEvent.Register<IRecipe> register, Block output, int width, int height, Object... input) {
        addShaped(register, new ItemStack(output), width, height, input);
    }

    /*
     * Adds a shapeless recipe with X output on a crafting grid that is W x H, using an array of inputs.  Use null for nothing, use Strings for OreDictionary support, this array must have a length of width * height.
     * This array is ordered, and items must follow from left to right, top to bottom of the crafting grid. This has a custom group.
     */
    public static void addShaped(RegistryEvent.Register<IRecipe> register, String group, ItemStack output, int width, int height, Object... input) {
        addRecipe(register, j++, genShaped(MODID + ":" + group, output, width, height, input));
    }

    public static void addShaped(RegistryEvent.Register<IRecipe> register, String group, Item output, int width, int height, Object... input) {
        addShaped(register, group, new ItemStack(output), width, height, input);
    }

    public static void addShaped(RegistryEvent.Register<IRecipe> register, String group, Block output, int width, int height, Object... input) {
        addShaped(register, group, new ItemStack(output), width, height, input);
    }

    public static ShapedRecipes genShaped(ItemStack output, int l, int w, Object[] input) {
        if (input[0] instanceof Object[]) input = (Object[]) input[0];
        if (l * w != input.length)
            throw new UnsupportedOperationException(
                    "Attempted to add invalid shaped recipe.  Complain to the author of  " + MODNAME);
        NonNullList<Ingredient> inputL = NonNullList.create();
        for (int i = 0; i < input.length; i++) {
            Object k = input[i];
            if (k instanceof String) {
                inputL.add(i, new OreIngredient((String) k));
            } else if (k instanceof ItemStack) {
                inputL.add(i, Ingredient.fromStacks((ItemStack) k));
            } else if (k instanceof Item) {
                inputL.add(i, Ingredient.fromStacks(new ItemStack((Item) k)));
            } else if (k instanceof Block) {
                inputL.add(i, Ingredient.fromStacks(new ItemStack((Block) k)));
            } else {
                inputL.add(i, Ingredient.EMPTY);
            }
        }

        return new ShapedRecipes(MODID + ":" + j, l, w, inputL, output);
    }

    public static ShapedRecipes genShaped(String group, ItemStack output, int l, int w, Object[] input) {
        if (input[0] instanceof Object[]) input = (Object[]) input[0];
        if (l * w != input.length)
            throw new UnsupportedOperationException(
                    "Attempted to add invalid shaped recipe.  Complain to the author of  " + MODNAME);
        NonNullList<Ingredient> inputL = NonNullList.create();
        for (int i = 0; i < input.length; i++) {
            Object k = input[i];
            if (k instanceof String) {
                inputL.add(i, new OreIngredient((String) k));
            } else if (k instanceof ItemStack) {
                inputL.add(i, Ingredient.fromStacks((ItemStack) k));
            } else if (k instanceof Item) {
                inputL.add(i, Ingredient.fromStacks(new ItemStack((Item) k)));
            } else if (k instanceof Block) {
                inputL.add(i, Ingredient.fromStacks(new ItemStack((Block) k)));
            } else {
                inputL.add(i, Ingredient.EMPTY);
            }
        }

        return new ShapedRecipes(group, l, w, inputL, output);
    }

    public static NonNullList<Ingredient> createInput(Object[] input) {
        if (input[0] instanceof Object[]) input = (Object[]) input[0];
        NonNullList<Ingredient> inputL = NonNullList.create();
        for (int i = 0; i < input.length; i++) {
            Object k = input[i];
            if (k instanceof String) {
                inputL.add(i, new OreIngredient((String) k));
            } else if (k instanceof ItemStack) {
                inputL.add(i, Ingredient.fromStacks((ItemStack) k));
            } else if (k instanceof Item) {
                inputL.add(i, Ingredient.fromItem((Item) k));
            } else if (k instanceof Block) {
                inputL.add(i, Ingredient.fromStacks(new ItemStack((Block) k)));
            } else {
                throw new UnsupportedOperationException(
                        "Attempted to add invalid shapeless recipe.  Complain to the author of  " + MODNAME);
            }
        }
        return inputL;
    }

}