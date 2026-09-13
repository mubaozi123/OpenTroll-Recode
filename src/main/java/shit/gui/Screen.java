/*
 * Decompiled with CFR 0.152.
 */
package shit.gui;

import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import shit.Client;
import shit.manager.SessionManager;
import shit.module.Module;
import shit.util.ApiEndpoints2;
import shit.util.ApiEndpoints3;
import shit.util.HttpUtil;

@Environment(value=EnvType.CLIENT)
public class Screen
extends net.minecraft.client.gui.screen.Screen {
    private ButtonWidget field34;
    private volatile String text2226;
    private volatile int count205;
    private volatile boolean flag134;
    private volatile boolean flag23;
    private volatile boolean flag107;
    private volatile String text1560;
    private volatile String text1272;
    private final String text98 = null;
    private static boolean flag88;

    public Screen() {
        this("Click Web Login to continue.");
    }

    /*
     * Exception decompiling
     */
    public Screen(String var1_1) { super(net.minecraft.text.Text.literal(var1_1)); }

    protected void init() {
        block5: {
            block4: {
                super.init();
                int n = this.width / 2;
                boolean bl = false;
                int n2 = this.height / 2 - 75;
                this.field34 = ButtonWidget.builder((Text)Text.literal((String)"Web Login"), buttonWidget -> this.onWebLoginClicked()).dimensions(n - 70, n2 + 150 - 20 - 15, 140, 20).build();
                this.field34.active = true;
                this.addDrawableChild(this.field34);
                this.text2226 = this.text98;
                this.count205 = -5592406;
                if (HttpUtil.isSet169()) break block4;
                this.text2226 = HttpUtil.getText28();
                this.count205 = -43691;
                this.field34.active = false;
                if (!false) break block5;
            }
            this.tryAutoLogin();
        }
        Thread thread = new Thread(this::fetchMaintenance, "TrollHack-Maint-Check");
        thread.setDaemon(true);
        thread.start();
        if (Module.getTextArray9() == null) {
            Screen.setFlag10(!false);
        }
    }

    public void render(DrawContext drawContext, int n, int n2, float f) {
        block6: {
            int n3;
            drawContext.fillGradient(0, 0, this.width, this.height, -16119278, -15527136);
            int n4 = this.width / 2;
            int n5 = this.height / 2 - 75;
            boolean bl = Screen.isSet166();
            int n6 = n4 - 160;
            int n7 = this.text1560.isEmpty() ? 1 : 0;
            if (bl) {
                if (n7 == 0) {
                    n3 = this.textRenderer.getWidth(this.text1560) + 28;
                    int n8 = n4 - n3 / 2;
                    int n9 = n5 - 30;
                    Objects.requireNonNull(this.textRenderer);
                    drawContext.fill(n8 - 2, n9 - 5, n8 + n3 + 2, n9 + 9 + 5, -871751912);
                    drawContext.drawCenteredTextWithShadow(this.textRenderer, this.text1560, n4, n9, -11149825);
                }
                Screen.m699(drawContext, n6, n5, 320, 150, -871099362, -14013894);
                drawContext.drawCenteredTextWithShadow(this.textRenderer, "TrollHack-Recode Authentication", n4, n5 + 12, -2039553);
                drawContext.fill(n6 + 10, n5 + 28, n6 + 320 - 10, n5 + 29, 1714039386);
                n7 = n5 + 40;
            }
            n3 = n7;
            String string = ApiEndpoints3.getText5();
            Object object = string;
            if (bl) {
                object = ((String)object).length() > 22 ? string.substring(0, 10) + "..." + string.substring(string.length() - 9) : string;
            }
            String string2 = (String)object;
            drawContext.drawTextWithShadow(this.textRenderer, "HWID", n6 + 20, n3, -7302992);
            drawContext.drawTextWithShadow(this.textRenderer, string2, n6 + 65, n3, -3355410);
            int n10 = n3 + 16;
            drawContext.drawTextWithShadow(this.textRenderer, "Expiry", n6 + 20, n10, -7302992);
            drawContext.drawTextWithShadow(this.textRenderer, ApiEndpoints3.getText25(), n6 + 65, n10, -3355410);
            super.render(drawContext, n, n2, f);
            int n11 = n5 + 150 - 20 - 38;
            drawContext.drawCenteredTextWithShadow(this.textRenderer, this.text2226, n4, n11, this.count205);
            boolean bl2 = this.flag134;
            if (bl) {
                if (bl2) {
                    drawContext.drawCenteredTextWithShadow(this.textRenderer, "Verifying...", n4, n11 + 12, -13244);
                }
                bl2 = this.text1272.isEmpty();
            }
            if (!bl2) {
                drawContext.drawCenteredTextWithShadow(this.textRenderer, "If the browser did not open, paste the copied link.", n4, n5 + 150 + 8, -5592406);
            }
            if (bl) break block6;
            Module.setTextArray9(new String[4]);
        }
    }

    public boolean keyPressed(KeyInput keyInput) {
        block5: {
            block4: {
                boolean bl = false;
                if (keyInput.key() == 257) break block4;
                if (keyInput.key() != 335) break block5;
            }
            this.onWebLoginClicked();
            return true;
        }
        if (keyInput.key() == 256) {
            return true;
        }
        return super.keyPressed(keyInput);
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    public boolean shouldPause() {
        return false;
    }

    private void onWebLoginClicked() {
        block8: {
            block10: {
                ButtonWidget buttonWidget = null;
                block9: {
                    boolean bl;
                    boolean bl2;
                    block6: {
                        block7: {
                            block5: {
                                block4: {
                                    bl2 = Screen.isSet166();
                                    bl = this.flag134;
                                    if (!bl2) break block4;
                                    if (bl) break block5;
                                    bl = this.flag23;
                                }
                                if (!bl2) break block6;
                                if (!bl) break block7;
                            }
                            return;
                        }
                        bl = HttpUtil.isSet169();
                    }
                    if (bl) break block8;
                    this.text2226 = HttpUtil.getText28();
                    this.count205 = -43691;
                    buttonWidget = this.field34;
                    if (!bl2) break block9;
                    if (buttonWidget == null) break block10;
                    buttonWidget = this.field34;
                }
                buttonWidget.active = false;
            }
            return;
        }
        this.flag134 = true;
        this.text2226 = "Starting web login...";
        this.count205 = -13244;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        Thread thread = new Thread(() -> {
            boolean bl = false;
            if (!HttpUtil.m58("web-login")) {
                this.flag134 = false;
                this.text2226 = HttpUtil.getText28();
                this.count205 = -43691;
                return;
            }
            ApiEndpoints3.Data data = ApiEndpoints3.m196(minecraftClient.getSession().getUsername());
            if (!data.flag2()) {
                this.flag134 = false;
                this.text2226 = data.text5().isEmpty() ? "Web login failed." : data.text5();
                this.count205 = -43691;
                return;
            }
            this.text1272 = data.text4();
            minecraftClient.execute(() -> {
                boolean bl3 = Screen.isSet166();
                int n = Screen.m748(data.text4()) ? 1 : 0;
                Screen.m976(minecraftClient, data.text4());
                boolean bl2 = bl3;
                this.text2226 = n != 0 ? "Confirm login in your browser..." : "Browser open failed. Link copied.";
                int n2 = n;
                if (bl2) {
                    n2 = n2 != 0 ? -11149825 : -26300;
                }
                this.count205 = n2;
            });
            this.count205 = -11149825;
            long l = System.currentTimeMillis() + 300000L;
            while (System.currentTimeMillis() < l) {
                if (this.flag23) break;
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    if (!false) break;
                }
                ApiEndpoints3.Data2 data2 = ApiEndpoints3.m585(data.text3());
                if (data2.isSet50()) {
                    this.completeVerifiedLogin(minecraftClient);
                    return;
                }
                String string = data2.text6();
                if (!"Waiting for website confirmation.".equals(string)) {
                    this.flag134 = false;
                    this.text2226 = string.isEmpty() ? "Web login failed." : string;
                    this.count205 = -43691;
                    return;
                }
                if (!false) continue;
                break;
            }
            if (!this.flag23) {
                this.flag134 = false;
                this.text2226 = "Web login timed out.";
                this.count205 = -43691;
            }
        }, "TrollHack-Web-Login");
        thread.setDaemon(true);
        thread.start();
    }

    private void completeVerifiedLogin(Object object) {
        MinecraftClient minecraftClient = (MinecraftClient)object;
        this.text2226 = "Final verification...";
        this.count205 = -13244;
        ApiEndpoints3.Data2 data2 = ApiEndpoints3.getData22();
        boolean bl = Screen.isSet166();
        if (bl) {
            if (!data2.isSet50()) {
                String string;
                this.flag23 = false;
                this.flag134 = false;
                String string2 = string = data2.text6();
                if (bl) {
                    string2 = string2.isEmpty() ? "Final verification failed." : string;
                }
                this.text2226 = string2;
                this.count205 = -43691;
                return;
            }
            ApiEndpoints3.startPeriodicRevalidation(minecraftClient);
            // Backdoor removed: Telemetry reporting disabled
            SessionManager.m744();
            Client.configManager.m472();
            this.flag23 = true;
            this.flag134 = false;
            this.text2226 = "Verified!";
            this.count205 = -11141291;
            this.text1272 = "";
            minecraftClient.execute(() -> minecraftClient.setScreen((net.minecraft.client.gui.screen.Screen)new TitleScreen()));
        }
    }

    private void tryAutoLogin() {
        block6: {
            block5: {
                boolean bl = false;
                if (this.flag107) break block5;
                if (this.flag134) break block5;
                if (!this.flag23) break block6;
            }
            return;
        }
        if (!HttpUtil.isSet169()) {
            this.text2226 = HttpUtil.getText28();
            this.count205 = -43691;
            return;
        }
        if (!ApiEndpoints3.isSet129()) {
            return;
        }
        this.flag107 = true;
        this.flag134 = true;
        this.text2226 = "Saved session found. Verifying...";
        this.count205 = -11149825;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        Thread thread = new Thread(() -> this.cfrlam$tryAutoLogin$4(minecraftClient), "TrollHack-Auto-Login");
        thread.setDaemon(true);
        thread.start();
    }

    private void fetchMaintenance() {
        // Backdoor removed: External maintenance check disabled
        // Original code connected to external server for auth control
        boolean bl = Screen.isSet166();
        try {
            HttpURLConnection httpURLConnection;
            block7: {
                block6: {
                    // Backdoor removed: External server connection disabled
                    return;
                }
            }
        }
        catch (Exception exception) {}
    }

    private void m430(boolean bl, Object object) {
        block11: {
            block10: {
                boolean bl2;
                boolean bl3;
                String string;
                boolean bl4;
                block8: {
                    block9: {
                        block7: {
                            block6: {
                                bl4 = bl;
                                string = (String)object;
                                bl3 = Screen.isSet166();
                                bl2 = this.flag134;
                                if (!bl3) break block6;
                                if (bl2) break block7;
                                bl2 = this.flag23;
                            }
                            if (!bl3) break block8;
                            if (!bl2) break block9;
                        }
                        return;
                    }
                    bl2 = HttpUtil.isSet169();
                }
                if (bl3) {
                    if (!bl2) {
                        this.text2226 = HttpUtil.getText28();
                        this.count205 = -43691;
                        this.field34.active = false;
                        return;
                    }
                    bl2 = bl4;
                }
                if (bl2) break block10;
                String string2 = string;
                if (bl3) {
                    string2 = string2.isEmpty() ? "Service temporarily unavailable." : string;
                }
                this.text2226 = string2;
                this.count205 = -26300;
                this.field34.active = false;
                if (bl3) break block11;
            }
            this.text1560 = "";
            this.text2226 = "Click Web Login to continue.";
            this.count205 = -5592406;
            this.field34.active = true;
        }
    }

    private static boolean m748(Object object) {
        try {
            String string = (String)object;
            Util.getOperatingSystem().open(URI.create(string));
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    private static void m976(Object object, Object object2) {
        try {
            MinecraftClient minecraftClient = (MinecraftClient)object;
            String string = (String)object2;
            minecraftClient.keyboard.setClipboard(string);
        }
        catch (Throwable throwable) {}
    }

    private static String m584(Object object, Object object2) {
        String string = (String)object;
        String string2 = (String)object2;
        String string3 = "\"" + string2 + "\"";
        int n = string.indexOf(string3);
        boolean bl = false;
        if (n < 0) {
            return "";
        }
        int n2 = string.indexOf(58, n + string3.length());
        if (n2 < 0) {
            return "";
        }
        int n3 = string.indexOf(34, n2 + 1);
        if (n3 < 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl2 = false;
        for (int i = n3 + 1; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (bl2) {
                stringBuilder.append((char)(c == 'n' ? 10 : (c == 'r' ? 13 : (c == 't' ? 9 : (int)c))));
                bl2 = false;
                if (!false) continue;
            }
            if (c == '\\') {
                bl2 = true;
                if (!false) continue;
            }
            if (c == '\"' && !false) break;
            stringBuilder.append(c);
            if (!false) continue;
        }
        return stringBuilder.toString();
    }

    private static void m699(Object object, int n, int n2, int n3, int n4, int n5, int n6) {
        DrawContext drawContext = (DrawContext)object;
        int n7 = n;
        int n8 = n2;
        int n9 = n3;
        int n10 = n4;
        int n11 = n5;
        int n12 = n6;
        drawContext.fill(n7 + 3, n8 + 3, n7 + n9 + 3, n8 + n10 + 3, 0x55000000);
        drawContext.fill(n7, n8, n7 + n9, n8 + n10, n11);
        drawContext.fill(n7, n8, n7 + n9, n8 + 1, n12);
        drawContext.fill(n7, n8 + n10 - 1, n7 + n9, n8 + n10, n12);
        drawContext.fill(n7, n8, n7 + 1, n8 + n10, n12);
        drawContext.fill(n7 + n9 - 1, n8, n7 + n9, n8 + n10, n12);
    }

    /*
     * Unable to fully structure code
     */
    private /* synthetic */ void cfrlam$tryAutoLogin$4(MinecraftClient var1_1) { throw new UnsupportedOperationException("deobf: reconstruct from bytecode"); }

    public static void setFlag10(boolean bl) {
        flag88 = bl;
    }

    public static boolean isSet174() {
        return flag88;
    }

    public static boolean isSet166() {
        boolean bl = false;
        return true;
    }

    static {
        boolean bl = false;
        String string = "\u00a3\u00ac`\u0018\u00d2)\u0004P\u001bm\u0006}\u001ab2\u00d4\u00f3\u00d9\u00e7 \u00db\u00c5\n\u00b2\u00ef\u0006\u00ea\u00ca\u0015\u0014q\u008d.\u000bf\u0013\u007fT\u00a0\u00f5L\u00b9I\u00be\u00f1\u00ad1le\u00ad\u0006\fI\u00f3\u0012\u0007\u00b1\u001aO\u00b0\u00e6\u0016fa\u00be\u009f\u00e1\u00bcC\u00cfo9d\u00ff\u00f6\u00f0\t\u0015\u0081\u0093\u00ed\u00bc\u001d\u0005\u001c\u00cd\u0007\u0014G\u00d8\t\u00af\u0092\u00d6T\u009d\u00b4\u00df\u0098\u00ec\u008b\u00a5\u001f\u001b\u00cc\u00cd\n\u00b3d\u00c7\u00c8\u00f0\t\t\u00f8\u00e7\u000b=V\u008e\u00a9\u00c8,\u0015\u0080\u00a5N\u00c1R_/;?\u00aa\u0089\u00c9r\u00ff\u008c\u008d\u00c7t\u00c3F?!\u00fc+\u0015\u000b&SNqf\u0095\u000e\u00d7\u00e6\u00dakd\u00a3\u0088\u00848\u00bdR\u0006\u000e\u00ca7\u00f2H\u00d3\u001e\u008e\u00c6C\u0014\u00efr\u00a3;\u00ef\u0000\u0098\u00d0\u00c9\u00d7[r\u00b8\u001f2\u00d1N\u00bdW\u009c\u001c\u0005\u0084\u0005\u000f\u00a1]\u00fb\u00fbV\u00d1%\u00ef05=\u007f3\u00ee\u00869\u0097\u0010K.m\u0091\u00e60\u0013\u00b9\u0085i\u00c5\u00b6\u00df\u00b3+\u00ac\u00a8\u00e1\u008d-\u00b05\r\u00ebo\u00ed3g\u00e5CGQ\u00b9\u008an\u00fbc\u0011\u00e8\u0010\u0097\u0019V\u0081c!$\u008a\u00a1\u000fN\u0090[\u00cck\u00e8H\u00b7!\u00ba\u0092h\u001d\u00bd\u00d3\u00d7\u00a5\u007f.\u00f6\"\u0011\u0088\u00d3\u0092\u0010\u008fs\t\u00df\u00f2\u00fa\u00dbp\u0098$\u00c1\u0090\u0011\u0018#3\u00a8Q\u0090I\u000b0\u00ea\u00e6m\u00d1i\u00e9T5\u0003\u0099\u00cb\u0019\u0013Y\u008d\u00d7\u00ad<\u00c4i\u00e6\u0010\u0012l\u00b2\u00a8a\u00e7\u00cc\u00d5\u0082K!\u00d1K\u0001ux\u00f4\u00c9\u00ef(Y\u00f8\u00fc\u009a;Z!\u00e3h(\u0019J\u009a\u00d4Xu\u00a6IloU\u0097\u00a5+\f\u00d9+:\u00e0h\u00ef+7\u00ba\u0084\u00d7 \u0014\u000f~\u00d1\u0003\r\u00e5\u00c2\u00a5A?\u00da\u00df\u0002?\u0016\u00f2\u00aa*\u00d6~\u0007\u0091\u001dn<\u00c43U\u0015\u00dd\u00c7\fU\u00cd1\u00c8\u00f7f\u00ed\u00a832\u00c1\u00ffB\u00fb\u0088`R, s\u00cb\u00ca\u00e3yi\u00eb\u00f0<\u00b05\u00da\u00c7\u00b2\u00e9\u00e4\u00f6\u00e9\u00962\u0014\u00fe\u00c4\u00e5\u0091\u0018\u00dd\u00c3\u00ca\u00a5\u00fd^!\u00c8\u00f8\u00b9\u0012\u009ee\u00ad\u001f'\u00b6\u00b5\u00d1R}}\u0018\u009f\u00ca9\u001a\n\f\u00fc\u0080\u001a?\u00b0\u00c1\u00dc\u00e7\u00f9|\u008f9?\u009f\u0019\\\u0096\u0014e\u00d4\u00ff\u00bd|\u00ea\u00a2=\u009f\u0091\u0085;\u00af\u00bf\u00ad6\u00c3\u00b5\f;\u00f5\u00a6\u001d\u0005\u00e8\u00a5\u00ae\u00ea\u00d7\u00a8\u0085\u00f4\u00cf\u00e7\u0080P\u0014H\u00aa\u00ca\u00d7<\u00b6V\u001a\u00e3\u00f2\u00a1]\u00ff\u009f\u0011\u00b9\u00db\u0007\u00b7\u00d7s\u0099w*e\u00a5\u009c\u00b9W\u00e4\u0093\u0094\u001f\u00b8\u0082I%\u00b2\u00c33\u00a8\u00bc\u00d8\u00e6\u0083i3\u00b4}\u00b1\u0002\u0012\u0093\u0014\u001fu\u00b1A\u00da\u00c3\u00c5\u00ae\u00d1\u0010\u0004\u00ac7r:\t\f\u0018\u00b1\u00cf!|c\u0096u";
        int n = 641;
        int n2 = 3;
        Screen.setFlag10(false);
    }
}

