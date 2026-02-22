package vortex.client.vortexclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.util.Formatting;

public class TeamUtil {
    public static Item getTeamWool() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return Items.WHITE_WOOL;

        // ПРАВИЛЬНЫЙ СПОСОБ: Используем AbstractTeam
        AbstractTeam team = mc.player.getScoreboardTeam();
        if (team == null) return Items.WHITE_WOOL;

        Formatting color = team.getColor();

        switch (color) {
            case RED: return Items.RED_WOOL;
            case YELLOW: return Items.YELLOW_WOOL;
            case GREEN: return Items.GREEN_WOOL;
            case BLUE: return Items.BLUE_WOOL;
            case AQUA: return Items.CYAN_WOOL;
            case GRAY: return Items.GRAY_WOOL;
            case LIGHT_PURPLE: return Items.MAGENTA_WOOL;
            default: return Items.WHITE_WOOL;
        }
    }
}
