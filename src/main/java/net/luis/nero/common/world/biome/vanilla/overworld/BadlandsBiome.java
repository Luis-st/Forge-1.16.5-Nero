package net.luis.nero.common.world.biome.vanilla.overworld;

import net.luis.nero.api.common.world.biome.IBiomeType;
import net.luis.nero.api.common.world.biome.vanilla.OverworldBiome;
import net.luis.nero.common.enums.BiomeEffects;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class BadlandsBiome extends OverworldBiome {
	
	protected final IBiomeType biomeType;
	
	public BadlandsBiome(BiomeEffects biomeEffects, IBiomeType biomeType) {
		super(biomeEffects, biomeType.getTemperature(), biomeType.getBiomeNoise());
		this.biomeType = biomeType;
	}
	
	@Override
	public MobSpawnSettings getMobSpawnSettings() {
		return this.biomeType.getMobSpawnSettings();
	}
	
	@Override
	public BiomeGenerationSettings getBiomeGenerationSettings() {
		return this.biomeType.getBiomeGenerationSettings();
	}
	
	@Override
	public boolean isUnderground() {
		return this.biomeType.isUnderground();
	}
	
	@Override
	public boolean isOcean() {
		return this.biomeType.isOcean();
	}
	
	@Override
	public boolean isBeach() {
		return this.biomeType.isBeach();
	}
	
	@Override
	public boolean isIsland() {
		return this.biomeType.isIsland();
	}
	
	@Override
	public boolean isMushroomIsland() {
		return this.biomeType.isMushroomIsland();
	}
	
	@Override
	public boolean isHilly() {
		return this.biomeType.isHilly();
	}
	
	@Override
	public boolean isWindswept() {
		return this.biomeType.isWindswept();
	}
	
}
