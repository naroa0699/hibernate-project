package com.acc.datos.hibernate_project.util;

import com.acc.datos.hibernate_project.servicios.SetupService55;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

//@Component
public class AppRunner55 implements CommandLineRunner {

    @Autowired
    private SetupService55 setupService55;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("=============================================================");
        System.out.println("APLICACIÓN ARRANCADA. EJECUTANDO LÓGICA INICIAL...");
        System.out.println("=============================================================");

        try {
            setupService55.crearEstructuraNaroa();
        } catch (DataIntegrityViolationException e) {
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.err.println("ERROR DE INTEGRIDAD DE DATOS: No se pudieron guardar los datos.");
            System.err.println("Causa: " + e.getMostSpecificCause().getMessage());
            System.err.println("Acción: La transacción ha sido deshecha (ROLLBACK) por Spring.");
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }

        System.out.println("=============================================================");
        System.out.println("LÓGICA INICIAL FINALIZADA. La aplicación está lista.");
        System.out.println("=============================================================");
    }
}