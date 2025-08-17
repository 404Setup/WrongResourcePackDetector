package one.pkg.wrd.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import one.pkg.wrd.shared.util.ModInstance;

public class ModMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModInstance.setHasIris(FabricLoader.getInstance().isModLoaded("iris"));
        ModInstance.setGameDir(FabricLoader.getInstance().getGameDir());
    }
}
