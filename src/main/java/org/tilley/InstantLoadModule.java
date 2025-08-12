package org.tilley;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.client.api.events.client.EventUpdate;
import org.rusherhack.core.setting.StringSetting;
import org.rusherhack.core.event.subscribe.Subscribe;

public class InstantLoadModule extends ToggleableModule {
	private boolean seen;
	StringSetting username = new StringSetting("Username", "tilley8");

	public InstantLoadModule() {
		super("InstantLoad", "Load a trapdoor as soon as the target logs on", ModuleCategory.COMBAT);
		this.registerSettings(username);
	}

	@Subscribe
	private void onUpdate(EventUpdate event) {
		if (mc.getConnection() == null) return;
		var match = username.getValue();
		boolean online = mc.getConnection().getOnlinePlayers().stream().anyMatch(p -> p.getProfile().getName().equalsIgnoreCase(match));

		if (online && !seen) {
			seen = true;
			ChatUtils.print(match + " has joined! Loading pearl...");
			interactTargetedBlock();
		} else if (!online) {
			seen = false;
		}
	}

	private void interactTargetedBlock() {
		if (mc.hitResult instanceof BlockHitResult bhr) {
            assert mc.gameMode != null;
            mc.gameMode.useItemOn(mc.player, InteractionHand.MAIN_HAND, bhr);
		}
	}
}
