package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class SegmentTree {
    public long additionTime = 0;
    public long additionNumOfOperations = 0;
    public long deletingTime = 0;
    public long deletingNumOfOperations = 0;

    public long searchingTime = 0;
    public long searchingNumOfOperations = 0;
    public ArrayList<Integer> segTree;
    public int[] array;
    int n;

    public SegmentTree(int[] array) {
        this.n = array.length;
        this.array = array;
        buildTree();
    }

    private void buildTree() {
        int size = (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1) - 1;
        segTree = new ArrayList<>(Collections.nCopies(size, 0));
        constructTree(array, 0, n - 1, 0);
    }

    private void constructTree(int[] arr, int start, int end, int position) {
        if (start == end) {
            segTree.set(position, arr[start]);
            return;
        }
        int mid = (start + end) / 2;
        constructTree(arr, start, mid, 2 * position + 1);
        constructTree(arr, mid + 1, end, 2 * position + 2);

//        Set minimum of two child on their parent
        segTree.set(position, Math.min(segTree.get(2 * position + 1), segTree.get(2 * position + 2)));
    }

    public void update(int i, int value) {
        update(i, value, 0, n - 1, 0);
    }

    public void add(int value) {
        long start = System.nanoTime();
        int[] newArray = new int[++n];
        System.arraycopy(array, 0, newArray, 0, n - 1);
        array = newArray;

        array[n - 1] = value;
        additionNumOfOperations += n;
        buildTree();
        additionNumOfOperations += n - 1;
        long end = System.nanoTime();
        additionTime += end - start;
    }

    public void delete(int i) {
        long start = System.nanoTime();
        int[] newArray = new int[--n];
        System.arraycopy(array, 0, newArray, 0, n - 1);
        array = newArray;
        deletingNumOfOperations += n;
        buildTree();
        deletingNumOfOperations += n - 1;
        long end = System.nanoTime();
        deletingTime += end - start;
    }

    public int minQuery(int l, int r) {
        return minQuery(l, r, 0, n - 1, 0);
    }

    public boolean search(int value) {
        long start = System.nanoTime();
        boolean result = search(value, 0, n - 1, 0);
        long end = System.nanoTime();
        searchingTime += end - start;
        searchingNumOfOperations += (int) Math.log(segTree.size());
        return result;
    }

    public void update(int i, int value, int start, int end, int position) {
        if (start == end) {
            segTree.set(position, value);
            return;
        }
        int mid = (start + end) / 2;
        if (start <= i && i <= mid) {
            update(i, value, start, mid, 2 * position + 1);
        } else {
            update(i, value, mid + 1, end, 2 * position + 2);
        }
        segTree.set(position, Math.min(segTree.get(2 * position + 1), segTree.get(2 * position + 2)));
    }

    public int minQuery(int l, int r, int start, int end, int position) {
        if (array.length == 1) {
            return array[0];
        }
        if (r < start || end < l) {
            return Integer.MAX_VALUE;
        }
        if (l <= start && end <= r) {
            return segTree.get(position);
        }
        int mid = (start + end) / 2;
        int leftQuery = minQuery(l, r, start, mid, 2 * position + 1);
        int rightQuery = minQuery(l, r, mid + 1, end, 2 * position + 2);
        return Math.min(leftQuery, rightQuery);
    }

    private boolean search(int value, int start, int end, int position) {
        if (start == end) {
            return segTree.get(position) == value;
        } else {
            int mid = (start + end) / 2;
            boolean leftSearch = search(value, start, mid, 2 * position + 1);
            boolean rightSearch = search(value, mid + 1, end, 2 * position + 2);
            return leftSearch || rightSearch;
        }
    }

    public int getN() {
        return n;
    }
}