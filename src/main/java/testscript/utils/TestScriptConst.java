package testscript.utils;

public class TestScriptConst {

    public static final String GEOCALL_HEADER = "#!inc geocall/base.properties";
    public static final String SCRIPT_SEPARETOR = "\n/";
    public static final String CREATE_TABLE = "";
    public static final String TABLE_NAME = "nome_tabella";
    public static final String FIELD_NAME = "nome_campo";
    public static final String FIELD_TYPE = "tipo_dato";
    public static final String READER_PATH = "src/main/files/fields.txt";
    public static final String WRITER_PATH = "src/main/files/script.sql";


    public static class FieldType {
        public static final String NUMBER = "NUMBER";
        public static final String VARCHAR = "VARCHAR";
        public static final String CHAR = "CHAR";
        public static final String TIMESTAMP = "TIMESTAMP";
    }
}
