package net.mcreator.kerilom.immersivestamina.network;

import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.client.Minecraft;

import net.mcreator.kerilom.immersivestamina.ImmersiveStaminaMod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ImmersiveStaminaModVariables {
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		ImmersiveStaminaMod.addNetworkMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new, PlayerVariablesSyncMessage::handleData);
	}

	@SubscribeEvent
	public static void init(RegisterCapabilitiesEvent event) {
		event.register(PlayerVariables.class);
	}

	@Mod.EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getCapability(PLAYER_VARIABLES).ifPresent(capability -> ImmersiveStaminaMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), new PlayerVariablesSyncMessage(capability)));
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getCapability(PLAYER_VARIABLES).ifPresent(capability -> ImmersiveStaminaMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), new PlayerVariablesSyncMessage(capability)));
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getCapability(PLAYER_VARIABLES).ifPresent(capability -> ImmersiveStaminaMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), new PlayerVariablesSyncMessage(capability)));
		}

		@SubscribeEvent
		public static void onPlayerTickUpdateSyncPlayerVariables(TickEvent.PlayerTickEvent event) {
			if (event.phase == TickEvent.Phase.END && event.player instanceof ServerPlayer player) {
				player.getCapability(PLAYER_VARIABLES).ifPresent(capability -> {
					if (capability._syncDirty) {
						ImmersiveStaminaMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), new PlayerVariablesSyncMessage(capability));
						capability._syncDirty = false;
					}
				});
			}
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			event.getOriginal().revive();
			event.getOriginal().getCapability(PLAYER_VARIABLES).ifPresent(original -> {
				event.getEntity().getCapability(PLAYER_VARIABLES).ifPresent(clone -> {
					clone.disabledregen = original.disabledregen;
					clone.disablecoststamina = original.disablecoststamina;
					clone.disablecostswing = original.disablecostswing;
					clone.disablecostsprinting = original.disablecostsprinting;
					clone.disablecostjump = original.disablecostjump;
					clone.disablecostbow = original.disablecostbow;
					clone.disablecostshield = original.disablecostshield;
					clone.disablecostdamage = original.disablecostdamage;
					clone.disabletired = original.disabletired;
					if (!event.isWasDeath()) {
						clone.stamina = original.stamina;
						clone.cd_regen = original.cd_regen;
						clone.memorypos = original.memorypos;
						clone.sprinttomultiplier = original.sprinttomultiplier;
						clone.regencdtomultiplier = original.regencdtomultiplier;
						clone.tired = original.tired;
						clone.usingItem = original.usingItem;
						clone.disableswingcheck = original.disableswingcheck;
						clone.usingItemSlot = original.usingItemSlot;
					}
				});
			});
		}
	}

	public static final Capability<PlayerVariables> PLAYER_VARIABLES = CapabilityManager.get(new CapabilityToken<PlayerVariables>() {
	});

	@Mod.EventBusSubscriber
	private static class PlayerVariablesProvider implements ICapabilitySerializable<CompoundTag> {
		@SubscribeEvent
		public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer))
				event.addCapability(new ResourceLocation("immersive_stamina", "player_variables"), new PlayerVariablesProvider());
		}

		private final PlayerVariables playerVariables = new PlayerVariables();
		private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == PLAYER_VARIABLES ? instance.cast() : LazyOptional.empty();
		}

		@Override
		public CompoundTag serializeNBT() {
			return playerVariables.serializeNBT();
		}

		@Override
		public void deserializeNBT(CompoundTag nbt) {
			playerVariables.deserializeNBT(nbt);
		}
	}

	public static class PlayerVariables implements INBTSerializable<CompoundTag> {
		boolean _syncDirty = false;
		public double stamina = 20.0;
		public double cd_regen = 0;
		public Vec3 memorypos = Vec3.ZERO;
		public double sprinttomultiplier = 0;
		public double regencdtomultiplier = 1.0;
		public boolean tired = false;
		public ItemStack usingItem = ItemStack.EMPTY;
		public boolean disableswingcheck = false;
		public boolean disabledregen = false;
		public boolean disablecoststamina = false;
		public boolean disablecostswing = false;
		public boolean disablecostsprinting = false;
		public boolean disablecostjump = false;
		public boolean disablecostbow = false;
		public boolean disablecostshield = false;
		public boolean disablecostdamage = false;
		public boolean disabletired = false;
		public String usingItemSlot = "\"\"";

		@Override
		public CompoundTag serializeNBT() {
			CompoundTag nbt = new CompoundTag();
			nbt.putDouble("stamina", stamina);
			nbt.putDouble("cd_regen", cd_regen);
			nbt.put("memorypos", Vec3.CODEC.encodeStart(NbtOps.INSTANCE, memorypos).result().orElseGet(CompoundTag::new));
			nbt.putDouble("sprinttomultiplier", sprinttomultiplier);
			nbt.putDouble("regencdtomultiplier", regencdtomultiplier);
			nbt.putBoolean("tired", tired);
			nbt.put("usingItem", usingItem.save(new CompoundTag()));
			nbt.putBoolean("disableswingcheck", disableswingcheck);
			nbt.putBoolean("disabledregen", disabledregen);
			nbt.putBoolean("disablecoststamina", disablecoststamina);
			nbt.putBoolean("disablecostswing", disablecostswing);
			nbt.putBoolean("disablecostsprinting", disablecostsprinting);
			nbt.putBoolean("disablecostjump", disablecostjump);
			nbt.putBoolean("disablecostbow", disablecostbow);
			nbt.putBoolean("disablecostshield", disablecostshield);
			nbt.putBoolean("disablecostdamage", disablecostdamage);
			nbt.putBoolean("disabletired", disabletired);
			nbt.putString("usingItemSlot", usingItemSlot);
			return nbt;
		}

		@Override
		public void deserializeNBT(CompoundTag nbt) {
			stamina = nbt.getDouble("stamina");
			cd_regen = nbt.getDouble("cd_regen");
			memorypos = Vec3.CODEC.parse(NbtOps.INSTANCE, nbt.get("memorypos")).result().orElse(Vec3.ZERO);
			sprinttomultiplier = nbt.getDouble("sprinttomultiplier");
			regencdtomultiplier = nbt.getDouble("regencdtomultiplier");
			tired = nbt.getBoolean("tired");
			usingItem = ItemStack.of(nbt.getCompound("usingItem"));
			disableswingcheck = nbt.getBoolean("disableswingcheck");
			disabledregen = nbt.getBoolean("disabledregen");
			disablecoststamina = nbt.getBoolean("disablecoststamina");
			disablecostswing = nbt.getBoolean("disablecostswing");
			disablecostsprinting = nbt.getBoolean("disablecostsprinting");
			disablecostjump = nbt.getBoolean("disablecostjump");
			disablecostbow = nbt.getBoolean("disablecostbow");
			disablecostshield = nbt.getBoolean("disablecostshield");
			disablecostdamage = nbt.getBoolean("disablecostdamage");
			disabletired = nbt.getBoolean("disabletired");
			usingItemSlot = nbt.getString("usingItemSlot");
		}

		public void markSyncDirty() {
			_syncDirty = true;
		}
	}

	public record PlayerVariablesSyncMessage(PlayerVariables data) {
		public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
			this(new PlayerVariables());
			data.deserializeNBT(buffer.readNbt());
		}

		public static void buffer(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
			buffer.writeNbt(message.data().serializeNBT());
		}

		public static void handleData(final PlayerVariablesSyncMessage message, final Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer() && message.data != null)
					Minecraft.getInstance().player.getCapability(PLAYER_VARIABLES).ifPresent(cap -> {
						cap.stamina = message.data().stamina;
						cap.cd_regen = message.data().cd_regen;
						cap.memorypos = message.data().memorypos;
						cap.sprinttomultiplier = message.data().sprinttomultiplier;
						cap.regencdtomultiplier = message.data().regencdtomultiplier;
						cap.tired = message.data().tired;
						cap.usingItem = message.data().usingItem;
						cap.disableswingcheck = message.data().disableswingcheck;
						cap.disabledregen = message.data().disabledregen;
						cap.disablecoststamina = message.data().disablecoststamina;
						cap.disablecostswing = message.data().disablecostswing;
						cap.disablecostsprinting = message.data().disablecostsprinting;
						cap.disablecostjump = message.data().disablecostjump;
						cap.disablecostbow = message.data().disablecostbow;
						cap.disablecostshield = message.data().disablecostshield;
						cap.disablecostdamage = message.data().disablecostdamage;
						cap.disabletired = message.data().disabletired;
						cap.usingItemSlot = message.data().usingItemSlot;
					});
			});
			context.setPacketHandled(true);
		}
	}
}