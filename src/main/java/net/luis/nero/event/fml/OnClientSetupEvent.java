package net.luis.nero.event.fml;

import net.luis.nero.Nero;
import net.luis.nero.client.render.blockentity.BloodAltarTileEntityRenderer;
import net.luis.nero.client.render.entity.HoveringInfernoEntityRenderer;
import net.luis.nero.client.render.entity.SoulBlazeEntityRenderer;
import net.luis.nero.client.render.entity.SoulFireballEntityRenderer;
import net.luis.nero.client.world.ModDimensionSpecialEffects;
import net.luis.nero.init.block.ModBlocks;
import net.luis.nero.init.block.util.ModBlockEntityTypes;
import net.luis.nero.init.entity.ModEntityTypes;
import net.luis.nero.init.world.ModWorldTypes;
import net.luis.nero.init.world.dimension.ModDimensionTypes;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeWorldTypeScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Nero.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class OnClientSetupEvent {
	
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		registerEntityRenderer(event);
		registerTileEntityRenderer(event);
		registerBlockRenderType(event);
		registerUtil(event);
		registerWorldTypes(event);
	}
	
	protected static void registerTileEntityRenderer(FMLClientSetupEvent event) {
//		BlockEntityRenderers.register(ModTileEntityTypes.MILESTONE.get(), MilestoneTileEntityRenderer::new);
		
		BlockEntityRenderers.register(ModBlockEntityTypes.BLOOD_ALTAR.get(), BloodAltarTileEntityRenderer::new);
	}
	
	protected static void registerEntityRenderer(FMLClientSetupEvent event) {
		EntityRenderers.register(ModEntityTypes.SOUL_BLAZE.get(), SoulBlazeEntityRenderer::new);
		EntityRenderers.register(ModEntityTypes.SOUL_FIREBALL.get(), SoulFireballEntityRenderer::new);
		EntityRenderers.register(ModEntityTypes.HOVERING_INFERNO.get(), HoveringInfernoEntityRenderer::new);
		EntityRenderers.register(ModEntityTypes.VILLAGER.get(), VillagerRenderer::new);
	}
	
	protected static void registerBlockRenderType(FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.BRIDGE_BLOCK.get(), RenderType.translucent());
	}
	
	protected static void registerUtil(FMLClientSetupEvent event) {
		DimensionSpecialEffects.EFFECTS.defaultReturnValue(ModDimensionSpecialEffects.OVERWORLD);
		DimensionSpecialEffects.EFFECTS.put(ModDimensionTypes.OVERWORLD.location(), ModDimensionSpecialEffects.OVERWORLD);
		DimensionSpecialEffects.EFFECTS.put(ModDimensionTypes.DEEPSLATE.location(), ModDimensionSpecialEffects.DEEPSLATE);
	}
	
	protected static void registerWorldTypes(FMLClientSetupEvent event) {
		ForgeWorldTypeScreens.registerFactory(ModWorldTypes.NERO_OVWERWORLD.get(), (worldScreen, worldGenSettings) -> new Screen(ModWorldTypes.NERO_OVWERWORLD.get().getDisplayName()) {
			@Override
			protected void init() {
				super.init();
				this.addRenderableWidget(new Button(0, 0, 120, 20, new TextComponent("close"), action -> {
					this.getMinecraft().setScreen(worldScreen);
				}));
			};
		});
	}

}
