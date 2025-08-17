package one.pkg.wrd.mod.shared.optifine;

import net.minecraft.client.Minecraft;
import net.optifine.gui.SlotGui;
import one.pkg.wrd.shared.util.ModToast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;

@Mixin(targets = "net.optifine.shaders.gui.GuiSlotShaders", remap = false)
public abstract class GuiSlotShadersMixin extends SlotGui {
    public GuiSlotShadersMixin(Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
        super(mcIn, width, height, topIn, bottomIn, slotHeightIn);
    }

    @Inject(method = "checkCompatible", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    public void returnsCheckCompatible(net.optifine.shaders.IShaderPack sp, int index, CallbackInfoReturnable<Boolean> cir) {
        InputStream meta = sp.getResourceAsStream("pack.mcmeta");
        if (meta == null)
            return;
        try {
            meta.close();
        } catch (Exception ignored) {
        }
        ModToast.sendShaderRPToast(sp.getName());
        cir.setReturnValue(false);
    }
}
