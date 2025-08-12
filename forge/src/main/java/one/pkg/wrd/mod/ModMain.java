package one.pkg.wrd.mod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import one.pkg.wrd.shared.util.ModInstance;

@Mod("wrdmod")
public class ModMain {
    public ModMain() {
        ModInstance.setGameDir(FMLPaths.GAMEDIR.get());
    }
}
