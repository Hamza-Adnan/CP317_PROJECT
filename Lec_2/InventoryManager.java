// filepath: /home/hamza/new317/CP317_PROJECT/Lec_2/InventoryManager.java
package Lec_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {

    public static void main(String[] args) {
        System.out.println("Starting InventoryManager...");

        // Update to your path:
        String productFile = "ProductFile.txt";
        String supplierFile = "SupplierFile.txt";
        // Output file location:
        String outputFile = "InventoryFile.txt";

        connectFiles(productFile, supplierFile, outputFile);
    }

    public static void connectFiles(String productFile, String supplierFile, String outputFile) {
        System.out.println("Connecting files...");
        System.out.println("  Product file:  " + productFile);
        System.out.println("  Supplier file: " + supplierFile);
        System.out.println("  Output file:   " + outputFile);

        // Load supplier data.
        Map<String, Supplier> supplierMap = loadSuppliers(supplierFile);

        // Load products and combine with supplier details.
        List<Product> productList = loadProducts(productFile, supplierMap);

        // Write the final output file (sorted by product ID).
        writeInventoryFile(outputFile, productList);
    }

    private static Map<String, Supplier> loadSuppliers(String fileName) {
        Map<String, Supplier> suppliers = new HashMap<>();
        File supplierFile = new File(fileName);
        System.out.println("Loading suppliers from: " + supplierFile.getAbsolutePath());

        try (BufferedReader br = new BufferedReader(new FileReader(supplierFile))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                // Debug print of each line.
                System.out.println("[SUPPLIER] Raw line " + lineCount + ": " + line);
                String[] parts = line.split("\\s*,\\s*");
                if (parts.length == 5) {
                    Supplier supplier = new Supplier(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    suppliers.put(parts[0], supplier);
                    System.out.println("   -> Added supplier: " + supplier.toString());
                } else {
                    System.out.println("   -> Skipped malformed supplier line: " + line);
                }
            }
            System.out.println("Finished loading suppliers. Total lines read: " + lineCount);
            System.out.println("Total suppliers stored: " + suppliers.size());
        } catch (IOException e) {
            System.out.println("Error reading supplier file: " + e.getMessage());
        }
        return suppliers;
    }

    private static List<Product> loadProducts(String fileName, Map<String, Supplier> suppliers) {
        List<Product> products = new ArrayList<>();
        File productFile = new File(fileName);
        System.out.println("Loading products from: " + productFile.getAbsolutePath());

        try (BufferedReader br = new BufferedReader(new FileReader(productFile))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                System.out.println("[PRODUCT] Raw line " + lineCount + ": " + line);
                String[] parts = line.split("\\s*,\\s*");
                if (parts.length == 7) {
                    try {
                        // Remove $ from the price
                        String priceString = parts[3].replace("$", "");
                        double price = Double.parseDouble(priceString);

                        Product product = new Product(parts[0], // productID
                                parts[1], // productName
                                parts[2], // description
                                price, // price
                                Integer.parseInt(parts[4]), // quantity
                                parts[5], // status
                                parts[6] // supplierID
                        );

                        // Look up the supplier
                        Supplier s = suppliers.get(parts[6]);
                        if (s != null) {
                            product.setSupplierName(s.getName());
                            product.setSupplierAddress(s.getAddress());
                            product.setSupplierPhone(s.getPhone());
                            product.setSupplierEmail(s.getEmail());
                        } else {
                            product.setSupplierName("Unknown");
                            product.setSupplierAddress("Unknown");
                            product.setSupplierPhone("Unknown");
                            product.setSupplierEmail("Unknown");
                        }
                        System.out.println("   -> Created product: " + product.toString());
                        products.add(product);
                    } catch (NumberFormatException e) {
                        System.out.println("   -> Number format error on line: " + line);
                    }
                } else {
                    System.out.println("   -> Skipped malformed product line: " + line);
                }
            }
            System.out.println("Finished loading products. Total lines read: " + lineCount);
            System.out.println("Total valid products stored: " + products.size());
        } catch (IOException e) {
            System.out.println("Error reading product file: " + e.getMessage());
        }

        Collections.sort(products);
        return products;
    }

    private static void writeInventoryFile(String fileName, List<Product> products) {
        File file = new File(fileName);
        System.out.println("Writing inventory to: " + file.getAbsolutePath());

        // Create directories if they don't exist.
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("Created directory: " + parentDir.getAbsolutePath());
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Product product : products) {
                System.out.println("   -> Writing: " + product.toString());
                bw.write(product.toString());
                bw.newLine();
            }
            System.out.println("Inventory file created successfully!");
        } catch (IOException e) {
            System.out.println("Error writing inventory file: " + e.getMessage());
        }
    }
}