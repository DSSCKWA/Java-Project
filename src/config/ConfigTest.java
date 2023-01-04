package src.config;

public class ConfigTest {
    public static void main(String[] args) {
        Config config = Config.getConfig();
        System.out.println(config.getEmail());
        System.out.println(config.getPassword());
        System.out.println(config.getSender());
    }
}
