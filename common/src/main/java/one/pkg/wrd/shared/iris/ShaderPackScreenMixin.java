package one.pkg.wrd.shared.iris;

import com.llamalad7.mixinextras.sugar.Local;
import net.irisshaders.iris.gui.element.ShaderPackSelectionList;
import net.irisshaders.iris.gui.screen.ShaderPackScreen;
import one.pkg.wrd.shared.util.ModInstance;
import one.pkg.wrd.shared.util.ModToast;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.nio.file.Path;

@Mixin(value = ShaderPackScreen.class, remap = false)
public class ShaderPackScreenMixin {
    @Inject(method = "applyChanges", at = @At(value = "INVOKE", target = "Lnet/irisshaders/iris/config/IrisConfig;setShaderPackName(Ljava/lang/String;)V", shift = At.Shift.AFTER, by = 2))
    private void vrkmod$applyChanges(CallbackInfo ci,
                                     @Local ShaderPackSelectionList.ShaderPackEntry entry) {
        Thread.ofVirtual().start(() -> {
            Path path = ModInstance.getGameDir().resolve("shaderpacks").resolve(entry.getPackName());
            File file = path.toFile();
            if (!file.exists()) return;
            if (file.isDirectory()) {
                File meta = path.resolve("pack.mcmeta").toFile();
                if (meta.exists()) ModToast.sendToast(file.getName());
            } else if (file.isFile()) {
                try (ZipFile zip = ZipFile.builder().setFile(file).get()) {
                    ZipArchiveEntry target = zip.getEntry("pack.mcmeta");
                    if (target != null) ModToast.sendToast(file.getName());
                } catch (Exception e) {
                    ModInstance.logger.error("Failed to open shaderpack {}", file, e);
                }
            }
        });
    }
}
