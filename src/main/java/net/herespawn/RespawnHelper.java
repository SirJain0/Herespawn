package net.herespawn;

import net.minecraft.util.math.Vec3d;

public class RespawnHelper {
	private static Vec3d spawnPos;

	public static void setSpawn(Vec3d spawn) {
		spawnPos = spawn;
	}

	public static Vec3d getAndClearSpawn() {
		Vec3d tempSpawn = spawnPos;
		spawnPos = null;
		return tempSpawn;
	}
}