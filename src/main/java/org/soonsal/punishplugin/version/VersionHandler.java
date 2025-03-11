package org.soonsal.punishplugin.version;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.soonsal.punishplugin.PunishPlugin;
import org.soonsal.punishplugin.util.Info;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class VersionHandler {
    public static final Version RUNTIME_VERSION = Version.of(PunishPlugin.getInstance().getDescription().getVersion());
    private static final String API_URL = "https://api.github.com/repos/DongHa1219/PunishPlugin/tags";
    private static Version NEW_VERSION;

    public static void init() {
        Bukkit.getScheduler().runTaskAsynchronously(PunishPlugin.getInstance(), () -> {
            NEW_VERSION = findNewVersion();

            if (hasNewVersion()) {
                Info.warningConsole("현재 구버전을 사용중입니다.");
                Info.warningConsole("최신버전으로 교체해주세요.");
                Info.warningConsole("");
            }
        });
    }

    public static boolean hasNewVersion() {
        if (NEW_VERSION.isUnknown()) {
            return false;
        }

        return !RUNTIME_VERSION.equals(NEW_VERSION);
    }

    private static Version fail() {
        Info.console("&c버전 업데이트 확인에 실패하였습니다.");
        return RUNTIME_VERSION;
    }

    private static @NotNull Version findNewVersion() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            List<String> versions = getVersions(connection);
            Version version = getLatestVersion(versions);

            if (version.isUnknown()) {
                return fail();
            }

            return version;
        } catch (IOException e) {
            return fail();
        }
    }

    private static @NotNull List<String> getVersions(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONArray array = new JSONArray(response.toString());
        List<String> versions = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject tagObject = array.getJSONObject(i);
            versions.add(tagObject.getString("name"));
        }
        return versions;
    }

    // 버전 비교 및 최신 버전 찾기
    private static Version getLatestVersion(List<String> versions) {
        return Version.of(versions.stream()
                .sorted(VersionHandler::compareVersions)
                .reduce((first, second) -> second) // 가장 최신 버전을 반환
                .orElse("unknown"));
    }

    // 버전 문자열을 비교하는 메서드
    private static int compareVersions(String v1, String v2) {
        String[] parts1 = v1.split("\\.");
        String[] parts2 = v2.split("\\.");
        int length = Math.max(parts1.length, parts2.length);

        for (int i = 0; i < length; i++) {
            int part1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
            int part2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;
            if (part1 != part2) {
                return Integer.compare(part1, part2);
            }
        }
        return 0;
    }
}
