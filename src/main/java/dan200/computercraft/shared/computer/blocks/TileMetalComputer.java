/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2016. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.computer.blocks;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.computer.blocks.TileComputerBase;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.computer.core.IComputer;
import dan200.computercraft.shared.computer.core.ServerComputer;
import dan200.computercraft.shared.computer.items.ComputerItemFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class TileMetalComputer extends TileComputerBase {
    // Statics

    // Members

    // For legacy reasons, computers invert the meaning of "left" and "right"
    private static final int[] s_remapSide = {0, 1, 2, 3, 5, 4};
    public EnumFacing facing = EnumFacing.NORTH;

    public TileMetalComputer() {
    }

    @Override
    protected ServerComputer createComputer(int instanceID, int id) {
        ComputerFamily family = getFamily();
        ServerComputer computer = new ServerComputer(
                getWorld(),
                id,
                m_label,
                instanceID,
                family,
                ComputerCraft.terminalWidth_computer,
                ComputerCraft.terminalHeight_computer
        );
        computer.setPosition(getPos());
        return computer;
    }

    @Override
    public void getDroppedItems(List<ItemStack> drops, boolean creative) {
        IComputer computer = getComputer();
        if (!creative || (computer != null && computer.getLabel() != null)) {
            drops.add(ComputerItemFactory.create(this));
        }
    }

    @Override
    public ItemStack getPickedItem() {
        return ComputerItemFactory.create(this);
    }

    @Override
    public void openGUI(EntityPlayer player) {
        ComputerCraft.openComputerGUI(player, this);
    }

    @Override
    public final void readDescription(NBTTagCompound nbttagcompound) {
        super.readDescription(nbttagcompound);
        updateBlock();
    }

    // IDirectionalTile

    public boolean isUseableByPlayer(EntityPlayer player) {
        return isUsable(player, false);
    }

    @Override
    public EnumFacing getDirection() {
        return facing;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("facing", facing.getIndex());
        return super.writeToNBT(nbttagcompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        facing = EnumFacing.getFront(nbttagcompound.getInteger("facing"));
        super.readFromNBT(nbttagcompound);
    }

    @Override
    public void setDirection(EnumFacing dir) {
        if (dir.getAxis() == EnumFacing.Axis.Y) {
            dir = EnumFacing.NORTH;
        }
        facing = dir;
        updateInput();
    }

    @Override
    protected int remapLocalSide(int localSide) {
        return s_remapSide[localSide];
    }
}