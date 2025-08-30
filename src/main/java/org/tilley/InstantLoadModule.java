package org.tilley;

import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import org.rusherhack.client.api.events.network.EventPacket;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.setting.StringSetting;
import org.rusherhack.core.event.subscribe.Subscribe;

public class InstantLoadModule extends ToggleableModule {
	StringSetting username = new StringSetting("Usernames", "tilley8");

	public InstantLoadModule() {
		super("InstantLoad", "Load a trapdoor as soon as the target logs on", ModuleCategory.COMBAT);

		this.registerSettings(username);
	}

    @Subscribe
    public void onPacketReceive(EventPacket.Receive event) {
        if (!(event.getPacket() instanceof ClientboundPlayerInfoUpdatePacket packet)) return;

        boolean trigger = packet.newEntries().stream().anyMatch(
                p -> p.profile() != null && p.profile().getName().equalsIgnoreCase(username.getValue())
        );
        if (!trigger) return;

        ChatUtils.print(username.getValue() + " has joined! Loading pearl...");
        interactTargetedBlock();
    }

	private void interactTargetedBlock() {
		if (!(mc.hitResult instanceof BlockHitResult bhr) || mc.gameMode == null) return;

        mc.gameMode.useItemOn(mc.player, InteractionHand.MAIN_HAND, bhr);
	}
}
