package com.acc.datos.hibernate_project.util;

import com.acc.datos.hibernate_project.servicios.SetupService56;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class AppRunner56 implements CommandLineRunner {

    @Autowired
    private SetupService56 setupService56;

    @Override
    public void run(String... args) {

        System.out.println("=============================================================");
        System.out.println("  ACTIVIDAD 5.6 — Prueba de restricciones de unicidad");
        System.out.println("=============================================================");

        // --- PRUEBA 1: dos sedes con el mismo nombre ---
        try {
            setupService56.crearSedeDuplicada();
        } catch (Exception e) {
            gestionarExcepcion(e);
        }

        // --- PRUEBA 2: dos departamentos con el mismo nombre en la misma sede ---
        try {
            setupService56.crearDepartamentoDuplicadoEnSede();
        } catch (Exception e) {
            gestionarExcepcion(e);
        }

        System.out.println("=============================================================");
        System.out.println("  PRUEBAS FINALIZADAS.");
        System.out.println("=============================================================");
    }

    /**
     * Recorre la cadena de causas de la excepción buscando una
     * ConstraintViolationException de Hibernate.
     *
     * Spring envuelve la excepción original en varias capas:
     *   DataIntegrityViolationException
     *     └─ causa → JdbcSQLIntegrityConstraintViolationException  (o similar)
     *                  └─ causa → ConstraintViolationException  (Hibernate)
     *
     * Por eso NO se puede capturar directamente con catch(ConstraintViolationException).
     * Hay que recorrer la cadena con getCause() hasta encontrarla.
     */
    private void gestionarExcepcion(Exception excepcionRaiz) {

        // Recorremos la cadena de causas
        Throwable causa = excepcionRaiz;
        while (causa != null) {
            if (causa instanceof ConstraintViolationException cve) {
                // ConstraintViolationException encontrada: mostramos info concisa
                System.err.println("┌─ ERROR DE RESTRICCIÓN DE UNICIDAD ─────────────────");
                System.err.println("│  Restricción violada : " + cve.getConstraintName());
                System.err.println("│  Mensaje SQL         : " + cve.getSQLException().getMessage()
                        .split("\n")[0]);   // solo la primera línea, evita el mensaje prolijo
                System.err.println("│  Acción              : ROLLBACK de la transacción.");
                System.err.println("└────────────────────────────────────────────────────");
                return;  // ya procesada, salimos
            }
            causa = causa.getCause();   // subimos un nivel en la cadena
        }

        // Si no era una ConstraintViolationException, mostramos la excepción genérica
        System.err.println("┌─ ERROR INESPERADO ─────────────────────────────────");
        System.err.println("│  Tipo    : " + excepcionRaiz.getClass().getSimpleName());
        System.err.println("│  Mensaje : " + excepcionRaiz.getMessage());
        System.err.println("└────────────────────────────────────────────────────");
    }
}
