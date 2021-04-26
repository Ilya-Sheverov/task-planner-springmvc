package ilya.sheverov.projectstask.model;

import ilya.sheverov.projectstask.entity.presenter.TaskPresenter;

import java.util.Map;
import java.util.Set;

public interface TaskModelFacade extends HeaderFacade {
    String getTitleOfThePagePersonList();

    String getTitleOfThePageCreatePerson();

    String getTitleOfThePageEditPerson();

    String getLang();

    String getCurrentPageNumber();

    String getNameOfTheColumnToBeSorted();

    boolean getMultipleTaskSelectionMode();

    String getLabelOfTheTableColumnId();

    String getLabelOfTheTableColumnName();

    String getLabelOfTheTableColumnVolumeOfTheWork();

    String getLabelOfTheTableColumnStartDate();

    String getLabelOfTheTableColumnDueDate();

    String getLabelOfTheTableColumnStatus();

    String getLabelOfTheTableColumnExecutor();

    String getTextValueCreateATask();

    String getTextValueSelectTasks();

    String getTextValueDeleteSelectedTasks();

    String getTextValueCancel();

    Map<TaskPresenter, String> getListOfTasksWithInitials();

    Set<TaskPresenter> getListOfTasks();

    String getTextValueEditATask();

    String getTextValueDeleteTheTask();

    String getTextValueFirstPage();

    String getTextValuePreviousPage();

    int getFirstNumberOfPageInPageNavigation();

    int getLastNumberOfPageInPageNavigation();

    int getNumberOfAllLinksToPagesInPageNavigation();

    String getTextValueLastPage();

    String getTextValueNextPage();

    Map<String, String> getPersonIdsAndInitials();

    Set<String> getPersonIds();

    TaskPresenter getTaskPresenter();

    String getLabelOfTheName();

    boolean getNameIsIncorrect();

    String getNameIsIncorrectMsg();

    String getPlaceholderOfTheName();

    String getTitleOfTheName();

    String getLabelOfTheVolumeOfWorkInHours();

    boolean getVolumeOfWorkInHoursIsIncorrect();

    String getVolumeOfWorkInHoursIsIncorrectMsg();

    String getPlaceholderOfTheVolumeOfWorkInHours();

    String getTitleOfTheVolumeOfWorkInHours();

    String getLabelOfTheStatus();

    boolean getStatusIsIncorrect();

    String getStatusIsIncorrectMsg();

    String getTitleOfTheStatus();

    String getLabelOfThePersonId();

    boolean getPersonIdIsIncorrect();

    String getPersonIsIncorrectMsg();

    String getTitleOfThePersonId();

    String getLabelOfTheStartDate();

    boolean getStartDateIsIncorrect();

    String getStartDateIsIncorrectMsg();

    String getPlaceholderOfTheStartDate();

    String getTitleOfTheStartDate();

    String getPatternDataTime();

    String getLabelOfTheDueDate();

    boolean getDueDateIsIncorrect();

    String getDueDateIsIncorrectMsg();

    String getPlaceholderOfTheDueDate();

    String getTitleOfTheDueDate();

    String getSubmitCreate();

    String getButtonBack();

    String getLabelOfTheId();

    boolean getTaskIdIsIncorrect();

    String getTaskIdIsIncorrectMsg();

    String getSubmitEdit();

    String[] getStatusValues();

    boolean getVersionIsIncorrect();

    String getVersionIsIncorrectMsg();

    String getDeleteATaskQuestion(String taskId);

    boolean getHasMessage();

    String getMessage();

    String getTextValueMainMenu();

    String getTextValuePersonList();

    String getTextValueTaskList();

    String getDefaultPageNumber();
}
