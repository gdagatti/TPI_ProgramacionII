package config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
     private static final String PROPERTIES_PATH = "config/db.properties";

    private DatabaseConnection() {
        // Constructor privado para que no se pueda instanciar
    }

    public static Connection getConnection() throws SQLException {
        try {
            // Cargar propiedades desde el archivo
            Properties props = new Properties();
            ClassLoader classLoader = DatabaseConnection.class.getClassLoader();
            InputStream input = classLoader.getResourceAsStream(PROPERTIES_PATH);

            if (input == null) {
                throw new SQLException("No se encontró el archivo de propiedades: " + PROPERTIES_PATH);
            }

            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            // Registrar el driver de MySQL 
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.err.println("Driver MySQL no encontrado. Verificá que agregaste el .jar al proyecto.");
            }

            // Devolver la conexión
            return DriverManager.getConnection(url, user, password);

        } catch (IOException e) {
            throw new SQLException("Error leyendo el archivo de propiedades de la BD", e);
        }
    }
}
