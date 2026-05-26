package com.ignorehighlighter;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("ignorehighlighter")
public interface IgnoreHighlighterConfig extends Config
{
	@Alpha
	@ConfigItem(
		keyName = "highlightColor",
		name = "Highlight color",
		description = "Color used to highlight ignored players",
		position = 0
	)
	default Color highlightColor()
	{
		return new Color(255, 0, 0, 200);
	}

	@ConfigItem(
		keyName = "showName",
		name = "Show name overhead",
		description = "Draw the player's RSN above their head in the highlight color",
		position = 1
	)
	default boolean showName()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightTile",
		name = "Highlight tile",
		description = "Draw a colored tile under ignored players",
		position = 2
	)
	default boolean highlightTile()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightHull",
		name = "Highlight hull",
		description = "Draw a colored outline around the body of ignored players",
		position = 3
	)
	default boolean highlightHull()
	{
		return false;
	}

	@ConfigItem(
		keyName = "showMinimapDot",
		name = "Highlight minimap dot",
		description = "Color the minimap dot for ignored players",
		position = 4
	)
	default boolean showMinimapDot()
	{
		return true;
	}
}
