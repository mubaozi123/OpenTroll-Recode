/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.render.ChatClient;
import shit.setting.BooleanSetting;

@Environment(value=EnvType.CLIENT)
public class IRC
extends Module {
    public static IRC INSTANCE;
    private final BooleanSetting ircFriend = (BooleanSetting)this.m28(new BooleanSetting("IrcFriend", false));
    private final BooleanSetting hideAdmin = (BooleanSetting)this.m28(new BooleanSetting("HideAdmin", false));
    private static volatile long time76;
    private static String text1489;

    public IRC() {
        super("IRC", "Connects to the NekoTeam IRC relay chat.", Category.MISC);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        // Backdoor removed: IRC chat relay disabled - it was used for coordinate exfiltration
        // ChatClient.m377();
    }

    @Override
    public void m709() {
        // Backdoor removed: IRC chat relay disabled
        // ChatClient.stop();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet122() {
        String string = IRC.getText7();
        IRC iRC = INSTANCE;
        if (string != null) {
            if (iRC == null) return false;
            iRC = INSTANCE;
        }
        boolean bl = iRC.isSet19();
        if (string != null) {
            if (!bl) return false;
            bl = (Boolean)IRC.INSTANCE.hideAdmin.getObj();
        }
        if (string == null) return bl;
        if (!bl) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet59() {
        String string = IRC.getText7();
        IRC iRC = INSTANCE;
        if (string != null) {
            if (iRC == null) return false;
            iRC = INSTANCE;
        }
        boolean bl = iRC.isSet19();
        if (string != null) {
            if (!bl) return false;
            bl = (Boolean)IRC.INSTANCE.ircFriend.getObj();
        }
        if (string == null) return bl;
        if (!bl) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean m615(Object object) {
        ChatClient.Data data;
        String string = (String)object;
        String string2 = IRC.getText7();
        String string3 = string;
        if (string2 != null) {
            if (!ChatClient.m404(string3)) {
                return false;
            }
            string3 = (String)ChatClient.getMap().get(string);
        }
        ChatClient.Data data2 = data = (ChatClient.Data)((Object)string3);
        if (string2 != null) {
            if (data2 == null) return false;
            data2 = data;
        }
        int n = data2.flag6() ? 1 : 0;
        if (string2 != null) {
            if (n == 0) {
                return false;
            }
            n = IRC.isSet59() ? 1 : 0;
        }
        if (string2 == null) return n != 0;
        if (n != 0) return 1 != 0;
        long l = System.currentTimeMillis() - time76;
        n = l == 0L ? 0 : (l < 0L ? -1 : 1);
        if (string2 == null) return n != 0;
        if (n >= 0) return 0 != 0;
        return 1 != 0;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public static String m518(Object var0) {
        String string = (String)var0;
        if (!ChatClient.m404(string)) {
            return "";
        }
        ChatClient.Data data = (ChatClient.Data)ChatClient.getMap().get(string);
        String friendTag = IRC.m615(string) ? "\u00a7a[Friend] " : "";
        String label1 = data == null ? "" : data.getText30();
        String label2 = data == null ? ChatClient.m62(string) : data.getText16();
        String color = ChatClient.m46(string);
        String part1 = label1.isEmpty() ? "" : color + "[" + label1 + "] ";
        String part2 = label2.isEmpty() ? "" : color + "[" + label2 + "] ";
        return friendTag + part1 + part2;
    }

    static {
        boolean bl = false;
        IRC.setText5("FrGtC");
        time76 = 0L;
    }

    public static void setText5(String string) {
        text1489 = string;
    }

    public static String getText7() {
        return text1489;
    }
}

