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
        String solicitud = "Si desea salir enviar 'x' \n ¿ Numero de telefono que desea buscar ?";


        socket = new Socket(host, port);

        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        while (true) {

            System.out.println(solicitud);

            Scanner in = new Scanner(System.in);
            String numeroTel = null;

            numeroTel = in.next();

            output.writeUTF(numeroTel);

            if(numeroTel.equals("x")){
                System.out.println("ADIOS");
                break;
            }else {
                System.out.println("---------------------------------------");
                System.out.println(input.readUTF());
                System.out.println("---------------------------------------");
            }
        }
    }
}