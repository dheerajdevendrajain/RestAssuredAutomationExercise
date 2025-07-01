package stepdefs;

import org.jasypt.util.text.BasicTextEncryptor;

public class EncryptProperty {
    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("myEncryptionPassword"); // THIS MUST BE A SECURE PASSWORD AND NOT HARDCODED IN REAL APPS
        String myPassword = "superSecretPassword123";
        String encryptedPassword = textEncryptor.encrypt(myPassword);
        System.out.println("Encrypted Password: " + encryptedPassword);
    }
}
