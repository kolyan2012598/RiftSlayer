package vortex.client.vortexclient.module.modules.render;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.ModeSetting;

import java.awt.Color;

public class Glow extends Module {
    public ModeSetting mode = new ModeSetting("Mode", this, "Rainbow", "Rainbow", "Team");
    private final Int2ObjectMap<Formatting> entityColors = new Int2ObjectOpenHashMap<>();

    public Glow() {
        super("Glow", Category.RENDER);
        settings.add(mode);
    }

    @Override
    public void onDisable() {
        if (mc.world == null) return;
        mc.world.getEntities().forEach(entity -> {
            if (entity.isGlowing()) {
                entity.setGlowing(false);
            }
        });
        entityColors.clear();
    }

    @Override
    public void onTick() {
        if (mc.world == null) return;

        mc.world.getEntities().forEach(entity -> {
            if (entity instanceof PlayerEntity && entity != mc.player) {
                if (mode.getMode().equals("Rainbow")) {
                    applyRainbowGlow(entity);
                } else {
                    entity.setGlowing(true);
                }
            }
        });
    }

    private void applyRainbowGlow(Entity entity) {
        Formatting color = getNextColor(entity);
        Scoreboard scoreboard = mc.world.getScoreboard();
        
        String teamName = "glow_" + color.ordinal(); 

        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.addTeam(teamName);
            team.setDisplayName(new LiteralText(teamName));
            team.setColor(color);
        }
        
        scoreboard.addPlayerToTeam(entity.getEntityName(), team);
        entity.setGlowing(true);
    }

    private Formatting getNextColor(Entity entity) {
        // ПРАВИЛЬНЫЙ МЕТОД: getEntityId()
        if (entityColors.containsKey(entity.getEntityId())) {
            Formatting currentColor = entityColors.get(entity.getEntityId());
            Formatting[] colors = Formatting.values();
            int nextIndex = (currentColor.ordinal() + 1) % 16;
            Formatting nextColor = colors[nextIndex];
            entityColors.put(entity.getEntityId(), nextColor);
            return nextColor;
        } else {
            Formatting initialColor = Formatting.RED;
            entityColors.put(entity.getEntityId(), initialColor);
            return initialColor;
        }
    }
}
