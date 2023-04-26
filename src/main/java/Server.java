import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {
    public static void main(String[] args) throws IOException, SQLException {


//    Conexion Base de Datos y utilidades SQL
        String url = "jdbc:mysql://localhost:3306/poli";
        String username = "root";
        String password = "";
        ResultSet resultSet = null;
        Connection con = DriverManager.getConnection(url, username, password);

//      Variables String
        String respuesta = "";

//        Configuracion variables serverSocket
        final int port = 5000;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socketCliente = null;


        System.out.println("Servidro Iniciado");
        while (true) {

            socketCliente = serverSocket.accept();
            System.out.println("Cliente conectado");

            DataInputStream input = new DataInputStream(socketCliente.getInputStream());
            DataOutputStream output = new DataOutputStream(socketCliente.getOutputStream());

            while (true) {

                respuesta = input.readUTF();
                System.out.println("Cliente: " + respuesta);

                if(respuesta.equals("x")) {
                    System.out.println("Cliente desconectado");
                    socketCliente.close();
                    break;
                }

                String sql = "select p.tel_persona, p.nombre_persona , p.direc_persona, c.nombre_ciudad  from persona p inner join  ciudad c on c.id_ciudad = p.id_cidudad_persona where p.tel_persona  = '" + respuesta + "'";
                Statement statement = con.createStatement();
                resultSet = statement.executeQuery(sql);
                String respuestaConsulta = null;

                if (resultSet.next()) {
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
//                socketCliente.close();
//                System.out.println("Cliente desconectado");
                } else {
                    System.out.println("---------------------------------------");
                    respuestaConsulta = "La persona con ese numero de telefono no existe";
                    System.out.println(respuestaConsulta);
                    output.writeUTF(respuestaConsulta);
                    System.out.println("---------------------------------------");
                }
            }


        }
    }
}




