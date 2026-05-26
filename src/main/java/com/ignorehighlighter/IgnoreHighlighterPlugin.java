package com.ignorehighlighter;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Ignore Highlighter",
	description = "Highlights players on your ignore list with a custom color",
	tags = {"ignore", "highlight", "players", "overlay"}
)
public class IgnoreHighlighterPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private IgnoreHighlighterOverlay overlay;

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
	}

	@Provides
	IgnoreHighlighterConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(IgnoreHighlighterConfig.class);
	}
}
