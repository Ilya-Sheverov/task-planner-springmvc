package ilya.sheverov.projectstask.model.pagemanager;

import org.springframework.stereotype.Service;

@Service
public class PageListServiceImpl implements PageListService {

    @Override
    public PageListConfiguration getPageListConfiguration(int numberOfAllObjects, int maxNumberOfObjectsPerPage,
                                                          int numberOfLinksPerPageToPages, int theCurrentPageNumber) {
        PageListManagerImp pageListManager = new PageListManagerImp(numberOfAllObjects, maxNumberOfObjectsPerPage,
            numberOfLinksPerPageToPages, theCurrentPageNumber);
        PageListConfiguration pageListConfiguration = new PageListConfiguration(
            pageListManager.getNumberOfAllLinksToPages(), theCurrentPageNumber,
            pageListManager.getFirstNumberOfPageInPageList(), pageListManager.getLastNumberOfPageInPageList());
        return pageListConfiguration;
    }

}

