package ilya.sheverov.projectstask.entity;

import java.util.Map;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.DataBinder;

public enum StatusOfATask {
    NOT_STARTED, STARTED, PAUSED, COMPLETED;
}
