package ilya.sheverov.projectstask.model;

import ilya.sheverov.projectstask.entity.StatusOfATask;
import ilya.sheverov.projectstask.entity.presenter.PersonPresenter;
import ilya.sheverov.projectstask.entity.presenter.TaskPresenter;
import java.util.Map;
import java.util.Set;

public class MainMenuModel implements MainMenuModelFacade {

    PersonModel personModel;
    TaskModel taskModel;

    public MainMenuModel(PersonModel personModel, TaskModel taskModel) {
        this.personModel = personModel;
        this.taskModel = taskModel;
    }

    public void prepareTheMainMenu(int pageNumberOfThePersonList,
        String nameOfTheColumnToBeSortedPersonList,
        boolean multiplePersonSelectionMode, int pageNumberOfTheTaskList,
        String nameOfTheColumnToBeSortedTaskList, boolean multipleTaskSelectionMode
    ) {
        personModel
            .prepareAListOfPersons(pageNumberOfThePersonList, nameOfTheColumnToBeSortedPersonList,
                multiplePersonSelectionMode);
        taskModel.prepareAListOfTasks(pageNumberOfTheTaskList, nameOfTheColumnToBeSortedTaskList,
            multipleTaskSelectionMode);
    }

    @Override
    public String getTitlePageMainMenu() {
        return personModel.getTextValueMainMenu();
    }

    @Override
    public String getLang() {
        return personModel.getLang();
    }

    @Override
    public String getTextValueMainMenu() {
        return personModel.getTextValueMainMenu();
    }

    @Override
    public String getTextValuePersonList() {
        return personModel.getTextValuePersonList();
    }

    @Override
    public String getTextValueTaskList() {
        return personModel.getTextValueTaskList();
    }

    @Override
    public String getPageNumberOfThePersonList() {
        return personModel.getCurrentPageNumber();
    }

    @Override
    public String getPageNumberOfTheTaskList() {
        return taskModel.getCurrentPageNumber();
    }

    @Override
    public String getNameOfTheColumnToBeSortedPersonList() {
        return personModel.getNameOfTheColumnToBeSorted();
    }

    @Override
    public String getNameOfTheColumnToBeSortedTaskList() {
        return taskModel.getNameOfTheColumnToBeSorted();
    }

    @Override
    public boolean getMultiplePersonSelectionMode() {
        return personModel.getMultiplePersonSelectionMode();
    }

    @Override
    public boolean getMultipleTaskSelectionMode() {
        return taskModel.getMultipleTaskSelectionMode();
    }

    @Override
    public String getLabelOfThePersonTableColumnId() {
        return personModel.getColumnNameIdNames();
    }

    @Override
    public String getLabelOfThePersonTableColumnLastNames() {
        return personModel.getColumnNameLastNames();
    }

    @Override
    public String getLabelOfThePersonTableColumnFirstNames() {
        return personModel.getColumnNameFirstNames();
    }

    @Override
    public String getLabelOfThePersonTableColumnMiddleNames() {
        return personModel.getColumnNameMiddleNames();
    }

    @Override
    public String getTextValueCreatePerson() {
        return personModel.getCreatePerson();
    }

    @Override
    public String getTextValueDeleteSelectedPersons() {
        return personModel.getDeleteSelectedPersons();
    }

    @Override
    public Set<PersonPresenter> getPersonList() {
        return personModel.getListOfPersonPresenters();
    }

    @Override
    public String getTextValueEditPerson() {
        return personModel.getEditPerson();
    }

    @Override
    public String getTextValueDeletePerson() {
        return personModel.getDeletePerson();
    }

    @Override
    public String getDeleteAPersonQuestion(String personId) {
        return personModel.getDeleteAPersonQuestion(personId);
    }

    @Override
    public String getPersonsTasksCount(PersonPresenter person) {
        return personModel.getPersonsTasksCount(person);
    }

    @Override
    public String getTextValueFirstPagePersonList() {
        return personModel.getFirstPage();
    }

    @Override
    public String getTextValuePreviousPagePersonList() {
        return personModel.getPreviousPage();
    }

    @Override
    public int getNumberOfTheFirstPageInThePersonListPageNavigation() {
        return personModel.getFirstNumberOfPageInPageList();
    }

    @Override
    public int getNumberOfTheLastPageInThePersonListPageNavigation() {
        return personModel.getLastNumberOfPageInPageList();
    }

    @Override
    public int getNumberOfAllLinksToPagesInPageNavigationOfTheListOfPersons() {
        return personModel.getNumberOfAllLinksToPages();
    }

    @Override
    public String getTextValueNextPageOfTheListOfPersons() {
        return personModel.getNextPage();
    }

    @Override
    public String getTextValueLastPageOfTheListOfPersons() {
        return personModel.getLastPage();
    }

    @Override
    public String getLabelOfTheTaskTableColumnId() {
        return taskModel.getLabelOfTheTableColumnId();
    }

    @Override
    public String getLabelOfTheTaskTableColumnName() {
        return taskModel.getLabelOfTheTableColumnName();
    }

    @Override
    public String getLabelOfTheTaskTableColumnVolumeOfTheWork() {
        return taskModel.getLabelOfTheTableColumnVolumeOfTheWork();
    }

    @Override
    public String getLabelOfTheTaskTableColumnStartDate() {
        return taskModel.getLabelOfTheTableColumnStartDate();
    }

    @Override
    public String getLabelOfTheTaskTableColumnDueDate() {
        return taskModel.getLabelOfTheTableColumnDueDate();
    }

    @Override
    public String getLabelOfTheTaskTableColumnStatus() {
        return taskModel.getLabelOfTheTableColumnStatus();
    }

    @Override
    public String getLabelOfTheTaskTableColumnExecutor() {
        return taskModel.getLabelOfTheTableColumnExecutor();
    }

    @Override
    public String getTextValueCreateATask() {
        return taskModel.getTextValueCreateATask();
    }

    @Override
    public String getTextValueSelectTasks() {
        return taskModel.getTextValueSelectTasks();
    }

    @Override
    public String getTextValueDeleteSelectedTasks() {
        return taskModel.getTextValueDeleteSelectedTasks();
    }

    @Override
    public String getTextValueCancel() {
        return taskModel.getTextValueCancel();
    }

    @Override
    public Map<TaskPresenter, String> getListOfTasksWithInitials() {
        return taskModel.getListOfTasksWithInitials();
    }

    @Override
    public Set<TaskPresenter> getListOfTasks() {
        return taskModel.getListOfTasks();
    }

    @Override
    public String getInitialsOfTheExecutor(TaskPresenter taskPresenter) {
        return taskModel.getInitialsOfTheExecutor(taskPresenter);
    }

    @Override
    public Map<Enum<StatusOfATask>, String> getStatusValues() {
        return taskModel.getStatusValues();
    }

    @Override
    public String getTextValueEditATask() {
        return taskModel.getTextValueEditATask();
    }

    @Override
    public String getDeleteATaskQuestion(String id) {
        return taskModel.getDeleteATaskQuestion(id);
    }

    @Override
    public String getTextValueDeleteTheTask() {
        return taskModel.getTextValueDeleteTheTask();
    }

    @Override
    public String getTextValueFirstPageOfListOfTasks() {
        return taskModel.getTextValueFirstPage();
    }

    @Override
    public String getTextValuePreviousPageOfListOfTasks() {
        return taskModel.getTextValuePreviousPage();
    }

    @Override
    public int getFirstPageNumberInThePersonListPageNavigation() {
        return taskModel.getFirstNumberOfPageInPageNavigation();
    }

    @Override
    public int getLastPageNumberInThePersonListPageNavigation() {
        return taskModel.getLastNumberOfPageInPageNavigation();
    }

    @Override
    public int getNumberOfAllLinksToPagesInPageNavigationOfTheListOfTasks() {
        return taskModel.getNumberOfAllLinksToPagesInPageNavigation();
    }

    @Override
    public String getTextValueNextPageOfTheListOfTasks() {
        return taskModel.getTextValueNextPage();
    }

    @Override
    public String getTextValueLastPageOfTheListOfTasks() {
        return taskModel.getTextValueLastPage();
    }

    @Override
    public String getTextValueSelectPersons() {
        return personModel.getSelectPersons();
    }
}
