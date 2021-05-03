package ilya.sheverov.projectstask.entity;

import java.util.Locale;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class LocalStatusValues {

    private final Map<Enum<StatusOfATask>, String> ruStatuses = Map
        .of(StatusOfATask.NOT_STARTED, "Не начата",
            StatusOfATask.STARTED, "Начата",
            StatusOfATask.PAUSED, "Отложена",
            StatusOfATask.COMPLETED, "Завершена"
        );

    private final Map<Enum<StatusOfATask>, String> enStatuses = Map
        .of(StatusOfATask.NOT_STARTED, "Not started",
            StatusOfATask.STARTED, "Started",
            StatusOfATask.PAUSED, "Paused",
            StatusOfATask.COMPLETED, "Completed"
        );


    public Map<Enum<StatusOfATask>, String> getListOfStatuses(Locale locale) {
        if (locale.getLanguage().equals("ru")) {
            return ruStatuses;
        } else {
            return enStatuses;
        }
    }
}
