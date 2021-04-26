package ilya.sheverov.projectstask.model;

import ilya.sheverov.projectstask.config.TaskViewsConfigI;
import ilya.sheverov.projectstask.entity.Task;
import ilya.sheverov.projectstask.entity.presenter.TaskPresenter;
import ilya.sheverov.projectstask.entity.presenter.converter.TaskViewConverter;
import ilya.sheverov.projectstask.exception.OptimisticLockException;
import ilya.sheverov.projectstask.model.pagemanager.PageListConfiguration;
import ilya.sheverov.projectstask.model.pagemanager.PageListService;
import ilya.sheverov.projectstask.service.TaskServiceI;

import java.sql.Timestamp;
import java.util.*;

public class TaskModel implements TaskModelFacade {
    final private TaskServiceI taskService;
    final private PageListService pageListService;
    final private ResourceBundle resourceBundle;
    final private TaskViewsConfigI taskViewsConfigI;

    private PageListConfiguration pageListConfiguration;
    private TaskViewConverter converter;

    private int maxNumberTasksPerPage;
    private int numberOfLinksPerPageToPages;
    private int currentPageNumber;
    private int numberOfAllTasks;

    private String[] validColumnNames;
    private String nameOfTheColumnToBeSorted;

    private boolean hasOptimisticLockException;
    private boolean hasMessage;
    private boolean multipleTaskSelectionMode;
    private boolean taskIdIsIncorrect;
    private boolean nameIsIncorrect;
    private boolean volumeOfWorkInHoursIsIncorrect;
    private boolean startDateIsIncorrect;
    private boolean dueDateIsIncorrect;
    private boolean statusIsIncorrect;
    private boolean personIdIsIncorrect;
    private boolean versionIsIncorrect;

    private String message;

    private TaskPresenter taskPresenter;

    private Map<TaskPresenter, String> listOfTasksWithInitials;

    private Map<String, String> personIdsAndInitials;

    public TaskModel(TaskServiceI taskService, PageListService pageListService, ResourceBundle resourceBundle,
                     TaskViewsConfigI taskViewsConfigI, TaskViewConverter converter) {
        this.taskService = taskService;
        this.pageListService = pageListService;
        this.resourceBundle = resourceBundle;
        this.taskViewsConfigI = taskViewsConfigI;
        this.converter = converter;
        maxNumberTasksPerPage = taskViewsConfigI.getMaxNumberTasksPerPage();
        numberOfLinksPerPageToPages = taskViewsConfigI.getNumberOfLinksPerPageToPages();
        currentPageNumber = taskViewsConfigI.getDefaultPageNumber();
        validColumnNames = taskViewsConfigI.getValidColumnNames();
        nameOfTheColumnToBeSorted = validColumnNames[0];
    }

    public void prepareAListOfTasks(int pageNumber, String nameOfTheColumnToBeSorted, boolean multipleTaskSelectionMode) {
        if (pageNumber > 1) {
            currentPageNumber = pageNumber;
        }
        if (nameOfTheColumnToBeSorted != null) {
            boolean isValid = Arrays.stream(validColumnNames)
                .anyMatch(s -> s.equals(nameOfTheColumnToBeSorted));
            if (isValid) {
                this.nameOfTheColumnToBeSorted = nameOfTheColumnToBeSorted;
            }
        }
        this.multipleTaskSelectionMode = multipleTaskSelectionMode;
        numberOfAllTasks = taskService.getTheNumberOfTasks();
        pageListConfiguration = pageListService.getPageListConfiguration(numberOfAllTasks, maxNumberTasksPerPage,
            numberOfLinksPerPageToPages, currentPageNumber);
        int firstTaskNumber = (currentPageNumber - 1) * maxNumberTasksPerPage;
        Map<Task, String> tasks = taskService.findSortedPartOfTheListOfTask(this.nameOfTheColumnToBeSorted, firstTaskNumber, maxNumberTasksPerPage);
        listOfTasksWithInitials = converter.convertTaskToTaskView(tasks);
    }

    public void prepareToCreateATask() {
        personIdsAndInitials = taskService.getPersonsIdAndInitials();
    }

    public void createTask(Task newTask) {
        try {
            taskService.createTask(newTask);
            hasMessage = true;
            message = resourceBundle.getString("message.taskHasBeenCreated");
        } catch (OptimisticLockException e) {
            hasOptimisticLockException = true;
            hasMessage = true;
            message = resourceBundle.getString("message.optimisticLocking.creating");
        }
    }

    public void findTheTask(Integer taskId, Timestamp taskVersion) {
        personIdsAndInitials = taskService.getPersonsIdAndInitials();
        Task desiredTask = taskService.findTaskByIdAndVersion(taskId, taskVersion);
        if (desiredTask == null) {
            hasOptimisticLockException = true;
            hasMessage = true;
            message = resourceBundle.getString("message.optimisticLocking.notFound");
        } else {
            taskPresenter = converter.convertTaskToTaskView(desiredTask);
        }
    }

    public void editTask(Task editedTask) {
        try {
            taskService.editTask(editedTask);
            hasMessage = true;
            message = injectTaskIdToString(resourceBundle.getString("message.taskHasBeenEdited"),
                String.valueOf(editedTask.getId()));
        } catch (OptimisticLockException e) {
            hasOptimisticLockException = true;
            hasMessage = true;
            message = injectTaskIdToString(resourceBundle.getString("message.optimisticLocking.editing"),
                String.valueOf(editedTask.getId()));
        }
    }

    public void deleteTask(Integer taskId, Timestamp taskVersion) {
        try {
            taskService.deleteTask(taskId, taskVersion);
            hasMessage = true;
            message = injectTaskIdToString(resourceBundle.getString("message.taskHasBeenDeleted"),
                String.valueOf(taskId));
        } catch (OptimisticLockException e) {
            hasOptimisticLockException = true;
            hasMessage = true;
            message = injectTaskIdToString(resourceBundle.getString("message.optimisticLocking.deleting"),
                String.valueOf(taskId));
        }
    }

    public void deleteTasks(List<Task> tasks) {
        try {
            taskService.deleteTasks(tasks);
            hasMessage = true;
            message = injectListTaskIdsToString(resourceBundle.getString("message.theTaskGroupWasDeleted"), tasks);
        } catch (OptimisticLockException e) {
            hasOptimisticLockException = true;
            hasMessage = true;
            message = injectListTaskIdsToString(resourceBundle.getString("message.optimisticLocking.groupDeletion"), tasks);
        }
    }

    public void setTaskPresenterWithInvalidFieldsNames(TaskPresenter taskPresenter, Set<String> invalidFieldsNames) {
        personIdsAndInitials = taskService.getPersonsIdAndInitials();
        this.taskPresenter = taskPresenter;
        for (String invalidFieldsName : invalidFieldsNames) {
            markTheTasksFieldAsIncorrect(invalidFieldsName);
        }
    }


    void markTheTasksFieldAsIncorrect(final String fieldsName) {
        switch (fieldsName) {
            case "id":
                taskIdIsIncorrect = true;
                break;
            case "name":
                nameIsIncorrect = true;
                break;
            case "volumeOfWorkInHours":
                volumeOfWorkInHoursIsIncorrect = true;
                break;
            case "startDate":
                startDateIsIncorrect = true;
                break;
            case "dueDate":
                dueDateIsIncorrect = true;
                break;
            case "status":
                statusIsIncorrect = true;
                break;
            case "personId":
                personIdIsIncorrect = true;
                break;
            case "version":
                versionIsIncorrect = true;
                break;
            default:
                break;
        }
    }

    private String injectTaskIdToString(String s, String taskId) {
        if (s.contains("${taskId}")) {
            s = s.replaceAll("\\$\\{taskId\\}", taskId);
        }
        return s;
    }

    private String injectListTaskIdsToString(String s, List<Task> taskIds) {
        if (taskIds.size() > 0) {
            if (s.contains("${taskIds}")) {
                StringBuilder sb = new StringBuilder();
                for (Task task : taskIds) {
                    sb.append(task.getId()).append(", ");
                }
                String ids = sb.toString();
                //todo убрать костыль
                ids = ids.substring(0, ids.length() - 2);

                s = s.replaceAll("\\$\\{taskIds\\}", ids);
            }
        }
        return s;
    }

    @Override
    public String getTitleOfThePagePersonList() {
        return resourceBundle.getString("page.title.listTasks");
    }

    @Override
    public String getTitleOfThePageCreatePerson() {
        return resourceBundle.getString("page.title.createATask");
    }

    @Override
    public String getTitleOfThePageEditPerson() {
        return resourceBundle.getString("page.title.editATask");
    }

    @Override
    public String getLang() {
        return resourceBundle.getLocale().getLanguage();
    }

    @Override
    public String getCurrentPageNumber() {
        return String.valueOf(currentPageNumber);
    }

    @Override
    public String getNameOfTheColumnToBeSorted() {
        return nameOfTheColumnToBeSorted;
    }

    @Override
    public boolean getMultipleTaskSelectionMode() {
        return multipleTaskSelectionMode;
    }

    @Override
    public String getLabelOfTheTableColumnId() {
        return resourceBundle.getString("table.column.label.taskId");
    }

    @Override
    public String getLabelOfTheTableColumnName() {
        return resourceBundle.getString("table.column.label.name");
    }

    @Override
    public String getLabelOfTheTableColumnVolumeOfTheWork() {
        return resourceBundle.getString("table.column.label.volumeOfTheWork");
    }

    @Override
    public String getLabelOfTheTableColumnStartDate() {
        return resourceBundle.getString("table.column.label.startDate");
    }

    @Override
    public String getLabelOfTheTableColumnDueDate() {
        return resourceBundle.getString("table.column.label.dueDate");
    }

    @Override
    public String getLabelOfTheTableColumnStatus() {
        return resourceBundle.getString("table.column.label.status");
    }

    @Override
    public String getLabelOfTheTableColumnExecutor() {
        return resourceBundle.getString("table.column.label.executor");
    }

    @Override
    public String getTextValueCreateATask() {
        return resourceBundle.getString("textValues.createTask");
    }

    @Override
    public String getTextValueSelectTasks() {
        return resourceBundle.getString("textValues.selectTasks");
    }

    @Override
    public String getTextValueDeleteSelectedTasks() {
        return resourceBundle.getString("textValues.deleteSelectedTasks");
    }

    @Override
    public String getTextValueCancel() {
        return resourceBundle.getString("textValues.cancel");
    }

    @Override
    public Map<TaskPresenter, String> getListOfTasksWithInitials() {
        return listOfTasksWithInitials;
    }

    @Override
    public Set<TaskPresenter> getListOfTasks() {
        return listOfTasksWithInitials.keySet();
    }

    @Override
    public String getTextValueEditATask() {
        return resourceBundle.getString("textValues.editTask");
    }

    @Override
    public String getTextValueDeleteTheTask() {
        return resourceBundle.getString("textValues.deleteTask");
    }

    @Override
    public String getTextValueFirstPage() {
        return resourceBundle.getString("textValues.firstPage");
    }

    @Override
    public String getTextValuePreviousPage() {
        return resourceBundle.getString("textValues.previousPage");
    }

    @Override
    public int getFirstNumberOfPageInPageNavigation() {
        return pageListConfiguration.getFirstNumberOfPageInPageList();
    }

    @Override
    public int getLastNumberOfPageInPageNavigation() {
        return pageListConfiguration.getLastNumberOfPageInPageList();
    }

    @Override
    public int getNumberOfAllLinksToPagesInPageNavigation() {
        return pageListConfiguration.getNumberOfAllLinksToPages();
    }

    @Override
    public String getTextValueLastPage() {
        return resourceBundle.getString("textValues.lastPage");
    }

    @Override
    public String getTextValueNextPage() {
        return resourceBundle.getString("textValues.nextPage");
    }

    @Override
    public Map<String, String> getPersonIdsAndInitials() {
        return personIdsAndInitials;
    }

    @Override
    public Set<String> getPersonIds() {
        return personIdsAndInitials.keySet();
    }

    @Override
    public TaskPresenter getTaskPresenter() {
        return taskPresenter;
    }

    @Override
    public String getLabelOfTheName() {
        return resourceBundle.getString("form.input.label.name");
    }

    @Override
    public boolean getNameIsIncorrect() {
        return nameIsIncorrect;
    }

    @Override
    public String getNameIsIncorrectMsg() {
        return resourceBundle.getString("message.incorrectValue.name");
    }

    @Override
    public String getPlaceholderOfTheName() {
        return resourceBundle.getString("form.input.placeholder.name");
    }

    @Override
    public String getTitleOfTheName() {
        return resourceBundle.getString("form.input.title.name");
    }

    @Override
    public String getLabelOfTheVolumeOfWorkInHours() {
        return resourceBundle.getString("form.input.label.volumeOfWork");
    }

    @Override
    public boolean getVolumeOfWorkInHoursIsIncorrect() {
        return volumeOfWorkInHoursIsIncorrect;
    }

    @Override
    public String getVolumeOfWorkInHoursIsIncorrectMsg() {
        return resourceBundle.getString("message.incorrectValue.volumeOfWork");
    }

    @Override
    public String getPlaceholderOfTheVolumeOfWorkInHours() {
        return resourceBundle.getString("form.input.placeholder.volumeOfTheWork");
    }

    @Override
    public String getTitleOfTheVolumeOfWorkInHours() {
        return resourceBundle.getString("form.input.title.volumeOfTheWork");
    }

    @Override
    public String getLabelOfTheStatus() {
        return resourceBundle.getString("form.input.label.status");
    }

    @Override
    public boolean getStatusIsIncorrect() {
        return statusIsIncorrect;
    }

    @Override
    public String getStatusIsIncorrectMsg() {
        return resourceBundle.getString("message.incorrectValue.status");
    }

    @Override
    public String getTitleOfTheStatus() {
        return resourceBundle.getString("form.input.title.status");
    }

    @Override
    public String getLabelOfThePersonId() {
        return resourceBundle.getString("form.input.label.executor");
    }

    @Override
    public boolean getPersonIdIsIncorrect() {
        return personIdIsIncorrect;
    }

    @Override
    public String getPersonIsIncorrectMsg() {
        return resourceBundle.getString("message.incorrectValue.executor");
    }

    @Override
    public String getTitleOfThePersonId() {
        return resourceBundle.getString("form.input.title.executor");
    }

    @Override
    public String getLabelOfTheStartDate() {
        return resourceBundle.getString("form.input.label.startDate");
    }

    @Override
    public boolean getStartDateIsIncorrect() {
        return startDateIsIncorrect;
    }

    @Override
    public String getStartDateIsIncorrectMsg() {
        return resourceBundle.getString("message.incorrectValue.startDate");
    }

    @Override
    public String getPlaceholderOfTheStartDate() {
        return resourceBundle.getString("form.input.placeholder.startDate");
    }

    @Override
    public String getTitleOfTheStartDate() {
        return resourceBundle.getString("form.input.title.startDate");
    }

    @Override
    public String getPatternDataTime() {
        return resourceBundle.getString("form.input.pattern.datatime");
    }

    @Override
    public String getLabelOfTheDueDate() {
        return resourceBundle.getString("form.input.label.dueDate");
    }

    @Override
    public boolean getDueDateIsIncorrect() {
        return dueDateIsIncorrect;
    }

    @Override
    public String getDueDateIsIncorrectMsg() {
        return resourceBundle.getString("message.incorrectValue.dueDate");
    }

    @Override
    public String getPlaceholderOfTheDueDate() {
        return resourceBundle.getString("form.input.placeholder.dueDate");
    }

    @Override
    public String getTitleOfTheDueDate() {
        return resourceBundle.getString("form.input.title.dueDate");
    }

    @Override
    public String getSubmitCreate() {
        return resourceBundle.getString("submit.create");
    }

    @Override
    public String getButtonBack() {
        return resourceBundle.getString("button.back");
    }

    @Override
    public String getLabelOfTheId() {
        return injectTaskIdToString(resourceBundle.getString("form.input.label.taskId"), taskPresenter.getId());
    }

    @Override
    public boolean getTaskIdIsIncorrect() {
        return taskIdIsIncorrect;
    }

    @Override
    public String getTaskIdIsIncorrectMsg() {
        return resourceBundle.getString("message.incorrectValue.taskId");
    }

    @Override
    public String getSubmitEdit() {
        return resourceBundle.getString("submit.edit");
    }

    @Override
    public String[] getStatusValues() {
        return TaskPresenter.statusValues;
    }

    @Override
    public boolean getVersionIsIncorrect() {
        return versionIsIncorrect;
    }

    @Override
    public String getVersionIsIncorrectMsg() {
        return resourceBundle.getString("message.incorrectValue.version");
    }

    @Override
    public String getDeleteATaskQuestion(String taskId) {
        return injectTaskIdToString(resourceBundle.getString("message.wantToDeleteQuestion"), taskId);
    }

    @Override
    public boolean getHasMessage() {
        return hasMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getTextValueMainMenu() {
        return resourceBundle.getString("textValues.mainmenu");
    }

    @Override
    public String getTextValuePersonList() {
        return resourceBundle.getString("textValues.personlist");
    }

    @Override
    public String getTextValueTaskList() {
        return resourceBundle.getString("textValues.tasklist");
    }

    @Override
    public String getDefaultPageNumber() {
        return String.valueOf(taskViewsConfigI.getDefaultPageNumber());
    }

    public boolean getHasOptimisticLockException() {
        return hasOptimisticLockException;
    }

    public String getInitialsOfTheExecutor(TaskPresenter taskPresenter) {
        return listOfTasksWithInitials.get(taskPresenter);
    }
}
