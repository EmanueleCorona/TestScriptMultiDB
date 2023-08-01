package testscript.script;

import testscript.utils.TestScriptConst.Error;

import java.io.FileNotFoundException;
import java.io.IOException;

import static testscript.utils.TestScriptConst.SCRIPT_SEPARETOR;
import static testscript.utils.TestScriptConst.TABLE_NAME;

public class RenameColumnScript extends ScriptGenerator {
    private static final String OLD_COLUMN_NAME = "OLD_COLUMN_NAME";
    private static final String NEW_COLUMN_NAME = "NEW_COLUMN_NAME";
    private static final String RENAME_COLUMN = "ALTER TABLE " + TABLE_NAME + " RENAME COLUMN " + OLD_COLUMN_NAME + " TO " + NEW_COLUMN_NAME + SCRIPT_SEPARETOR;


    @Override
    public void generateStatement() {
        try {
            writeHeader();
            String sql;

            while ((row = reader.readLine()) != null) {
                if (!row.isEmpty()) {
                    String[] fieldNames = row.split(";");

                    sql = getFormattedStatement(RENAME_COLUMN);
                    sql = sql.replace(OLD_COLUMN_NAME, fieldNames[0]);
                    sql = sql.replace(NEW_COLUMN_NAME, fieldNames[1]);

                    writer.write(sql);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(Error.ERR_IO_FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(Error.ERR_IO_FILE_WRITING);
        } finally {
            closeResources();
        }
    }
}
