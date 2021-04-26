package ilya.sheverov.projectstask.model.pagemanager;

public class PageListManagerImp implements PageListManager {

    /**
     * Число всех объектов, которых требуется отобразить на страницах.
     */
    private int numberOfAllObjects;

    /**
     * Максимальное число объектов, отображаемых на странице.
     */
    private int maxNumberOfObjectsPerPage;

    /**
     * Обозначает количество ссылок на страницы, которое потребуется для отображения всех персон.
     */
    private int numberOfAllLinksToPages;

    /**
     * Обозначает количество ссылок на страницы отображаемых на странице.
     */
    private int numberOfLinksPerPageToPages = 5;

    /**
     * Обозначает номер текущей страницы.
     */
    private int theCurrentPageNumber;

    /**
     * Является номером первой страницы отображаемой в списке ссылок на страницы.
     */
    private int firstNumberOfPageInPageList;

    /**
     * Является номером последней страницы отображаемой в списке ссылок на страницы.
     */
    private int lastNumberOfPageInPageList;

    public PageListManagerImp() {

    }

    public PageListManagerImp(int numberOfAllObjects, int maxNumberOfObjectsPerPage, int numberOfLinksPerPageToPages,
                              int theCurrentPageNumber) {
        if (numberOfAllObjects < 0) {
            throw new IllegalArgumentException("Parameter \"numberOfAllObjects\" is less than zero.");
        }
        if (maxNumberOfObjectsPerPage < 1) {
            throw new IllegalArgumentException("Parameter \"maxNumberOfObjectsPerPage\" is less than one.");
        }
        if (numberOfLinksPerPageToPages < 1) {
            throw new IllegalArgumentException("Parameter \"numberOfLinksPerPageToPages\" is less than one.");
        }
        if (theCurrentPageNumber < 1) {
            throw new IllegalArgumentException("Parameter \"theCurrentPageNumber\" is less than one.");
        }
        this.numberOfAllObjects = numberOfAllObjects;
        this.maxNumberOfObjectsPerPage = maxNumberOfObjectsPerPage;
        this.numberOfLinksPerPageToPages = numberOfLinksPerPageToPages;
        this.theCurrentPageNumber = theCurrentPageNumber;
        calculateNumberOfAllPages();
        calculateFirstNumberOfPageInPageList();
        calculateLastNumberOfPageInPageList();
    }

    @Override
    public int getNumberOfAllLinksToPages() {
        return numberOfAllLinksToPages;
    }

    @Override
    public int getNumberOfLinksPerPageToPages() {
        return numberOfLinksPerPageToPages;
    }

    @Override
    public int getFirstNumberOfPageInPageList() {
        return firstNumberOfPageInPageList;
    }

    @Override
    public int getLastNumberOfPageInPageList() {
        return lastNumberOfPageInPageList;
    }

    private void calculateNumberOfAllPages() {
        if (numberOfAllObjects == 0) {
            numberOfAllLinksToPages = 1;
        } else {
            numberOfAllLinksToPages = (int) Math.ceil((float) numberOfAllObjects / (float) maxNumberOfObjectsPerPage);
        }

    }

    private void calculateFirstNumberOfPageInPageList() {
        if (theCurrentPageNumber % numberOfLinksPerPageToPages == 0) {
            firstNumberOfPageInPageList = theCurrentPageNumber - numberOfLinksPerPageToPages + 1;
        } else {
            firstNumberOfPageInPageList =
                ((int) Math.floor((float) theCurrentPageNumber / (float) numberOfLinksPerPageToPages) *
                    numberOfLinksPerPageToPages) + 1;
        }
    }

    private void calculateLastNumberOfPageInPageList() {
        lastNumberOfPageInPageList = firstNumberOfPageInPageList + numberOfLinksPerPageToPages - 1;
        if (lastNumberOfPageInPageList > numberOfAllLinksToPages) {
            lastNumberOfPageInPageList = numberOfAllLinksToPages;
        }
    }
}
