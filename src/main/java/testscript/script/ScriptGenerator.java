package testscript.script;

import testscript.utils.TestScriptConst;

import java.io.*;

public abstract class ScriptGenerator {
    protected boolean isFieldName = true;
    protected BufferedWriter writer;
    protected BufferedReader reader;
    protected String tableName;
    public static final String READER_PATH = "src/main/files/fields.txt";
    public static final String WRITER_PATH = "src/main/files/script.sql";


    protected abstract void generateStatement();

    protected abstract String getFormattedFieldType(String fieldType);

    protected String getFormattedNameType(String nameType) {
        return nameType.replaceAll("\\s", "").toUpperCase();
    }

    protected void initResources() throws IOException {
        reader = new BufferedReader(new FileReader(READER_PATH));
        writer = new BufferedWriter(new FileWriter(WRITER_PATH));

        writer.append(TestScriptConst.GEOCALL_HEADER);
    }

    protected void closeResources() {
        closeBufferedReader();
        closeBufferedWriter();
    }

    protected void closeBufferedWriter() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore chiusura file di scrittura");
        }
    }

    protected void closeBufferedReader() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore chiusura file di lettura");
        }
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
