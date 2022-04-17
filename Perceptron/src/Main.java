import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        String splitBy = ",";
        double alfa = 0.01;
        double theta = (Math.random()*6) -3;
        double goodHit;
        double accuracy;

        HashMap<Integer, List<Double>> vectorMap = new HashMap<>();
        List<String> typeList = new ArrayList<>();
        List<Integer> answersList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Adani\\Desktop\\Projects\\" +
                "Perceptron\\Perceptron\\src\\perceptron.data.csv"));
        BufferedReader readerTest = new BufferedReader(new FileReader("C:\\Users\\Adani\\Desktop\\Projects\\" +
                "Perceptron\\Perceptron\\src\\perceptron.test.data.csv"));

        Scanner scanner = new Scanner(System.in);
        System.out.print("How many parameters need to be loaded: ");
        int numberOfParameters = scanner.nextInt();

        scan(splitBy, typeList, vectorMap,numberOfParameters,reader);
        double[] weightArr = new double[numberOfParameters];
        for (int i = 0; i < weightArr.length; i++)
            weightArr[i] = (Math.random() * 6) - 3;

        System.out.println("How many times is the algorithm to learn: ");
        int howManyTimes = scanner.nextInt();

        while(howManyTimes > 0) {
            String firstType = typeList.get(0);
            for (String s : typeList) {
                if (s.equals(firstType))
                    answersList.add(0);
                else
                    answersList.add(1);
            }
            for (int i = 0; i < vectorMap.size(); i++) {
                learn(vectorMap,answersList,weightArr,alfa,theta,numberOfParameters);
            }
            howManyTimes--;
        }
        vectorMap.clear();
        answersList.clear();
        typeList.clear();
        System.out.println();
        scan(splitBy, typeList, vectorMap,numberOfParameters,readerTest);

        String firstType = typeList.get(0);
        for (String s : typeList) {
            if (s.equals(firstType))
                answersList.add(0);
            else
                answersList.add(1);
        }

        goodHit = test(vectorMap,answersList,weightArr,numberOfParameters,theta);
        accuracy = (goodHit / (double) typeList.size()) * 100;
        System.out.println((int)accuracy + "%");

    }
    private static void scan(String splitBy, List<String> typeList,
                             HashMap<Integer, List<Double>> vectorMap, int size, BufferedReader reader) throws IOException {
        String line;
        int objectNumber = 0;
        while ((line = reader.readLine()) != null) {
            List<Double> objectVectors = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                String[] values = line.split(splitBy);
                if (i == size - 1)
                    typeList.add(values[size - 1]);
                else
                    objectVectors.add(Double.parseDouble(values[i]));
            }
            vectorMap.put(objectNumber, objectVectors);
            objectNumber++;
        }
    }
    public static void learn(HashMap<Integer, List<Double>> vectorMap, List<Integer> answersList, double[] weightArr,
                             double alpha,double theta, int size){
        for (int i = 0; i < vectorMap.size(); i++) {
            int net = 0;
            double y = 0;
            int d = answersList.get(i);
            for (int j = 0; j < size; j++) {
                y = y + vectorMap.get(i).get(j) * weightArr[j];
            }
            y -= theta;

            if (y >= 0)
                net = 1;

            for (int k = 0; k < weightArr.length; k++) {
                weightArr[k] = weightArr[k] + (alpha * (d - net) * vectorMap.get(i).get(k));
            }
            theta = theta - (alpha * (d - net));
        }
    }
    public static double test(HashMap<Integer, List<Double>> vectorMap, List<Integer> answersList, double[] weightArr
            ,int size,double theta){
        double goodHit = 0;
        for (int i = 0; i < vectorMap.size(); i++) {
            double y = 0;
            int net = 0;
            for (int j = 0; j < size; j++) {
                y = y + vectorMap.get(i).get(j) * weightArr[j];
            }
            y -= theta;

            if (y >= 0)
                net = 1;

            if (net == answersList.get(i))
                goodHit++;
        }
        return goodHit;
    }
}
