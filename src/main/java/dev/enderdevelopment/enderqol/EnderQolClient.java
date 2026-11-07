package dev.enderdevelopment.enderqol;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class EnderQolClient implements ClientModInitializer {
    private static boolean autoSprint = true;
    private static boolean statusHud = true;
    private static KeyBinding sprintToggle;

    @Override
    public void onInitializeClient() {
        sprintToggle = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ender_qol.toggle_sprint", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V,
                "category.ender_qol"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (sprintToggle.wasPressed()) {
                autoSprint = !autoSprint;
                notify(client, "Auto-sprint", autoSprint);
            }
            if (autoSprint && client.player != null && client.currentScreen == null
                    && client.player.forwardSpeed > 0.0F && !client.player.isSneaking()
                    && client.player.getHungerManager().getFoodLevel() > 6) {
                client.player.setSprinting(true);
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (statusHud && client.player != null) {
                String message = "Sprint " + (autoSprint ? "ON" : "OFF")
                        + "  |  FPS " + client.getCurrentFps();
                drawContext.drawTextWithShadow(client.textRenderer, message, 6, 6, 0xFFFFFF);
            }
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal("enderqol")
                        .then(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal("sprint")
                                .executes(context -> {
                                    autoSprint = !autoSprint;
                                    notify(MinecraftClient.getInstance(), "Auto-sprint", autoSprint);
                                    return 1;
                                }))
                        .then(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal("hud")
                                .executes(context -> {
                                    statusHud = !statusHud;
                                    notify(MinecraftClient.getInstance(), "Status HUD", statusHud);
                                    return 1;
                                }))
                        .then(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal("status")
                                .executes(context -> {
                                    MinecraftClient client = MinecraftClient.getInstance();
                                    if (client.player != null) {
                                        client.player.sendMessage(Text.literal("Ender QOL: auto-sprint "
                                                + (autoSprint ? "ON" : "OFF") + ", HUD "
                                                + (statusHud ? "ON" : "OFF") + ", FPS "
                                                + client.getCurrentFps()), false);
                                    }
                                    return 1;
                                }))));
    }

    private static void notify(MinecraftClient client, String feature, boolean enabled) {
        if (client.player != null) {
            client.player.sendMessage(Text.literal(feature + (enabled ? " enabled" : " disabled")), true);
        }
    }
}
