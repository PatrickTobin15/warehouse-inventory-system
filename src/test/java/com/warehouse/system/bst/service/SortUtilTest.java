package com.warehouse.system.service;

import com.warehouse.system.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortUtilTest {

    @Test
    void sortsProductsByPriceAscending() {
        List<Product> products = new ArrayList<>(List.of(
                new Product("Hammer", 25.99, 10),
                new Product("Nail", 0.10, 500),
                new Product("Drill", 89.50, 4)
        ));

        SortUtil.insertionSort(products, Comparator.comparingDouble(Product::getPrice));

        assertEquals("Nail", products.get(0).getName());
        assertEquals("Hammer", products.get(1).getName());
        assertEquals("Drill", products.get(2).getName());
    }

    @Test
    void sortsProductsByStockAscending() {
        List<Product> products = new ArrayList<>(List.of(
                new Product("Hammer", 25.99, 10),
                new Product("Nail", 0.10, 500),
                new Product("Drill", 89.50, 4)
        ));

        SortUtil.insertionSort(products, Comparator.comparingInt(Product::getStock));

        assertEquals("Drill", products.get(0).getName());
        assertEquals("Hammer", products.get(1).getName());
        assertEquals("Nail", products.get(2).getName());
    }
}
