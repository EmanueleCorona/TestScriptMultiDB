package testscript.script;

import testscript.utils.FileUtils;
import testscript.utils.TestScriptConst;
import testscript.utils.TestScriptConst.Error;

import java.io.*;

import static testscript.utils.TestScriptConst.*;
import static testscript.utils.TestScriptConst.FieldType.*;

public abstract class ScriptGenerator {
    protected String tableName;
    protected boolean isFieldName = true;

    protected String row;
    protected BufferedWriter writer;
    protected BufferedReader reader;
    public static final String READER_PATH = "src/main/java/testscript/files/fields.txt";
    public static final String WRITER_PATH = "src/main/java/testscript/files/script.sql";

    public ScriptGenerator() {
        super();
        initResources();
    }

    protected abstract void generateStatement();

    protected String getFormattedStatement(String statement) {
        return statement.replace(TABLE_NAME, tableName);
    }

    protected String getFormattedFieldName(String nameType) {
        return nameType.replaceAll("\\s", "").toUpperCase();
    }

    protected String getFormattedFieldType(String fieldType) {
        if (fieldType.contains(NUMBER)) {
            if (fieldType.contains(COMMA)) {
                return NUMBER + SPACE + fieldType.replaceAll("[^0-9,]", "").replace(COMMA, SPACE);
            } else {
                return NUMBER + SPACE + fieldType.replaceAll("[^0-9]", "") + " 0";
            }
        } else if (fieldType.contains(VARCHAR)) {
            fieldType = fieldType.replaceFirst("VARCHAR2", "");
            return VARCHAR + SPACE + fieldType.replaceAll("[^0-9]", "");

        } else if (fieldType.contains(CHAR)) {
            return CHAR + " 1";

        } else {
            return TIMESTAMP;
        }
    }

    protected void writeHeader() throws IOException {
        writer.append(TestScriptConst.GEOCALL_HEADER);
    }

    protected boolean isThereAnotherRow() throws IOException {
        return reader.ready();
    }

    protected boolean isStandardField(String fieldName) {
        return (fieldName.equals(TestScriptConst.DATASTAMP) || fieldName.equals(TestScriptConst.LOGIN) ||
                fieldName.equals(TestScriptConst.ACTION) || fieldName.contains(TestScriptConst.AAZI));
    }

    protected boolean isLogicStateTable() {
        boolean isLogicStateTable;

        // Controllo messo per evitare errori in caso di test del programma
        if (tableName.length() > 4) {
            isLogicStateTable = containChar(tableName.charAt(0), LOGIC_STATE_TABLE) || containChar(tableName.charAt(4), LOGIC_STATE_TABLE);
        } else {
            isLogicStateTable = containChar(tableName.charAt(0), LOGIC_STATE_TABLE);
        }

        return isLogicStateTable;
    }

    protected boolean isMasterTypeTable() {
        boolean isMasterTypeTable;

        // Controllo messo per evitare errori in caso di test del programma
        if (tableName.length() > 4) {
            isMasterTypeTable = containChar(tableName.charAt(0), MASTER_TYPE_TABLE) || containChar(tableName.charAt(4), MASTER_TYPE_TABLE);
        } else {
            isMasterTypeTable = containChar(tableName.charAt(0), MASTER_TYPE_TABLE);
        }

        return isMasterTypeTable;
    }

    protected boolean containChar(char value, char... characters) {
        boolean isPresent = false;

        for (char c : characters) {
            if (value == c) {
                isPresent = true;
                break;
            }
        }

        return isPresent;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    protected void initResources() {
        try {
            reader = new BufferedReader(new FileReader(READER_PATH));
            writer = new BufferedWriter(new FileWriter(WRITER_PATH));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(Error.ERR_IO_FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(Error.ERR_IO_FILE_WRITING);
        }
    }

    protected void closeResources() {
        FileUtils.closeBufferedReader(reader);
        FileUtils.closeBufferedWriter(writer);
    }
}
