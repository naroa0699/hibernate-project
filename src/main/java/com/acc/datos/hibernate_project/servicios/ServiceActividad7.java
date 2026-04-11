package com.acc.datos.hibernate_project.servicios;

import com.acc.datos.hibernate_project.pojos.*;
import com.acc.datos.hibernate_project.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class ServiceActividad7 {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private ProyectoSedeRepository proyectoSedeRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoDatosProfRepository datosProfRepository;

    // -------------------------------------------------------
    // Crea dos proyectos y los vincula a una sede
    // en la tabla proyecto_sede
    // -------------------------------------------------------
    @Transactional
    public void crearProyectosConSede() {
        System.out.println("--- CREANDO PROYECTOS ---");

        // Usamos la primera sede existente en la BD
        List<Sede> sedes = sedeRepository.findAll();
        if (sedes.isEmpty()) {
            System.out.println("No hay sedes en la BD. Crea una sede primero.");
            return;
        }
        Sede sede = sedes.get(0);

        // --- PROYECTO 1 ---
        try {
            Proyecto proyecto1 = new Proyecto();
            proyecto1.setNomProy("Proyecto Alpha");
            proyecto1.setFInicio(Date.valueOf("2025-01-15"));
            proyecto1.setFFin(Date.valueOf("2025-12-31"));

            Proyecto p1Guardado = proyectoRepository.saveAndFlush(proyecto1);
            System.out.println("Proyecto 1 creado: " + p1Guardado.getNomProy()
                    + " | ID: " + p1Guardado.getIdProy());

            // Vinculamos el proyecto a la sede en proyecto_sede
            ProyectoSedeId sedeId1 = new ProyectoSedeId(p1Guardado.getIdProy(), sede.getIdSede());
            ProyectoSede proyectoSede1 = new ProyectoSede();
            proyectoSede1.setId(sedeId1);
            proyectoSede1.setProyecto(p1Guardado);
            proyectoSede1.setSede(sede);
            proyectoSede1.setFInicio(Date.valueOf("2025-01-15"));
            proyectoSedeRepository.save(proyectoSede1);
            System.out.println("Proyecto 1 vinculado a sede: " + sede.getNomSede());

        } catch (DataIntegrityViolationException e) {
            System.out.println("ERROR: Ya existe un proyecto con el nombre 'Proyecto Alpha'.");
            System.out.println("Causa: " + e.getMostSpecificCause().getMessage());
        }

        // --- PROYECTO 2 ---
        try {
            Proyecto proyecto2 = new Proyecto();
            proyecto2.setNomProy("Proyecto Beta");
            proyecto2.setFInicio(Date.valueOf("2025-03-01"));
            proyecto2.setFFin(null);

            Proyecto p2Guardado = proyectoRepository.saveAndFlush(proyecto2);
            System.out.println("Proyecto 2 creado: " + p2Guardado.getNomProy()
                    + " | ID: " + p2Guardado.getIdProy());

            // Vinculamos el proyecto a la sede en proyecto_sede
            ProyectoSedeId sedeId2 = new ProyectoSedeId(p2Guardado.getIdProy(), sede.getIdSede());
            ProyectoSede proyectoSede2 = new ProyectoSede();
            proyectoSede2.setId(sedeId2);
            proyectoSede2.setProyecto(p2Guardado);
            proyectoSede2.setSede(sede);
            proyectoSede2.setFInicio(Date.valueOf("2025-03-01"));
            proyectoSedeRepository.save(proyectoSede2);
            System.out.println("Proyecto 2 vinculado a sede: " + sede.getNomSede());

        } catch (DataIntegrityViolationException e) {
            System.out.println("ERROR: Ya existe un proyecto con el nombre 'Proyecto Beta'.");
            System.out.println("Causa: " + e.getMostSpecificCause().getMessage());
        }
    }

    // -------------------------------------------------------
    // Inserta datos en empleado_datos_prof para cada empleado
    // -------------------------------------------------------
    @Transactional
    public void insertarDatosEmpleados() {
        System.out.println("\n--- INSERTANDO DATOS PROFESIONALES DE EMPLEADOS ---");

        List<Empleado> empleados = empleadoRepository.findAll();

        for (Empleado emp : empleados) {
            // Solo insertamos si no tiene ya datos profesionales
            if (!datosProfRepository.existsById(emp.getDni())) {
                EmpleadoDatosProf datos = new EmpleadoDatosProf();
                datos.setEmpleado(emp);
                datos.setCategoria("A1");
                datos.setSueldoBrutoAnual(30000.0);

                datosProfRepository.save(datos);
                System.out.println("Datos insertados para: "
                        + emp.getNomEmp() + " (DNI: " + emp.getDni() + ")");
            } else {
                System.out.println("El empleado " + emp.getNomEmp()
                        + " ya tiene datos profesionales. Se omite.");
            }
        }
    }

    // -------------------------------------------------------
    // Verifica el contenido de las tablas
    // -------------------------------------------------------
    public void verificarDatosCreados() {
        System.out.println("\n========== VERIFICACIÓN DE DATOS CREADOS ==========");

        // Tabla: proyecto
        List<Proyecto> proyectos = proyectoRepository.findAll();
        System.out.println("\n[TABLA: proyecto] → " + proyectos.size() + " registros:");
        proyectos.forEach(p -> System.out.println(
                "  ID: " + p.getIdProy()
                        + " | Nombre: " + p.getNomProy()
                        + " | Inicio: " + p.getFInicio()
                        + " | Fin: " + p.getFFin()));

        // Tabla: proyecto_sede
        List<ProyectoSede> vinculaciones = proyectoSedeRepository.findAll();
        System.out.println("\n[TABLA: proyecto_sede] → " + vinculaciones.size() + " registros:");
        vinculaciones.forEach(ps -> System.out.println(
                "  Proyecto ID: " + ps.getId().getIdProy()
                        + " | Sede ID: " + ps.getId().getIdSede()
                        + " | Inicio: " + ps.getFInicio()));

        // Tabla: empleado_datos_prof
        List<EmpleadoDatosProf> datosList = datosProfRepository.findAll();
        System.out.println("\n[TABLA: empleado_datos_prof] → " + datosList.size() + " registros:");
        datosList.forEach(d -> System.out.println(
                "  DNI: " + d.getDni()
                        + " | Categoría: " + d.getCategoria()
                        + " | Sueldo bruto anual: " + d.getSueldoBrutoAnual() + "€"));

        System.out.println("\n====================================================\n");
    }
}
