package org.soonsal.punishplugin.util.page;

import org.soonsal.punishplugin.util.Util;

import java.util.*;

public class PageManager<T> {
    private final Map<Integer, Page<T>> pageMap = new HashMap<>();

    public PageManager(Collection<T> collection, int amountPerPage) {
        final List<T> list = new ArrayList<>(collection);

        int size = list.size();
        int totalPage = Math.max(1, (size + amountPerPage - 1) / amountPerPage);

        int index = 0;
        for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
            Page<T> page = new Page<>(currentPage, amountPerPage);

            for (int i = 0; i < amountPerPage; i++) {
                if (!Util.hasIndex(list, index)) {
                    break;
                }

                page.addElement(list.get(index));
                index++;
            }

            pageMap.put(currentPage, page);
        }
    }

    public Page<T> getPage(int page) {
        return pageMap.get(page);
    }

    public boolean hasPage(int page) {
        return pageMap.containsKey(page);
    }

    public Collection<Page<T>> getPages() {
        return pageMap.values();
    }
}
