package net.mcreator.kerilom.immersivestamina;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(modid = "immersive_stamina", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VignetteOverlayRegister {

    @SubscribeEvent
    public static void register(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("immersive_stamina_vignette",
            (gui, guiGraphics, partialTick, w, h) -> VignetteOverlay.renderOverlay(guiGraphics, w, h));
    }
}