package org.soonsal.punishplugin.util;

import org.soonsal.punishplugin.PunishPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Folder {
    private final File folder;

    public Folder(String folderName) {
        this.folder = new File(PunishPlugin.getInstance().getDataFolder(), folderName);
    }

    public Folder(File parents, String folderName) {
        this.folder = new File(parents, folderName);
    }

    public Folder(Folder parents, String folderName) {
        this(parents.getFolderFile(), folderName);
    }

    public File getFolderFile() {
        return folder;
    }

    @SuppressWarnings("all")
    public void deleteFile(String name) {
        final File file = new File(folder,name + ".yml");

        if (file.exists()) {
            file.delete();
        }
    }

    public File[] getFiles() {
        return getFolderFile().listFiles() != null ? getFolderFile().listFiles() : new File[0];
    }

    public List<String> getFileNames() {
        return Arrays.stream(getFiles())
                .map(File::getName)
                .map(s -> s.split("\\.")[0])
                .toList();
    }
}
