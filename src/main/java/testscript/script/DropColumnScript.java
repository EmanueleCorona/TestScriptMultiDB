package testscript.script;

import testscript.utils.TestScriptConst;

import java.io.FileNotFoundException;
import java.io.IOException;

import static testscript.utils.TestScriptConst.*;

public class DropColumnScript extends ScriptGenerator {
    private static final String DROP_COLUMN = "ALTER TABLE " + TABLE_NAME + " DROP COLUMN " + FIELD_NAME + SCRIPT_SEPARETOR;


    @Override
    public void generateStatement() {
        try {
            writeHeader();

            while ((row = reader.readLine()) != null) {
                if (!row.isEmpty()) {
                    writer.append(getFormattedStatement(DROP_COLUMN).replace(FIELD_NAME, getFormattedFieldName(row)));
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(TestScriptConst.Error.ERR_FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(TestScriptConst.Error.ERR_FILE_WRITING);
        } finally {
            closeResources();
        }
    }
}
