package dao;

import java.sql.Connection;

public abstract class AbstractDao {
    protected Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }
}
