package com.ignorehighlighter;

import net.runelite.client.plugins.PluginDescriptor;
import org.junit.Assert;
import org.junit.Test;

public class IgnoreHighlighterPluginTest
{
	@Test
	public void testPluginDescriptor()
	{
		PluginDescriptor descriptor = IgnoreHighlighterPlugin.class.getAnnotation(PluginDescriptor.class);
		Assert.assertNotNull("PluginDescriptor annotation is missing", descriptor);
		Assert.assertFalse("Plugin name must not be empty", descriptor.name().isEmpty());
	}
}
