import utils.CryptoUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PasswordManager {
    private Map<String, String> vault = new HashMap<>();

    public static void main(String[] args) {
        PasswordManager pm = new PasswordManager();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Password Manager ====");
            System.out.println("1. Save a Password");
            System.out.println("2. View a Password");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int option = sc.nextInt();
            sc.nextLine(); // clear newline

            switch (option) {
                case 1:
                    System.out.print("Enter the service/site name: ");
                    String site = sc.nextLine();
                    System.out.print("Enter the password: ");
                    String pwd = sc.nextLine();
                    try {
                        pm.vault.put(site, CryptoUtils.encrypt(pwd));
                        System.out.println("Password saved successfully.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Enter the service/site name: ");
                    site = sc.nextLine();
                    try {
                        String encrypted = pm.vault.get(site);
                        String decrypted = encrypted != null ? CryptoUtils.decrypt(encrypted) : "No entry found.";
                        System.out.println("Password: " + decrypted);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }
}
