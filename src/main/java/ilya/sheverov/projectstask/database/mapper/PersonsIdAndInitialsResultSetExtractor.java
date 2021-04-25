package ilya.sheverov.projectstask.database.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PersonsIdAndInitialsResultSetExtractor implements ResultSetExtractor<Map<String, String>> {
    @Override
    public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, String> personsIdAndInitials = new LinkedHashMap<>();
        while (rs.next()) {
            String personId = rs.getString("id");
            String personsInitial = rs.getString("initial");
            personsIdAndInitials.put(personId, personsInitial);
        }
        return personsIdAndInitials;
    }
}
