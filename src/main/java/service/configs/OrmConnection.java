package service.configs;

import java.sql.Connection;

public interface OrmConnection
{
    public Connection connect ();
}
