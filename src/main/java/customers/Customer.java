package customers;

public class Customer {
    private int id;
    private String lastName;
    private String firstName;
    private String midleNAme;
    private String Address;
    private String cardNumber;
    private double balance;

    public Customer(String lastName, String firstName, String midleNAme, String Address, String cardNumber, double balance) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.midleNAme = midleNAme;
        this.Address = Address;
        this.cardNumber = cardNumber;
        this.balance = balance;
    }
    public static class CustomerBuilder {
        private int id;
        private String lastName;
        private String firstName;
        private String midleNAme;
        private String Address;
        private String cardNumber;
        private double balance;

        public CustomerBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public CustomerBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CustomerBuilder setMidleNAme(String midleNAme) {
            this.midleNAme = midleNAme;
            return this;
        }

        public CustomerBuilder setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public CustomerBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public CustomerBuilder setAddress(String address) {
            Address = address;
            return this;
        }

        public CustomerBuilder setBalance(double balance) {
            this.balance = balance;
            return this;
        }
    }
    public static CustomerBuilder builder(){
        return new CustomerBuilder();
    }
}
