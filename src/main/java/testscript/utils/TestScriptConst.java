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
    public static final char[] MASTER_TYPE_TABLE = {'A', 'T'};
    public static final String AAZI = "_AAZI";
    public static final String DATASTAMP = "DATASTAMP";
    public static final String LOGIN = "LOGIN";
    public static final String ACTION = "ACTION";


    public static class Menu {
        public static final String EXIT = "0 ESCI";
        public static final String CREATE_TABLE = "1 CREAZIONE TABELLA";
        public static final String ADD_COLUMN = "2 AGGIUNTA COLONNE";
        public static final String ALTER_TYPE = "3 ALTERAZIONE TIPO CAMPO";
        public static final String DROP_COLUMN = "4 ELIMINAZIONE CAMPI";
        public static final String RENAME_COLUMN = "5 RIDENOMINAZIONE CAMPI";
        public static final String RENAME_TABLE = "6 RIDENOMINAZIONE TABELLA";
    }

    public static class FieldType {
        public static final String NUMBER = "NUMBER";
        public static final String VARCHAR = "VARCHAR";
        public static final String CHAR = "CHAR";
        public static final String TIMESTAMP = "TIMESTAMP";
        public static final String WRONG_DATATYPE = "WRONG_DATATYPE";
    }

    public static class Error {
        public static final String ERR_OPTION_NOT_VALID = "Opzione non valida, scegliere un'opzione: ";
        public static final String ERR_IO_FILE_NOT_FOUND = "Il file non è stato trovato all'interno del percorso indicato";
        public static final String ERR_IO_FILE_WRITING = "Errore durante il tentativo di scrittura su file";
        public static final String ERR_IO_CLOSE_READER = "Errore durante la chiusura del file di lettura";
        public static final String ERR_IO_CLOSE_WRITER = "Errore durante la chiusura del file di scrittura";
    }
}
