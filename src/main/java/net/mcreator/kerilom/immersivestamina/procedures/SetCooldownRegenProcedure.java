package net.mcreator.kerilom.immersivestamina.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.kerilom.immersivestamina.network.ImmersiveStaminaModVariables;

public class SetCooldownRegenProcedure {
	public static void execute(Entity entity, double value) {
		if (entity == null)
			return;
		if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen < value) {
			{
				var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
				if (_playerVars != null) {
					_playerVars.cd_regen = value;
					_playerVars.markSyncDirty();
				}
			}
		}
	}
}