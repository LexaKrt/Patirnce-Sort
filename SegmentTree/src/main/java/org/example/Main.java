package org.example;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int[] array = new int[10_000];
        int[] indicesForDeleting = new int[1000];
        int[] valuesForSearching = new int[100];

        for (int i = 0; i < 10000; ++i) {
            array[i] = random.nextInt(0, 100000);
        }
        for (int i = 0; i < 100; ++i) {
            valuesForSearching[i] = array[random.nextInt(0, 10000)];
        }
        for (int i = 0; i < 1000; ++i) {
            indicesForDeleting[i] = random.nextInt(0, 10000);
        }
        SegmentTree st = new SegmentTree(array);

        for (int a : array) {
            st.add(a);
        }

        for (int v : valuesForSearching) {
            st.search(v);
        }

        for (int i : indicesForDeleting) {
            st.delete(i);
        }

        System.out.println("Adding: Time(ns) per 1 adding - " + (st.additionTime / 10_000) + " Number of iterations - " + (st.additionNumOfOperations / 10_000));
        System.out.println("Searching: Time(ns) - " + (st.searchingTime / 100) + " Number of iterations - " + (st.searchingNumOfOperations / 100));
        System.out.println("Deleting: Time(ns) - " + (st.deletingTime / 1000) + " Number of iterations - " + (st.deletingNumOfOperations / 1000));
    }
}