/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.kerilom.immersivestamina.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.EntityType;

import net.mcreator.kerilom.immersivestamina.ImmersiveStaminaMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ImmersiveStaminaModAttributes {
	public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ImmersiveStaminaMod.MODID);
	public static final RegistryObject<Attribute> SPRINT_COST_STAMINA_MULTIPLIER = REGISTRY.register("sprint_cost_stamina_multiplier",
			() -> new RangedAttribute("attribute.immersive_stamina.sprint_cost_stamina_multiplier", 1d, 0d, 10000d).setSyncable(true));
	public static final RegistryObject<Attribute> SWING_COST_STAMINA_MULTIPLIER = REGISTRY.register("swing_cost_stamina_multiplier",
			() -> new RangedAttribute("attribute.immersive_stamina.swing_cost_stamina_multiplier", 1d, 0d, 10000d).setSyncable(true));
	public static final RegistryObject<Attribute> JUMP_COST_STAMINA_MULTIPLIER = REGISTRY.register("jump_cost_stamina_multiplier",
			() -> new RangedAttribute("attribute.immersive_stamina.jump_cost_stamina_multiplier", 1d, 0d, 10000d).setSyncable(true));
	public static final RegistryObject<Attribute> BOW_COST_STAMINA_MULTIPLIER = REGISTRY.register("bow_cost_stamina_multiplier", () -> new RangedAttribute("attribute.immersive_stamina.bow_cost_stamina_multiplier", 1d, 0d, 10000d).setSyncable(true));
	public static final RegistryObject<Attribute> SHIELD_COST_STAMINA_MULTIPLIER = REGISTRY.register("shield_cost_stamina_multiplier",
			() -> new RangedAttribute("attribute.immersive_stamina.shield_cost_stamina_multiplier", 1d, 0d, 10000d).setSyncable(true));
	public static final RegistryObject<Attribute> DAMAGE_COST_STAMINA_MULTIPLIER = REGISTRY.register("damage_cost_stamina_multiplier",
			() -> new RangedAttribute("attribute.immersive_stamina.damage_cost_stamina_multiplier", 1d, 0d, 10000d).setSyncable(true));
	public static final RegistryObject<Attribute> REGEN_STAMINA_MULTIPLIER = REGISTRY.register("regen_stamina_multiplier", () -> new RangedAttribute("attribute.immersive_stamina.regen_stamina_multiplier", 1d, 0d, 10000d).setSyncable(true));
	public static final RegistryObject<Attribute> REGEN_COOLDOWN_STAMINA_MULTIPLIER = REGISTRY.register("regen_cooldown_stamina_multiplier",
			() -> new RangedAttribute("attribute.immersive_stamina.regen_cooldown_stamina_multiplier", 1d, 0d, 10000d).setSyncable(true));
	public static final RegistryObject<Attribute> HUNGER_REGEN_STAMINA_MULTIPLIER = REGISTRY.register("hunger_regen_stamina_multiplier",
			() -> new RangedAttribute("attribute.immersive_stamina.hunger_regen_stamina_multiplier", 1d, 0d, 10000d).setSyncable(true));
	public static final RegistryObject<Attribute> THIRST_REGEN_STAMINA_MULTIPLIER = REGISTRY.register("thirst_regen_stamina_multiplier",
			() -> new RangedAttribute("attribute.immersive_stamina.thirst_regen_stamina_multiplier", 1d, 0d, 10000d).setSyncable(true));

	@SubscribeEvent
	public static void addAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, SPRINT_COST_STAMINA_MULTIPLIER.get());
		event.add(EntityType.PLAYER, SWING_COST_STAMINA_MULTIPLIER.get());
		event.add(EntityType.PLAYER, JUMP_COST_STAMINA_MULTIPLIER.get());
		event.add(EntityType.PLAYER, BOW_COST_STAMINA_MULTIPLIER.get());
		event.add(EntityType.PLAYER, SHIELD_COST_STAMINA_MULTIPLIER.get());
		event.add(EntityType.PLAYER, DAMAGE_COST_STAMINA_MULTIPLIER.get());
		event.add(EntityType.PLAYER, REGEN_STAMINA_MULTIPLIER.get());
		event.add(EntityType.PLAYER, REGEN_COOLDOWN_STAMINA_MULTIPLIER.get());
		event.add(EntityType.PLAYER, HUNGER_REGEN_STAMINA_MULTIPLIER.get());
		event.add(EntityType.PLAYER, THIRST_REGEN_STAMINA_MULTIPLIER.get());
	}

	@Mod.EventBusSubscriber
	public static class PlayerAttributesSync {
		@SubscribeEvent
		public static void playerClone(PlayerEvent.Clone event) {
			Player oldPlayer = event.getOriginal();
			Player newPlayer = event.getEntity();
			newPlayer.getAttribute(SPRINT_COST_STAMINA_MULTIPLIER.get()).setBaseValue(oldPlayer.getAttribute(SPRINT_COST_STAMINA_MULTIPLIER.get()).getBaseValue());
			newPlayer.getAttribute(SWING_COST_STAMINA_MULTIPLIER.get()).setBaseValue(oldPlayer.getAttribute(SWING_COST_STAMINA_MULTIPLIER.get()).getBaseValue());
			newPlayer.getAttribute(JUMP_COST_STAMINA_MULTIPLIER.get()).setBaseValue(oldPlayer.getAttribute(JUMP_COST_STAMINA_MULTIPLIER.get()).getBaseValue());
			newPlayer.getAttribute(BOW_COST_STAMINA_MULTIPLIER.get()).setBaseValue(oldPlayer.getAttribute(BOW_COST_STAMINA_MULTIPLIER.get()).getBaseValue());
			newPlayer.getAttribute(SHIELD_COST_STAMINA_MULTIPLIER.get()).setBaseValue(oldPlayer.getAttribute(SHIELD_COST_STAMINA_MULTIPLIER.get()).getBaseValue());
			newPlayer.getAttribute(DAMAGE_COST_STAMINA_MULTIPLIER.get()).setBaseValue(oldPlayer.getAttribute(DAMAGE_COST_STAMINA_MULTIPLIER.get()).getBaseValue());
			newPlayer.getAttribute(REGEN_STAMINA_MULTIPLIER.get()).setBaseValue(oldPlayer.getAttribute(REGEN_STAMINA_MULTIPLIER.get()).getBaseValue());
			newPlayer.getAttribute(REGEN_COOLDOWN_STAMINA_MULTIPLIER.get()).setBaseValue(oldPlayer.getAttribute(REGEN_COOLDOWN_STAMINA_MULTIPLIER.get()).getBaseValue());
			newPlayer.getAttribute(HUNGER_REGEN_STAMINA_MULTIPLIER.get()).setBaseValue(oldPlayer.getAttribute(HUNGER_REGEN_STAMINA_MULTIPLIER.get()).getBaseValue());
			newPlayer.getAttribute(THIRST_REGEN_STAMINA_MULTIPLIER.get()).setBaseValue(oldPlayer.getAttribute(THIRST_REGEN_STAMINA_MULTIPLIER.get()).getBaseValue());
		}
	}
}