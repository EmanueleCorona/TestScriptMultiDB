package testscript.script;

import testscript.utils.FileUtils;
import testscript.utils.TestScriptConst;

import java.io.*;

import static testscript.utils.TestScriptConst.COMMA;
import static testscript.utils.TestScriptConst.FieldType.*;
import static testscript.utils.TestScriptConst.SPACE;

public abstract class ScriptGenerator {
    protected String tableName;
    protected boolean isFieldName = true;

    protected String row;
    protected BufferedWriter writer;
    protected BufferedReader reader;
    public static final String READER_PATH = "src/main/files/fields.txt";
    public static final String WRITER_PATH = "src/main/files/script.sql";

    public ScriptGenerator() {
        super();
        initResources();
    }

    protected abstract void generateStatement();

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

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    protected void initResources() {
        try {
            reader = new BufferedReader(new FileReader(READER_PATH));
            writer = new BufferedWriter(new FileWriter(WRITER_PATH));

        } catch (FileNotFoundException e) {
            throw new RuntimeException("File non trovato");
        } catch (IOException e) {
            throw new RuntimeException("Errore durante l'operazione di scrittura su file");
        }
    }

    protected void closeResources() {
        FileUtils.closeBufferedReader(reader);
        FileUtils.closeBufferedWriter(writer);
    }
}
