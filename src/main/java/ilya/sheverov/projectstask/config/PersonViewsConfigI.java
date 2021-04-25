package ilya.sheverov.projectstask.config;

public interface PersonViewsConfigI {

    Integer getMaxNumberPersonsPerPage();

    Integer numberOfAllLinksToPagesInPageNavigationOfTheListOfPersons();

    Integer getDefaultPageNumberOfTheListOfPersons();

    String[] getValidColumnNamesOfTableOfTheListOfPersons();

}
