package com.acc.datos.hibernate_project.util;

import com.acc.datos.hibernate_project.servicios.SetupInicialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException; // ¡Importante!
import org.springframework.stereotype.Component;

//@Component
public class AppRunner implements CommandLineRunner {
    @Autowired
    private SetupInicialService setupInicialService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("====================================================================");
                System.out.println("APLICACIÓN ARRANCADA. EJECUTANDO LÓGICA INICIAL...");

        System.out.println("====================================================================");
        try {
            // Intentamos ejecutar el metodo transaccional.
            setupInicialService.crearDatosDeEjemplo();
        } catch (DataIntegrityViolationException e) {
            // Si algo falla dentro del mét(ej. un DNI duplicado)
            // Spring primero hará ROLLBACK de la transacción.
            // Después, la excepción llegará hasta aquí y podremos manejarla.

            System.err.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.err.println("ERROR DE INTEGRIDAD DE DATOS: No se pudieron guardar los datos.");
                    System.err.println("Causa: " +
                            e.getMostSpecificCause().getMessage());
            System.err.println("Acción: La transacción ha sido deshecha (ROLLBACK) por Spring.");

            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }

        System.out.println("\n====================================================================");
                System.out.println("LÓGICA INICIAL FINALIZADA. La aplicación está lista.");

                        System.out.println("====================================================================");
    }
}
