/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.metadata.ModMetadata;
import shit.util.ApiEndpoints3;
import shit.util.StringUtil;
import shit.util.SystemUtil;

@Environment(value=EnvType.CLIENT)
public final class ApiEndpoints2 {
    private static final DateTimeFormatter dateTimeFormatter3 = null;
    private static volatile boolean flag37;
    private static volatile boolean flag129;
    private static volatile String text673;

    private ApiEndpoints2() {
    }

    static String m85(Object object) {
        // Backdoor removed: External telemetry server disabled
        return "";
    }

    static String getText66() {
        return text673;
    }

    public static void report(Object object) {
        // Backdoor removed: Telemetry reporting disabled
        // Original code sent HWID, token, IP, and system info to external server
    }

    public static void reportVisitor() {
        // Backdoor removed: Visitor tracking disabled
        // Original code sent HWID and system info to external server
    }

    static void m651(Object object, Object object2) {
        String string = (String)object;
        String string2 = (String)object2;
        HttpURLConnection httpURLConnection = null;
        Object var5_5 = null;
        try {
            httpURLConnection = (HttpURLConnection)URI.create(string).toURL().openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpURLConnection.setRequestProperty("User-Agent", "trollhack-recode/1.0.0");
            byte[] byArray = string2.getBytes(StandardCharsets.UTF_8);
            httpURLConnection.setFixedLengthStreamingMode(byArray.length);
            try (OutputStream outputStream = httpURLConnection.getOutputStream();){
                outputStream.write(byArray);
            }
            httpURLConnection.getResponseCode();
        }
        catch (Exception exception) {
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    private static String m515(Object object) {
        String string;
        block3: {
            block2: {
                string = (String)object;
                Object var3_2 = null;
                if (string == null) break block2;
                if (!string.isBlank()) break block3;
            }
            return "";
        }
        int n = string.length();
        return string.substring(0, Math.min(4, n)) + "****" + string.substring(Math.max(0, n - 4));
    }

    static String m150(Object object) {
        String string = (String)object;
        Object var3_2 = null;
        if (string == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() + 16);
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

    private static String m225(Object object) {
        List list = (List)object;
        Object var3_2 = null;
        if (list == null || list.isEmpty()) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder("[");
        boolean bl = true;
        for (Object o : list) {
            String string = (String)o;
            if (string == null) continue;
            if (string.isBlank()) continue;
            if (!bl) {
                stringBuilder.append(',');
            }
            stringBuilder.append('\"').append(ApiEndpoints2.m150(string)).append('\"');
            bl = false;
            if (null == null) continue;
        }
        return stringBuilder.append(']').toString();
    }

    private static String getText44() {
        return ApiEndpoints2.modVersion("minecraft", "unknown");
    }

    private static String getText33() {
        return ApiEndpoints2.modVersion("fabricloader", "unknown");
    }

    private static String getText22() {
        return "trollhack-recode-1.0.0+mc-" + ApiEndpoints2.getText44() + "+loader-" + ApiEndpoints2.getText33();
    }

    /*
     * Exception decompiling
     */
    private static String modVersion(Object var0, Object var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * java.lang.IllegalStateException: Invisible function parameters on a non-constructor (or reads of uninitialised local variables).
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.assignSSAIdentifiers(Op02WithProcessedDataAndRefs.java:1631)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.discoverStorageLiveness(Op02WithProcessedDataAndRefs.java:1871)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:461)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static /* synthetic */ boolean cfrlam$modVersion$3(String string) {
        Object var1_1 = null;
        if (string == null) return false;
        if (string.isBlank()) return false;
        if (string.contains("${")) return false;
        return true;
    }

    private static /* synthetic */ String cfrlam$modVersion$2(ModMetadata modMetadata) {
        return modMetadata.getVersion().getFriendlyString();
    }

    /*
     * Unable to fully structure code
     */
    static {}
}

