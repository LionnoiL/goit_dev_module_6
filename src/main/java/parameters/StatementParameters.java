package parameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatementParameters<T> {
    public StatementParameters() {
    }

    public StatementParameters(T value) {
        items.add(new Parametr<T>(value));
    }
    private final List<Parametr> items = new ArrayList<Parametr>();

    public void add(Parametr parametr) {
        items.add(parametr);
    }

    public void add(T value) {
        items.add(new Parametr<T>(value));
    }

    public List<Parametr> getItems() {
        return items;
    }

    public void fillStatement(PreparedStatement statement) throws SQLException {
        List<Parametr> params = getItems();
        int index = 1;
        for (Parametr p : params) {
            statement.setObject(index++, p.getValue());
        }
    }

}



