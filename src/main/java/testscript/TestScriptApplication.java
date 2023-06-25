package testscript;

import testscript.script.AddColumnScript;
import testscript.script.CreateTableScript;
import testscript.script.InsertIntoScript;

import java.util.Scanner;

public class TestScriptApplication {

    private static final int EXIT = 0;
    private static final int TABLE_CREATION = 1;
    private static final int ADD_COLUMN = 2;
    private static final int INSERT = 3;
    private static final Scanner scanner = new Scanner(System.in);
    private static final AddColumnScript addColumnScript = new AddColumnScript();
    private static final InsertIntoScript insertIntoScript = new InsertIntoScript();
    private static final CreateTableScript createTableScript = new CreateTableScript();


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
                case INSERT: {
                    insertValues();
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
        System.out.println("3 INSERISCI RECORD \n");
        System.out.print("Scegliere un'opzione: ");

        int choice = scanner.nextInt();
        if (choice < 0 || choice > 3) {
            System.out.print("Opzione non valida, scegliere un'opzione: ");
            choice = scanner.nextInt();
        }

        return choice;
    }

    private static void addColumns() {
        System.out.println("========== Aggiunta Colonne ==========");
        System.out.print("Inserisci il nome della tabella: ");
        addColumnScript.setTableName(scanner.next().toUpperCase());
        addColumnScript.generateStatement();
    }

    private static void insertValues() {
//        System.out.println("========== Inserimento Valori ==========");
//        System.out.print("Inserisci il nome della tabella: ");
//        insertIntoScript.insertValues(scanner.next());
    }

    private static void createTable() {
//        System.out.println("========== Creazione Tabella ==========");
//        System.out.print("Inserisci il nome della tabella: ");
//        createTableScript.setTableName(scanner.next().toUpperCase());
//        createTableScript.generateStatement();
    }
}