/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2016. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.computercraft.shared.computer.blocks;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.computer.core.IComputer;
import dan200.computercraft.shared.computer.items.ItemComputer;
import dan200.computercraft.shared.util.DirectionUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockMetalComputer extends BlockComputerBase {
    public BlockMetalComputer() {
        super(Material.ROCK);
        setHardness(2.0f);
        setUnlocalizedName("computercraft.metal_computer");
        setCreativeTab(ComputerCraft.mainCreativeTab);
        setDefaultState(this.blockState.getBaseState()
                .withProperty(Properties.FACING, EnumFacing.NORTH)
                .withProperty(Properties.METAL, BlockType.COPPER)
                .withProperty(Properties.STATE, ComputerState.Off)
        );
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(Properties.METAL).ordinal();
    }

    @Override
    public int getLightValue(@Nonnull IBlockState state, IBlockAccess world, @Nonnull BlockPos pos) {
        return state.getValue(Properties.STATE) != ComputerState.Off ? 7 : 0;
    }
// Members

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, Properties.FACING,
                Properties.METAL,
                Properties.STATE);
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(Properties.METAL, BlockType.get(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(Properties.METAL).ordinal();
    }

    @Override
    protected IBlockState getDefaultBlockState(ComputerFamily family, EnumFacing placedSide) {
        return getDefaultState();
    }

    @Override
    @Nonnull
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof IComputerTile) {
            IComputer computer = ((IComputerTile) tile).getComputer();
            if (computer != null && computer.isOn()) {
                if (computer.isCursorDisplayed()) {
                    return state.withProperty(Properties.STATE, ComputerState.Blinking);
                } else {
                    return state.withProperty(Properties.STATE, ComputerState.On);
                }
            }
        }
        return state.withProperty(Properties.STATE, ComputerState.Off);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public ComputerFamily getFamily(int damage) {
        return ComputerFamily.Metal;
    }

    @Override
    public ComputerFamily getFamily(IBlockState state) {
        return ComputerFamily.Metal;
    }

    @Override
    protected TileComputer createTile(ComputerFamily family) {
        return new TileComputer();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack) {
        // Not sure why this is necessary
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileComputer) {
            tile.setWorld(world); // Not sure why this is necessary
            tile.setPos(pos); // Not sure why this is necessary
        }

        // Set direction
        EnumFacing dir = DirectionUtil.fromEntityRot(player);
        setDirection(world, pos, dir);
    }

    // Statics
    public static class Properties {
        public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
        public static final PropertyEnum<BlockType> METAL = PropertyEnum.create("type", BlockType.class);
        public static final PropertyEnum<ComputerState> STATE = PropertyEnum.create("state", ComputerState.class);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getStateFromMeta(meta);
    }

    public enum BlockType implements IStringSerializable {
        COPPER,
        TIN,
        SILVER,
        LEAD,
        NICKEL,
        PLATINUM,
        MITHRIL,
        BRONZE,
        INVAR,
        ELECTRUM,
        SIGNALUM,
        LUMIUM,
        ENDERIUM,
        ;

        public static BlockType get(int i) {
            return values()[i];
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}