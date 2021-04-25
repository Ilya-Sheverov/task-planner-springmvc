package ilya.sheverov.projectstask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MainMenuConfig implements MainMenuViewConfigI {

    private Integer maxNumberPersonsPerPage;
    private Integer numberOfAllLinksToPagesInPageNavigationOfTheListOfPersons;
    private Integer defaultPageNumberOfTheListOfPersons;
    private String[] validColumnNamesOfTableOfTheListOfPersons;

    private Integer maxNumberTasksPerPage;
    private Integer numberOfAllLinksToPagesInPageNavigationOfTheListOfTasks;
    private Integer defaultPageNumberOfTheListOfTasks;
    private String[] validColumnNamesOfTableOfTheListOfTasks;

    @Override
    public Integer getMaxNumberPersonsPerPage() {
        return maxNumberPersonsPerPage;
    }

    @Value("${mainMenu.maxNumberPersonsPerPage}")
    public void setMaxNumberPersonsPerPage(String maxNumberPersonsPerPage) {
        this.maxNumberPersonsPerPage = Integer.valueOf(maxNumberPersonsPerPage);
    }

    @Override
    public Integer numberOfAllLinksToPagesInPageNavigationOfTheListOfPersons() {
        return numberOfAllLinksToPagesInPageNavigationOfTheListOfPersons;
    }

    @Override
    public Integer getDefaultPageNumberOfTheListOfPersons() {
        return defaultPageNumberOfTheListOfPersons;
    }

    @Value("${mainMenu.defaultPageNumberOfTheListOfPersons}")
    public void setDefaultPageNumberOfTheListOfPersons(String defaultPageNumberOfTheListOfPersons) {
        this.defaultPageNumberOfTheListOfPersons = Integer.valueOf(defaultPageNumberOfTheListOfPersons);
    }

    @Override
    public String[] getValidColumnNamesOfTableOfTheListOfPersons() {
        return validColumnNamesOfTableOfTheListOfPersons;
    }

    @Value("#{'${mainMenu.validColumnNamesOfTableOfTheListOfPersons}'.split(',')}")
    public void setValidColumnNamesOfTableOfTheListOfPersons(String[] validColumnNamesOfTableOfTheListOfPersons) {
        this.validColumnNamesOfTableOfTheListOfPersons = validColumnNamesOfTableOfTheListOfPersons;
    }

    @Override
    public Integer getMaxNumberTasksPerPage() {
        return maxNumberTasksPerPage;
    }

    @Value("${mainMenu.maxNumberTasksPerPage}")
    public void setMaxNumberTasksPerPage(String maxNumberTasksPerPage) {
        this.maxNumberTasksPerPage = Integer.valueOf(maxNumberTasksPerPage);
    }

    @Override
    public Integer getNumberOfLinksPerPageToPages() {
        return numberOfAllLinksToPagesInPageNavigationOfTheListOfTasks;
    }

    @Override
    public Integer getDefaultPageNumber() {
        return defaultPageNumberOfTheListOfTasks;
    }

    @Override
    public String[] getValidColumnNames() {
        return validColumnNamesOfTableOfTheListOfTasks;
    }

    @Value("${mainMenu.numberOfAllLinksToPagesInPageNavigationOfTheListOfPersons}")
    public void setNumberOfAllLinksToPagesInPageNavigationOfTheListOfPersons(String numberOfAllLinksToPagesInPageNavigationOfTheListOfPersons) {
        this.numberOfAllLinksToPagesInPageNavigationOfTheListOfPersons = Integer.valueOf(numberOfAllLinksToPagesInPageNavigationOfTheListOfPersons);
    }

    @Value("${mainMenu.numberOfAllLinksToPagesInPageNavigationOfTheListOfTasks}")
    public void setNumberOfAllLinksToPagesInPageNavigationOfTheListOfTasks(String numberOfAllLinksToPagesInPageNavigationOfTheListOfTasks) {
        this.numberOfAllLinksToPagesInPageNavigationOfTheListOfTasks = Integer.valueOf(numberOfAllLinksToPagesInPageNavigationOfTheListOfTasks);
    }

    @Value("${mainMenu.defaultPageNumberOfTheListOfTasks}")
    public void setDefaultPageNumberOfTheListOfTasks(String defaultPageNumberOfTheListOfTasks) {
        this.defaultPageNumberOfTheListOfTasks = Integer.valueOf(defaultPageNumberOfTheListOfTasks);
    }

    @Value("#{'${mainMenu.validColumnNamesOfTableOfTheListOfTasks}'.split(',')}")
    public void setValidColumnNamesOfTableOfTheListOfTasks(String[] validColumnNamesOfTableOfTheListOfTasks) {
        this.validColumnNamesOfTableOfTheListOfTasks = validColumnNamesOfTableOfTheListOfTasks;
    }
}
