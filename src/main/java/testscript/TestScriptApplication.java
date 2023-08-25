package testscript;

import testscript.script.*;
import testscript.utils.TestScriptConst.Menu;

import java.util.Scanner;

import static testscript.utils.TestScriptConst.Error.ERR_OPTION_NOT_VALID;
import static testscript.utils.TestScriptConst.NEW_LINE;

public class TestScriptApplication {
    private static final int EXIT = 0;
    private static final int CREATE_TABLE = 1;
    private static final int ADD_COLUMN = 2;
    private static final int ALTER_TYPE = 3;
    private static final int DROP_COLUMN = 4;
    private static final int RENAME_COLUMN = 5;
    private static final int RENAME_TABLE = 6;
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
                case CREATE_TABLE: {
                    createTable();
                    break;
                }
                case ADD_COLUMN: {
                    addColumn();
                    break;
                }
                case ALTER_TYPE: {
                    alterType();
                    break;
                }
                case DROP_COLUMN: {
                    dropColumn();
                    break;
                }
                case RENAME_COLUMN: {
                    renameColumn();
                    break;
                }
                case RENAME_TABLE: {
                    renameTable();
                    break;
                }
            }
        }
    }

    private static int showMenu() {
        System.out.println(NEW_LINE + "========== Test Script MultiDB ==========");
        System.out.println(Menu.EXIT);
        System.out.println(Menu.CREATE_TABLE);
        System.out.println(Menu.ADD_COLUMN);
        System.out.println(Menu.ALTER_TYPE);
        System.out.println(Menu.DROP_COLUMN);
        System.out.println(Menu.RENAME_COLUMN);
        System.out.println(Menu.RENAME_TABLE);
        System.out.print(NEW_LINE + "Scegliere un'opzione: ");
        return getChoiceUntilIsValid();
    }

    private static int getChoiceUntilIsValid() {
        int choice = 0;
        boolean keepGoing = true;

        while (keepGoing) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();

                if (choice < 0 || choice > 6) {
                    System.err.print(ERR_OPTION_NOT_VALID);
                    choice = scanner.nextInt();
                }

                keepGoing = false;

            } else {
                System.err.print(ERR_OPTION_NOT_VALID);
                scanner.next(); // Consuma l'input non valido per evitare un loop infinito
            }
        }

        return choice;
    }

    private static void addColumn() {
        System.out.println(NEW_LINE + "========== Aggiunta Colonne ==========");
        System.out.print("Inserisci il nome della tabella: ");

        String tableName = scanner.next().toUpperCase();

        AddColumnScript addColumnScript = new AddColumnScript();
        addColumnScript.setTableName(tableName);
        addColumnScript.generateStatement();
    }

    private static void createTable() {
        System.out.println(NEW_LINE + "========== Creazione Tabella ==========");
        System.out.print("Inserisci il nome della tabella: ");

        String tableName = scanner.next().toUpperCase();

        CreateTableScript createTableScript = new CreateTableScript();
        createTableScript.setTableName(tableName);
        createTableScript.generateStatement();
    }

    private static void alterType() {
        System.out.println(NEW_LINE + "========== Alterazione Tipo Campo ==========");
        System.out.print("Inserisci il nome della tabella: ");

        String tableName = scanner.next().toUpperCase();

        AlterTypeScript alterTypeScript = new AlterTypeScript();
        alterTypeScript.setTableName(tableName);
        alterTypeScript.generateStatement();
    }

    private static void dropColumn() {
        System.out.println(NEW_LINE + "========== Eliminazione Campo ==========");
        System.out.print("Inserisci il nome della tabella: ");

        String tableName = scanner.next().toUpperCase();

        DropColumnScript dropColumnScript = new DropColumnScript();
        dropColumnScript.setTableName(tableName);
        dropColumnScript.generateStatement();
    }

    private static void renameColumn() {
        System.out.println(NEW_LINE + "========== Ridenominazione Campi ==========");
        System.out.print("Inserisci il nome della tabella: ");

        String tableName = scanner.next().toUpperCase();

        RenameColumnScript renameColumnScript = new RenameColumnScript();
        renameColumnScript.setTableName(tableName);
        renameColumnScript.generateStatement();
    }

    private static void renameTable() {
        System.out.println(NEW_LINE + "========== Ridenominazione Tabella ==========");
        RenameTableScript renameTableScript = new RenameTableScript();
        renameTableScript.generateStatement();
    }
}