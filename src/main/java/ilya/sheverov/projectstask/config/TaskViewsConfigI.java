package ilya.sheverov.projectstask.config;

public interface TaskViewsConfigI {
    Integer getMaxNumberTasksPerPage();

    Integer getNumberOfLinksPerPageToPages();

    Integer getDefaultPageNumber();

    String[] getValidColumnNames();
}
