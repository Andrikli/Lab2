package app;

import customers.Customer;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       System.out.println("Введіть кількість клієнтів: ");
       int n = sc.nextInt();;
       sc.nextLine();
       Customer[] customers =  new Customer[n];
       for (int i = 0; i < n; i++) {
           System.out.println("\nВведення даних для клієнта "+ (i+1));
           System.out.print("ID: ");
           int id = sc.nextInt();
           sc.nextLine();
           System.out.print("Прізвище: ");
           String lastName = sc.nextLine();
           System.out.print("Ім'я: ");
           String firstName = sc.nextLine();
           System.out.print("По батькові: ");
           String middleName = sc.nextLine();
           System.out.print("Адреса: ");
           String address = sc.nextLine();
           System.out.print("Номер картки: ");
           String cardNumber = sc.nextLine();
           System.out.print("Баланс: ");
           double balance = sc.nextDouble();
           sc.nextLine();
           customers[i] = Customer.builder()
                   .setId(id)
                   .setLastName(lastName)
                   .setFirstName(firstName)
                   .setMiddleName(middleName)
                   .setAddress(address)
                   .setCardNumber(cardNumber)
                   .setBalance(balance)
                   .build();
       }
       int choice;
       do{
           System.out.println("\nМеню");
           System.out.println("1. Пошук за ім'ям");
           System.out.println("2. Пошук за інтервалом нормерів карток");
           System.out.println("3. Пошук боржника");
           System.out.println("0. Вихід");
           choice = sc.nextInt();
           sc.nextLine();
           switch (choice) {
               case 1 -> {
                   System.out.print("Введіть ім'я для пошуку: ");
                   String name = sc.nextLine();
                   findByFirstName(customers, name);
               }
               case 2 -> {
               System.out.print("Мінімальний номер картки: ");
               int min = sc.nextInt();
               System.out.print("Максимальний номер картки: ");
               int max = sc.nextInt();
               sc.nextLine();
               findByCardRange(customers, min, max);
               }
               case 3 ->{
                   findWithMinus(customers);
               }
               case 0 -> {
                   System.out.println("Вихід з програми...");
               }
                   default -> System.out.println("Невірний вибір!");
           }

       }while (choice != 0);
       sc.close();
    }
    public static void findByFirstName(Customer[] customers, String name) {
        boolean found = false;
        for (Customer c : customers) {
            if (c.getFirstName().equalsIgnoreCase(name)) {
                System.out.println(c);
                found = true;
            }
        }
        if (!found) System.out.println("Клієнтів з ім'ям " + name + " не знайдено.");
    }
    public static void findByCardRange(Customer[] customers, int min, int max) {
        boolean found = false;
        for (Customer c : customers) {
            try {
                int card = Integer.parseInt(c.getCardNumber());
                if (card >= min && card <= max) {
                    System.out.println(c);
                    found = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Некоректний номер карти: " + c.getCardNumber());
            }
        }
        if (!found) System.out.println("Клієнтів у цьому інтервалі не знайдено.");
    }

    public static void findWithMinus(Customer[] customers) {
        int count = 0;
        for (Customer c : customers) {
            if (c.getBalance() < 0) {
                System.out.println(c);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("Боржників немає.");
        } else {
            System.out.println("Загальна кількість боржників: " + count);
        }
    }
}

