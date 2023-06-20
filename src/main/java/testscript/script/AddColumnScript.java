package testscript.script;

import testscript.utils.TestScriptConst;
import testscript.utils.TestScriptUtils;

import java.io.*;
import java.util.Scanner;

import static testscript.utils.TestScriptConst.SCRIPT_SEPARETOR;

public class AddColumnScript {
    private boolean isFieldName = true;
    private String fieldName = TestScriptConst.FIELD_NAME;
    private String fieldType = TestScriptConst.FIELD_TYPE;
    private BufferedWriter writer;
    private BufferedReader reader;
    private final Scanner scanner = new Scanner(System.in);
    private static final String ADD_COLUMN = "{ALTER_ADD_COLUMN nome_tabella nome_campo {tipo_dato}}" + SCRIPT_SEPARETOR;


    public void addColumns() {
        try {
            initResources();

            System.out.println("========== Aggiunta Colonne ==========");
            System.out.print("Inserisci il nome della tabella: ");

            String sql = ADD_COLUMN.replace(TestScriptConst.TABLE_NAME, scanner.next());
            String row;

            while ((row = reader.readLine()) != null) {
                if (!row.isEmpty()) {
                    boolean readyToWrite;

                    if (isFieldName) {
                        // Fase impostazione nome campo
                        sql = sql.replace(fieldName, row);
                        fieldName = row;
                        readyToWrite = false;
                        isFieldName = false;
                    } else {
                        // Fase impostazione tipo capo
                        sql = sql.replace(fieldType, TestScriptUtils.getFormattedFieldType(row));
                        fieldType = TestScriptUtils.getFormattedFieldType(row);
                        readyToWrite = true;
                        isFieldName = true;
                    }

                    // Stampa lo script dopo che sono stati impostati sia il nome che il tipo del campo
                    if (readyToWrite) {
                        writer.append(sql).append("\n");
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("File di lettura non trovato");
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il tentativo di aggiunta delle colonne");
        } finally {
            TestScriptUtils.closeBufferedReader(reader);
            TestScriptUtils.closeBufferedWriter(writer);
        }
    }

    public void initResources() throws IOException {
        reader = new BufferedReader(new FileReader(TestScriptConst.READER_PATH));
        writer = new BufferedWriter(new FileWriter(TestScriptConst.WRITER_PATH));

        writer.append(TestScriptConst.GEOCALL_HEADER).append("\n");
    }
}
