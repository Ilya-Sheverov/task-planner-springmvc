package ilya.sheverov.projectstask.entity.converter;

import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;

public class DataTimeConverter implements Converter<String, Timestamp> {

    @Override
    public Timestamp convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        if (source.matches("^((\\d{4}-\\d{2}-\\d{2})([ T])((\\d{2}:\\d{2})(:\\d{2}(.(\\d){1,9})?)?))|$")) {
            if (source.contains("T")) {
                source = source.replace('T', ' ');
            }
            int dateLength = source.length();
            if (dateLength == 16) {
                source = source + ":00.0";
            }
            if (dateLength == 19) {
                source = source + ".0";
            }
        }
        return Timestamp.valueOf(source);
    }
}
