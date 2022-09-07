package application; //This refers to the folder that is currently holding the class; this is so classes can access our
//public methods, i.e. our main method.

//These are imported libraries that are not built into java, so we import them to utilise their functionalities.

import java.util.ArrayList;
//Here we are creating the Invoice object so that we can utilise it throughout our application.
public class Invoice {
    private ArrayList<Product> products; //Here we are defining a private ArrayList as we don't want other classes
    //to utilise this variable. This is because we don't want to affect the output with the same keywords.
    private double total; //Same as the above, we don't want other classes to access our total variable.

    //Here we've created our constructor for the Invoice class so that we can reference both the products and total
    //variables in our code.
    public Invoice(ArrayList<Product> products, double total) {
        this.products = products;
        this.total = total;
    }
    public ArrayList<Product> getProducts() {
        return products;
    } //Here is a simple method of when we call 'getProducts'
    //we return the value of products.

    //Here is another method that assigns the Invoice's products to the products variable when 'setProducts' is called.
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    //Another method that returns a floating point value of the variable total when 'getTotal' is called.
    public double getTotal() {
        return total;
    }

    //Another method that uses the previous total variable and assigns that to the Invoice's total when the setTotal method
    //is called.
    public void setTotal(double total) {
        this.total = total;
    }

    //Another method that returns a string when 'toString' is called. This method will loop all the items in the
    //ArrayList 'products' and print out the invoice total for all products.
    public String toString() {
        for (Product p : products) {
            System.out.println(p.toString());
        }
        return "Invoice Total: $" + String.format("%.2f", getTotal()) + "\n";
        }

    //Here we're defining a new class called 'Product'. This will have private constructors of name and price as well
    //some unique methods.
    static class Product {
        private String name;
        private double price;
        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        //The first method here is the 'getName' method that returns the variable 'name' when 'getName' is called.
        public String getName() {
            return name;
        }

        //The second method assigns the Product's name with the variable name when setName is called.
        public void setName(String name) {
            this.name = name;
        }

        //The third method returns the variable price when the 'getPrice' method is called.
        public double getPrice() {
            return price;
        }

        //The forth method assigns the Product's price with the variable price when 'setPrice' method is called.
        public void setPrice(double price) {
            this.price = price;
        }

        //The last method returns a string of the name with its price when the 'toString' method is called.
        public String toString() {
            return getName() + ": " + "$" + String.format("%.2f", getPrice());
        }
    }
}


