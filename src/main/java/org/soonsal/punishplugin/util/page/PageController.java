package org.soonsal.punishplugin.util.page;

import org.soonsal.punishplugin.gui.InventoryGraphic;

public class PageController<T> {
    private final InventoryGraphic graphic;
    private PageManager<T> pageManager;
    private int currentPage;

    public PageController(PageManager<T> pageManager, int startPage, InventoryGraphic graphic) {
        this.pageManager = pageManager;
        this.currentPage = startPage;
        this.graphic = graphic;
    }

    public void reload(PageManager<T> pageManager) {
        this.pageManager = pageManager;

        if (!this.pageManager.hasPage(currentPage)) {
            currentPage = 1;
        }
    }

    public Page<T> getPage() {
        return pageManager.getPage(currentPage);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void pressNextButton() {
        currentPage++;
        graphic.repaint();
    }

    public void pressPreviousButton() {
        currentPage--;
        graphic.repaint();
    }

    public boolean hasNextPage() {
        return pageManager.hasPage(currentPage + 1);
    }

    public boolean hasPreviousPage() {
        return pageManager.hasPage(currentPage - 1);
    }
}
