package data;


/**
 *Key Содежржит имя полей
 * <b>
 *Value содержит значение полей
 */
public class FieldMap {

    private String key;
    private Object value;

    public FieldMap(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
