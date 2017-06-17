/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2016. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.computer.items;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.computer.blocks.BlockMetalComputer;
import dan200.computercraft.shared.computer.blocks.IComputerTile;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMetalComputer extends ItemComputerBase {
    public static int HIGHEST_DAMAGE_VALUE_ID = 16382;

    public ItemMetalComputer(Block block) {
        super(block);
        setMaxStackSize(64);
        setHasSubtypes(true);
        setUnlocalizedName("computercraft.metal_computer");
        setCreativeTab(ComputerCraft.mainCreativeTab);
    }

    public ItemStack create(int id, String label, ComputerFamily family, BlockMetalComputer.BlockType metal) {
        // Ignore types we can't handle
        if (family != ComputerFamily.Metal) {
            return ItemStack.EMPTY;
        }

        // Build the damage
        int damage = metal.ordinal();

        // Return the stack
        ItemStack result = new ItemStack(this, 1, damage);

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("computerID", id);
        result.setTagCompound(nbt);

        if (label != null) {
            result.setStackDisplayName(label);
        }
        return result;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list) {
        if (func_194125_a(tabs)) {
            for (BlockMetalComputer.BlockType metal : BlockMetalComputer.BlockType.values())
                list.add(ComputerItemFactory.createMetal(-1, null, ComputerFamily.Metal, metal));
        }
    }


    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState)) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null && tile instanceof IComputerTile) {
                IComputerTile computer = (IComputerTile) tile;
                setupComputerAfterPlacement(stack, computer);
            }
            return true;
        }
        return false;
    }

    private void setupComputerAfterPlacement(ItemStack stack, IComputerTile computer) {
        // Set ID
        int id = getComputerID(stack);
        if (id >= 0) {
            computer.setComputerID(id);
        }

        // Set Label
        String label = getLabel(stack);
        if (label != null) {
            computer.setLabel(label);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        switch (getFamily(stack)) {
            case Metal:
            default: {
                return "tile.computercraft.metal_computer."+BlockMetalComputer.BlockType.get(stack.getMetadata()).getName();
            }
        }
    }

    // IComputerItem implementation

    @Override
    public int getComputerID(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("computerID")) {
            return stack.getTagCompound().getInteger("computerID");
        } else {
            int damage = stack.getItemDamage() & 0x3fff;
            return (damage - 1);
        }
    }

    @Override
    public ComputerFamily getFamily(int damage) {
        if ((damage & 0x4000) != 0) {
            return ComputerFamily.Advanced;
        }
        return ComputerFamily.Normal;
    }
}
