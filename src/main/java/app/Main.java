package app;
import java.util.ArrayList;
import java.util.List;
import customers.Customer;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Customer> customers;

        System.out.println("Завантажити дані з файлу? : ");
        String answer = sc.nextLine().trim().toLowerCase();
        if (answer.equals("так")) {
            customers = loadCustomersFromFile("customers.txt");
            System.out.println("Завантажено клієнтів: " + customers.size());
        } else {
            customers = new ArrayList<>();
            System.out.println("База пуста, додайте нових клієнтів вручну.");
        }
        int choice;
        do{
            System.out.println("\nМеню");
            System.out.println("1. Пошук за ім'ям");
            System.out.println("2. Пошук за інтервалом номерів карток");
            System.out.println("3. Пошук боржника");
            System.out.println("4. Додати нового користувача");
            System.out.println("5. Видалити користувача");
            System.out.println("6. Вивести усіх користувачів");
            System.out.println("7. Відновити користувача");
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
                    long min = sc.nextInt();
                    System.out.print("Максимальний номер картки: ");
                    long max = sc.nextInt();
                    sc.nextLine();
                    findByCardRange(customers, min, max);
                }
                case 3 ->{
                    findWithMinus(customers);
                }
                case 4 ->{
                    Customer newCustomer = inputCustomer(sc, customers);
                    if (newCustomer != null) {
                        customers.add(newCustomer);
                    }
                }
                case 5 ->{
                    removeByFullName(sc, customers);
                }
                case 6 ->{
                    for (Customer c : customers) {
                        if (!c.isDeleted()) {
                            System.out.println(c);
                        }
                    }
                }
                case 7 -> {
                    restoreByFullName(sc, customers);
                }
                case 0 -> {
                    System.out.println("Зберегти зміни у файл перед виходом? (так/ні): ");
                    String save = sc.nextLine().trim().toLowerCase();
                    if (save.equals("так")) {
                        saveCustomersToFile("customers.txt", customers);
                    }
                    System.out.println("Вихід з програми");


                }
                default -> System.out.println("Невірний вибір!");
            }

        }while (choice != 0);
        sc.close();
    }

    public static List<Customer> loadCustomersFromFile(String filename) {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length != 8) continue;

                long id = Long.parseLong(parts[0].trim());
                String lastName = parts[1].trim();
                String firstName = parts[2].trim();
                String middleName = parts[3].trim();
                String address = parts[4].trim();
                String cardNumber = parts[5].trim();
                double balance = Double.parseDouble(parts[6].trim());
                boolean deleted = Boolean.parseBoolean(parts[7].trim());

                customers.add(Customer.builder()
                        .setId(id)
                        .setLastName(lastName)
                        .setFirstName(firstName)
                        .setMiddleName(middleName)
                        .setAddress(address)
                        .setCardNumber(cardNumber)
                        .setBalance(balance)
                        .setDeleted(deleted)
                        .build());
            }
        } catch (IOException e) {
            System.out.println("Помилка при зчитуванні файлу: " + e.getMessage());
        }
        return customers;
    }

    public static void saveCustomersToFile(String filename, List<Customer> customers) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Customer c : customers) {
                pw.printf(Locale.US, "%d,%s,%s,%s,%s,%s,%.2f, %b%n",
                        c.getId(),
                        c.getLastName(),
                        c.getFirstName(),
                        c.getMiddleName(),
                        c.getAddress(),
                        c.getCardNumber(),
                        c.getBalance(),
                        c.isDeleted());
            }
            System.out.println(" Дані збережено у файл: " + filename);
        } catch (IOException e) {
            System.out.println(" Помилка при записі у файл: " + e.getMessage());
        }
    }
    private static Customer inputCustomer(Scanner sc, List<Customer> customers){
        System.out.println("\nВведення даних для клієнта ");
        System.out.print("ID: ");
        long id = sc.nextInt();
        sc.nextLine();

        for (Customer c : customers) {
            if(c!=null){
                if (c.getId() == id) {
                    System.out.println(" Клієнт з таким ID вже існує! Введіть інший.");
                    System.out.println("Введіть ще раз id");
                    return null;
                }
            }

        }
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

        return Customer.builder()
                .setId(id)
                .setLastName(lastName)
                .setFirstName(firstName)
                .setMiddleName(middleName)
                .setAddress(address)
                .setCardNumber(cardNumber)
                .setBalance(balance)
                .build();
    }

    private static void findAndChangeCustomerStatus(Scanner sc, List<Customer> customers, boolean searchStatus,
                                                    String successMessage, String failureMessage) {
        System.out.print("Введіть ID клієнта: ");
        long id = sc.nextLong();
        sc.nextLine();

        boolean found = false;
        for (Customer c : customers) {

            if (c.getId() == id && c.isDeleted() == searchStatus) {
                c.setDeleted(!searchStatus);
                System.out.println(successMessage);
                found = true;
                break;
            }
        }

        if (!found) {

            System.out.println(failureMessage);
        }
    }


    public static void removeByFullName(Scanner sc, List<Customer> customers) {
        findAndChangeCustomerStatus(sc, customers,
                false,
                "Клієнта успішно приховано.",
                "Такого клієнта не знайдено або він вже видалений.");
    }


    public static void restoreByFullName(Scanner sc, List<Customer> customers) {
        findAndChangeCustomerStatus(sc, customers,
                true,
                "Клієнта успішно відновлено.",
                "Видаленого клієнта з таким ПІБ не знайдено.");
    }
    public static void findByFirstName(List<Customer> customers, String name) {
        boolean found = false;
        for (Customer c : customers) {
            if (!c.isDeleted() && c.getFirstName().equalsIgnoreCase(name)) {
                System.out.println(c);
                found = true;
            }
        }
        if (!found) System.out.println("Клієнтів з ім'ям " + name + " не знайдено.");
    }
    public static void findByCardRange(List<Customer> customers, long min, long max) {
        boolean found = false;
        for (Customer c : customers) {
            if(!c.isDeleted()) continue;
            try {

                long card = Long.parseLong(c.getCardNumber().replaceAll("\\D", ""));
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


    public static void findWithMinus(List<Customer> customers) {
        int count = 0;
        for (Customer c : customers) {
            if (!c.isDeleted() && c.getBalance() < 0) {
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

