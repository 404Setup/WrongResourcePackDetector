package one.pkg.wrd.shared.util;

import org.apache.commons.compress.archivers.zip.ZipFile;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

public record ModZipFile(File file) {
    public static @Nullable ZipFile getZipFile(File file) {
        return new ModZipFile(file).getZipFile();
    }

    public @Nullable ZipFile getZipFile() {
        try {
            return ZipFile.builder().setFile(this.file).get();
        } catch (IOException e) {
            ModInstance.logger.error("Failed to open pack {}", file, e);
            return null;
        }
    }
}
