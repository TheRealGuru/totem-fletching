package com.github.therealguru.totemfletching;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class TotemFletchingPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(TotemFletchingPlugin.class);
		RuneLite.main(args);
	}
}