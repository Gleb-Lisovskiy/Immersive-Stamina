package net.mcreator.kerilom.immersivestamina.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.mcreator.kerilom.immersivestamina.network.ImmersiveStaminaModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class CmdAddStaminaProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		{
			var _playerVars = (commandParameterEntity(arguments, "name")).getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElse(null);
			if (_playerVars != null) {
				_playerVars.stamina = (commandParameterEntity(arguments, "name")).getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina
						+ DoubleArgumentType.getDouble(arguments, "add");
				_playerVars.markSyncDirty();
			}
		}
	}

	private static Entity commandParameterEntity(CommandContext<CommandSourceStack> arguments, String parameter) {
		try {
			return EntityArgument.getEntity(arguments, parameter);
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}