package dan200.computercraft.shared.turtle.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.server.permission.PermissionAPI;

public class PermissionHandler {
    public static boolean canBreakBlock(World world, BlockPos pos, EntityPlayer player) {
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, world.getBlockState(pos), player);
        return !MinecraftForge.EVENT_BUS.post(event);
    }

    public static boolean canPlaceBlock(World world, BlockPos pos, EntityPlayer player, EnumHand hand) {
        BlockEvent.PlaceEvent event = new BlockEvent.PlaceEvent(BlockSnapshot.getBlockSnapshot(world, pos), world.getBlockState(pos), player, hand);
        return !MinecraftForge.EVENT_BUS.post(event);
    }
}