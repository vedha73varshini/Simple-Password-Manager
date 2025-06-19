import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordManager {
    private static final String CIPHER_ALGORITHM = "AES";
    private static final byte[] AES_KEY = new byte[] { 'M', 'y', 'S', 'e', 'c', 'u', 'r', 'e', 'K', 'e', 'y', '9', '8', '7', '6', '5' };
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
                    String service = sc.nextLine();
                    System.out.print("Enter the password: ");
                    String pwd = sc.nextLine();
                    pm.savePassword(service, pwd);
                    break;
                case 2:
                    System.out.print("Enter the service/site name: ");
                    service = sc.nextLine();
                    String result = pm.fetchPassword(service);
                    System.out.println("Stored Password: " + result);
                    break;
                case 3:
                    System.out.println("Thank you for using Password Manager!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public void savePassword(String site, String password) {
        try {
            String encryptedPwd = encrypt(password);
            vault.put(site, encryptedPwd);
            System.out.println("Password saved successfully.");
        } catch (Exception e) {
            System.err.println("Failed to encrypt and store the password.");
            e.printStackTrace();
        }
    }

    public String fetchPassword(String site) {
        try {
            String encryptedPwd = vault.get(site);
            return encryptedPwd != null ? decrypt(encryptedPwd) : "No password stored for this service.";
        } catch (Exception e) {
            System.err.println("Error while decrypting the password.");
            e.printStackTrace();
            return null;
        }
    }

    private String encrypt(String plainText) throws Exception {
        SecretKeySpec key = new SecretKeySpec(AES_KEY, CIPHER_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private String decrypt(String encryptedText) throws Exception {
        SecretKeySpec key = new SecretKeySpec(AES_KEY, CIPHER_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}
