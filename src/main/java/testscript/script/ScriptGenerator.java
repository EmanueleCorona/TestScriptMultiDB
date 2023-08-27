package testscript.script;

import testscript.utils.FileUtils;
import testscript.utils.TestScriptConst;
import testscript.utils.TestScriptConst.Error;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static testscript.utils.TestScriptConst.*;
import static testscript.utils.TestScriptConst.FieldType.*;

public abstract class ScriptGenerator {
    protected String tableName;
    protected String tableAcronym;

    protected String row;
    protected boolean isFieldName = true;
    protected BufferedWriter writer;
    protected BufferedReader reader;
    public static final String READER_PATH = "src/main/java/testscript/files/fields.txt";
    public static final String WRITER_PATH = "src/main/java/testscript/files/script.sql";

    public ScriptGenerator() {
        super();
        initResources();
    }

    protected abstract void generateStatement();

    protected String getFormattedStatement(String statement) {
        return statement.replace(TABLE_NAME, tableName);
    }

    protected void writeHeader() throws IOException {
        writer.append(TestScriptConst.GEOCALL_HEADER);
    }

    protected String getFormattedFieldName(String fieldName) {
        String tmpFieldName = getTrimmedString(fieldName).toUpperCase();

        // Controllo messo per evitare errori nel programma
        if (tmpFieldName.length() < 8) {
            return tmpFieldName;
        }

        if (this instanceof CreateTableScript || this instanceof AddColumnScript || this instanceof AlterTypeScript) {
            if (tableAcronym == null) {
                tableAcronym = tmpFieldName.substring(0, 8);
            }

            tmpFieldName = tmpFieldName.replace(tmpFieldName.substring(0, 8), tableAcronym);
        }

        return tmpFieldName;
    }

    protected String getFormattedFieldType(String fieldType) {
        StringBuilder formattedType = new StringBuilder();

        String upperFieldType = fieldType.toUpperCase();
        String trimmedFieldType = getTrimmedString(upperFieldType);

        if (trimmedFieldType.contains(NUMBER)) {
            setNumberFieldType(upperFieldType, formattedType);

        } else if (trimmedFieldType.contains(VARCHAR)) {
            setVarcharFieldType(upperFieldType, formattedType);

        } else if (trimmedFieldType.contains(CHAR)) {
            formattedType.append(CHAR).append(SPACE).append("1");

        } else if (trimmedFieldType.contains(TIMESTAMP)) {
            formattedType.append(TIMESTAMP);

        } else {
            formattedType.append(WRONG_DATATYPE);
        }

        setDataTypeBraces(formattedType);

        return formattedType.toString();
    }

    protected void setNumberFieldType(String fieldType, StringBuilder formattedType) {
        String numberValue = getNumberValue(fieldType);

        if (!numberValue.isEmpty()) {
            formattedType.append(NUMBER).append(SPACE).append(numberValue);
        } else {
            formattedType.append(WRONG_DATATYPE);
        }
    }

    protected void setVarcharFieldType(String fieldType, StringBuilder formattedType) {
        String varcharValue = getVarcharValue(fieldType);

        if (!varcharValue.isEmpty()) {
            formattedType.append(VARCHAR).append(SPACE).append(varcharValue);
        } else {
            formattedType.append(WRONG_DATATYPE);
        }
    }

    protected String getNumberValue(String fieldType) {
        String fieldValue = getBracketsValue(fieldType);

        if (!fieldValue.isEmpty()) {
            if (fieldType.contains(COMMA)) {
                fieldValue = fieldValue.replace(COMMA, SPACE);
            } else {
                fieldValue = fieldValue.concat(SPACE).concat("0");
            }
        }

        return fieldValue;
    }

    protected String getVarcharValue(String fieldType) {
        return getBracketsValue(fieldType);
    }

    protected String getBracketsValue(String fieldType) {
        Pattern regex = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = regex.matcher(fieldType);

        String result;

        // Controllo per verificare che siano presenti le parentesi
        if (matcher.find()) {
            result = matcher.group(1);
        } else {
            if (fieldType.contains(NUMBER)) {
                result = fieldType.replaceAll(".*?(NUMBER)", "$1").replaceAll("NUMBER[^ ]*", "").replaceAll("\\s", "");
            } else {
                result = fieldType.replaceAll(".*?(VARCHAR)", "$1").replaceAll("VARCHAR[^ ]*", "").replaceAll("\\s", "");
            }
        }

        return getTrimmedString(result).replaceAll("[^0-9,]", "");
    }

    protected void setDataTypeBraces(StringBuilder fieldType) {
        fieldType.insert(0, "{").append("}");
    }

    protected boolean isStandardField(String fieldName) {
        return (fieldName.equals(TestScriptConst.DATASTAMP) || fieldName.equals(TestScriptConst.LOGIN) ||
                fieldName.equals(TestScriptConst.ACTION) || fieldName.contains(TestScriptConst.AAZI));
    }

    protected boolean isLogicStateTable() {
        boolean isLogicStateTable;

        // Controllo messo per evitare errori in caso di test del programma
        if (tableName.length() > 4) {
            isLogicStateTable = containChar(tableName.charAt(0), LOGIC_STATE_TABLE) || containChar(tableName.charAt(4), LOGIC_STATE_TABLE);
        } else {
            isLogicStateTable = containChar(tableName.charAt(0), LOGIC_STATE_TABLE);
        }

        return isLogicStateTable;
    }

    protected boolean isMasterTypeTable() {
        boolean isMasterTypeTable;

        // Controllo messo per evitare errori in caso di test del programma
        if (tableName.length() > 4) {
            isMasterTypeTable = containChar(tableName.charAt(0), MASTER_TYPE_TABLE) || containChar(tableName.charAt(4), MASTER_TYPE_TABLE);
        } else {
            isMasterTypeTable = containChar(tableName.charAt(0), MASTER_TYPE_TABLE);
        }

        return isMasterTypeTable;
    }

    protected boolean isThereAnotherRow() throws IOException {
        return reader.ready();
    }

    protected boolean containChar(char value, char... characters) {
        boolean isPresent = false;

        for (char c : characters) {
            if (value == c) {
                isPresent = true;
                break;
            }
        }

        return isPresent;
    }

    protected String getTrimmedString(String str) {
        return str.replaceAll("\\s", "");
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    protected void initResources() {
        try {
            reader = new BufferedReader(new FileReader(READER_PATH));
            writer = new BufferedWriter(new FileWriter(WRITER_PATH));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(Error.ERR_IO_FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(Error.ERR_IO_FILE_WRITING);
        }
    }

    protected void closeResources() {
        FileUtils.closeBufferedReader(reader);
        FileUtils.closeBufferedWriter(writer);
    }
}