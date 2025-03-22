// filepath: /home/hamza/new317/CP317_PROJECT/Lec_2/Product.java
package Lec_2;

public class Product implements Comparable<Product> {
    private String productID;
    private String productName;
    private String description;
    private double price;
    private int quantity;
    private String status;
    private String supplierID;
    private String supplierName;
    private String supplierAddress;
    private String supplierPhone;
    private String supplierEmail;

    public Product(String productID, String productName, String description, double price, int quantity, String status,
                   String supplierID) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.supplierID = supplierID;
    }

    // Setters for supplier details
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    @Override
    public int compareTo(Product other) {
        return this.productID.compareTo(other.productID);
    }

    @Override
    public String toString() {
        // Combine everything: product details and supplier details.
        return productID + ", " + productName + ", " + description + ", " + price + ", " + quantity + ", " + status
                + ", " + supplierID + ", " + supplierName + ", " + supplierAddress + ", " + supplierPhone + ", "
                + supplierEmail;
    }
}