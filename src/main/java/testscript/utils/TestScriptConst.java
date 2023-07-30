package testscript.utils;

public class TestScriptConst {
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String NEW_LINE = "\n";
    public static final String TABULATION = "\t";
    public static final String GEOCALL_HEADER = "#!inc geocall/base.properties" + NEW_LINE;
    public static final String SCRIPT_SEPARETOR = NEW_LINE + "/" + NEW_LINE;
    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String FIELD_NAME = "FIELD_NAME";
    public static final String FIELD_TYPE = "FIELD_TYPE";
    public static final char LOGIC_STATE_TABLE = 'L';
    public static final String ID = "ID";
    public static final String DATASTAMP = "DATASTAMP";
    public static final String LOGIN = "LOGIN";
    public static final String ACTION = "ACTION";


    public static class FieldType {
        public static final String NUMBER = "NUMBER";
        public static final String VARCHAR = "VARCHAR";
        public static final String CHAR = "CHAR";
        public static final String TIMESTAMP = "TIMESTAMP";
    }

    public static class Error {
        public static final String ERR_FILE_NOT_FOUND = "Il file non è stato trovato all'interno del percorso indicato";
        public static final String ERR_FILE_WRITING = "Errore durante il tentativo di scrittura su file";
    }
}
