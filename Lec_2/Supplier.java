// filepath: /home/hamza/new317/CP317_PROJECT/Lec_2/Supplier.java
package Lec_2;

public class Supplier {
    private String supplierID;
    private String name;
    private String address;
    private String phone;
    private String email;

    public Supplier(String supplierID, String name, String address, String phone, String email) {
        this.supplierID = supplierID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "SupplierID: " + supplierID + ", Name: " + name + ", Address: " + address + ", Phone: " + phone
                + ", Email: " + email;
    }
}