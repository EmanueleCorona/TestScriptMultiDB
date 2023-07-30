package testscript;

import testscript.script.AddColumnScript;
import testscript.script.AlterTypeScript;
import testscript.script.CreateTableScript;

import java.util.Scanner;

public class TestScriptApplication {
    private static final int EXIT = 0;
    private static final int TABLE_CREATION = 1;
    private static final int ADD_COLUMN = 2;
    private static final int ALTER_COLUMN = 3;
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        boolean keepGoing = true;

        while (keepGoing) {
            int choice = showMenu();

            switch (choice) {
                case EXIT: {
                    keepGoing = false;
                    break;
                }
                case TABLE_CREATION: {
                    createTable();
                    break;
                }
                case ADD_COLUMN: {
                    addColumns();
                    break;
                }
                case ALTER_COLUMN: {
                    alterType();
                    break;
                }
            }
        }
    }

    private static int showMenu() {
        System.out.println("\n========== Test Script MultiDb ==========");
        System.out.println("0 ESCI");
        System.out.println("1 CREAZIONE TABELLA");
        System.out.println("2 AGGIUNTA COLONNE");
        System.out.println("3 ALTERAZIONE TIPO CAMPO");
        System.out.print("\nScegliere un'opzione: ");
        return getChoiceUntilIsValid();
    }

    private static void addColumns() {
        System.out.println("\n========== Aggiunta Colonne ==========");
        System.out.print("Inserisci il nome della tabella: ");

        String tableName = scanner.next().toUpperCase();

        AddColumnScript addColumnScript = new AddColumnScript();
        addColumnScript.setTableName(tableName);
        addColumnScript.generateStatement();
    }

    private static void createTable() {
        System.out.println("\n========== Creazione Tabella ==========");
        System.out.print("Inserisci il nome della tabella: ");

        String tableName = scanner.next().toUpperCase();

        CreateTableScript createTableScript = new CreateTableScript();
        createTableScript.setTableName(tableName);
        createTableScript.generateStatement();
    }

    private static void alterType() {
        System.out.println("\n========== Alterazione Tipo Campo ==========");
        System.out.print("Inserisci il nome della tabella: ");

        String tableName = scanner.next().toUpperCase();

        AlterTypeScript alterTypeScript = new AlterTypeScript();
        alterTypeScript.setTableName(tableName);
        alterTypeScript.generateStatement();
    }

    private static int getChoiceUntilIsValid() {
        int choice = 0;
        boolean keepGoing = true;

        while (keepGoing) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();

                if (choice < 0 || choice > 3) {
                    System.out.print("Opzione non valida, scegliere un'opzione: ");
                    choice = scanner.nextInt();
                }

                keepGoing = false;
            } else {
                System.out.print("Opzione non valida, scegliere un'opzione: ");
                scanner.next(); // Consuma l'input non valido per evitare un loop infinito
            }
        }

        return choice;
    }
}