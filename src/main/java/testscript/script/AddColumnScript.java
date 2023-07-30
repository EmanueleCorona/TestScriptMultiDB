package testscript.script;

import testscript.utils.TestScriptConst.Error;

import java.io.FileNotFoundException;
import java.io.IOException;

import static testscript.utils.TestScriptConst.*;

public class AddColumnScript extends ScriptGenerator {
    private static final String ADD_COLUMN = "{ALTER_ADD_COLUMN " + TABLE_NAME + " " + FIELD_NAME + " {" + FIELD_TYPE + "}}" + SCRIPT_SEPARETOR;

    @Override
    public void generateStatement() {
        try {
            writeHeader();

            String sql = "";
            boolean readyToWrite = false;

            while ((row = reader.readLine()) != null) {
                if (!row.isEmpty()) {
                    if (isFieldName) {
                        // Fase impostazione nome campo
                        sql = getFormattedStatement().replace(FIELD_NAME, getFormattedFieldName(row));
                        isFieldName = false;
                    } else {
                        // Fase impostazione tipo campo
                        sql = sql.replace(FIELD_TYPE, getFormattedFieldType(row));
                        isFieldName = true;
                        readyToWrite = true;
                    }

                    if (readyToWrite) {
                        writer.append(sql);
                        readyToWrite = false;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(Error.ERR_FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(Error.ERR_FILE_WRITING);
        } finally {
            closeResources();
        }
    }

    protected String getFormattedStatement() {
        return ADD_COLUMN.replace(TABLE_NAME, tableName);
    }
}
