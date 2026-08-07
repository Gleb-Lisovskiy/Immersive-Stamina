package net.mcreator.kerilom.immersivestamina.client.screens;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;

import net.mcreator.kerilom.immersivestamina.procedures.StaminaProcedureHUDProcedure;
import net.mcreator.kerilom.immersivestamina.procedures.HideStaminaProcedureHUDProcedure;
import net.mcreator.kerilom.immersivestamina.procedures.HideGlowStaminaProcedureHUDProcedure;
import net.mcreator.kerilom.immersivestamina.procedures.GlowStaminaProcedureHUDProcedure;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class StaminaOverlay {
	private static final ResourceLocation SPRITE_0 = new ResourceLocation("immersive_stamina:textures/screens/stamina.png");
	private static final ResourceLocation SPRITE_1 = new ResourceLocation("immersive_stamina:textures/screens/glow.png");

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getWindow().getGuiScaledWidth();
		int h = event.getWindow().getGuiScaledHeight();
		Level world = null;
		double x = 0;
		double y = 0;
		double z = 0;
		Player entity = Minecraft.getInstance().player;
		if (entity != null) {
			world = entity.level();
			x = entity.getX();
			y = entity.getY();
			z = entity.getZ();
		}
		if (true) {
			if (HideStaminaProcedureHUDProcedure.execute(entity)) {
				event.getGuiGraphics().blit(SPRITE_0, w / 2 + 93, h - 40, 0, Mth.clamp((int) StaminaProcedureHUDProcedure.execute(entity) * 16, 0, 320), 16, 16, 16, 336);
			}
			if (HideGlowStaminaProcedureHUDProcedure.execute(entity)) {
				event.getGuiGraphics().blit(SPRITE_1, w / 2 + 93, h - 40, 0, Mth.clamp((int) GlowStaminaProcedureHUDProcedure.execute(entity) * 16, 0, 112), 16, 16, 16, 128);
			}
		}
	}
}