# Warehouse Inventory & Order Priority System

This is my final project for Software Design, Architecture, and Testing. It is a Spring Boot backend that isfor a warehouse system that manages products, customers, and orders, and it applies two of the data structures concepts from class: a binary search tree for order priority, and a manually written sorting algorithm for products.

## What it does

- It is a CRUD endpoints for products, customers, and orders that is purely backed by MySQL through Spring Data JPA
- A binary search tree that organizes orders by their `priorityLevel`, so that I can quickly find the highest priority order, the lowest priority order, within the list or get every order back within the sorted priority order
- A manual insertion sort (no `Collections.sort()` / `Stream.sorted()`) is used to sort the products by price or by the stock

## Tech stack

- Java 17
- Spring Boot 3.2 (Web, Data JPA, Validation)
- MySQL
- JUnit 5 for unit tests
- Maven

## Project structure

```
src/main/java/com/warehouse/system/
├── entity/         Product, Customer, Order, OrderItem
├── repository/      Spring Data JPA repositories
├── bst/            OrderNode + OrderBST (my hand-written binary search tree)
├── service/         business logic, including SortUtil (my manual insertion sort)
├── controller/       REST controllers
├── dto/            request bodies for creating orders
└── exception/       custom exceptions + a global exception handler
```

## Running it locally

1. Create a MySQL database or you can let it auto-create see `application.properties`, it uses `createDatabaseIfNotExist=true`
2. Update `src/main/resources/application.properties` with your MySQL username/password
3. `mvn spring-boot:run`
4. The API comes up on `http://localhost:8080`

## Endpoints

### Products
| Method | Endpoint | Description |
|---|---|---|
| GET | `/products` | get all products |
| POST | `/products` | create a product |
| GET | `/products/sorted?by=price` | products sorted by price (manual insertion sort) |
| GET | `/products/sorted?by=stock` | products sorted by stock (manual insertion sort) |

### Customers
| Method | Endpoint | Description |
|---|---|---|
| GET | `/customers` | get all customers |
| POST | `/customers` | create a customer (needed before you can create an order) |

### Orders
| Method | Endpoint | Description |
|---|---|---|
| POST | `/orders` | create an order (also inserts it into the priority BST) |
| GET | `/orders` | get all orders |
| POST | `/orders/add-to-priority-tree?orderId=1` | manually (re)insert an existing order into the tree |
| GET | `/orders/priority/inorder` | all orders, sorted by priority (BST inorder traversal) |
| GET | `/orders/priority/highest` | the highest priority order in the tree |
| GET | `/orders/priority/lowest` | the lowest priority order in the tree |

## Sample request bodies

**POST /customers**
```json
{
  "name": "Jamie Wilson",
  "email": "jamie@example.com"
}
```

**POST /products**
```json
{
  "name": "Cordless Drill",
  "price": 89.50,
  "stock": 25
}
```

**POST /orders**
```json
{
  "customerId": 1,
  "priorityLevel": 7,
  "items": [
    { "productId": 1, "quantity": 2 }
  ]
}
```

## How the BST works

`OrderBST` this inserts new orders based on their `priorityLevel`. Lower priority will go left, while higher priority ones will go right and since the priority levels can repeat I had decided to send equal values right along with the higher ones so more on that reasoning in `ANSWERS.md`. Because of how a BST is structured walking it inorder (left, node, right) always visits nodes from lowest to highest so that `inorder()` gives me the orders back fully sorted by priority without any extra separate sorting steps.

## How the sorting works

`SortUtil.insertionSort()` is a manual insertion sort that takes a `Comparator<Product>` so that I can reuse the exact same algorithm for sorting by the price and by the stock instead of have to write it up twice it just walks through the list one single item at a time and shifts everything bigger than the current item over by one slot until it finds the right place to drop it in.

## Testing

Unit tests live in `src/test/java` and cover the BST (sorted traversal, highest/lowest, duplicate priorities, empty-tree edge cases) and the sorting utility (sorting by price and by stock).

## Notes

The priority tree is in memory and it rebuilds itself from the database on startup, but it isn't the source of truth to be honest the database is and the tree just exists to demonstrate the data structure and give fast highest/lowest/sorted lookups.
