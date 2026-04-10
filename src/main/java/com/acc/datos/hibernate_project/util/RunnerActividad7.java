package com.acc.datos.hibernate_project.util;

import com.acc.datos.hibernate_project.servicios.ServiceActividad7;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(7)
public class RunnerActividad7 implements CommandLineRunner {

    @Autowired
    private ServiceActividad7 serviceActividad7;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n===============================================");
        System.out.println("          ACTIVIDAD 5.7 - INICIO");
        System.out.println("===============================================\n");

        serviceActividad7.crearProyectosConSede();
        serviceActividad7.insertarDatosEmpleados();
        serviceActividad7.verificarDatosCreados();

        System.out.println("===============================================");
        System.out.println("          ACTIVIDAD 5.7 - FIN");
        System.out.println("===============================================\n");
    }
}
