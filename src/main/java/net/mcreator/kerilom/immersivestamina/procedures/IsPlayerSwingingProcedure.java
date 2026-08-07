package net.mcreator.kerilom.immersivestamina.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.mcreator.kerilom.immersivestamina.network.ImmersiveStaminaModVariables;

public class IsPlayerSwingingProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		Entity varentity = null;
		if (!entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disablecostswing) {
			varentity = entity;
			if (varentity instanceof Player swingplayer && swingplayer.swinging && swingplayer.swingTime == 0
					&& !entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disableswingcheck) {
				return true;
			} else if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disableswingcheck) {
				{
					var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
					if (_playerVars != null) {
						_playerVars.disableswingcheck = false;
						_playerVars.markSyncDirty();
					}
				}
			}
		}
		return false;
	}
}