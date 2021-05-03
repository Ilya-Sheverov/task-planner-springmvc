package ilya.sheverov.projectstask.model;

import ilya.sheverov.projectstask.config.PersonViewsConfigI;
import ilya.sheverov.projectstask.entity.Person;
import ilya.sheverov.projectstask.entity.converter.PersonToPersonPresenterConverter;
import ilya.sheverov.projectstask.entity.presenter.PersonPresenter;
import ilya.sheverov.projectstask.exception.OptimisticLockException;
import ilya.sheverov.projectstask.model.pagemanager.PageListConfiguration;
import ilya.sheverov.projectstask.model.pagemanager.PageListService;
import ilya.sheverov.projectstask.service.PersonServiceI;

import java.sql.Timestamp;
import java.util.*;

public class PersonModel implements PersonModelFacade {

    PersonPresenter personPresenter;
    int maxNumberPersonsPerPage;
    int numberOfLinksPerPageToPages;
    int currentPageNumber;
    String[] validColumnNames;
    String nameOfTheColumnToBeSorted;
    boolean multiplePersonSelectionMode;
    private PersonServiceI personService;
    private PageListService pageListService;
    private PageListConfiguration pageListConfiguration;
    private ResourceBundle resourceBundle;
    private PersonViewsConfigI personViewsConfigI;
    private PersonToPersonPresenterConverter converter;
    private boolean hasFatalException;
    private boolean hasOptimisticLockException;
    private boolean hasValidationException;
    private boolean hasMessage;
    private String message;
    private boolean idIsIncorrect;
    private boolean lastNameIsIncorrect;
    private boolean firstNameIsIncorrect;
    private boolean middleNameIsIncorrect;
    private boolean versionIsIncorrect;
    private Map<PersonPresenter, Integer> personPresenters;

    public PersonModel(PersonServiceI personService, PageListService pageListService, ResourceBundle resourceBundle,
                       PersonViewsConfigI personViewsConfigI, PersonToPersonPresenterConverter converter) {
        this.personService = personService;
        this.pageListService = pageListService;
        this.resourceBundle = resourceBundle;
        this.personViewsConfigI = personViewsConfigI;
        this.converter = converter;
        maxNumberPersonsPerPage = personViewsConfigI.getMaxNumberPersonsPerPage();
        numberOfLinksPerPageToPages = personViewsConfigI.numberOfAllLinksToPagesInPageNavigationOfTheListOfPersons();
        currentPageNumber = personViewsConfigI.getDefaultPageNumberOfTheListOfPersons();
        validColumnNames = personViewsConfigI.getValidColumnNamesOfTableOfTheListOfPersons();
        nameOfTheColumnToBeSorted = validColumnNames[0];
    }


    public void prepareAListOfPersons(int pageNumber, String nameOfTheColumnToBeSorted,
                                      Boolean selectPersons) {
        if (pageNumber > currentPageNumber) {
            currentPageNumber = pageNumber;
        }
        boolean isValid = Arrays.stream(validColumnNames)
            .anyMatch(s -> {
                return s.equals(nameOfTheColumnToBeSorted);
            });
        if (isValid) {
            this.nameOfTheColumnToBeSorted = nameOfTheColumnToBeSorted;
        }
        if (selectPersons != null) {
            multiplePersonSelectionMode = selectPersons;
        }
        int numberOfAllPersons = personService.getTheNumberOfPersons();
        pageListConfiguration = pageListService.getPageListConfiguration(numberOfAllPersons, maxNumberPersonsPerPage,
            numberOfLinksPerPageToPages, pageNumber);
        int firstPersonsNumber = (pageNumber - 1) * maxNumberPersonsPerPage;

        Map<Person, Integer> personsWithTasksCount = personService.findPartOfTheListOfPersons(this.nameOfTheColumnToBeSorted,
            firstPersonsNumber, maxNumberPersonsPerPage);
        personPresenters = converter.convert(personsWithTasksCount);
    }

    public void createPerson(Person newPerson) {
        try {
            personService.createPerson(newPerson);
            hasMessage = true;
            message = resourceBundle.getString("message.wasCreated");
        } catch (OptimisticLockException e) {
            hasOptimisticLockException = true;
            hasMessage = true;
            message = resourceBundle.getString("message.optimisticLocking.creating");
        }
    }

    public void findThePerson(Integer personId, Timestamp personVersion) {
        Person person = personService.findPersonByIdAndVersion(personId, personVersion);
        if (person == null) {
            hasOptimisticLockException = true;
            hasMessage = true;
            message = resourceBundle.getString("message.optimisticLocking.personNotFound");
        } else {
            personPresenter = converter.convert(person);
        }
    }

    public void editPerson(Person editedPerson) {
        try {
            personService.editPerson(editedPerson);
            hasMessage = true;
            message = injectPersonIdToString(resourceBundle.getString("message.wasEdited"),
                String.valueOf(editedPerson.getId()));
        } catch (OptimisticLockException e) {
            hasOptimisticLockException = true;
            hasMessage = true;
            message = resourceBundle.getString("message.optimisticLocking.edit");
        }
    }

    public void deletePerson(Integer personId, Timestamp personVersion) {
        try {
            personService.deletePerson(personId, personVersion);
            hasMessage = true;
            message = injectPersonIdToString(resourceBundle.getString("message.wasDeleted"),
                String.valueOf(personId));
        } catch (OptimisticLockException e) {
            hasOptimisticLockException = true;
            hasMessage = true;
            message = resourceBundle.getString("message.optimisticLocking.delete");
        }
    }

    public void deletePersons(List<Person> listPersons) {
        try {
            personService.deletePersons(listPersons);
            hasMessage = true;
            message = injectListPersonIdsToString(resourceBundle.getString("message.groupWasDeleted"),
                listPersons);
        } catch (OptimisticLockException e) {
            hasOptimisticLockException = true;
            hasMessage = true;
            message = resourceBundle.getString("message.optimisticLocking.groupDelete");
            message = injectListPersonIdsToString(message, listPersons);
        }
    }

    public void setPersonPresenterWithInvalidFieldsNames(PersonPresenter personPresenter, Set<String> invalidFieldsNames) {
        this.personPresenter = personPresenter;
        markFieldsAsIncorrect(invalidFieldsNames);
    }

    private void markFieldsAsIncorrect(Set<String> invalidFieldsNames) {
        if (invalidFieldsNames != null && !invalidFieldsNames.isEmpty()) {
            hasValidationException = true;
            for (String invalidFieldsName : invalidFieldsNames) {
                if (invalidFieldsNames.contains("id")) {
                    idIsIncorrect = true;
                }
                if (invalidFieldsNames.contains("lastName")) {
                    lastNameIsIncorrect = true;
                }
                if (invalidFieldsNames.contains("firstName")) {
                    firstNameIsIncorrect = true;
                }
                if (invalidFieldsNames.contains("middleName")) {
                    middleNameIsIncorrect = true;
                }
                if (invalidFieldsNames.contains("version")) {
                    versionIsIncorrect = true;
                }
            }
        }
    }

    private String injectPersonIdToString(String s, String personId) {
        if (s.contains("${personId}")) {
            s = s.replaceAll("\\$\\{personId\\}", personId);
        }
        return s;
    }

    private String injectListPersonIdsToString(String s, List<Person> personIds) {
        if (personIds.size() > 0) {
            if (s.contains("${listPersonIds}")) {
                StringBuilder sb = new StringBuilder();
                for (Person person : personIds) {
                    sb.append(person.getId()).append(", ");
                }
                String ids = sb.toString();
                ids = ids.substring(0, ids.length() - 2);
                s = s.replaceAll("\\$\\{listPersonIds\\}", ids);
            }
        }
        return s;
    }

    @Override
    public String getLang() {
        return resourceBundle.getLocale().getLanguage();
    }

    /**
     * Возвращает номер текущей страницы.
     *
     * @return номер страницы в формате строки.
     */
    @Override
    public String getCurrentPageNumber() {
        return String.valueOf(currentPageNumber);
    }

    /**
     * Возвращает названия столбца по которому была отсортирована таблица персон.
     *
     * @return названия столбца.
     */
    @Override
    public String getNameOfTheColumnToBeSorted() {
        return nameOfTheColumnToBeSorted;
    }

    /**
     * Возвращает значение, по которому определяется следует ли отображать режим выбора множества персон или нет.
     *
     * @return true, если режим выбора множества персон активирован, если нет, то false.
     */
    @Override
    public boolean getMultiplePersonSelectionMode() {
        return multiplePersonSelectionMode;
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
    public String getTitlePageWithAListOfPersons() {
        return resourceBundle.getString("title.listPersons");
    }

    @Override
    public String getTitlePageCreatePerson() {
        return resourceBundle.getString("title.createPerson");
    }

    @Override
    public String getTitlePageEditPerson() {
        return resourceBundle.getString("title.editPerson");
    }

    @Override
    public String getColumnNameIdNames() {
        return resourceBundle.getString("column.label.id");
    }

    @Override
    public String getColumnNameLastNames() {
        return resourceBundle.getString("column.label.lastName");
    }

    @Override
    public String getColumnNameFirstNames() {
        return resourceBundle.getString("column.label.firstName");
    }

    @Override
    public String getColumnNameMiddleNames() {
        return resourceBundle.getString("column.label.middleName");
    }

    @Override
    public String getCreatePerson() {
        return resourceBundle.getString("text.createPerson");
    }

    @Override
    public String getSelectPersons() {
        return resourceBundle.getString("text.selectPersons");
    }

    @Override
    public String getDeleteSelectedPersons() {
        return resourceBundle.getString("text.deleteSelectedPersons");
    }

    @Override
    public String getCancel() {
        return resourceBundle.getString("text.cancel");
    }

    @Override
    public String getEditPerson() {
        return resourceBundle.getString("text.editPerson");
    }

    @Override
    public String getDeletePerson() {
        return resourceBundle.getString("text.deletePerson");
    }

    @Override
    public String getPreviousPage() {
        return resourceBundle.getString("text.previousPage");
    }

    @Override
    public String getNextPage() {
        return resourceBundle.getString("text.nextPage");
    }

    @Override
    public String getFirstPage() {
        return resourceBundle.getString("text.firstPage");
    }

    @Override
    public String getLastPage() {
        return resourceBundle.getString("text.lastPage");
    }

    @Override
    public Map<PersonPresenter, Integer> getPersonsWithTasksCount() {
        return personPresenters;
    }

    @Override
    public int getFirstNumberOfPageInPageList() {
        return pageListConfiguration.getFirstNumberOfPageInPageList();
    }

    @Override
    public int getLastNumberOfPageInPageList() {
        return pageListConfiguration.getLastNumberOfPageInPageList();
    }

    @Override
    public int getNumberOfAllLinksToPages() {
        return pageListConfiguration.getNumberOfAllLinksToPages();
    }

    @Override
    public String getInputLabelId(String personId) {
        String inputLabelId = resourceBundle.getString("form.label.id");
        inputLabelId = injectPersonIdToString(inputLabelId, personId);
        return inputLabelId;
    }

    @Override
    public String getMessageIdIsIncorrect() {
        return resourceBundle.getString("message.idIsIncorrect");
    }

    @Override
    public boolean getVersionIsIncorrect() {
        return versionIsIncorrect;
    }

    @Override
    public String getMessageVersionIsIncorrect() {
        return resourceBundle.getString("message.versionIsIncorrect");
    }

    @Override
    public String getEditSubmit() {
        return resourceBundle.getString("submit.editPerson");
    }

    @Override
    public String getDeleteAPersonQuestion(String personId) {
        String deleteAPersonQuestion = resourceBundle.getString("message.deletePerson");
        deleteAPersonQuestion = injectPersonIdToString(deleteAPersonQuestion, personId);
        return deleteAPersonQuestion;
    }

    @Override
    public PersonPresenter getPersonPresenter() {
        return personPresenter;
    }

    @Override
    public String getInputLabelLastName() {
        return resourceBundle.getString("form.label.lastName");
    }

    @Override
    public boolean getIdIsIncorrect() {
        return idIsIncorrect;
    }

    @Override
    public String getInputLabelFirstName() {
        return resourceBundle.getString("form.input.placeholder.firstName");
    }

    @Override
    public String getInputLabelMiddleName() {
        return resourceBundle.getString("form.input.placeholder.middleName");
    }

    @Override
    public boolean getLastNameIsIncorrect() {
        return lastNameIsIncorrect;
    }

    @Override
    public boolean getFirstNameIsIncorrect() {
        return firstNameIsIncorrect;
    }

    @Override
    public boolean getMiddleNameIsIncorrect() {
        return middleNameIsIncorrect;
    }

    @Override
    public String getMessageLastNameIsIncorrect() {
        return resourceBundle.getString("message.lastNameIsIncorrect");
    }

    @Override
    public String getMessageFirstNameIsIncorrect() {
        return resourceBundle.getString("message.firstNameIsIncorrect");
    }

    @Override
    public String getMessageMiddleNameIsIncorrect() {
        return resourceBundle.getString("message.middleNameIsIncorrect");
    }

    @Override
    public String getLastNamePlaceholder() {
        return resourceBundle.getString("form.input.placeholder.lastName");
    }

    @Override
    public String getFirstNamePlaceholder() {
        return resourceBundle.getString("form.input.placeholder.firstName");
    }

    @Override
    public String getMiddleNamePlaceholder() {
        return resourceBundle.getString("form.input.placeholder.middleName");
    }

    @Override
    public String getLastNameTitle() {
        return resourceBundle.getString("form.input.title.lastName");
    }

    @Override
    public String getFirstNameTitle() {
        return resourceBundle.getString("form.input.title.firstName");
    }

    @Override
    public String getMiddleNameTitle() {
        return resourceBundle.getString("form.input.title.middleName");
    }

    @Override
    public String getCreateSubmit() {
        return resourceBundle.getString("submit.createPerson");
    }

    @Override
    public String getBackButton() {
        return resourceBundle.getString("button.back");
    }

    public boolean getHasOptimisticLockException() {
        return hasOptimisticLockException;
    }

    @Override
    public String getTextValueMainMenu() {
        return resourceBundle.getString("text.mainmenu");
    }

    @Override
    public String getTextValuePersonList() {
        return resourceBundle.getString("text.listPersons");
    }

    @Override
    public String getTextValueTaskList() {
        return resourceBundle.getString("text.listTasks");
    }

    public Set<PersonPresenter> getListOfPersonPresenters() {
        return personPresenters.keySet();
    }

    public String getPersonsTasksCount(PersonPresenter person) {
        return String.valueOf(personPresenters.get(person));
    }
}

