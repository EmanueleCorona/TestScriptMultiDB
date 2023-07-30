package testscript.script;

import testscript.utils.TestScriptConst;
import testscript.utils.TestScriptConst.Error;

import java.io.FileNotFoundException;
import java.io.IOException;

import static testscript.utils.TestScriptConst.FieldType.*;
import static testscript.utils.TestScriptConst.*;

public class CreateTableScript extends ScriptGenerator {
    public static final String CREATE_TABLE_HEADER = "CREATE TABLE " + TABLE_NAME + " ( " + NEW_LINE;
    public static final String CREATE_TABLE_FOOTER = "){TABLESPACE {TS_TABLE0}}" + SCRIPT_SEPARETOR;
    public static final String PRIMARY_KEY = "{KEY 10} {PRIMARY_KEY} {AUTO_INCREMENT} {USING_INDEX_TABLESPACE {TS_INDEX0}}," + NEW_LINE;
    public static final String SEQUENCE = "{CREATE_SEQUENCE} S" + TABLE_NAME + SCRIPT_SEPARETOR;
    private static final String DATASTAMP = TABULATION + "DATASTAMP {TIMESTAMP}," + NEW_LINE;
    private static final String LOGIN = TABULATION + "LOGIN {NUMBER 10 0}," + NEW_LINE;
    private static final String ACTION = TABULATION + "ACTION {NUMBER 10 0}" + NEW_LINE;
    private boolean isPrimaryKey = true;

    @Override
    public void generateStatement() {
        try {
            writeHeader();

            while ((row = reader.readLine()) != null) {
                if (!row.isEmpty()) {
                    if (isFieldName) {
                        // Fase impostazione nome campo
                        String fieldName = getFormattedFieldName(row);
                        if (isFieldNameValid(fieldName)) writeFieldName(fieldName);
                    } else {
                        // Fase impostazione tipo campo
                        String fieldType = getFormattedFieldType(row);
                        writeFieldType(fieldType);
                    }
                }
            }

            writeFooter();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(Error.ERR_FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(Error.ERR_FILE_WRITING);
        } finally {
            closeResources();
        }
    }

    @Override
    protected void writeHeader() throws IOException {
        super.writeHeader();
        writer.append(CREATE_TABLE_HEADER.replace(TABLE_NAME, tableName));
    }

    @Override
    protected String getFormattedFieldType(String fieldType) {
        if (fieldType.contains(NUMBER)) {
            if (fieldType.contains(COMMA)) {
                fieldType = "{" + NUMBER + SPACE + fieldType.replaceAll("[^0-9,]", "").replace(COMMA, SPACE).concat("}");
            } else {
                fieldType = "{" + NUMBER + SPACE + fieldType.replaceAll("[^0-9]", "") + " 0}";
            }
        } else if (fieldType.contains(VARCHAR)) {
            fieldType = fieldType.replaceFirst("VARCHAR2", "");
            return "{" + VARCHAR + SPACE + fieldType.replaceAll("[^0-9]", "") + "}";

        } else if (fieldType.contains(CHAR)) {
            return "{" + CHAR + " 1" + "}";

        } else {
            return "{" + TIMESTAMP + "}";
        }

        return fieldType;
    }

    protected boolean isFieldNameValid(String fieldName) throws IOException {
        boolean isFieldNameValid = true;

        // Controllo per skippare DATASTAMP, LOGIN e ACTION e inserirli alla fine dello script
        if (fieldName.equals(TestScriptConst.DATASTAMP) || fieldName.equals(TestScriptConst.LOGIN) || fieldName.equals(TestScriptConst.ACTION)) {
            while (reader.readLine().isEmpty()) ;
            isFieldNameValid = false;
        }

        return isFieldNameValid;
    }

    protected void writeFieldName(String fieldName) throws IOException {
        // Se il primo campo non contiene "ID" si presume che non ci sia una Primary Key
        if (!fieldName.endsWith(ID)) isPrimaryKey = false;
        writer.append(TABULATION).append(fieldName).append(SPACE);
        isFieldName = false;
    }

    protected void writeFieldType(String fieldType) throws IOException {
        isFieldName = true;

        if (isPrimaryKey) {
            writer.append(PRIMARY_KEY);
            isPrimaryKey = false;
            return;
        }

        if (isThereAnotherRow()) {
            writer.append(fieldType).append(COMMA).append(NEW_LINE);
        } else {
            if (isLogicStateTable()) {
                writer.append(fieldType).append(NEW_LINE);
            } else {
                // DATASTAMP, LOGIN e ACTION vengono automaticamente messi come ultimi campi
                writer.append(fieldType).append(COMMA).append(NEW_LINE);
            }
        }
    }

    protected boolean isLogicStateTable() {
        boolean isLogicStateTable;

        // Controllo messo per evitare errori in caso di test del programma
        if (tableName.length() > 4) {
            isLogicStateTable = tableName.charAt(1) == LOGIC_STATE_TABLE || tableName.charAt(4) == LOGIC_STATE_TABLE;
        } else {
            isLogicStateTable = tableName.charAt(1) == LOGIC_STATE_TABLE;
        }

        return isLogicStateTable;
    }

    protected void writeFooter() throws IOException {
        if (!isLogicStateTable()) {
            writer.append(DATASTAMP);
            writer.append(LOGIN);
            writer.append(ACTION);
            writer.append(CREATE_TABLE_FOOTER);
            writer.append(SEQUENCE.replace(TABLE_NAME, tableName));
        } else {
            writer.append(CREATE_TABLE_FOOTER);
        }
    }
}
