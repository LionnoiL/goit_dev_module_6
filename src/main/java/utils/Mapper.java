package utils;

import java.sql.ResultSet;

public interface Mapper<T> {
    T map(ResultSet rs);
}
