package ilya.sheverov.projectstask.controller.task;

import ilya.sheverov.projectstask.config.TaskViewsConfig;
import ilya.sheverov.projectstask.entity.Task;
import ilya.sheverov.projectstask.entity.presenter.TaskPresenter;
import ilya.sheverov.projectstask.factory.TaskModelFactory;
import ilya.sheverov.projectstask.model.TaskModel;
import ilya.sheverov.projectstask.validator.TaskValidator;
import ilya.sheverov.projectstask.wrapper.IncorrectFieldsBindingResultWrapper;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskModelFactory taskModelFactory;

    @Autowired
    public TaskController(TaskModelFactory taskModelFactory) {
        this.taskModelFactory = taskModelFactory;
    }

    @GetMapping(value = "/all")
    public ModelAndView getTaskList(@RequestParam(name = "page", defaultValue = "1") int page,
                                    @RequestParam(name = "sortTasksList", defaultValue = "id") String order,
                                    @RequestParam(name = "multipleTaskSelectionMode", defaultValue = "false") boolean multipleTaskSelectionMode,
                                    Model model, Locale locale) {
        TaskModel taskModel = (TaskModel) model.getAttribute("model");
        if (taskModel == null) {
            taskModel = taskModelFactory.getNewTaskModel(TaskViewsConfig.class, locale);
        }
        taskModel.prepareAListOfTasks(page, order, multipleTaskSelectionMode);
        return new ModelAndView("task-list", "model", taskModel);
    }

    @GetMapping(value = "/create")
    public ModelAndView createTask(Model model, Locale locale) {
        TaskModel taskModel = (TaskModel) model.getAttribute("model");
        if (taskModel == null) {
            taskModel = taskModelFactory.getNewTaskModel(TaskViewsConfig.class, locale);
            taskModel.prepareToCreateATask();
        }
        return new ModelAndView("task-create", "model", taskModel);
    }

    @InitBinder({"task"})
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new TaskValidator());
    }

    @PostMapping(value = "/create")
    public ModelAndView createTask(@Valid Task task, BindingResult br, WebRequest webRequest, RedirectAttributes redirectAttributes, Locale locale) {
        TaskModel taskModel = taskModelFactory.getNewTaskModel(TaskViewsConfig.class, locale);
        if (br.hasErrors()) {
            IncorrectFieldsBindingResultWrapper incorrectFieldsBindingResultWrapper =
                new IncorrectFieldsBindingResultWrapper(br);
            Set<String> namesOfTheIncorrectFields = incorrectFieldsBindingResultWrapper.getNamesOfTheIncorrectFields();
            TaskPresenter taskPresenter = createATaskPresenterUsingTheWebRequestParameters(webRequest);
            taskModel.setTaskPresenterWithInvalidFieldsNames(taskPresenter, namesOfTheIncorrectFields);
            redirectAttributes.addFlashAttribute("model", taskModel);
            return new ModelAndView(new RedirectView("/task/create", true));
        }
        taskModel.createTask(task);
        if (taskModel.getHasOptimisticLockException()) {
            redirectAttributes.addFlashAttribute("model", taskModel);
            return new ModelAndView(new RedirectView("/task/all", true));
        }
        redirectAttributes.addFlashAttribute("model", taskModel);
        return new ModelAndView(new RedirectView("/task/all", true));
    }

    @GetMapping(value = "/edit")
    public ModelAndView editTask(@RequestParam(name = "id") Integer id,
                                 @RequestParam(name = "version") Timestamp version,
                                 Model model, Locale locale) {
        TaskModel taskModel = (TaskModel) model.getAttribute("model");
        if (taskModel == null) {
            taskModel = taskModelFactory.getNewTaskModel(TaskViewsConfig.class, locale);
            taskModel.findTheTask(id, version);
        }
        return new ModelAndView("task-edit", "model", taskModel);
    }

    @PostMapping(value = "/edit")
    public ModelAndView editTask(@Valid Task task, BindingResult br, WebRequest webRequest, RedirectAttributes redirectAttributes, Locale locale) {
        TaskModel taskModel = taskModelFactory.getNewTaskModel(TaskViewsConfig.class, locale);
        if (br.hasErrors()) {
            IncorrectFieldsBindingResultWrapper incorrectFieldsBindingResultWrapper =
                new IncorrectFieldsBindingResultWrapper(br);
            Set<String> namesOfTheIncorrectFields = incorrectFieldsBindingResultWrapper.getNamesOfTheIncorrectFields();
            TaskPresenter taskPresenter = createATaskPresenterUsingTheWebRequestParameters(webRequest);
            taskModel.setTaskPresenterWithInvalidFieldsNames(taskPresenter, namesOfTheIncorrectFields);
            redirectAttributes
                .addFlashAttribute("model", taskModel)
                .addAttribute("id", taskPresenter.getId())
                .addAttribute("version", taskPresenter.getVersion());
            return new ModelAndView(new RedirectView("/task/edit", true));
        }
        taskModel.editTask(task);
        if (taskModel.getHasOptimisticLockException()) {
            redirectAttributes
                .addFlashAttribute("model", taskModel);
            return new ModelAndView(new RedirectView("/task/all", true));
        }
        redirectAttributes
            .addFlashAttribute("model", taskModel);
        return new ModelAndView(new RedirectView("/task/all", true));
    }

    @GetMapping(value = "/delete")
    public ModelAndView deleteTask(@RequestParam(name = "id") Integer id, @RequestParam Timestamp version,
                                   RedirectAttributes redirectAttributes, Locale locale) {
        TaskModel taskModel = taskModelFactory.getNewTaskModel(TaskViewsConfig.class, locale);
        taskModel.deleteTask(id, version);
        if (taskModel.getHasOptimisticLockException()) {
            redirectAttributes.addFlashAttribute("model", taskModel);
            RedirectView redirectView = new RedirectView("/task/all", true);
            return new ModelAndView(redirectView);
        }
        redirectAttributes.addFlashAttribute("model", taskModel);
        return new ModelAndView("redirect:/task/all");
    }

    @PostMapping("/delete/selected")
    public ModelAndView deleteTasks(WebRequest webRequest,
                                    RedirectAttributes redirectAttributes,
                                    @RequestParam(name = "page", defaultValue = "1") int page,
                                    @RequestParam(name = "sortTasksList", defaultValue = "id") String order,
                                    Locale locale) {
        TaskModel taskModel = taskModelFactory.getNewTaskModel(TaskViewsConfig.class, locale);
        String[] selectedTasks = webRequest.getParameterMap().get("selectedTask");
        if (selectedTasks == null || selectedTasks.length == 0) {
            redirectAttributes
                .addAttribute("page", page)
                .addAttribute("sortTasksList", order)
                .addAttribute("multipleTaskSelectionMode", true);
            return new ModelAndView(new RedirectView("/task/all", true));
        }
        List<Task> taskList = new ArrayList<>();
        for (String selectedTask : selectedTasks) {
            String[] taskProperties = selectedTask.split(",");
            Task task = new Task();
            task.setId(Integer.valueOf(taskProperties[0]));
            task.setVersion(Timestamp.valueOf(taskProperties[1]));
            taskList.add(task);
        }
        taskModel.deleteTasks(taskList);
        if (taskModel.getHasOptimisticLockException()) {
            redirectAttributes
                .addAttribute("page", page)
                .addAttribute("sortTasksList", order)
                .addFlashAttribute("model", taskModel);
            return new ModelAndView(new RedirectView("/task/all", true));
        }
        redirectAttributes
            .addAttribute("page", page)
            .addAttribute("sortTasksList", order)
            .addFlashAttribute("model", taskModel);
        return new ModelAndView(new RedirectView("/task/all", true));
    }

    private TaskPresenter createATaskPresenterUsingTheWebRequestParameters(WebRequest webRequest) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(TaskPresenter.class);
        PropertyValues propertyValues = new MutablePropertyValues(webRequest.getParameterMap());
        beanWrapper.setPropertyValues(propertyValues, true);
        return (TaskPresenter) beanWrapper.getWrappedInstance();
    }
}
