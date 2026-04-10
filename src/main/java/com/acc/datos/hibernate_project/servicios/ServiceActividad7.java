package com.acc.datos.hibernate_project.servicios;

import com.acc.datos.hibernate_project.pojos.DatosProfesionales;
import com.acc.datos.hibernate_project.pojos.Empleado;
import com.acc.datos.hibernate_project.pojos.Proyecto;
import com.acc.datos.hibernate_project.pojos.Sede;
import com.acc.datos.hibernate_project.repositorios.DatosProfesionalesRepository;
import com.acc.datos.hibernate_project.repositorios.EmpleadoRepository;
import com.acc.datos.hibernate_project.repositorios.ProyectoRepository;
import com.acc.datos.hibernate_project.repositorios.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceActividad7 {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DatosProfesionalesRepository datosProfRepository;

    @Transactional
    public void crearProyectosConSede() {
        System.out.println("--- CREANDO PROYECTOS ---");

        List<Sede> sedes = sedeRepository.findAll();
        if (sedes.isEmpty()) {
            System.out.println("No hay sedes disponibles. Crea una sede primero.");
            return;
        }
        Sede sede = sedes.get(0);

        // --- PROYECTO 1 ---
        try {
            Proyecto proyecto1 = new Proyecto();
            proyecto1.setNombre("Proyecto Alpha");
            proyecto1.setFechaInicio(LocalDate.of(2025, 1, 15));
            proyecto1.setFechaFin(LocalDate.of(2025, 12, 31));
            proyecto1.getSedes().add(sede);

            proyectoRepository.saveAndFlush(proyecto1);
            System.out.println("Proyecto 1 creado: " + proyecto1.getNombre()
                    + " | ID: " + proyecto1.getIdProy());

        } catch (DataIntegrityViolationException e) {
            System.out.println("ERROR: Ya existe un proyecto con el nombre 'Proyecto Alpha'.");
            System.out.println("Causa: " + e.getMostSpecificCause().getMessage());
        }

        // --- PROYECTO 2 ---
        try {
            Proyecto proyecto2 = new Proyecto();
            proyecto2.setNombre("Proyecto Beta");
            proyecto2.setFechaInicio(LocalDate.of(2025, 3, 1));
            proyecto2.setFechaFin(null);
            proyecto2.getSedes().add(sede);

            proyectoRepository.saveAndFlush(proyecto2);
            System.out.println("Proyecto 2 creado: " + proyecto2.getNombre()
                    + " | ID: " + proyecto2.getIdProy());

        } catch (DataIntegrityViolationException e) {
            System.out.println("ERROR: Ya existe un proyecto con el nombre 'Proyecto Beta'.");
            System.out.println("Causa: " + e.getMostSpecificCause().getMessage());
        }
    }

    @Transactional
    public void insertarDatosEmpleados() {
        System.out.println("\n--- INSERTANDO DATOS PROFESIONALES DE EMPLEADOS ---");

        List<Empleado> empleados = empleadoRepository.findAll();

        for (Empleado emp : empleados) {
            if (!datosProfRepository.existsById(emp.getDni())) {
                DatosProfesionales datos = new DatosProfesionales();
                datos.setDni(emp.getDni());
                datos.setEmpleado(emp);
                datos.setCategoria("A1");
                datos.setSueldo(30000.0);

                datosProfRepository.save(datos);
                System.out.println("Datos insertados para: "
                        + emp.getNombre() + " (DNI: " + emp.getDni() + ")");
            } else {
                System.out.println("El empleado " + emp.getNombre()
                        + " ya tiene datos profesionales. Se omite.");
            }
        }
    }

    public void verificarDatosCreados() {
        System.out.println("\n========== VERIFICACIÓN DE DATOS CREADOS ==========");

        List<Proyecto> proyectos = proyectoRepository.findAll();
        System.out.println("\n[TABLA: proyecto] - " + proyectos.size() + " registros:");
        proyectos.forEach(p -> System.out.println("  ID: " + p.getIdProy()
                + " | Nombre: " + p.getNombre()
                + " | Inicio: " + p.getFechaInicio()
                + " | Fin: " + p.getFechaFin()));

        System.out.println("\n[TABLA: proyecto_sede] - Vinculaciones:");
        proyectos.forEach(p -> p.getSedes().forEach(s ->
                System.out.println("  Proyecto: " + p.getNombre()
                        + " <-> Sede: " + s.getNomSede())));

        List<DatosProfesionales> datosList = datosProfRepository.findAll();
        System.out.println("\n[TABLA: empleado_datos_prof] - " + datosList.size() + " registros:");
        datosList.forEach(d -> System.out.println("  DNI: " + d.getDni()
                + " | Categoría: " + d.getCategoria()
                + " | Sueldo: " + d.getSueldo() + "€"));

        System.out.println("\n====================================================\n");
    }
}
