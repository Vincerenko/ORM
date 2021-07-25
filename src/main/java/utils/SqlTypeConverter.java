package utils;

import java.util.Map;

/**
 * Реализация конвентора с Convert для каждой бд
 */
public interface SqlTypeConverter {
    Map<Class<?>, String> getConverter();
}
