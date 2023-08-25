package testscript.script;

import testscript.utils.TestScriptConst.Error;

import java.io.FileNotFoundException;
import java.io.IOException;

import static testscript.utils.TestScriptConst.SCRIPT_SEPARETOR;
import static testscript.utils.TestScriptConst.SPACE;

public class RenameTableScript extends ScriptGenerator {
    private static final String OLD_TABLE_NAME = "OLD_TABLE_NAME";
    private static final String NEW_TABLE_NAME = "NEW_TABLE_NAME";
    private static final String RENAME_TABLE = "{RENAME_TABLE" + SPACE + OLD_TABLE_NAME + SPACE + NEW_TABLE_NAME + "}" + SCRIPT_SEPARETOR;


    @Override
    public void generateStatement() {
        try {
            writeHeader();
            String sql;

            while ((row = reader.readLine()) != null) {
                if (!row.isEmpty()) {
                    String[] fieldNames = row.split(";");

                    sql = RENAME_TABLE;
                    sql = sql.replace(OLD_TABLE_NAME, getFormattedFieldName(fieldNames[0]));
                    sql = sql.replace(NEW_TABLE_NAME, getFormattedFieldName(fieldNames[1]));

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
