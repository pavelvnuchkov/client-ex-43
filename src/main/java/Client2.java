import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        try (Socket client = new Socket("localhost", Integer.parseInt(new BufferedReader(new FileReader("src/main/resources/settings.txt")).readLine()));
             Logger logger = new Logger("client2")) {
            System.out.println("Клиент запустился!");
            OutputStream outputStream = client.getOutputStream();
            InputStream inputStream = client.getInputStream();
            registration(System.in, outputStream, inputStream, logger);
            Thread thread = new Thread(() -> {
                resavingMessage(inputStream, logger);
            });
            thread.start();

            sendingMessage(System.in, outputStream, logger);
            thread.join();
        } catch (Exception e) {
        }
    }

    public static void registration(InputStream input, OutputStream outputStream, InputStream inputStream, Logger logger) throws IOException {
        Scanner scanner = new Scanner(input);
        PrintWriter writer = new PrintWriter(outputStream, true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println(reader.readLine());
        String name = scanner.nextLine();
        writer.println(name);
        logger.addMessage(name);
    }

    public static void sendingMessage(InputStream inputStream, OutputStream outputStream, Logger logger) throws IOException {
        Scanner scanner = new Scanner(inputStream);
        PrintWriter writer = new PrintWriter(outputStream, true);

        while (true) {
            String message = scanner.nextLine();
            logger.addMessage(message);
            if (message.equals("/exit")) {
                writer.println(message);
                break;
            } else if (message != null) {
                writer.println(message);
            }
        }
    }

    public static void resavingMessage(InputStream inputStream, Logger logger) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (true) {
                String message = reader.readLine();

                if (message.equals("/exit")) {
                    logger.addMessage("Вышли из чата");
                    break;
                }
                logger.addMessage(message);
                System.out.println(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
