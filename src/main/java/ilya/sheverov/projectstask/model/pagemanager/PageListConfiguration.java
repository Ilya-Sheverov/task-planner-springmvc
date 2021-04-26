package ilya.sheverov.projectstask.model.pagemanager;

/**
 * Класс представляет из себя обычный DTO, для хранения информации, необходимой для построения навигации по страницам.
 */
public final class PageListConfiguration {

    /**
     * Обозначает количество ссылок на страницы, которое потребуется для отображения всех объектов.
     */
    private final int numberOfAllLinksToPages;

    /**
     * Обозначает номер текущей страницы.
     */
    private final int theCurrentPageNumber;

    /**
     * Является номером первой страницы отображаемой в списке ссылок на страницы.
     */
    private final int firstNumberOfPageInPageList;

    /**
     * Является номером последней страницы отображаемой в списке ссылок на страницы.
     */
    private final int lastNumberOfPageInPageList;


    /**
     * @param numberOfAllLinksToPages     обозначает количество ссылок на страницы, которое потребуется для отображения
     *                                    всех объектов.
     * @param theCurrentPageNumber        обозначает номер текущей страницы.
     * @param firstNumberOfPageInPageList номер первой страницы, отображаемой в списке ссылок навигации по страницам.
     * @param lastNumberOfPageInPageList  номер последней страницы, отображаемой в списке ссылок навигации по страницам.
     */
    public PageListConfiguration(int numberOfAllLinksToPages, int theCurrentPageNumber, int firstNumberOfPageInPageList,
                                 int lastNumberOfPageInPageList) {
        this.numberOfAllLinksToPages = numberOfAllLinksToPages;
        this.theCurrentPageNumber = theCurrentPageNumber;
        this.firstNumberOfPageInPageList = firstNumberOfPageInPageList;
        this.lastNumberOfPageInPageList = lastNumberOfPageInPageList;
    }

    public int getNumberOfAllLinksToPages() {
        return numberOfAllLinksToPages;
    }

    public int getTheCurrentPageNumber() {
        return theCurrentPageNumber;
    }

    public int getFirstNumberOfPageInPageList() {
        return firstNumberOfPageInPageList;
    }

    public int getLastNumberOfPageInPageList() {
        return lastNumberOfPageInPageList;
    }
}
