import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator();
        List<Customer> customers = dataGenerator.getCustomers();
        List<Product> products = dataGenerator.getProducts();
        List<Order> orders = dataGenerator.getOrders();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        System.out.println();
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println();
        for (Order order : orders) {
            System.out.println(order);
        }
        System.out.println();


        //01 Obtain a list of products belongs to category "Books" with price > 100
        List<Product> solution01 = products.stream()
                .filter(p -> p.getCategory().equals("Books")
                        && p.getPrice() > 100)
                .collect(toList());
        for (Product product : solution01) {
            System.out.println(product);
        }
        System.out.println();

        //02 Obtain a list of orders with products belong to category "Baby"
        List<Order> solution02 = orders.stream()
                .filter(o -> {
                    for (Product product : o.getProducts()) {
                        if (product.getCategory().equals("Baby")) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(toList());
        for (Order order : solution02) {
            System.out.println(order);
        }
        System.out.println();

        //03 Obtain a list of product with category = "Toys" and then apply 10% discount
        List<Product> solution03 = products.stream()
                .filter(p -> p.getCategory().equals("Toys"))
                .map(p -> p.getProductWithDiscount(0.1))
                .collect(toList());

        for (Product product : solution03) {
            System.out.println(product);
        }
        System.out.println();

        //04 Obtain a list of products ordered by customer of tier 2 between 01-Feb-2021 and 01-Apr-2021
        Set<Product> solution04 = orders.stream()
                .filter(o -> o.getCustomer().getTier().equals(2)
                        && o.getOrderDate().isAfter(LocalDate.of(2021, 2, 1))
                        && o.getOrderDate().isBefore(LocalDate.of(2021,4,1)))
                .flatMap(o -> o.getProducts().stream())
                .collect(Collectors.toSet());
        for (Product product : solution04) {
            System.out.println(product);
        }
        System.out.println();

        //05 Get the cheapest products of "Books" category
        Product solution05 = products.stream()
                .filter(p -> p.getCategory().equals("Books"))
                .min((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice())).get();
        System.out.println(solution05);
        System.out.println();

        //06 Get the 3 most recent placed order
        List<Order> solution06 = orders.stream()
                .sorted((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()))
                .limit(3)
                .collect(toList());
        for (Order order : solution06) {
            System.out.println(order);
        }
        System.out.println();

        //07 Get a list of orders which were ordered on 15-Mar-2021, log the order records to the console and then return its product list
        List<Product> solution07 = orders.stream()
                .filter(o -> o.getOrderDate().equals(LocalDate.of(2021, 3, 15)))
                .peek(System.out::println)
                .flatMap(o -> o.getProducts().stream())
                .distinct()
                .collect(toList());
        for (Product product : solution07) {
            System.out.println(product);
        }
        System.out.println();

        //08 Calculate total sum of all orders placed in Feb 2021
        Double solution08 = orders.stream()
                .filter(o -> o.getOrderDate().isAfter(LocalDate.of(2021, 2, 1))
                        && o.getOrderDate().isBefore(LocalDate.of(2021, 3, 1)))
                .flatMap(o -> o.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .sum();
        System.out.println(solution08);
        System.out.println();

        //09 Calculate order average payment placed on 14-Mar-2021
        Double solution09 = orders.stream()
                .filter(o -> o.getDeliveryDate().equals(LocalDate.of(2021, 3, 14)))
                .flatMap(o -> o.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .average().getAsDouble();
        System.out.println(solution09);
        System.out.println();

        //10 Obtain a collection of statistic figures
        // (i.e. sum, average, max, min, count) for all products of category "Books" look DoubleSummaryStatistics
        DoubleSummaryStatistics solution10 = products.stream()
                .filter(p -> p.getCategory().equals("Books"))
                .collect(Collectors.summarizingDouble(Product::getPrice));
        System.out.println(solution10);
        System.out.println();

        //11 Obtain a data map with order id and order's product count
        Map<Long, Integer> solution11 = orders.stream()
                .collect(Collectors.toMap(Order::getId, o -> o.getProducts().size()));
        for (Map.Entry<Long, Integer> entry : solution11.entrySet()) {
            System.out.println(entry);
        }
        System.out.println();

        //12 Produce a data map with order records grouped by customer
        Map<Customer, List<Order>> solution12 = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));
        for (Map.Entry<Customer, List<Order>> entry : solution12.entrySet()) {
            System.out.println(entry);
        }
        System.out.println();

        //13 Produce a data map with order record and product total sum
        Map<Order, Double> solution13 = orders.stream()
                .collect(Collectors.toMap(Function.identity(),
                        o -> {
                            double sum = 0;
                            for (Product product : o.getProducts()) {
                                sum += product.getPrice();
                            }
                            return sum;
                        }));
        for (Map.Entry<Order, Double> entry : solution13.entrySet()) {
            System.out.println(entry);
        }
        System.out.println();

        //14 Obtain a data map with list of product name by category
        Map<String, List<String>> solution14 = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory,
                        Collectors.mapping(Product::getName, toList())));
        for (Map.Entry<String, List<String>> entry : solution14.entrySet()) {
            System.out.println(entry);
        }
        System.out.println();

        //15 Get the most expensive product by category
        Map<String, Optional<Product>> solution15 = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory,
                        Collectors.maxBy(Comparator.comparing(Product::getPrice))));
        for (Map.Entry<String, Optional<Product>> entry : solution15.entrySet()) {
            System.out.println(entry);
        }
        System.out.println();
    }
}



