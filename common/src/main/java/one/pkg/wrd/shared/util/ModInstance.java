package one.pkg.wrd.shared.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class ModInstance {
    public static final Logger logger = LoggerFactory.getLogger("IrisRPCheck");
    private static Path gameDir;
    private static boolean hasIris = false;

    public static boolean hasIris() {
        return hasIris;
    }

    public static void setHasIris(boolean hasIris) {
        ModInstance.hasIris = hasIris;
    }

    public static Path getGameDir() {
        return gameDir;
    }

    public static void setGameDir(Path gameDir) {
        if (ModInstance.gameDir == null)
            ModInstance.gameDir = gameDir;
    }

    public static Path getResourcePackDir() {
        return getGameDir().resolve("resourcepacks");
    }

    public static Path getResourcePackDir(String name) {
        return getResourcePackDir().resolve(name);
    }

    public static Path getShaderPackDir() {
        return getGameDir().resolve("shaderpacks");
    }

    public static Path getShaderPackDir(String name) {
        return getShaderPackDir().resolve(name);
    }
}
