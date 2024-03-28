import java.util.LinkedList;
import java.io.*;
import java.util.Random;
import java.util.*;

public class WorkingWithData {
    public static void main(String[] args) {
        /*WorkingWithData.testCreation("data.txt", 100);
        WorkingWithData.clearFile("resultsOfTheTests.txt");*/
        WorkingWithData.writingResultsToTheFile("data.txt", "resultsOfTheTests.txt");
    }

    public static void testCreation(String fileName, int numberOfSets) {
        Random random = new Random();

        int valueCoefficient = 100;
        int minValue = - 8_000_000;
        int maxValue = 8_000_000;

        File file = new File(fileName);

        try {
            if (file.createNewFile()) {
                System.out.println("File has been successfully done");
            }

            FileWriter writer = new FileWriter(file);

            for (int i = 1; i < numberOfSets + 1; i++) {
                writer.write("[");
                for (int j = 0; j < (i * valueCoefficient) - 1; j++) {
                    writer.write(random
                            .nextInt(minValue, maxValue) + " ");
                }
                writer.write(random
                        .nextInt(minValue, maxValue) + "]\n");
            }

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void writingResultsToTheFile(String fileWithTests, String destinationFile) {
        File firstFile = new File(fileWithTests);
        File secondFile = new File(destinationFile);
        LinkedList<Integer> resultList = new LinkedList<>();
        PatienceSort patienceSort = new PatienceSort();

        try {
            if (firstFile.createNewFile()) {
                System.out.println("File has successfully created");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileWithTests));
             FileWriter writer = new FileWriter(secondFile, true);) {

            String currentLine;
            int lineNumber = 1;

            while ((currentLine = reader.readLine()) != null) {
                currentLine = currentLine.replaceAll("[\\[\\]]", "");
                resultList = Arrays.stream(currentLine.split(" "))
                        .map(Integer::parseInt)
                        .collect(LinkedList::new, LinkedList::add, LinkedList::addAll);

                long before = System.currentTimeMillis();
                patienceSort.patienceSort(resultList);
                long after = System.currentTimeMillis();
                long timeOfExecution = after - before;
                writer.write((int) timeOfExecution + " " + patienceSort.getNumberOfIterations() + " " + (lineNumber * 100) + "\n");

                lineNumber++;
                }
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearFile(String fileName) {
        try {
            FileWriter writer = new FileWriter(new File(fileName));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}