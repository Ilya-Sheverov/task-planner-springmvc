package ilya.sheverov.projectstask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PersonViewsConfig implements PersonViewsConfigI {

    Integer maxNumberPersonsPerPage;

    Integer numberOfLinksPerPageToPages;

    Integer defaultPageNumber;

    String[] validColumnNames;

    public Integer getMaxNumberPersonsPerPage() {
        return maxNumberPersonsPerPage;
    }

    @Value("${person.maxNumberPersonsPerPage}")
    public void setMaxNumberPersonsPerPage(String maxNumberPersonsPerPage) {
        this.maxNumberPersonsPerPage = Integer.valueOf(maxNumberPersonsPerPage);
    }

    public Integer numberOfAllLinksToPagesInPageNavigationOfTheListOfPersons() {
        return numberOfLinksPerPageToPages;
    }

    @Value("${person.numberOfLinksPerPageToPages}")
    public void setNumberOfLinksPerPageToPages(String numberOfLinksPerPageToPages) {
        this.numberOfLinksPerPageToPages = Integer.valueOf(numberOfLinksPerPageToPages);
    }

    public Integer getDefaultPageNumberOfTheListOfPersons() {
        return defaultPageNumber;
    }

    @Value("${person.defaultPageNumber}")
    public void setDefaultPageNumber(String defaultPageNumber) {
        this.defaultPageNumber = Integer.valueOf(defaultPageNumber);
    }

    public String[] getValidColumnNamesOfTableOfTheListOfPersons() {
        return validColumnNames;
    }

    @Value("#{'${person.validColumnNames}'.split(',')}")
    public void setValidColumnNames(String[] validColumnNames) {
        this.validColumnNames = validColumnNames;
    }
}

