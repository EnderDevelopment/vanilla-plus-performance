# Client-Side Minecraft Mod: Vanilla Plus Performance

Vanilla Plus Performance is a lightweight client-side Fabric Minecraft mod for Minecraft 1.21.1 that adds vanilla-plus quality-of-life features, auto-sprint controls, FPS visibility, and client commands without changing server data, adding custom world content, or requiring a server-side installation.

## Install

1. Install Minecraft 1.21.1.
2. Install Fabric Loader 0.16.10 or newer.
3. Install Fabric API 0.115.1 or newer.
4. Ensure Java 21 is installed.
5. Build the project with:

```bash
./gradlew build
```

On Windows, run:

```bat
gradlew.bat build
```

6. Copy `build/libs/ender-qol-1.0.0.jar` into the Minecraft instance's `mods` folder.
7. Launch Minecraft using the Fabric profile.

The mod is client-only. Do not install it on a dedicated server.

## Use

Auto-sprint is enabled by default.

- Press `V` to toggle auto-sprint.
- Auto-sprint activates while moving forward, when the player is not sneaking, and when the hunger level is above 6.
- The top-left HUD displays the current auto-sprint state and FPS.
- Use `/enderqol sprint` to toggle auto-sprint.
- Use `/enderqol hud` to toggle the status HUD.
- Use `/enderqol status` to display the current settings and FPS.

Vanilla Plus Performance does not add custom blocks, items, world-generation registries, atmospheric fog, weighted animations, or combat automation. It does not force an FPS cap, allowing Minecraft's configured maximum FPS and other installed optimization mods to control rendering.

---

More Minecraft mod projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/f3ed445c-bdbc-4b25-9d12-b91528b51793?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=f3ed445c-bdbc-4b25-9d12-b91528b51793
