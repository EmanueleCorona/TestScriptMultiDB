package utils;

public class TestScriptConst {

    public static final String GEOCALL_HEADER = "#!inc geocall/base.properties";
    public static final String CREATE_TABLE = "";
    public static final String ADD_COLUMN = "{ALTER_ADD_COLUMN nome_tabella nome_campo {tipo_dato}}\n/";


    public static class FieldType {
        public static final String NUMBER = "NUMBER";
        public static final String VARCHAR = "VARCHAR";
        public static final String CHAR = "CHAR";
        public static final String TIMESTAMP = "TIMESTAMP";
    }
}
