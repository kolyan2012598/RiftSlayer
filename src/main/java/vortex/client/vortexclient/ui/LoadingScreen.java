package vortex.client.vortexclient.ui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import vortex.client.vortexclient.config.ConfigMenuBind;
import vortex.client.vortexclient.config.HudConfig;
import vortex.client.vortexclient.ui.alt.AltManager;

import java.util.Arrays;
import java.util.List;

public class LoadingScreen extends Screen {
    private static final Identifier CUSTOM_BACKGROUND = new Identifier("vortex", "textures/gui/menu.png");
    private int ticks = 0;
    private int stage = 0;
    private final List<String> stages = Arrays.asList(
        "Загрузка обходов...",
        "Инициализация классов...",
        "Загрузка визуальных эффектов...",
        "Подключение к серверам...",
        "Загрузка конфигураций...", // Этот этап будет реальным
        "Проверка обновлений...",
        "Финализация...",
        "Завершение..."
    );
    private final int STAGE_DURATION = 50; // 2 секунды на стадию
    private final int TOTAL_STAGES = stages.size();
    private final int TOTAL_TICKS = TOTAL_STAGES * STAGE_DURATION;

    public LoadingScreen() {
        super(Text.of("Loading..."));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.client.getTextureManager().bindTexture(CUSTOM_BACKGROUND);
        drawTexture(matrices, 0, 0, this.width, this.height, 0, 0, 1920, 1080, 1920, 1080);
        
        ticks++;

        int currentStage = ticks / STAGE_DURATION;
        if (currentStage > stage) {
            stage = currentStage;
            performLoadingStage(stage);
        }

        if (ticks >= TOTAL_TICKS) {
            this.client.openScreen(new TitleScreen());
            return;
        }

        String currentStatus = stages.get(Math.min(stage, stages.size() - 1));
        float progress = (float)ticks / TOTAL_TICKS;
        int percentage = (int)(progress * 100);
        
        int barWidth = 200;
        int barHeight = 10;
        int barX = this.width / 2 - barWidth / 2;
        int barY = this.height - 50;

        fill(matrices, barX, barY, barX + barWidth, barY + barHeight, 0x80000000);
        fill(matrices, barX, barY, barX + (int)(barWidth * progress), barY + barHeight, 0xFF0090FF);

        drawCenteredText(matrices, this.textRenderer, currentStatus, this.width / 2, barY - 15, 0xFFFFFF);
        drawCenteredText(matrices, this.textRenderer, percentage + "%", this.width / 2, barY + 1, 0xFFFFFF);
        
        super.render(matrices, mouseX, mouseY, delta);
    }

    private void performLoadingStage(int stage) {
        // На 3-й стадии (индекс 3) мы реально загружаем конфиги
        if (stage == 3) {
            System.out.println("VortexClient: Loading configs...");
            ConfigMenuBind.INSTANCE.load();
            HudConfig.INSTANCE.load();
            AltManager.INSTANCE.loadAlts();
            System.out.println("VortexClient: Configs loaded.");
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
