package testscript.utils;

import java.io.*;

public class FileUtils {
    public static void closeBufferedReader(BufferedReader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore chiusura file di lettura");
        }
    }

    public static void closeBufferedWriter(BufferedWriter writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore chiusura file di scrittura");
        }
    }
}
