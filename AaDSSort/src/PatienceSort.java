import java.util.*;


public class PatienceSort {
    public static void main(String[] args) {
        PatienceSort patienceSort = new PatienceSort();
        List<Integer> linkedList = new LinkedList<>(List.of(8, 4, 2, 5, 9, 3, 5, 2, 1));
        linkedList = patienceSort.patienceSort(linkedList);
        System.out.println(linkedList.toString() + " Number of iterations: " + patienceSort.numberOfIterations);
    }
    private long numberOfIterations = 0;

    public List<Integer> patienceSort(List<Integer> array) {
        List<List<Integer>> arrayOfPiles = new LinkedList<>();

        for (Integer elementI : array) {
            int left = 0;
            int right = arrayOfPiles.size() - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (elementI.compareTo(arrayOfPiles.get(mid)
                        .get(arrayOfPiles.get(mid).size() - 1)) < 0) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
                numberOfIterations++;
            }
            if (left == arrayOfPiles.size()) {

                List<Integer> tempArray = new LinkedList<>();

                arrayOfPiles.add(tempArray);
            }
            arrayOfPiles.get(left).add(elementI);
        }
        return mergePiles(arrayOfPiles);
    }

    public List<Integer> mergePiles(List<List<Integer>> array) {
        List<Integer> result = new ArrayList<>();

        while (true) {

            int minElement = Integer.MAX_VALUE;
            int index = -1;

            for (int i = 0; i < array.size(); i++) {
                List<Integer> tempArray = array.get(i);

                if (!tempArray.isEmpty() &&
                        minElement > tempArray.get(tempArray.size() - 1)) {

                    minElement = tempArray.get(tempArray.size() - 1);
                    index = i;
                }
                numberOfIterations++;
            }

            if (index == -1) {
                break;
            }

            result.add(minElement);
            array.get(index).remove(array.get(index).size() - 1);

            if (array.get(index).isEmpty()) {
                array.remove(index);
            }
        }
        return result;
    }

    public long getNumberOfIterations() {
        return numberOfIterations;
    }
}
