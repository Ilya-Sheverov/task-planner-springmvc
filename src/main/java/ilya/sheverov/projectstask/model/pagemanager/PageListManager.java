package ilya.sheverov.projectstask.model.pagemanager;

public interface PageListManager {

    /**
     * Метод возвращает общее количество ссылок на страницы.
     */
    int getNumberOfAllLinksToPages();

    /**
     * Метод возвращает количество ссылок на страницы отображаемых на странице.
     */
    int getNumberOfLinksPerPageToPages();

    /**
     * Метод возвращает номер первой страницы отображаемой в списке ссылок на страницы.
     */
    int getFirstNumberOfPageInPageList();

    /**
     * Метод возвращает номер последней страницы отображаемой в списке ссылок на страницы.
     */
    int getLastNumberOfPageInPageList();
}
