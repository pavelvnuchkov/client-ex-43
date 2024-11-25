import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

class ClientTest {
    @Test
    public void registrationTest() {
        String execute = "Павел";
        String plug = "Заглушка";
        try {
            Client.registration(new ByteArrayInputStream(execute.getBytes()), new ByteArrayOutputStream(),
                    new ByteArrayInputStream(plug.getBytes()), new Logger("clientTest"));

            Assertions.assertEquals(execute, new BufferedReader(new FileReader("clientTest.log")).readLine());
            new FileOutputStream("clientTest.log", false).close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void sendingMessageTest() {
        String execute = "/exit";
        try {
            Client.sendingMessage(new ByteArrayInputStream(execute.getBytes()), new ByteArrayOutputStream(), new Logger("clientTest"));
            Assertions.assertEquals(execute, new BufferedReader(new FileReader("clientTest.log")).readLine());
            new FileOutputStream("clientTest.log", false).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void resavingMessage() {
        String execute = "Вышли из чата";
        Client.resavingMessage(new ByteArrayInputStream("/exit".getBytes()), new Logger("clientTest"));
        try {
            Assertions.assertEquals(execute, new BufferedReader(new FileReader("clientTest.log")).readLine());
            new FileOutputStream("clientTest.log", false).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}