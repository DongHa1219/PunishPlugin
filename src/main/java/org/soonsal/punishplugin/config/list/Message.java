package org.soonsal.punishplugin.config.list;

import org.soonsal.punishplugin.config.ConfigManager;
import org.soonsal.punishplugin.util.MessageList;
import org.soonsal.punishplugin.util.PathBuilder;
import org.soonsal.punishplugin.util.Util;

import java.util.List;
import java.util.stream.Collectors;

public enum Message {
    PUNISH,
    INCREASE,
    DECREASE,
    WARNING_RESET,
    NO_PERMISSION,
    ;

    public MessageList getMessageList(String... path) {
        List<String> list = ConfigManager.message.getConfig().getStringList(
                PathBuilder.builder()
                        .append(name())
                        .append(path)
                        .assemble()
        ).stream().map(Util::color).collect(Collectors.toList());
        return new MessageList(list);
    }

    public String getMessage(String... path) {
        return Util.color(ConfigManager.message.getConfig().getString(
                PathBuilder.builder()
                        .append(name())
                        .append(path)
                        .assemble()
        ));
    }
}
