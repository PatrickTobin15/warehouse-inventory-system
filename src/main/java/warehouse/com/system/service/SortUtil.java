package com.warehouse.system.service;

import com.warehouse.system.entity.Product;

import java.util.Comparator;
import java.util.List;

// my one manual sorting algorithm insertion sort. no Collections.sort() or Stream.sorted() is anywhere within this coding that is here.
public class SortUtil {

    public static void insertionSort(List<Product> products, Comparator<Product> comparator) {
        for (int i = 1; i < products.size(); i++) {
            Product current = products.get(i);
            int j = i - 1;

            // shift everything bigger than the current one spot to the right
            while (j >= 0 && comparator.compare(products.get(j), current) > 0) {
                products.set(j + 1, products.get(j));
                j--;
            }
            products.set(j + 1, current);
        }
    }
}
