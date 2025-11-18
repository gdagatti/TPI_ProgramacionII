package test;

import config.DatabaseConnection;
import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Conexión exitosa!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
