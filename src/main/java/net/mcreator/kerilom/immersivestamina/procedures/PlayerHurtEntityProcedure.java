package net.mcreator.kerilom.immersivestamina.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import net.mcreator.kerilom.immersivestamina.network.ImmersiveStaminaModVariables;
import net.mcreator.kerilom.immersivestamina.init.ImmersiveStaminaModAttributes;
import net.mcreator.kerilom.immersivestamina.configuration.ConfigConfiguration;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PlayerHurtEntityProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getSource(), event.getEntity());
		}
	}

	public static void execute(DamageSource damagesource, Entity entity) {
		execute(null, damagesource, entity);
	}

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity entity) {
		if (damagesource == null || entity == null)
			return;
		if (!(getEntityGameType(entity) == GameType.CREATIVE || getEntityGameType(entity) == GameType.SPECTATOR)) {
			if (damagesource.is(DamageTypes.GENERIC) || damagesource.is(DamageTypes.GENERIC_KILL) || damagesource.is(DamageTypes.PLAYER_ATTACK)) {
				if (entity instanceof Player && ModList.get().isLoaded("bettercombat") && !IsPlayerSwingingProcedure.execute(entity)) {
					{
						var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
						if (_playerVars != null) {
							_playerVars.stamina = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina
									- (double) ConfigConfiguration.COST_SWING.get() * (entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.SWING_COST_STAMINA_MULTIPLIER.get())
											? _livingEntity8.getAttribute(ImmersiveStaminaModAttributes.SWING_COST_STAMINA_MULTIPLIER.get()).getValue()
											: 0);
							_playerVars.markSyncDirty();
						}
					}
					SetCooldownRegenProcedure.execute(entity,
							(double) ConfigConfiguration.SETCDREGEN_SWING.get() * (entity instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get())
									? _livingEntity10.getAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get()).getValue()
									: 0));
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