package net.mcreator.kerilom.immersivestamina.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.Minecraft;

import net.mcreator.kerilom.immersivestamina.network.ImmersiveStaminaModVariables;
import net.mcreator.kerilom.immersivestamina.init.ImmersiveStaminaModAttributes;
import net.mcreator.kerilom.immersivestamina.configuration.ConfigConfiguration;

import javax.annotation.Nullable;

import java.util.UUID;

@Mod.EventBusSubscriber
public class CoreProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		double alphaDouble = 0;
		if (!(getEntityGameType(entity) == GameType.CREATIVE || getEntityGameType(entity) == GameType.SPECTATOR)) {
			if (!entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disablecoststamina) {
				if (ConfigConfiguration.ENABLE_SWING.get() && !entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disablecostswing) {
					if (IsPlayerSwingingProcedure.execute(entity)) {
						if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina > 0) {
							SetCooldownRegenProcedure.execute(entity,
									(double) ConfigConfiguration.SETCDREGEN_SWING.get()
											* (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get())
													? _livingEntity4.getAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get()).getValue()
													: 0));
							{
								var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
								if (_playerVars != null) {
									_playerVars.stamina = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina - (double) ConfigConfiguration.COST_SWING.get()
											* (entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.SWING_COST_STAMINA_MULTIPLIER.get())
													? _livingEntity6.getAttribute(ImmersiveStaminaModAttributes.SWING_COST_STAMINA_MULTIPLIER.get()).getValue()
													: 0);
									_playerVars.markSyncDirty();
								}
							}
						}
						if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina > (double) ConfigConfiguration.MAX_STAMINA.get()) {
							{
								var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
								if (_playerVars != null) {
									_playerVars.stamina = (double) ConfigConfiguration.MAX_STAMINA.get();
									_playerVars.markSyncDirty();
								}
							}
						}
						if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier > 0) {
							{
								var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
								if (_playerVars != null) {
									_playerVars.regencdtomultiplier = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier - 0.02;
									_playerVars.markSyncDirty();
								}
							}
						}
					}
				}
				if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).usingItem.getItem() instanceof BowItem) {
					if (ConfigConfiguration.ENABLE_BOW.get() && !entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).disablecostbow) {
						if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina > 0) {
							SetCooldownRegenProcedure.execute(entity,
									(double) ConfigConfiguration.SETCDREGEN_BOW.get()
											* (entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get())
													? _livingEntity12.getAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get()).getValue()
													: 0));
							{
								var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
								if (_playerVars != null) {
									_playerVars.stamina = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina
											- 0.05 * (entity instanceof LivingEntity _livingEntity13 && _livingEntity13.getAttributes().hasAttribute(ImmersiveStaminaModAttributes.BOW_COST_STAMINA_MULTIPLIER.get())
													? _livingEntity13.getAttribute(ImmersiveStaminaModAttributes.BOW_COST_STAMINA_MULTIPLIER.get()).getValue()
													: 0);
									_playerVars.markSyncDirty();
								}
							}
						}
					}
				}
			}
			if (!(entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).usingItem
					.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem())
					&& (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).usingItemSlot).equals("main")) {
				{
					var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
					if (_playerVars != null) {
						_playerVars.usingItem = ItemStack.EMPTY.copy();
						_playerVars.markSyncDirty();
					}
				}
			}
			if (!(entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).usingItem
					.getItem() == (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem())
					&& (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).usingItemSlot).equals("off")) {
				{
					var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
					if (_playerVars != null) {
						_playerVars.usingItem = ItemStack.EMPTY.copy();
						_playerVars.markSyncDirty();
					}
				}
			}
			if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).usingItem.getItem() == ItemStack.EMPTY.getItem()
					&& !(entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).usingItemSlot).equals("None")) {
				{
					var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
					if (_playerVars != null) {
						_playerVars.usingItemSlot = "None";
						_playerVars.markSyncDirty();
					}
				}
			}
			if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina < 0) {
				{
					var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
					if (_playerVars != null) {
						_playerVars.stamina = 0;
						_playerVars.markSyncDirty();
					}
				}
			}
			if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen < 0) {
				{
					var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
					if (_playerVars != null) {
						_playerVars.cd_regen = 0;
						_playerVars.markSyncDirty();
					}
				}
			}
			if (ConfigConfiguration.ENABLE_SMOOTHREGEN.get()) {
				if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier < 1
						&& entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen <= 0) {
					{
						var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
						if (_playerVars != null) {
							_playerVars.regencdtomultiplier = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier + 0.01;
							_playerVars.markSyncDirty();
						}
					}
				}
				if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier > 0
						&& entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen > 0) {
					{
						var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
						if (_playerVars != null) {
							_playerVars.regencdtomultiplier = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier - 0.02;
							_playerVars.markSyncDirty();
						}
					}
				}
				if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier < 0) {
					{
						var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
						if (_playerVars != null) {
							_playerVars.regencdtomultiplier = 0;
							_playerVars.markSyncDirty();
						}
					}
				}
				if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier > 1) {
					{
						var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
						if (_playerVars != null) {
							_playerVars.regencdtomultiplier = 1;
							_playerVars.markSyncDirty();
						}
					}
				}
			} else {
				if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier < 1
						&& entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen <= 0) {
					{
						var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
						if (_playerVars != null) {
							_playerVars.regencdtomultiplier = 1;
							_playerVars.markSyncDirty();
						}
					}
				}
				if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).regencdtomultiplier > 0
						&& entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen > 0) {
					{
						var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
						if (_playerVars != null) {
							_playerVars.regencdtomultiplier = 0;
							_playerVars.markSyncDirty();
						}
					}
				}
			}
			if (ConfigConfiguration.ENABLE_SMOOTHSPRINTINGCOST.get()) {
				if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).sprinttomultiplier > 1) {
					{
						var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
						if (_playerVars != null) {
							_playerVars.sprinttomultiplier = 1;
							_playerVars.markSyncDirty();
						}
					}
				}
				if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).sprinttomultiplier < 0) {
					{
						var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
						if (_playerVars != null) {
							_playerVars.sprinttomultiplier = 0;
							_playerVars.markSyncDirty();
						}
					}
				}
				if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen <= 0
						&& entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).sprinttomultiplier > 0) {
					{
						var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
						if (_playerVars != null) {
							_playerVars.sprinttomultiplier = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).sprinttomultiplier - 0.02;
							_playerVars.markSyncDirty();
						}
					}
				}
			}
			if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen > 0
					&& GetPercentFromValueProcedure.execute(entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina, (double) ConfigConfiguration.MAX_STAMINA.get()) <= 0
					&& entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).tired == false) {
				if (entity instanceof LivingEntity _entity) {
					AttributeModifier modifier = new AttributeModifier(UUID.fromString("237e7346-8953-349b-b805-f42b3848daa1"), "immersive_stamina:tired", (-0.65), AttributeModifier.Operation.MULTIPLY_TOTAL);
					if (!_entity.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(modifier)) {
						_entity.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(modifier);
					}
				}
				if (entity instanceof LivingEntity _entity) {
					AttributeModifier modifier = new AttributeModifier(UUID.fromString("237e7346-8953-349b-b805-f42b3848daa1"), "immersive_stamina:tired", (-0.4), AttributeModifier.Operation.MULTIPLY_TOTAL);
					if (!_entity.getAttribute(ImmersiveStaminaModAttributes.REGEN_STAMINA_MULTIPLIER.get()).hasModifier(modifier)) {
						_entity.getAttribute(ImmersiveStaminaModAttributes.REGEN_STAMINA_MULTIPLIER.get()).addPermanentModifier(modifier);
					}
				}
				if (entity instanceof LivingEntity _entity) {
					AttributeModifier modifier = new AttributeModifier(UUID.fromString("237e7346-8953-349b-b805-f42b3848daa1"), "immersive_stamina:tired", 0.4, AttributeModifier.Operation.MULTIPLY_TOTAL);
					if (!_entity.getAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get()).hasModifier(modifier)) {
						_entity.getAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get()).addPermanentModifier(modifier);
					}
				}
				if (entity instanceof LivingEntity _entity) {
					AttributeModifier modifier = new AttributeModifier(UUID.fromString("237e7346-8953-349b-b805-f42b3848daa1"), "immersive_stamina:tired", (-0.7), AttributeModifier.Operation.MULTIPLY_TOTAL);
					if (!_entity.getAttribute(Attributes.ATTACK_SPEED).hasModifier(modifier)) {
						_entity.getAttribute(Attributes.ATTACK_SPEED).addPermanentModifier(modifier);
					}
				}
				{
					var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
					if (_playerVars != null) {
						_playerVars.tired = true;
						_playerVars.markSyncDirty();
					}
				}
			}
			if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).tired == true) {
				entity.setSprinting(false);
			}
			if (entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).cd_regen <= 0 && GetPercentFromValueProcedure
					.execute(entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina, (double) ConfigConfiguration.MAX_STAMINA.get()) >= 30
					&& entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).tired == true) {
				if (entity instanceof LivingEntity _entity) {
					_entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(UUID.fromString("237e7346-8953-349b-b805-f42b3848daa1"));
				}
				if (entity instanceof LivingEntity _entity) {
					_entity.getAttribute(ImmersiveStaminaModAttributes.REGEN_STAMINA_MULTIPLIER.get()).removeModifier(UUID.fromString("237e7346-8953-349b-b805-f42b3848daa1"));
				}
				if (entity instanceof LivingEntity _entity) {
					_entity.getAttribute(ImmersiveStaminaModAttributes.REGEN_COOLDOWN_STAMINA_MULTIPLIER.get()).removeModifier(UUID.fromString("237e7346-8953-349b-b805-f42b3848daa1"));
				}
				if (entity instanceof LivingEntity _entity) {
					_entity.getAttribute(Attributes.ATTACK_SPEED).removeModifier(UUID.fromString("237e7346-8953-349b-b805-f42b3848daa1"));
				}
				{
					var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
					if (_playerVars != null) {
						_playerVars.tired = false;
						_playerVars.markSyncDirty();
					}
				}
			}
			RegenTickProcedure.execute(entity);
			SprintTickProcedure.execute(entity);
		}
		if (entity.level().isClientSide()) {
			double percent = GetPercentFromValueProcedure.execute(entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina,
					(double) ConfigConfiguration.MAX_STAMINA.get());
			float threshold = ConfigConfiguration.VIGNETTE_THRESHOLD_MAX.get().floatValue();
			float fullAt = ConfigConfiguration.VIGNETTE_THRESHOLD_MIN.get().floatValue();
			float maxAlpha = ConfigConfiguration.VIGNETTE_ALPHA.get().floatValue();
			float range = Math.max(0.01F, threshold - fullAt);
			float target;
			if (percent >= threshold) {
				target = 0F;
			} else if (percent <= fullAt) {
				target = maxAlpha;
			} else {
				target = ((threshold - (float) percent) / range) * maxAlpha;
			}
			net.mcreator.kerilom.immersivestamina.VignetteOverlay.fadeTo(target);
			net.mcreator.kerilom.immersivestamina.VignetteOverlay.setColor(0F, 0F, 0F);
		}
		{
			var _playerVars = entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
			if (_playerVars != null) {
				_playerVars.memorypos = entity.position();
				_playerVars.markSyncDirty();
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