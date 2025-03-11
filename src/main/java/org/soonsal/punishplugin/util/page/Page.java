package org.soonsal.punishplugin.util.page;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
    private final int page;
    private final List<T> element;

    public Page(int page, int amountPerPage) {
        this.page = page;
        this.element = new ArrayList<>(amountPerPage);
    }

    public int getPage() {
        return page;
    }

    public void addElement(T t) {
        element.add(t);
    }

    public List<T> getElements() {
        return element;
    }
}
