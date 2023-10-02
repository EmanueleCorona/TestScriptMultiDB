package testscript.script;

import testscript.utils.TestScriptConst.Error;

import java.io.FileNotFoundException;
import java.io.IOException;

import static testscript.utils.TestScriptConst.*;

public class RenameColumnScript extends ScriptGenerator {
    private static final String OLD_COLUMN_NAME = "OLD_COLUMN_NAME";
    private static final String NEW_COLUMN_NAME = "NEW_COLUMN_NAME";
    private static final String RENAME_COLUMN = "ALTER TABLE" + SPACE + TABLE_NAME + SPACE + "RENAME COLUMN" + SPACE + OLD_COLUMN_NAME + SPACE + "TO" + SPACE + NEW_COLUMN_NAME + SCRIPT_SEPARETOR;


    @Override
    public void generateStatement() {
        try {
            writeHeader();
            String sql;

            while ((row = reader.readLine()) != null) {
                if (!row.isEmpty()) {
                    String[] fieldNames = row.split(";");

                    sql = getFormattedStatement(RENAME_COLUMN);
                    sql = sql.replace(OLD_COLUMN_NAME, getFormattedFieldName(fieldNames[0]));
                    sql = sql.replace(NEW_COLUMN_NAME, getFormattedFieldName(fieldNames[1]));

                    writer.write(sql);
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
