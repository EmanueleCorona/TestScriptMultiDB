package testscript.utils;

import testscript.utils.TestScriptConst.Error;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class FileUtils {
    public static void closeBufferedReader(BufferedReader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(Error.ERR_IO_CLOSE_READER);
        }
    }

    public static void closeBufferedWriter(BufferedWriter writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(Error.ERR_IO_CLOSE_WRITER);
        }
    }
}
