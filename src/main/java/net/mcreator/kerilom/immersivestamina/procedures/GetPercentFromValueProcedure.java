package net.mcreator.kerilom.immersivestamina.procedures;

public class GetPercentFromValueProcedure {
	public static double execute(double currect, double max) {
		double result = 0;
		return currect * (100 / max);
	}
}