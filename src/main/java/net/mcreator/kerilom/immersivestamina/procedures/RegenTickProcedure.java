package net.mcreator.kerilom.immersivestamina.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.kerilom.immersivestamina.network.ImmersiveStaminaModVariables;
import net.mcreator.kerilom.immersivestamina.init.ImmersiveStaminaModAttributes;
import net.mcreator.kerilom.immersivestamina.configuration.ConfigConfiguration;

public class RegenTickProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disabledregen) {
			if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen > 0) {
				{
					var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
					if (_playerVars != null) {
						_playerVars.cd_regen = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen - 1;
						_playerVars.markSyncDirty();
					}
				}
			}
			if (GetPercentFromValueProcedure.execute(entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina, (double) ConfigConfiguration.MAX_STAMINA.get()) < 100
					&& entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier > 0
					&& entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen <= 0) {
				if ((entity.position()).equals(entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).memorypos)) {
					{
						var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
						if (_playerVars != null) {
							_playerVars.stamina = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina
									+ (double) ConfigConfiguration.REGEN.get() * entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier
											* (GetPercentFromValueProcedure.execute(entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0, 20) / 100)
											* (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.HUNGER_REGEN_STAMINA_MULTIPLIER.get())
													? _livingEntity5.getAttribute(ImmersiveStaminaModAttributes.HUNGER_REGEN_STAMINA_MULTIPLIER.get()).getValue()
													: 0)
											* (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.REGEN_STAMINA_MULTIPLIER.get())
													? _livingEntity6.getAttribute(ImmersiveStaminaModAttributes.REGEN_STAMINA_MULTIPLIER.get()).getValue()
													: 0);
							_playerVars.markSyncDirty();
						}
					}
				} else {
					{
						var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
						if (_playerVars != null) {
							_playerVars.stamina = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina
									+ (double) ConfigConfiguration.REGENMOVING.get() * entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier
											* (GetPercentFromValueProcedure.execute(entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0, 20) / 100)
											* (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.HUNGER_REGEN_STAMINA_MULTIPLIER.get())
													? _livingEntity9.getAttribute(ImmersiveStaminaModAttributes.HUNGER_REGEN_STAMINA_MULTIPLIER.get()).getValue()
													: 0)
											* (entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.REGEN_STAMINA_MULTIPLIER.get())
													? _livingEntity10.getAttribute(ImmersiveStaminaModAttributes.REGEN_STAMINA_MULTIPLIER.get()).getValue()
													: 0);
							_playerVars.markSyncDirty();
						}
					}
				}
			}
		}
	}
}