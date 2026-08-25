package io.github.firemarios.airportutils;

import com.mojang.logging.LogUtils;
import io.github.firemarios.airportutils.block.MainServerBlock;
import io.github.firemarios.airportutils.block.FlightServerBlock;
import io.github.firemarios.airportutils.block.GateServerBlock;
import io.github.firemarios.airportutils.block.RunwayServerBlock;
import io.github.firemarios.airportutils.block.NetworkRouterBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(AirportUtils.MODID)
public class AirportUtils
{
    public static final String MODID = "airportutils";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<net.minecraft.world.item.Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> AIRPORT_UTILS_TAB = CREATIVE_MODE_TABS.register("airport_utils_tab",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> net.minecraft.world.item.Items.PAPER.getDefaultInstance())
                    .build());

    // Server Room Blocks
    public static final RegistryObject<Block> MAIN_SERVER = BLOCKS.register("main_server", MainServerBlock::new);
    public static final RegistryObject<Block> FLIGHT_SERVER = BLOCKS.register("flight_server", FlightServerBlock::new);
    public static final RegistryObject<Block> GATE_SERVER = BLOCKS.register("gate_server", GateServerBlock::new);
    public static final RegistryObject<Block> RUNWAY_SERVER = BLOCKS.register("runway_server", RunwayServerBlock::new);
    public static final RegistryObject<Block> NETWORK_ROUTER = BLOCKS.register("network_router", NetworkRouterBlock::new);

    // Server Room Block Items
    public static final RegistryObject<Item> MAIN_SERVER_ITEM = ITEMS.register("main_server", () -> new BlockItem(MAIN_SERVER.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLIGHT_SERVER_ITEM = ITEMS.register("flight_server", () -> new BlockItem(FLIGHT_SERVER.get(), new Item.Properties()));
    public static final RegistryObject<Item> GATE_SERVER_ITEM = ITEMS.register("gate_server", () -> new BlockItem(GATE_SERVER.get(), new Item.Properties()));
    public static final RegistryObject<Item> RUNWAY_SERVER_ITEM = ITEMS.register("runway_server", () -> new BlockItem(RUNWAY_SERVER.get(), new Item.Properties()));
    public static final RegistryObject<Item> NETWORK_ROUTER_ITEM = ITEMS.register("network_router", () -> new BlockItem(NETWORK_ROUTER.get(), new Item.Properties()));

    public AirportUtils(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        LOGGER.info("Airport Utils loaded");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("Airport Utils: Server starting");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTab() == AIRPORT_UTILS_TAB.get())
        {
            event.accept(MAIN_SERVER_ITEM);
            event.accept(FLIGHT_SERVER_ITEM);
            event.accept(GATE_SERVER_ITEM);
            event.accept(RUNWAY_SERVER_ITEM);
            event.accept(NETWORK_ROUTER_ITEM);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("Airport Utils: Client setup");
        }
    }
}
