package ilya.sheverov.projectstask.model;

import ilya.sheverov.projectstask.entity.StatusOfATask;
import ilya.sheverov.projectstask.entity.presenter.PersonPresenter;
import ilya.sheverov.projectstask.entity.presenter.TaskPresenter;

import java.util.Map;
import java.util.Set;

public interface MainMenuModelFacade extends HeaderFacade {
    String getTitlePageMainMenu();

    String getLang();

    String getPageNumberOfThePersonList();

    String getPageNumberOfTheTaskList();

    String getNameOfTheColumnToBeSortedPersonList();

    String getNameOfTheColumnToBeSortedTaskList();

    boolean getMultiplePersonSelectionMode();

    boolean getMultipleTaskSelectionMode();

    String getLabelOfThePersonTableColumnId();

    String getLabelOfThePersonTableColumnLastNames();

    String getLabelOfThePersonTableColumnFirstNames();

    String getLabelOfThePersonTableColumnMiddleNames();

    String getTextValueCreatePerson();

    String getTextValueDeleteSelectedPersons();

    Set<PersonPresenter> getPersonList();

    String getTextValueEditPerson();

    String getTextValueDeletePerson();

    String getDeleteAPersonQuestion(String personId);

    String getPersonsTasksCount(PersonPresenter person);

    String getTextValueFirstPagePersonList();

    String getTextValuePreviousPagePersonList();

    int getNumberOfTheFirstPageInThePersonListPageNavigation();

    int getNumberOfTheLastPageInThePersonListPageNavigation();

    int getNumberOfAllLinksToPagesInPageNavigationOfTheListOfPersons();

    String getTextValueNextPageOfTheListOfPersons();

    String getTextValueLastPageOfTheListOfPersons();

    String getLabelOfTheTaskTableColumnId();

    String getLabelOfTheTaskTableColumnName();

    String getLabelOfTheTaskTableColumnVolumeOfTheWork();

    String getLabelOfTheTaskTableColumnStartDate();

    String getLabelOfTheTaskTableColumnDueDate();

    String getLabelOfTheTaskTableColumnStatus();

    String getLabelOfTheTaskTableColumnExecutor();

    String getTextValueCreateATask();

    String getTextValueSelectTasks();

    String getTextValueDeleteSelectedTasks();

    String getTextValueCancel();

    Map<TaskPresenter, String> getListOfTasksWithInitials();

    Set<TaskPresenter> getListOfTasks();

    String getInitialsOfTheExecutor(TaskPresenter taskPresenter);

    Map<Enum<StatusOfATask>, String> getStatusValues();

    String getTextValueEditATask();

    String getDeleteATaskQuestion(String id);

    String getTextValueDeleteTheTask();

    String getTextValueFirstPageOfListOfTasks();

    String getTextValuePreviousPageOfListOfTasks();

    int getFirstPageNumberInThePersonListPageNavigation();

    int getLastPageNumberInThePersonListPageNavigation();

    int getNumberOfAllLinksToPagesInPageNavigationOfTheListOfTasks();

    String getTextValueNextPageOfTheListOfTasks();

    String getTextValueLastPageOfTheListOfTasks();

    String getTextValueSelectPersons();
}
