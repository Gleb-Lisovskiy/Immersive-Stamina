package net.mcreator.kerilom.immersivestamina.procedures;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.SectionPos;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import net.mcreator.kerilom.immersivestamina.network.ImmersiveStaminaModVariables;
import net.mcreator.kerilom.immersivestamina.init.ImmersiveStaminaModAttributes;
import net.mcreator.kerilom.immersivestamina.configuration.ConfigConfiguration;
import net.mcreator.kerilom.immersivestamina.ImmersiveStaminaMod;

import javax.annotation.Nullable;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class LeftClickAirProcedure {
	@SubscribeEvent
	public static void onLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
		ImmersiveStaminaMod.PACKET_HANDLER.sendToServer(new LeftClickAirMessage());
		execute(event.getEntity());
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class LeftClickAirMessage {
		public LeftClickAirMessage() {
		}

		public LeftClickAirMessage(FriendlyByteBuf buffer) {
		}

		public static void buffer(LeftClickAirMessage message, FriendlyByteBuf buffer) {
		}

		public static void handler(LeftClickAirMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getSender().level().getChunkSource().hasChunk(SectionPos.blockToSectionCoord(context.getSender().getX()), SectionPos.blockToSectionCoord(context.getSender().getZ())))
					return;
				execute(context.getSender());
			});
			context.setPacketHandled(true);
		}

		@SubscribeEvent
		public static void registerMessage(FMLCommonSetupEvent event) {
			ImmersiveStaminaMod.addNetworkMessage(LeftClickAirMessage.class, LeftClickAirMessage::buffer, LeftClickAirMessage::new, LeftClickAirMessage::handler);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (!(getEntityGameType(entity) == GameType.CREATIVE || getEntityGameType(entity) == GameType.SPECTATOR)) {
			if (ConfigConfiguration.ENABLE_SWING.get() && !entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disablecostswing) {
				{
					var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
					if (_playerVars != null) {
						_playerVars.disableswingcheck = true;
						_playerVars.stamina = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina
								- (double) ConfigConfiguration.COST_SWING.get() * (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.SWING_COST_STAMINA_MULTIPLIER.get())
										? _livingEntity4.getAttribute(ImmersiveStaminaModAttributes.SWING_COST_STAMINA_MULTIPLIER.get()).getValue()
										: 0);
						_playerVars.markSyncDirty();
					}
				}
				SetCooldownRegenProcedure.execute(entity,
						(double) ConfigConfiguration.SETCDREGEN_SWING.get() * (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get())
								? _livingEntity6.getAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get()).getValue()
								: 0));
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