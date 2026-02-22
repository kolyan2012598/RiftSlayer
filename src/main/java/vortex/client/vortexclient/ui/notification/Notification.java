package vortex.client.vortexclient.ui.notification;

public class Notification {
    public String message; // ПРАВИЛЬНЫЙ СПОСОБ: Делаем поле публичным
    public long startTime;
    public long duration;
    public NotificationType type;

    public Notification(String message, long duration, NotificationType type) {
        this.message = message;
        this.duration = duration;
        this.type = type;
        this.startTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - startTime > duration;
    }

    public NotificationType getType() {
        return type;
    }

    public enum NotificationType {
        SUCCESS,
        INFO,
        WARNING,
        ERROR
    }
}
