package org.soonsal.punishplugin.util;

import java.util.List;

public class MessageList {
    private final List<String> list;

    public MessageList(List<String> list) {
        this.list = list;
    }

    public MessageList replace(CharSequence holder, CharSequence target) {
        list.replaceAll(s -> s.replace(holder, target));
        return this;
    }

    public List<String> get() {
        return list;
    }
}
