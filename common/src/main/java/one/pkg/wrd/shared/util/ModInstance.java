package one.pkg.wrd.shared.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class ModInstance {
    public static final Logger logger = LoggerFactory.getLogger("IrisRPCheck");
    private static Path gameDir;

    public static Path getGameDir() {
        return gameDir;
    }

    public static void setGameDir(Path gameDir) {
        if (ModInstance.gameDir == null)
            ModInstance.gameDir = gameDir;
    }
}
