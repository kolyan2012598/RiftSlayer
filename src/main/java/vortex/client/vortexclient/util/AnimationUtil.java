package vortex.client.vortexclient.util;

public class AnimationUtil {
    private long lastMS;
    private float time, state;
    private boolean initialState;

    public AnimationUtil(float time) {
        this.time = time;
    }

    public double getFactor() {
        update();
        return Math.tanh(state / time * 3) / Math.tanh(3);
    }

    public void setState(boolean state) {
        if (initialState == state) return;
        this.initialState = state;
        this.state = 0;
        this.lastMS = System.currentTimeMillis();
    }

    public void toggle() {
        setState(!initialState);
    }

    // Новый публичный метод
    public boolean getState() {
        return initialState;
    }

    private void update() {
        if (state < time) {
            state += System.currentTimeMillis() - lastMS;
        }
        lastMS = System.currentTimeMillis();
    }
}
