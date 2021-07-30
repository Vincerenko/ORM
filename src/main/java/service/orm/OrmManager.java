package service.orm;

import annotations.Id;
import service.configs.OrmConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrmManager<T, I> extends AbstractOrmManager<T, I>
{
    private static final java.util.logging.Logger LOGGER =
            java.util.logging.Logger.getLogger(CheckDBTable.class.getName());

    public OrmManager(Class<T> clazz, Class<I> idClazz, OrmConnection ormConnection) {
        super(clazz, idClazz, ormConnection);
        new CheckDBTable(ormConnection).check();//FIXME
    }

    @Override
    public T save(T object) {
        Object idValue = annotationFieldService.findFieldValueByAnnotation(object, Id.class);
        if (idValue == null) {
            LOGGER.info("Object was created");
            return create(object);

        }
        else {
            LOGGER.info("Object was updated");
            return update(idValue, object);
        }
    }

    @Override
    public void deleteById(I id) {
        String sql = "DELETE FROM " + tableName + " WHERE id = '" + id + "'";
        try (Statement statement = ormConnection.connect().createStatement()) {
            statement.executeUpdate(sql);
            LOGGER.info("Object was deleted");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public T getById(I id) {
        Connection connect = ormConnection.connect();
        T object = null;
        String sql = "SELECT * FROM " + tableName + " WHERE id = " + "'" + id + "'";
        try {
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                List<String> fieldKeys = annotationFieldService.findColumnKeys(clazz);
                object = createObject(resultSet, fieldKeys);
            }

        } catch (SQLException | InstantiationException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        LOGGER.info("Object was deleted by Id");
        return object;
    }

    @Override
    public List<T> selectAll() {
        List<String> fieldKeys = annotationFieldService.findColumnKeys(clazz);
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try {
            Statement statement = ormConnection.connect().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                try {
                    T object = createObject(resultSet, fieldKeys);
                    list.add(object);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void clear(String table) throws SQLException {
        String sql = "TRUNCATE TABLE " + table + " CASCADE";
        Statement statement = ormConnection.connect().createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    public List<T> saveAll(List<T> list){
        List<T> tList= new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            T save = save(list.get(i));
            tList.add(save);
        }
        LOGGER.info("DataBase is clear");
        return tList;
    }

}
