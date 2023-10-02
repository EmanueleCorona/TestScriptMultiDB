package testscript.script;

import testscript.utils.TestScriptConst.Error;

import java.io.FileNotFoundException;
import java.io.IOException;

import static testscript.utils.TestScriptConst.*;

public class AlterTypeScript extends ScriptGenerator {
    private static final String ALTER_TYPE = "{ALTER_TYPE" + SPACE + TABLE_NAME + SPACE + FIELD_NAME + SPACE + FIELD_TYPE + "}" + SCRIPT_SEPARETOR;

    @Override
    public void generateStatement() {
        try {
            writeHeader();

            String sql = "";
            boolean readyToWrite = false;

            while ((row = reader.readLine()) != null) {
                if (!row.isEmpty()) {

                    if (isFieldName) {
                        sql = getFormattedStatement(ALTER_TYPE).replace(FIELD_NAME, getFormattedFieldName(row));
                        isFieldName = false;
                    } else {
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
