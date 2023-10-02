package testscript.script;

import testscript.utils.TestScriptConst.Error;

import java.io.FileNotFoundException;
import java.io.IOException;

import static testscript.utils.TestScriptConst.*;

public class AddColumnScript extends ScriptGenerator {
    private static final String ADD_COLUMN = "{ALTER_ADD_COLUMN" + SPACE + TABLE_NAME + SPACE + FIELD_NAME + SPACE + FIELD_TYPE + "}" + SCRIPT_SEPARETOR;

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
                        sql = getFormattedStatement(ADD_COLUMN).replace(FIELD_NAME, getFormattedFieldName(row));
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
            throw new RuntimeException(Error.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(Error.FILE_WRITING);
        } finally {
            closeResources();
        }
    }
}
