package ilya.sheverov.projectstask.entity.converter;

import ilya.sheverov.projectstask.entity.StatusOfATask;
import org.springframework.core.convert.converter.Converter;

public class StringArrayToEnum implements Converter<String[], StatusOfATask> {

    @Override
    public StatusOfATask convert(String[] source) {
        return StatusOfATask.valueOf(source[0].trim());
    }
}
