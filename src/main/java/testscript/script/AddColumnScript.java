package testscript.script;

import java.io.FileNotFoundException;
import java.io.IOException;

import static testscript.utils.TestScriptConst.*;
import static testscript.utils.TestScriptConst.FieldType.*;

// HWFHERAWFM-4865
public class AddColumnScript extends ScriptGenerator {
    private static final String ADD_COLUMN = "{ALTER_ADD_COLUMN " + TABLE_NAME + " " + FIELD_NAME + " {" + FIELD_TYPE + "}}" + SCRIPT_SEPARETOR;

    @Override
    public void generateStatement() {
        try {
            initResources();

            String sql = ADD_COLUMN.replace(TABLE_NAME, tableName);

            String fieldName = FIELD_NAME;
            String fieldType = FIELD_TYPE;

            String row;
            boolean readyToWrite;

            while ((row = reader.readLine()) != null) {
                if (!row.isEmpty()) {

                    if (isFieldName) {
                        row = getFormattedNameType(row);
                        sql = sql.replace(fieldName, row);
                        fieldName = row;
                        readyToWrite = false;
                        isFieldName = false;
                    } else {
                        row = getFormattedFieldType(row);
                        sql = sql.replace(fieldType, row);
                        fieldType = row;
                        readyToWrite = true;
                        isFieldName = true;
                    }

                    if (readyToWrite) {
                        writer.append(sql);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("File di lettura non trovato");
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il tentativo di aggiunta delle colonne");
        } finally {
            closeResources();
        }
    }

    @Override
    protected String getFormattedFieldType(String fieldType) {
        if (fieldType.contains(NUMBER)) {
            if (fieldType.contains(",")) {
                return NUMBER + " " + fieldType.replaceAll("[^0-9,]", "").replace(",", " ");
            } else {
                return NUMBER + " " + fieldType.replaceAll("[^0-9]", "") + " 0";
            }
        } else if (fieldType.contains(VARCHAR)) {
            fieldType = fieldType.replaceFirst("VARCHAR2", "");
            return VARCHAR + " " + fieldType.replaceAll("[^0-9]", "");

        } else if (fieldType.contains(CHAR)) {
            return CHAR + " 1";

        } else {
            return TIMESTAMP;
        }
    }
}
