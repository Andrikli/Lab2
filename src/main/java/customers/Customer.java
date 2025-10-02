package customers;

public class Customer {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String address;
    private String cardNumber;
    private double balance;

    private Customer() {}

    private Customer(int id, String lastName, String firstName, String middleName,
                     String address, String cardNumber, double balance) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.address = address;
        this.cardNumber = cardNumber;
        this.balance = balance;
    }
    public int getId() { return id; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getAddress() { return address; }
    public String getCardNumber() { return cardNumber; }
    public double getBalance() { return balance; }

    public static CustomerBuilder builder(){
        return new CustomerBuilder();
    }

    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", address='" + address + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
    public static class CustomerBuilder {
        private int id;
        private String lastName;
        private String firstName;
        private String middleName;
        private String address;
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

        public CustomerBuilder setMiddleName(String middleName) {
            this.middleName = middleName;
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
            this.address = address;
            return this;
        }

        public CustomerBuilder setBalance(double balance) {
            this.balance = balance;
            return this;
        }
        public Customer build() {
            Customer customer = new Customer(id, lastName, firstName, middleName, address, cardNumber, balance);
            validate(customer);
            return customer;
        }
        private void validate(Customer customer) {
            if(customer.lastName == null || customer.firstName == null || customer.middleName == null) {
                throw new IllegalArgumentException("\"Ім'я, прізвище і по батькові не можуть бути порожні\"");
            }
        }
    }
}
