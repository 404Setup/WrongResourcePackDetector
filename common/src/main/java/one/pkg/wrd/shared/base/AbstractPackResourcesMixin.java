package one.pkg.wrd.shared.base;

import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import one.pkg.wrd.shared.util.ModInstance;
import one.pkg.wrd.shared.util.ModToast;
import one.pkg.wrd.shared.util.ModZipFile;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.util.Enumeration;

@Mixin(AbstractPackResources.class)
public abstract class AbstractPackResourcesMixin implements PackResources {
    @Shadow
    @Final
    private PackLocationInfo location;

    @Inject(method = "getMetadataSection", at = @At(value = "RETURN", ordinal = 0))
    private void wrpd$getMetadataSection(MetadataSectionSerializer<?> deserializer, CallbackInfoReturnable<?> cir) {
        if (deserializer.getMetadataSectionName().equals("pack")) {
            Thread.ofVirtual().start(() -> {
                String packName = this.location.title().getString();
                File rF = ModInstance.getResourcePackDir(packName).toFile();
                if (rF.exists() && rF.isFile() && rF.getName().endsWith(".zip")) {
                    try (ZipFile mzF = ModZipFile.getZipFile(rF)) {
                        if (mzF == null) return;
                        Enumeration<ZipArchiveEntry> enumeration = mzF.getEntries();

                        while (enumeration.hasMoreElements()) {
                            ZipArchiveEntry entry = enumeration.nextElement();
                            if (entry.getName().endsWith(".zip")) {
                                ModToast.sendPackInPackRPToast(packName);
                                break;
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
            });
        }
    }
}
