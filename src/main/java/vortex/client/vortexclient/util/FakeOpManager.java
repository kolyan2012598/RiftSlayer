package vortex.client.vortexclient.util;

public class FakeOpManager {
    private static boolean isFakingOp = false;

    public static boolean isFakingOp() {
        return isFakingOp;
    }

    public static void setFakingOp(boolean fakingOp) {
        isFakingOp = fakingOp;
    }
}
