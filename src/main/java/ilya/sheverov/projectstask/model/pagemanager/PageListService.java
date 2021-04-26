package ilya.sheverov.projectstask.model.pagemanager;

public interface PageListService {

    PageListConfiguration getPageListConfiguration(int numberOfAllObjects, int maxNumberOfObjectsPerPage,
                                                   int numberOfLinksPerPageToPages, int theCurrentPageNumber);

}
