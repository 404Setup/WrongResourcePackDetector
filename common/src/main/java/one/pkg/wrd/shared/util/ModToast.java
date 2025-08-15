package one.pkg.wrd.shared.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.List;

public class ModToast {
    static MethodHandle call;

    static {
        try {
            Constructor<SystemToast> constructor = SystemToast.class.getDeclaredConstructor(SystemToast.SystemToastId.class, Component.class, List.class, int.class);
            constructor.setAccessible(true);

            call = MethodHandles.lookup().unreflectConstructor(constructor);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static SystemToast multiline(Component title, Component description) {
        Font font = Minecraft.getInstance().font;
        List<FormattedCharSequence> list = font.split(description, 230);
        int i = Math.max(230, list.stream().mapToInt(font::width).max().orElse(230));
        //Math.max(160, 30 + Math.max(Minecraft.getInstance().font.width(title), message == null ? 0 : Minecraft.getInstance().font.width(message)))
        try {
            return (SystemToast) call.invoke(SystemToast.SystemToastId.PACK_LOAD_FAILURE, title, list, i + 30);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendToast(String title) {
        Minecraft.getInstance().doRunTask(() -> {
            ToastManager manager = Minecraft.getInstance().getToastManager();
            manager.addToast(
                    multiline(
                            Component.translatable("ircmod.toast.wrong_shaderpack.title"),
                            Component.literal(ChatFormatting.GOLD + title + ChatFormatting.RESET)
                                    .append(Component.translatable("ircmod.toast.wrong_shaderpack.description"))
                    )
            );
        });
    }
}
