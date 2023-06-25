package testscript.script;

import testscript.utils.TestScriptConst;
import testscript.utils.TestScriptConst.FieldType;

import java.io.IOException;

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
            initResources();

            String row;

            while ((row = reader.readLine()) != null) {
                if (!row.isEmpty()) {
                    if (isFieldName) {
                        String fieldName = getFormattedNameType(row);
                        if (checkFieldName(fieldName)) break;
                        writeFieldName(fieldName);
                    } else {
                        String fieldType = getFormattedFieldType(row);
                        writeFieldType(fieldType);
                    }
                }
            }

            writeFooter();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources();
        }
    }

    @Override
    protected void initResources() throws IOException {
        super.initResources();
        writer.append(CREATE_TABLE_HEADER.replace(TABLE_NAME, tableName));
    }

    @Override
    protected String getFormattedFieldType(String fieldType) {
        if (fieldType.contains(FieldType.NUMBER)) {
            if (fieldType.contains(",")) {
                fieldType = "{NUMBER " + fieldType.replaceAll("[^0-9,]", "").replace(",", " ").concat("}");
            } else {
                fieldType = "{NUMBER " + fieldType.replaceAll("[^0-9]", "") + " 0}";
            }
        }

        if (fieldType.contains(FieldType.VARCHAR)) {
            fieldType = fieldType.replaceFirst("VARCHAR2", "");
            return "{" + FieldType.VARCHAR + " " + fieldType.replaceAll("[^0-9]", "") + "}";
        }

        if (fieldType.contains(FieldType.CHAR)) {
            return "{" + FieldType.CHAR + " 1" + "}";
        }

        if (fieldType.contains(FieldType.TIMESTAMP)) {
            return "{" + FieldType.TIMESTAMP + "}";
        }

        return fieldType;
    }

    protected boolean checkFieldName(String row) {
        return row.equals(TestScriptConst.DATASTAMP) || row.equals(TestScriptConst.LOGIN) || row.equals(TestScriptConst.ACTION);
    }

    protected void writeFieldName(String fieldName) throws IOException {
        if (!fieldName.endsWith(TestScriptConst.ID)) isPrimaryKey = false;
        writer.append(TABULATION).append(fieldName).append(" ");
        isFieldName = false;
    }

    protected void writeFieldType(String fieldType) throws IOException {
        isFieldName = true;

        if (isPrimaryKey) {
            writer.append(PRIMARY_KEY);
            isPrimaryKey = false;
            return;
        }

        if (reader.ready()) {
            writer.append(fieldType).append(",").append(NEW_LINE);
        } else {
            writer.append(fieldType).append(NEW_LINE);
        }
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

    protected boolean isLogicStateTable() {
        return (tableName.charAt(1) == TestScriptConst.LOGIC_STATE_TABLE || tableName.charAt(4) == TestScriptConst.LOGIC_STATE_TABLE);
    }
}
