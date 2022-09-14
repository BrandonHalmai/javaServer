package application; //This refers to the folder that is currently holding the class; this is so classes can access our
//public methods, i.e. our main method.

//These are imported libraries that are not built into java, so we import them to utilise their functionalities.
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import application.Invoice.Product;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;


/**
Here is the MenuThread class that inherits the Thread library. Here we'll use the MenuThread method to execute other
methods when they are called; these methods are called report, delete and exit. By using the Thread library we're
able to use multiple threads for our application. It starts by displaying the options and when the letter
that corresponds to the method is entered, it executes that method; if any other letter is entered, a message is
returned stating 'Invalid Task'.
 */
public class MenuThread extends Thread {
    public void run() {
        while (true) {
            System.out.println("Enter 'R' to generate report, the total of all invoices saved");
            System.out.println("Enter 'D' to delete all invoices from server");
            System.out.println("Enter 'E' to terminate server");

            Scanner scanner = new Scanner(System.in);
            System.out.println("\nEnter the task to perform");
            String task = scanner.nextLine().toUpperCase();

            switch (task) {
                case "R":
                    System.out.println(report());
                    break;
                case "D":
                    delete();
                    break;
                case "E":
                    System.exit(0);
                default:
                    System.out.println("Invalid Task\n");
            }
        }
    }
//Here the report method will display all the invoices for the server.
    private String report() {
        ArrayList<Product> products = new ArrayList<Product>(); //Here we're defining a new ArrayList as 'products'.
        double total = 0; //Here we're defining 'total' as a floating point to hold the value zero.
        Invoice invoice = new Invoice(products, total); //Using the object 'Invoice' we can utilise the custom methods
        //we've built for the class.
        List<Invoice> invoices = new ArrayList<Invoice>(); //Creating a list that holds our objects of 'Invoice' named
        //'invoices' to hold our invoices.

        String name = null; //String variable that holds no data; this is because it's a dynamic variable, meaning
        //it will change often.
        double price = 0; //Floating point variable named price.

        Product product = new Product(name, price); //Creating a new 'Product' called 'product'. It will take in both
        //the name and price parameters defined above.
        System.out.println("REPORT\n"); //This will print out the word 'REPORT' and a new line.

        String path = System.getProperty("user.home") + File.separator + "Invoices"; //Here we're creating a new
        //variable that will store the path to which the invoices will be saved at.

        File directory = new File(path); //Here we are assigning the directory to be equal to the path variable.
        File filelist[] = directory.listFiles(); //Here this 'filelist' array will hold the names of all the files in
        //the current directory.


        /**Here the for loop will first traverse the 'filelist' array and store the names of files in the directory in
        lowercase to the variable 'readThisFile'. It then checks if the extension is equal to '.json'; if true, a
        Gson object is created to translate the .json file to an object, so it can be later be printed out. The same
        is done to the '.xml' file extension. After the translation is complete, a return statement is created stating
        the amount of invoices there are in the list and the total price of the invoices.
         */

        for (File file:filelist) {
            String readThisFile = file.getName().toLowerCase();

            if (readThisFile.endsWith(".json")) {
                try {
                    Gson gson = new Gson();
                    URI uri = file.toURI();
                    Reader reader = Files.newBufferedReader(Paths.get(uri));
                    JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

                    reader.close();

                    products = new ArrayList<Product>();

                    JsonArray jsonArray = jsonObject.get("invoice").getAsJsonObject().get("products").getAsJsonObject().get("product").getAsJsonArray();

                    for (JsonElement element : jsonArray) {
                        JsonObject elementAsJsonObject = element.getAsJsonObject();
                        name = elementAsJsonObject.get("name").getAsString();
                        price = elementAsJsonObject.get("price").getAsDouble();

                        product = new Product(name, price);
                        products.add(product);
                    }

                    total = jsonObject.get("invoice").getAsJsonObject().get("total").getAsDouble();

                    invoice = new Invoice(products, total);
                    invoices.add(invoice);
                    System.out.println(invoice.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (readThisFile.endsWith(".xml")) {
                try {

                    Document xml = null;
                    xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

                    NodeList nameList = xml.getElementsByTagName("name");
                    NodeList priceList = xml.getElementsByTagName("price");
                    products = new ArrayList<Product>();
                    for (int i = 0; i < nameList.getLength(); i++) {
                        name = nameList.item(i).getTextContent();
                        price = Double.parseDouble(priceList.item(i).getTextContent());

                        product = new Product(name, price);
                        products.add(product);
                    }
                    NodeList nodeList = xml.getElementsByTagName("total");
                    total = Double.parseDouble(nodeList.item(0).getTextContent());

                    invoice = new Invoice(products, total);
                    invoices.add(invoice);
                    System.out.println(invoice.toString());
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }

            }
            total = 0;
            for (Invoice i : invoices) {
                total += i.getTotal();
            }
            return "The total amount for " + invoices.size() + " invoices is $" + String.format("%.2f", total) + "\n";


        //return "";
    }

    /**
    Here the delete method will be used to delete invoices saved into the directory. It first warns the user that
    file extensions ending with .xml and .json will be deleted from the current working directory. It then prints the
    names of all the files that will be deleted if the user proceeds.
    */
    public void delete() {
        String path = System.getProperty("user.home") + File.separator + "Invoices";
        System.out.println("WARNING! YOU HAVE CHOSEN TO DELETE ALL INVOICES.\n" + "THIS WILL DELETE ALL FILES ENDING WITH .xml OR .json FROM " + path);
        System.out.println("THE FOLLOWING FILES WILL BE DELETED: \n");

        File directory = new File(path);
        File fileList[] = directory.listFiles();

        for (File file:fileList) {
            String fileToDelete = file.getName().toLowerCase();
            if (fileToDelete.endsWith(".xml")|fileToDelete.endsWith(".json")) {
                System.out.println(fileToDelete);
            }
        }

        /**
        If the user enters the word 'DELETE' in uppercase characters after the first warning, the files will be
        deleted and a string will be returned stating that the invoices have been deleted. However, if the user
        enters anything other than the word 'DELETE', the operation is cancelled.
        System.out.println("TYPE 'DELETE' AND PRESS ENTER TO PROCEED OR PRESS ENTER TO CANCEL");
        */

        Scanner scanner = new Scanner(System.in);
        String delete = scanner.nextLine().toUpperCase();
        if(delete.equals("DELETE")) {
            for (File file:fileList) {
                String fileToDelete = file.getName().toLowerCase();
                if (fileToDelete.endsWith(".xml")|fileToDelete.endsWith(".json")) {
                    file.delete();
                }
            }
            System.out.println("All invoices deleted\n");
        } else {
            System.out.println("Operation cancelled.No invoices have been deleted\n");
        }


    }
}
