package ilya.sheverov.projectstask.controller;

import ilya.sheverov.projectstask.config.MainMenuConfig;
import ilya.sheverov.projectstask.factory.MainMenuModelFactory;
import ilya.sheverov.projectstask.model.MainMenuModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class MainMenuController {

    @Autowired
    private MainMenuModelFactory factory;

    @GetMapping("/mainmenu")
    public ModelAndView getMainMenu(@RequestParam(name = "pageNumberOfThePersonList", defaultValue = "1") int pageNumberOfThePersonList,
                                    @RequestParam(name = "nameOfTheColumnToBeSortedPersonList", defaultValue = "id") String nameOfTheColumnToBeSortedPersonList,
                                    @RequestParam(name = "multiplePersonSelectionMode", defaultValue = "false", required = false) Boolean multiplePersonSelectionMode,
                                    @RequestParam(name = "pageNumberOfTheTaskList", defaultValue = "1") int pageNumberOfTheTaskList,
                                    @RequestParam(name = "nameOfTheColumnToBeSortedTaskList", defaultValue = "id") String nameOfTheColumnToBeSortedTaskList,
                                    @RequestParam(name = "multipleTaskSelectionMode", defaultValue = "false") Boolean multipleTaskSelectionMode,
                                    Locale locale, Model model) {

        MainMenuModel mainMenuModel = factory.getMainMenuModel(MainMenuConfig.class, locale);
        mainMenuModel.prepareTheMainMenu(pageNumberOfThePersonList, nameOfTheColumnToBeSortedPersonList, multiplePersonSelectionMode,
            pageNumberOfTheTaskList, nameOfTheColumnToBeSortedTaskList, multipleTaskSelectionMode);
        return new ModelAndView("main-menu", "model", mainMenuModel);
    }
}
