package testProject;

// Импортируем необходимые классы для работы с командной строкой
import org.apache.commons.cli.*;
// Импортируем необходимые классы для работы с файлами и чтения данных
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    // Статические списки для хранения данных из файлов
    static ArrayList<String> namesOfInputFiles = new ArrayList<>();
    static ArrayList<String> dataForStringFileOutput = new ArrayList<>();
    static ArrayList<Integer> dataForIntFileOutput = new ArrayList<>();
    static ArrayList<Float> dataForFloatFileOutput = new ArrayList<>();
    static ArrayList<String> targetFiles = new ArrayList<>();

    // Функция для сортировки данных в зависимости от их типа
    public static void sortLinesFromInputData(String line) {
        try {
            int intValue = Integer.parseInt(line);
            dataForIntFileOutput.add(intValue);
        } catch (NumberFormatException e) {
            try {
                float floatValue = Float.parseFloat(line);
                dataForFloatFileOutput.add(floatValue);
            } catch (NumberFormatException e2) {
                dataForStringFileOutput.add(line);
            }
        }
    }

    // Функция для чтения данных из файлов, указанных в ArrayList
    public static void processInputFiles(ArrayList<String> inputFiles) {
        for (String name : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sortLinesFromInputData(line);
                }
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла " + name + ": " + e.getMessage());
            }
        }
    }

    // Функция для записи данных в файл
    public static void writeDataToFile(String fileName,
            ArrayList<?> dataForInput) {
        try (FileWriter fw = new FileWriter(fileName, true);
                BufferedWriter bw = new BufferedWriter(fw)) {
            for (Object value : dataForInput) {
                bw.write(value.toString() + "\n");
            }
            
        } catch (IOException e) {
            System.err.println("Ошибка при добавлении данных: " + e.getMessage());
        }
    }

    public static void createOutputFile(String fileName) {
        try {
            File outputFile = new File(fileName);
            outputFile.createNewFile();

        } catch (IOException e) {
            System.out.println("Ошибка при создании файла: " + e.getMessage());
        }
    }

    // Функция для создания файлов в зависимости от данных
    public static void createOutputFiles(ArrayList<Integer> ints, ArrayList<Float> floats, ArrayList<String> strings,
            ArrayList<String> targetFiles) {
        for (String fileName : targetFiles) {
            if (ints.size() > 0 && fileName.endsWith("int.txt")) {
                createOutputFile(fileName);
            }
            if (floats.size() > 0 && fileName.endsWith("float.txt")) {
                createOutputFile(fileName);

            }
            if (strings.size() > 0 && fileName.endsWith("string.txt")) {
                createOutputFile(fileName);

            }
        }
    }

    // Функция для отображения полной статистики по собранным данным
    public static void showFullStatistics() {
        showShortStatistics();
        int intSum = 0;
        int intMax = 0;
        int intMin = Integer.MAX_VALUE;
        boolean hasIntData = false;
        for (Integer intValue : dataForIntFileOutput) {
            hasIntData = true;
            intSum += intValue;
            if (intValue > intMax) {
                intMax = intValue;
            }
            if (intValue < intMin) {
                intMin = intValue;
            }
        }
        if (hasIntData) {
            System.out.println("Максимальное значение " + intMax + " и минимальное значение "
                    + intMin + " для целочисленных данных. Сумма: " + intSum + " Среднее: "
                    + (intSum / (int) dataForIntFileOutput.size()));
        } else {
            System.out.println("Нет данных для целочисленных значений.");
        }

        float floatSum = 0;
        float floatMax = 0;
        float floatMin = Float.MAX_VALUE;
        boolean hasFloatData = false;
        for (Float floatValue : dataForFloatFileOutput) {
            hasFloatData = true;
            floatSum += floatValue;
            if (floatValue > floatMax) {
                floatMax = floatValue;
            }
            if (floatValue < floatMin) {
                floatMin = floatValue;
            }
        }
        if (hasFloatData) {
            System.out.println("Максимальное значение " + floatMax + " и минимальное значение "
                    + floatMin + " для вещественных данных. Сумма: " + floatSum
                    + " Среднее: " + (floatSum / (int) dataForFloatFileOutput.size()));
        } else {
            System.out.println("Нет данных для вещественных значений.");
        }
        String longestString;
        longestString = findLongestStringAndShortest(dataForStringFileOutput);
        System.out.println("Самая длинная строка и самая короткая: " + longestString);
        ;
    }

    // Функция для поиска самой длинной и самой короткой строки
    private static String findLongestStringAndShortest(ArrayList<String> dataForStringFileOutput) {
        String longestString = "";
        String shortestString = "";
        for (String str : dataForStringFileOutput) {
            if (str.length() > longestString.length()) {
                longestString = str;
            }
            if (shortestString.isEmpty() || str.length() < shortestString.length()) {
                shortestString = str;
            }
        }

        return longestString + " и " + shortestString + ". Размер самой длинной: "
                + longestString.length() + ". Размер самой короткой: " + shortestString.length();
    }

    // Функция для отображения краткой статистики по собранным данным
    private static void showShortStatistics() {
        System.out.println("Количество целочисленных данных: " + dataForIntFileOutput.size());
        System.out.println("Количество вещественных данных: " + dataForFloatFileOutput.size());
        System.out.println("Количество строковых данных: " + dataForStringFileOutput.size());

    }

    // Функция для записи данных в выходные файлы
    public static void writeDataInOutputFiles(
            ArrayList<Integer> dataForIntFileOutput,
            ArrayList<Float> dataForFloatFileOutput,
            ArrayList<String> dataForStringFileOutput, ArrayList<String> madeOutputFiles) {
        System.out.println("Запись данных в выходные файлы: " + madeOutputFiles);
        for (String fileName : madeOutputFiles) {

            if (fileName.endsWith("int.txt")) {
                writeDataToFile(fileName, dataForIntFileOutput);
            }
            if (fileName.endsWith("float.txt")) {
                writeDataToFile(fileName, dataForFloatFileOutput);
            }
            if (fileName.endsWith("string.txt")) {
                writeDataToFile(fileName, dataForStringFileOutput);
            }

        }
    }

    public static void main(String[] args) {
        boolean hasPrefix = true;
        String path = "";
        String prefix = "";
        Options options = new Options();
        boolean shortStat = false;
        boolean fullStat = false;
        boolean appendData = false;
        // Определяем опции командной строки
        options.addOption("o", "output", true, "Путь для результата");
        options.addOption("p", "prefix", true, "Префикс для имен файлов");
        options.addOption("s", "small", false, "Краткая статистика");
        options.addOption("f", "full", false, "Полная статистика");
        options.addOption("a", "add", false, "Добавление данных в выходные файлы");
        CommandLineParser parser = new DefaultParser();
        // Парсим аргументы командной строки
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("p")) {
                prefix = cmd.getOptionValue("p");
                System.out.println("Префикс для имен файлов: " + prefix);
            } else {
                hasPrefix = false;
                System.out.println("Префикс не указан, будет использоваться значение по умолчанию.");
            }
            if (cmd.hasOption("o")) {
                path = cmd.getOptionValue("o");
                if (!path.endsWith(File.separator)) {
                    path += File.separator;
                }
            } else {
                path = System.getProperty("user.dir") + File.separator; // Текущая директория по умолчанию
                System.out.println("Путь не указан, используется текущая директория: " + path);
            }
            if (cmd.hasOption("s")) {
                shortStat = true;
                System.out.println("Краткая статистика включена.");
            }
            if (cmd.hasOption("f")) {
                fullStat = true;
                System.out.println("Полная статистика включена.");
            }
            if (cmd.hasOption("a")) {
                appendData = true;
                System.out.println("Добавление данных в выходные файлы включено.");
            } else {
                System.out.println("Добавление данных в выходные файлы не включено.");
            }
        } catch (ParseException e) {
            System.out.println("Ошибка при разборе аргументов: " + e.getMessage());
            return;
        }

        // Сбор входных файлов
        for (String arg : args) {
            if (arg.endsWith(".txt")) {
                namesOfInputFiles.add(arg);
            }
        }

        // Чтение данных из входных файлов
        processInputFiles(namesOfInputFiles);

        // Определяем имена файлов
        String intFileName = hasPrefix ? prefix + "int.txt" : "int.txt";
        String floatFileName = hasPrefix ? prefix + "float.txt" : "float.txt";
        String stringFileName = hasPrefix ? prefix + "string.txt" : "string.txt";
        targetFiles = new ArrayList<>();
        targetFiles.add(path + intFileName);
        targetFiles.add(path + floatFileName);
        targetFiles.add(path + stringFileName);

        if (appendData) {

            boolean filesExist = false;
            for (String filePath : targetFiles) {
                File file = new File(filePath);
                if (file.exists()) {
                    filesExist = true;

                }
            }
            if (!filesExist) {

                createOutputFiles(dataForIntFileOutput, dataForFloatFileOutput, dataForStringFileOutput, targetFiles);
            }
            writeDataInOutputFiles(dataForIntFileOutput, dataForFloatFileOutput, dataForStringFileOutput,
                    targetFiles);
        } else {

            for (String filePath : targetFiles) {
                File file = new File(filePath);
                if (file.exists()) {
                    try {
                        Files.delete(Path.of(filePath));
                        System.out.println("File deleted successfully: " + filePath);
                    } catch (IOException e) {
                        System.err.println("Error deleting file: " + e.getMessage());
                    }
                }
            }
            createOutputFiles(dataForIntFileOutput, dataForFloatFileOutput, dataForStringFileOutput, targetFiles);
            writeDataInOutputFiles(dataForIntFileOutput, dataForFloatFileOutput, dataForStringFileOutput,
                    targetFiles);
        }
        // Отображаем статистику в зависимости от флагов
        if (fullStat) {
            showFullStatistics();
        }
        if (shortStat) {
            showShortStatistics();
        }

    }

}
