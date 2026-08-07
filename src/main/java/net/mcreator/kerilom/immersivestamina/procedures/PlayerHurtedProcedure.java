package net.mcreator.kerilom.immersivestamina.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import net.mcreator.kerilom.immersivestamina.network.ImmersiveStaminaModVariables;
import net.mcreator.kerilom.immersivestamina.init.ImmersiveStaminaModAttributes;
import net.mcreator.kerilom.immersivestamina.configuration.ConfigConfiguration;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PlayerHurtedProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity(), event.getAmount());
		}
	}

	public static void execute(Entity entity, double amount) {
		execute(null, entity, amount);
	}

	private static void execute(@Nullable Event event, Entity entity, double amount) {
		if (entity == null)
			return;
		if (!(getEntityGameType(entity) == GameType.CREATIVE || getEntityGameType(entity) == GameType.SPECTATOR)) {
			if (!entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disablecoststamina) {
				if (ConfigConfiguration.ENABLE_DAMAGE.get() && !entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disablecostdamage) {
					if (entity instanceof Player && !(entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).usingItem.getItem() instanceof ShieldItem)) {
						{
							var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
							if (_playerVars != null) {
								_playerVars.stamina = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina - (double) ConfigConfiguration.COST_DAMAGE.get() * amount
										* (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.DAMAGE_COST_STAMINA_MULTIPLIER.get())
												? _livingEntity6.getAttribute(ImmersiveStaminaModAttributes.DAMAGE_COST_STAMINA_MULTIPLIER.get()).getValue()
												: 0);
								_playerVars.markSyncDirty();
							}
						}
						if (ConfigConfiguration.COST_DAMAGEMULTIPLIER.get()) {
							SetCooldownRegenProcedure.execute(entity,
									(double) ConfigConfiguration.SETCDREGEN_DAMAGE.get() * amount
											* (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get())
													? _livingEntity9.getAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get()).getValue()
													: 0));
						} else {
							SetCooldownRegenProcedure.execute(entity,
									(double) ConfigConfiguration.SETCDREGEN_DAMAGE.get()
											* (entity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get())
													? _livingEntity11.getAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get()).getValue()
													: 0));
						}
						if (ConfigConfiguration.COST_DAMAGEMULTIPLIER.get()) {
							if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen == Double.POSITIVE_INFINITY) {
								{
									var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
									if (_playerVars != null) {
										_playerVars.cd_regen = 300;
										_playerVars.markSyncDirty();
									}
								}
							} else if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen > 300) {
								{
									var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
									if (_playerVars != null) {
										_playerVars.cd_regen = 300;
										_playerVars.markSyncDirty();
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private static GameType getEntityGameType(Entity entity) {
		if (entity instanceof ServerPlayer serverPlayer) {
			return serverPlayer.gameMode.getGameModeForPlayer();
		} else if (entity instanceof Player player && player.level().isClientSide()) {
			PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
			if (playerInfo != null)
				return playerInfo.getGameMode();
		}
		return null;
	}
}