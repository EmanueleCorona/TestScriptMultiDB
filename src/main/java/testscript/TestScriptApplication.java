package testscript;

import testscript.script.AddColumnScript;

import java.util.Scanner;

// Jira per testare: HWFHERAWFM-4868
public class TestScriptApplication {

    private static final int EXIT = 0;
    private static final int TABLE_CREATION = 1;
    private static final int ADD_COLUMN = 2;
    private static final int INSERT = 3;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean keepGoing = true;

        while (keepGoing) {
            showMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case EXIT: {
                    keepGoing = false;
                    break;
                }
                case TABLE_CREATION: {
                    System.out.println("Creazione Tabella");
                    break;
                }
                case ADD_COLUMN: {
                    addColumns();
                    break;
                }
                case INSERT: {
                    break;
                }
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n========== Test Script MultiDb ==========");
        System.out.println("0 ESCI");
        System.out.println("1 CREAZIONE TABELLA");
        System.out.println("2 AGGIUNTA COLONNE \n");
        System.out.print("Scegliere un'opzione: ");
    }

    private static void addColumns() {
        new AddColumnScript().addColumns();
    }
}