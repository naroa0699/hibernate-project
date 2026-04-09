package com.acc.datos.hibernate_project.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
/**
 * Esta clase sirve como una prueba de arranque para verificar la conexión a la base de datos.
 * Al implementar CommandLineRunner, el método 'run' se ejecutará automáticamente
 * después de que la aplicación Spring Boot se haya iniciado.
 */
//@Component // Le dice a Spring que esta clase es un componente que debe gestionar.
public class VerificadorConexion implements CommandLineRunner {
    // @Autowired le pide a Spring que nos "inyecte" o proporcione el objeto DataSource
    // que ya ha sido configurado automáticamente a partir de application.properties.
    @Autowired
    private DataSource dataSource;
    @Override
    public void run(String... args) throws Exception {

        System.out.println("====================================================================");
                System.out.println("INICIANDO PRUEBA DE CONEXIÓN A LA BASE DE DATOS...");

        System.out.println("====================================================================");
                // Usamos un bloque try-with-resources para asegurar que la conexión se cierre siempre.
        try (Connection connection = dataSource.getConnection()) {
            if (connection != null) {
                System.out.println("¡CONEXIÓN ESTABLECIDA CON ÉXITO!");
                DatabaseMetaData metaData = connection.getMetaData();
                System.out.println(" -> URL de la BBDD: " + metaData.getURL());
                System.out.println(" -> Usuario: " + metaData.getUserName());
                System.out.println(" -> Driver: " + metaData.getDriverName());
                System.out.println("\n--- OBTENIENDO ESQUEMA DE TABLAS ---");
                // Obtenemos las tablas del catálogo/esquema actual.
                ResultSet tables = metaData.getTables(connection.getCatalog(), null, "%", new
                        String[]{"TABLE"});
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    System.out.println("\n[TABLA]: " + tableName.toUpperCase());
                    // Para cada tabla, obtenemos y listamos sus columnas.
                    ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName,
                            "%");
                    while (columns.next()) {
                        String columnName = columns.getString("COLUMN_NAME");
                        String columnType = columns.getString("TYPE_NAME");
                        int columnSize = columns.getInt("COLUMN_SIZE");
                        System.out.printf(" - Columna: %-25s | Tipo: %-15s | Tamaño: %d%n",
                                columnName, columnType, columnSize);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR AL CONECTAR CON LA BASE DE DATOS: " +
                    e.getMessage());
        }

        System.out.println("\n====================================================================");
                System.out.println("PRUEBA DE CONEXIÓN FINALIZADA. La aplicación continuará su arranque.");

                        System.out.println("====================================================================");
    }
}