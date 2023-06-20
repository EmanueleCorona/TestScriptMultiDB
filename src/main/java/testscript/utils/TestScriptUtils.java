package testscript.utils;

import java.io.*;

public class TestScriptUtils {

    public boolean checkRowValidity(BufferedReader reader, String row) throws IOException {
        return ((row = reader.readLine()) != null && !row.isEmpty());
    }

    public static String getFormattedFieldType(String fieldType) {
        if (fieldType.contains(TestScriptConst.FieldType.NUMBER)) {
            if (fieldType.contains(",")) {
                fieldType = "NUMBER " + fieldType.replaceAll("[^0-9,]", "").replace(",", " ");
            } else {
                fieldType = "NUMBER " + fieldType.replaceAll("[^0-9]", "") + " 0";
            }
        }

        if (fieldType.contains(TestScriptConst.FieldType.VARCHAR)) {
            fieldType = fieldType.replaceFirst("VARCHAR2", "");
            return TestScriptConst.FieldType.VARCHAR + " " + fieldType.replaceAll("[^0-9]", "");
        }

        if (fieldType.contains(TestScriptConst.FieldType.CHAR)) {
            return TestScriptConst.FieldType.CHAR + " 1";
        }

        if (fieldType.contains(TestScriptConst.FieldType.TIMESTAMP)) {
            return TestScriptConst.FieldType.TIMESTAMP;
        }

        return fieldType;
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
