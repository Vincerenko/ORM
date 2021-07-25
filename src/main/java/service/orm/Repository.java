package service.orm;

import java.util.List;

/**
 * Реализация методов с Repo с каждой бд
 * @param <T>
 * @param <I>
 */
public interface Repository<T, I> {
    T save(T object);
    T getById(I id);
    void deleteById(I id);
    List<T> selectAll();//TODO переименовать под один формат и посмотреть какие методы мы пропустили

    //saveAll
    //deleteAll
}
