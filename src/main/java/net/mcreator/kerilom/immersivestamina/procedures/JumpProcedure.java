package net.mcreator.kerilom.immersivestamina.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingEvent;

import net.minecraft.world.level.GameType;
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
public class JumpProcedure {
	@SubscribeEvent
	public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (!(getEntityGameType(entity) == GameType.CREATIVE || getEntityGameType(entity) == GameType.SPECTATOR)) {
			if (!entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disablecoststamina) {
				if (ConfigConfiguration.ENABLE_JUMP.get() && !entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disablecostjump) {
					if (entity instanceof Player) {
						{
							var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
							if (_playerVars != null) {
								_playerVars.stamina = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina
										- (double) ConfigConfiguration.COST_JUMP.get() * (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.JUMP_COST_STAMINA_MULTIPLIER.get())
												? _livingEntity5.getAttribute(ImmersiveStaminaModAttributes.JUMP_COST_STAMINA_MULTIPLIER.get()).getValue()
												: 0);
								_playerVars.markSyncDirty();
							}
						}
						SetCooldownRegenProcedure.execute(entity,
								(double) ConfigConfiguration.SETCDREGEN_JUMP.get() * (entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get())
										? _livingEntity7.getAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get()).getValue()
										: 0));
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