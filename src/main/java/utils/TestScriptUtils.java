package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class TestScriptUtils {

    public static void closeBufferedWriter(BufferedWriter writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore chiusura file di scrittura");
        }
    }

    public static void closeBufferedReader(BufferedReader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore chiusura file di lettura");
        }
    }
}
