package org.tilley;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

public class LoaderPlugin extends Plugin {
	@Override
	public void onLoad() {
		this.getLogger().info("Loaded InstantLoadModule plugin!");

		final InstantLoadModule InstantLoadModule = new InstantLoadModule();
		RusherHackAPI.getModuleManager().registerFeature(InstantLoadModule);
	}

	@Override
	public void onUnload() {
		this.getLogger().info("InstantLoadModule plugin unloaded!");
	}
}