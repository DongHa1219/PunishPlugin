package org.soonsal.punishplugin.util;

import java.util.ArrayList;
import java.util.List;

public class PathBuilder {
    private final List<String> pathList = new ArrayList<>();

    public static PathBuilder builder() {
        return new PathBuilder();
    }

    public PathBuilder append(String... paths) {
        for (String path : paths) {
            if (path == null) {
                continue;
            }

            pathList.add(path);
        }

        return this;
    }

    public String assemble() {
        return String.join(".", pathList);
    }
}
