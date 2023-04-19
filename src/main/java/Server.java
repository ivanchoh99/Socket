import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {
    public static void main(String[] args) throws IOException, SQLException {

        final int port = 5000;


        String solicitud = "¿ Numero de telefono que desea buscar ?";
        String respuesta = null;

        String url = "jdbc:mysql://localhost:3306/poli";
        String username = "root";
        String password = "michi0602";

        Connection con = DriverManager.getConnection(url, username, password);

        ResultSet resultSet = null;


        ServerSocket serverSocket = new ServerSocket(port);
        Socket socketCliente = null;


        System.out.println("Servidro Iniciado");
        while (true) {
            socketCliente = serverSocket.accept();
            System.out.println("Cliente conectado");

            DataInputStream input = new DataInputStream(socketCliente.getInputStream());
            DataOutputStream output = new DataOutputStream(socketCliente.getOutputStream());

            output.writeUTF(solicitud);
            respuesta = input.readUTF();

            String sql = "select p.tel_persona, p.nombre_persona , p.direc_persona, c.nombre_ciudad  from persona p inner join  ciudad c on c.id_ciudad = p.id_cidudad_persona where p.tel_persona  = '"+respuesta+"'";

            Statement statement = con.createStatement();

            resultSet = statement.executeQuery(sql);

            String respuestaConsulta = null;

            if(resultSet.next()) {
                String tel = resultSet.getString("tel_persona");
                String nom = resultSet.getString("nombre_persona");
                String direc = resultSet.getString("direc_persona");
                String ciudad = resultSet.getString("nombre_ciudad");

                System.out.println("---------------------------------------");
                respuestaConsulta = "Telefono: " + tel + "\n" +
                        "Nombre: " + nom + "\n" +
                        "Direccion: " + direc + "\n" +
                        "Ciudad: " + ciudad;
                System.out.println("---------------------------------------");


                System.out.println(respuestaConsulta);
                output.writeUTF(respuestaConsulta);
                output.writeUTF("ADIOS");
                socketCliente.close();
                System.out.println("Cliente desconectado");
            }else{
                System.out.println("---------------------------------------");
                respuestaConsulta = "La persona con ese numero de telefono no existe";
                System.out.println("La persona con ese numero de telefono no existe");
                output.writeUTF(respuestaConsulta);
                System.out.println("---------------------------------------");
                output.writeUTF("ADIOS");
                socketCliente.close();
                System.out.println("Cliente desconectado");
            }



        }
    }
}




