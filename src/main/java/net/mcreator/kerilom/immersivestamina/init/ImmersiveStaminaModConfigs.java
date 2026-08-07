package net.mcreator.kerilom.immersivestamina.init;

import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.mcreator.kerilom.immersivestamina.configuration.ConfigConfiguration;
import net.mcreator.kerilom.immersivestamina.ImmersiveStaminaMod;

@Mod.EventBusSubscriber(modid = ImmersiveStaminaMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ImmersiveStaminaModConfigs {
	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		event.enqueueWork(() -> {
			ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigConfiguration.SPEC, "immersivestamina-common.toml");
		});
	}
}