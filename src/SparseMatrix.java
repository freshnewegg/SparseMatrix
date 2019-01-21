import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/******************************************************************************
 *  Compilation:  javac SparseMatrix.java
 *  Execution:    java SparseMatrix
 *
 *  A sparse n*m matrix, implemented using two linked hash-maps. Optimized for
 *  fast single element get/put, row/col retrieval and retrieving all elements
 *
 *  Author: Edgar Wang
 *
 ******************************************************************************/

public class SparseMatrix<T>
{
    private HashMap<Integer, HashMap<Integer, T>> rows;
    private HashMap<Integer, HashMap<Integer, T>> columns;

    /**
     * Initilize the base size of the hashmaps backing the sparse matrix
     */
    public SparseMatrix() {
        this.rows = new HashMap<>();
        this.columns = new HashMap<>();
    }

    /**
     * Puts an element into the sparse matrix, elements must be inserted in a top down, left
     * to right order
     */
    public void put(int x, int y, T value) {
        if (rows.get(x) == null) {
            HashMap<Integer, T> linkedHashMap = new HashMap<Integer, T>();
            linkedHashMap.put(y, value);
            rows.put(x, linkedHashMap);
        } else {
            rows.get(x).put(y, value);
        }

        if (columns.get(y) == null) {
            HashMap<Integer, T> linkedHashMap = new HashMap<>();
            linkedHashMap.put(x, value);
            columns.put(y, linkedHashMap);
        } else {
            columns.get(y).put(x, value);
        }
    }

    /**
     * @return a list of all non null elements in the sparse matrix
     */
    public List<T> getAllElements() {
        List<T> answer = new ArrayList<>();
        for (HashMap<Integer, T> a: rows.values()) {
            answer.addAll(a.values());
        }
        return answer;
    }

    /**
     * @return a list of all non null elements in a row of the sparse matrix
     */
    public List<T> getElementsInRow(int x) {
        if (rows.get(x) == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(rows.get(x).values());
    }

    public List<T> getElementsInCol(int y) {
        if (columns.get(y) == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(columns.get(y).values());
    }

    /**
     * Gets an element in the sparse matrix
     */
    public T get(int x, int y) {
        HashMap<Integer, T> linkedHashMap = rows.get(x);

        if (linkedHashMap == null) {
            return null;
        }

        T object = linkedHashMap.get(y);

        if (object == null) {
            return null;
        }

        return object;
    }

    /**
     * Removes an element from the sparse matrix
     */
    public void remove(int x, int y) {
        HashMap<Integer, T> linkedHashMap = rows.get(x);
        if (linkedHashMap != null && linkedHashMap.get(y) != null) {
            linkedHashMap.remove(y);
            if (linkedHashMap.size() == 0) {
                rows.remove(x);
            }
        }

        linkedHashMap = columns.get(y);
        if (linkedHashMap != null && linkedHashMap.get(x) != null) {
            linkedHashMap.remove(x);
            if (linkedHashMap.size() == 0) {
                columns.remove(y);
            }
        }
    }

    /**
     * @return Returns the number of non null elements in the sparse matrix
     */
    public int getSize() {
        return rows.size();
    }

    // test client
    public static void main(String[] args) {
        SparseMatrix<Integer> a = new SparseMatrix<>();

        a.put(0,0,0);
        a.put(1000,1000,1000);
        a.put(100000,100000,100000);
        a.put(1000000,1000000,1000000);
        a.put(10000000,10000000,10000000);
        a.put(100000000,100000000,100000000);

        System.out.println(a.getAllElements());
        System.out.println(a.getElementsInRow(0));
        System.out.println(a.getElementsInCol(0));
        System.out.println(a.getSize());

        a.remove(100000000, 100000000);
        System.out.println(a.getAllElements());
        System.out.println(a.getElementsInRow(100000000));
        System.out.println(a.getElementsInCol(100000000));
        System.out.println(a.getSize());
    }
}
