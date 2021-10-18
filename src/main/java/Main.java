import java.security.spec.RSAOtherPrimeInfo;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator();
        List<Customer> calendars = dataGenerator.getCustomers();
        List<Product> products = dataGenerator.getProducts();
        List<Order> orders = dataGenerator.getOrders();

        //01 Obtain a list of products belongs to category "Books" with price > 100
        List<Product> solution01 = products.stream()
                .filter(p -> p.getCategory().equals("Books")
                        && p.getPrice() > 100)
                .collect(Collectors.toList());
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
                .collect(Collectors.toList());
        for (Order order : solution02) {
            System.out.println(order);
        }
        System.out.println();

        //03 Obtain a list of product with category = "Toys" and then apply 10% discount
        List<Product> solution03 = products.stream()
                .filter(p -> p.getCategory().equals("Toys"))
                .map(p -> p.getProductWithDiscount(0.1))
                .collect(Collectors.toList());

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
                .collect(Collectors.toList());
        for (Order order : solution06) {
            System.out.println(order);
        }
        System.out.println();

        //07 Get a list of orders which were ordered on 15-Mar-2021, log the order records to the console and then return its product list


        //08 Calculate total sum of all orders placed in Feb 2021

        //09 Calculate order average payment placed on 14-Mar-2021

        //10 Obtain a collection of statistic figures
        // (i.e. sum, average, max, min, count) for all products of category "Books" look DoubleSummaryStatistics

        //11 Obtain a data map with order id and order's product count

        //12 Produce a data map with order records grouped by customer


        //13 Produce a data map with order record and product total sum

        //14 Obtain a data map with list of product name by category

        //15 Get the most expensive product by category

    }
}



