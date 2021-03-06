package io.github.jorelali.commandapi.api.arguments;

import org.bukkit.Location;

import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.SemiReflector;

@SuppressWarnings("unchecked")
public class LocationArgument implements Argument, OverrideableSuggestions {

	public enum LocationType {
		/**
		 * Represents the integer coordinates of a block, for example: (10, 70, -19)
		 */
		BLOCK_POSITION, 
		
		/**
		 * Represents the exact coordinates of a position, for example: (10.24, 70.00, -18.79)
		 */
		PRECISE_POSITION;
	}
	
	com.mojang.brigadier.arguments.ArgumentType<?> rawType;
	
	/**
	 * A Location argument. Represents Minecraft locations
	 */
	public LocationArgument() {
		this(LocationType.PRECISE_POSITION);
	}
	
	/**
	 * A Location argument. Represents Minecraft locations
	 */
	public LocationArgument(LocationType type) {
		locationType = type;
		switch(type) {
			case BLOCK_POSITION:
				rawType = SemiReflector.getNMSArgumentInstance("ArgumentPosition");
				break;
			case PRECISE_POSITION:
				rawType = SemiReflector.getNMSArgumentInstance("ArgumentVec3");
				break;
		}
	}
	
	private final LocationType locationType;
	
	@Override
	public <T> com.mojang.brigadier.arguments.ArgumentType<T> getRawType() {
		return (com.mojang.brigadier.arguments.ArgumentType<T>) rawType;
	}

	@Override
	public <V> Class<V> getPrimitiveType() {
		return (Class<V>) Location.class;
	}

	@Override
	public boolean isSimple() {
		return false;
	}
	
	public LocationType getLocationType() {
		return locationType;
	}
	
	private String[] suggestions;
	
	@Override
	public LocationArgument overrideSuggestions(String... suggestions) {
		this.suggestions = suggestions;
		return this;
	}
	
	@Override
	public String[] getOverriddenSuggestions() {
		return suggestions;
	}
	
	private CommandPermission permission = CommandPermission.NONE;
	
	@Override
	public LocationArgument withPermission(CommandPermission permission) {
		this.permission = permission;
		return this;
	}

	@Override
	public CommandPermission getArgumentPermission() {
		return permission;
	}
}
