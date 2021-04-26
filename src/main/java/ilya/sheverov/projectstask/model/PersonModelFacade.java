package ilya.sheverov.projectstask.model;

import ilya.sheverov.projectstask.entity.presenter.PersonPresenter;

import java.util.Map;

public interface PersonModelFacade extends HeaderFacade {

    String getCurrentPageNumber();

    String getNameOfTheColumnToBeSorted();

    boolean getMultiplePersonSelectionMode();


    boolean getHasMessage();

    String getMessage();

    String getColumnNameIdNames();

    String getColumnNameLastNames();

    String getColumnNameFirstNames();

    String getColumnNameMiddleNames();

    String getCreatePerson();

    String getSelectPersons();

    String getDeleteSelectedPersons();

    Map<PersonPresenter, Integer> getPersonsWithTasksCount();

    String getEditPerson();

    String getDeletePerson();

    int getFirstNumberOfPageInPageList();

    int getLastNumberOfPageInPageList();

    int getNumberOfAllLinksToPages();

    String getPreviousPage();

    String getNextPage();

    String getFirstPage();

    String getLastPage();

    String getDeleteAPersonQuestion(String personId);

    String getTitlePageWithAListOfPersons();

    String getTitlePageCreatePerson();

    String getTitlePageEditPerson();

    String getInputLabelLastName();

    boolean getIdIsIncorrect();

    boolean getLastNameIsIncorrect();

    String getMessageLastNameIsIncorrect();

    String getLastNamePlaceholder();

    String getLastNameTitle();

    String getFirstNameTitle();

    PersonPresenter getPersonPresenter();

    String getInputLabelFirstName();

    boolean getFirstNameIsIncorrect();

    String getMessageFirstNameIsIncorrect();

    String getFirstNamePlaceholder();

    String getInputLabelMiddleName();

    boolean getMiddleNameIsIncorrect();

    String getMessageMiddleNameIsIncorrect();

    String getMiddleNamePlaceholder();

    String getMiddleNameTitle();

    String getCreateSubmit();

    String getBackButton();

    String getInputLabelId(String personId);

    String getMessageIdIsIncorrect();

    boolean getVersionIsIncorrect();

    String getMessageVersionIsIncorrect();

    String getEditSubmit();

    String getLang();

    String getCancel();
}
