package net.mcreator.kerilom.immersivestamina.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.kerilom.immersivestamina.network.ImmersiveStaminaModVariables;
import net.mcreator.kerilom.immersivestamina.init.ImmersiveStaminaModAttributes;
import net.mcreator.kerilom.immersivestamina.configuration.ConfigConfiguration;

public class SprintTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disablecoststamina) {
			if (ConfigConfiguration.ENABLE_SPRINTING.get() && !entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disablecostsprinting) {
				if (entity.isSprinting()) {
					if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina > 0) {
						SetCooldownRegenProcedure.execute(entity,
								(double) ConfigConfiguration.SETCDREGEN_SPRINT.get()
										* (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get())
												? _livingEntity3.getAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get()).getValue()
												: 0));
						if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).sprinttomultiplier < 1) {
							{
								var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
								if (_playerVars != null) {
									_playerVars.sprinttomultiplier = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).sprinttomultiplier + 0.02;
									_playerVars.markSyncDirty();
								}
							}
						}
						{
							var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
							if (_playerVars != null) {
								_playerVars.stamina = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina
										- (double) ConfigConfiguration.COST_SPRINT.get() * entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).sprinttomultiplier
												* (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.SPRINT_COST_STAMINA_MULTIPLIER.get())
														? _livingEntity5.getAttribute(ImmersiveStaminaModAttributes.SPRINT_COST_STAMINA_MULTIPLIER.get()).getValue()
														: 0);
								_playerVars.markSyncDirty();
							}
						}
					}
				}
			}
		}
	}
}