package ilya.sheverov.projectstask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaskViewsConfig implements TaskViewsConfigI {

    Integer maxNumberPersonsPerPage;

    Integer numberOfLinksPerPageToPages;

    Integer defaultPageNumber;

    String[] validColumnNames;

    @Override
    public Integer getMaxNumberTasksPerPage() {
        return maxNumberPersonsPerPage;
    }

    @Value("${task.maxNumberTasksPerPage}")
    public void setMaxNumberPersonsPerPage(String maxNumberPersonsPerPage) {
        this.maxNumberPersonsPerPage = Integer.valueOf(maxNumberPersonsPerPage);
    }

    @Override
    public Integer getNumberOfLinksPerPageToPages() {
        return numberOfLinksPerPageToPages;
    }

    @Value("${task.numberOfLinksPerPageToPages}")
    public void setNumberOfLinksPerPageToPages(String numberOfLinksPerPageToPages) {
        this.numberOfLinksPerPageToPages = Integer.valueOf(numberOfLinksPerPageToPages);
    }

    @Override
    public Integer getDefaultPageNumber() {
        return defaultPageNumber;
    }

    @Value("${task.defaultPageNumber}")
    public void setDefaultPageNumber(String defaultPageNumber) {
        this.defaultPageNumber = Integer.valueOf(defaultPageNumber);
    }

    @Override
    public String[] getValidColumnNames() {
        return validColumnNames;
    }

    @Value("#{'${task.validColumnNames}'.split(',')}")
    public void setValidColumnNames(String[] validColumnNames) {
        this.validColumnNames = validColumnNames;
    }
}
