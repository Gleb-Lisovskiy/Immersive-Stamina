package net.mcreator.kerilom.immersivestamina.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;

import net.mcreator.kerilom.immersivestamina.network.ImmersiveStaminaModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class RightClickOnBlockProcedure {
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getHand() != InteractionHand.MAIN_HAND)
			return;
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		{
			var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
			if (_playerVars != null) {
				_playerVars.disableswingcheck = true;
				_playerVars.markSyncDirty();
			}
		}
	}
}