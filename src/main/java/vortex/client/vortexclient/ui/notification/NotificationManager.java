package vortex.client.vortexclient.ui.notification;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager {
    public static final NotificationManager INSTANCE = new NotificationManager();
    private final CopyOnWriteArrayList<Notification> notifications = new CopyOnWriteArrayList<>();
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public void show(Notification notification) {
        notifications.add(notification);
    }

    public void render(MatrixStack matrices) {
        int y = mc.getWindow().getScaledHeight() - 30;
        
        notifications.removeIf(Notification::isExpired);

        for (Notification notification : notifications) {
            int width = mc.textRenderer.getWidth(notification.message) + 10;
            int x = mc.getWindow().getScaledWidth() - width - 5;
            
            // ПРАВИЛЬНЫЙ СПОСОБ: Используем классический switch для Java 8
            Color color;
            switch (notification.getType()) {
                case INFO:
                    color = new Color(100, 100, 255);
                    break;
                case WARNING:
                    color = new Color(255, 255, 100);
                    break;
                case ERROR:
                    color = new Color(255, 80, 80);
                    break;
                default: // SUCCESS
                    color = new Color(80, 200, 120);
                    break;
            }

            DrawableHelper.fill(matrices, x, y, x + width, y + 20, new Color(0, 0, 0, 120).getRGB());
            DrawableHelper.fill(matrices, x, y, x + 2, y + 20, color.getRGB()); // Цветная полоска
            mc.textRenderer.drawWithShadow(matrices, notification.message, x + 5, y + 6, -1);
            
            y -= 25;
        }
    }
}
