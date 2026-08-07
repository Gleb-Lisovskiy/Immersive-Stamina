package net.mcreator.kerilom.immersivestamina.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.kerilom.immersivestamina.network.ImmersiveStaminaModVariables;

public class GlowStaminaProcedureHUDProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		double index = 0;
		index = 7;
		if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier >= 1) {
			index = 0;
		} else if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier >= 0.858) {
			index = 1;
		} else if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier >= 0.715) {
			index = 2;
		} else if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier >= 0.572) {
			index = 3;
		} else if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier >= 0.429) {
			index = 4;
		} else if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier >= 0.286) {
			index = 5;
		} else if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier >= 0.143) {
			index = 6;
		} else if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier >= 0) {
			index = 7;
		}
		return index;
	}
}