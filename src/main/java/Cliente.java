import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) throws IOException {

    Socket socket;
    DataInputStream input;
    DataOutputStream output;

        String host = "192.168.1.5";
        int port = 5000;

        socket = new Socket(host, port);

        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        Scanner in = new Scanner(System.in);
        String numeroTel = null;


            String mensaje  = input.readUTF();
            System.out.println(mensaje);

            numeroTel = in.next();
            output.writeUTF(numeroTel);

        System.out.println("---------------------------------------");
        System.out.println(input.readUTF());
        System.out.println("---------------------------------------");
        System.out.println(input.readUTF());

    }

}