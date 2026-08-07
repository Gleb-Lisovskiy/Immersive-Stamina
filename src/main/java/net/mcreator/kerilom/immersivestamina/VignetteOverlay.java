package net.mcreator.kerilom.immersivestamina;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = "immersive_stamina",
        value = Dist.CLIENT
)
public class VignetteOverlay {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("immersive_stamina", "textures/screens/vignette.png");
    
    private static float alpha = 0F;
    private static float targetAlpha = 0F;

    public static void setAlpha(float value) {
        alpha = Math.max(0F, Math.min(1F, value));
        targetAlpha = alpha;
    }

    private static float red = 1F;
	private static float green = 1F;
	private static float blue = 1F;
	
	public static void setColor(float r, float g, float b) {
	    red = r;
	    green = g;
	    blue = b;
	}

    public static void fadeTo(float value) {
        targetAlpha = Math.max(0F, Math.min(1F, value));
    }

    public static float getAlpha() {
        return alpha;
    }

    public static void renderOverlay(GuiGraphics gui, int w, int h) {
	    if (alpha <= 0.001F) return;
	
	    RenderSystem.enableBlend();
	    gui.setColor(red, green, blue, alpha);
	
	    // x, y, width, height, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight
	    gui.blit(TEXTURE, 0, 0, w, h, 0F, 0F, 480, 270, 480, 270);
	
	    gui.setColor(1F, 1F, 1F, 1F);
	    RenderSystem.disableBlend();
	}

    @SubscribeEvent
    public static void clientTick(net.minecraftforge.event.TickEvent.ClientTickEvent e) {

        if (e.phase != net.minecraftforge.event.TickEvent.Phase.END)
            return;

        alpha += (targetAlpha - alpha) * 0.12F;

        if (Math.abs(targetAlpha - alpha) < 0.002F)
            alpha = targetAlpha;
    }
}