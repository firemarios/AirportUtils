package io.github.firemarios.airportutils.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class NetworkRouterBlock extends HorizontalDirectionalBlock
{
    private static final VoxelShape SHAPE_NORTH = Shapes.or(
            Block.box(0, 0, 1, 16, 4, 11),
            Block.box(1, 4, 4, 3, 7, 6),
            Block.box(1.5, 7, 4.5, 2.5, 12, 5.5));
    private static final VoxelShape SHAPE_SOUTH = Shapes.or(
            Block.box(0, 0, 5, 16, 4, 15),
            Block.box(13, 4, 10, 15, 7, 12),
            Block.box(13.5, 7, 10.5, 14.5, 12, 11.5));
    private static final VoxelShape SHAPE_EAST = Shapes.or(
            Block.box(5, 0, 0, 15, 4, 16),
            Block.box(10, 4, 1, 12, 7, 3),
            Block.box(10.5, 7, 1.5, 11.5, 12, 2.5));
    private static final VoxelShape SHAPE_WEST = Shapes.or(
            Block.box(1, 0, 0, 11, 4, 16),
            Block.box(4, 4, 13, 6, 7, 15),
            Block.box(4.5, 7, 13.5, 5.5, 12, 14.5));

    public NetworkRouterBlock()
    {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .requiresCorrectToolForDrops()
                .strength(3.5F, 6.0F)
                .noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, net.minecraft.world.level.BlockGetter level, net.minecraft.core.BlockPos pos, CollisionContext context)
    {
        return switch (state.getValue(FACING))
        {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            default -> SHAPE_WEST;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
}
