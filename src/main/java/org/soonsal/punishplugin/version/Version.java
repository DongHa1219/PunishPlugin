package org.soonsal.punishplugin.version;

public class Version {
    private final String value;

    public Version(String value) {
        this.value = value;
    }

    public static Version of(String version) {
        return new Version(version);
    }

    public String getValue() {
        return value;
    }

    public boolean isUnknown() {
        return value.equalsIgnoreCase("unknown");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Version v)) return false;
        if (v == this) return true;
        return this.getValue().equalsIgnoreCase(v.getValue());
    }
}
