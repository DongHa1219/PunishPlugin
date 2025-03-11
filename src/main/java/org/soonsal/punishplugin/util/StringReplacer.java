package org.soonsal.punishplugin.util;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringReplacer {
    private final Map<String, String> singleMap = new HashMap<>();
    private final Map<String, List<String>> listMap = new HashMap<>();

    public static StringReplacer newReplacer() {
        return new StringReplacer();
    }

    @SuppressWarnings("deprecation")
    public StringReplacer append(ItemMeta meta) {
        append("display", meta.getDisplayName());
        append("lore", meta.getLore());

        return this;
    }

    public StringReplacer append(String key, String value) {
        singleMap.put(key, value);
        return this;
    }

    public StringReplacer append(String key, List<String> list) {
        listMap.put(key, list);
        return this;
    }

    public StringReplacer replace(CharSequence target, CharSequence replacement) {
        singleMap.replaceAll((k, v) -> v.replace(target, replacement));
        listMap.replaceAll((k, v) -> replaceInList(v, target, replacement));
        return this;
    }

    private List<String> replaceInList(List<String> list, CharSequence target, CharSequence replacement) {
        list.replaceAll(string -> string.replace(target, replacement));
        return list;
    }

    @SuppressWarnings("deprecation")
    public StringReplacer apply(ItemMeta meta) {
        meta.setDisplayName(getString("display"));
        meta.setLore(getList("lore"));

        return this;
    }

    public String getString(String key) {
        return singleMap.get(key);
    }

    public List<String> getList(String key) {
        return listMap.get(key);
    }
}
