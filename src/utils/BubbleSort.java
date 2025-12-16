package utils;

import java.util.List;

import models.Item;

public class BubbleSort {
    public static List<Item> sortByWeight(List<Item> slots) {
        // Copy the list
        List<Item> array = slots;
        // Array size (unsorted range)
        int size = array.size();
        boolean swapped;

        do {
            swapped = false;

            for (int i = 0; i < size - 1; i++) {
                // Compares the two getWeight() values from Item
                if (array.get(i).getWeight() > array.get(i + 1).getWeight()) {
                    // Temporarily store the current item references as temp
                    Item temp = array.get(i);
                    // Swap array[i] and array[i + 1] [references]
                    array.set(i, array.get(i + 1));
                    array.set(i + 1, temp);

                    swapped = true;
                }
            }
            // Unsorted part of the list
            size -= 1;
        } while (swapped);

        return array;
    }

    public static List<Item> sortByName(List<Item> slots) {
        List<Item> array = slots;
        // Array size (unsorted range)
        int size = array.size();
        boolean swapped;

        do {
            swapped = false;

            for (int i = 0; i < size - 1; i++) {
                // Compares the two getName() values from Item
                if (array.get(i).getName().compareTo(array.get(i + 1).getName()) > 0) {
                    // Temporarily store the current item references as temp
                    Item temp = array.get(i);
                    // Swap array[i] and array[i + 1] [references]
                    array.set(i, array.get(i + 1));
                    array.set(i + 1, temp);

                    swapped = true;
                }
            }
            // Unsorted part of the list
            size -= 1;
        } while (swapped);

        return array;
    }

    public static List<Item> sortByType(List<Item> slots) {
        List<Item> array = slots;
        // Array size (unsorted range)
        int size = array.size();
        boolean swapped;

        do {
            swapped = false;

            for (int i = 0; i < size - 1; i++) {
                // Compares the two getType() values from Item
                if (array.get(i).getType().compareTo(array.get(i + 1).getType()) > 0) {
                    // Temporarily store the current item references as temp
                    Item temp = array.get(i);
                    // Swap array[i] and array[i + 1] [references]
                    array.set(i, array.get(i + 1));
                    array.set(i + 1, temp);

                    swapped = true;
                }
            }
            // Unsorted part of the list
            size -= 1;
        } while (swapped);

        return array;
    }
}
