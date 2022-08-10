package application;

import java.util.ArrayList;

public class Invoice {
    private ArrayList<Product> products;
    private double total;

    public Invoice(ArrayList<Product> products, double total) {
        this.products = products;
        this.total = total;
    }
    public ArrayList<Product> getProducts() {
        return products;
    }
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public String toString() {
        for (Product p : products) {
            System.out.println(p.toString());
        }
        return "Invoice Total: $" + String.format("%.2f", getTotal()) + "\n";
        }
    static class Product {
        private String name;
        private double price;
        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
        public String toString() {
            return getName() + ": " + "$" + String.format("%.2f", getPrice());
        }
    }
}


