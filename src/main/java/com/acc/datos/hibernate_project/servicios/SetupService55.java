package com.acc.datos.hibernate_project.servicios;

import com.acc.datos.hibernate_project.pojos.Departamento;
import com.acc.datos.hibernate_project.pojos.Empleado;
import com.acc.datos.hibernate_project.pojos.Sede;
import com.acc.datos.hibernate_project.repositorios.DepartamentoRepository;
import com.acc.datos.hibernate_project.repositorios.EmpleadoRepository;
import com.acc.datos.hibernate_project.repositorios.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SetupService55 {
    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Transactional
    public void crearEstructuraNaroa(){

        System.out.println("--- CREANDO DATOS INICIALES DE FORMA EXPLÍCITA ---");
        // 1. Creamos y guardamos la Sede para obtener su ID.
        Sede sedeNaroalandia = new Sede();
        sedeNaroalandia.setNomSede("Naroalandia");

        // Usamos saveAndFlush para forzar el INSERT y obtener el ID generado.
        Sede sedeGuardada = sedeRepository.saveAndFlush(sedeNaroalandia);
        System.out.println("Sede creada con ID: " + sedeGuardada.getIdSede());

        //Creamos dos nuevos departamentos
        //1.Departamento
        Departamento deptoCI = new Departamento();
        deptoCI.setNomDepto("Ciencias de la Información");
        deptoCI.setSede(sedeGuardada); // La relación se establece en el lado"dueño".
        Departamento deptoGuardado = departamentoRepository.saveAndFlush(deptoCI);
        System.out.println("Departamento creado con ID: " + deptoGuardado.getIdDepto());
        //2.Departamento
        Departamento deptoSC = new Departamento();
        deptoSC.setNomDepto("Servicios al cliente");
        deptoSC.setSede(sedeGuardada); // La relación se establece en el lado"dueño".
        Departamento deptoGuardado2 = departamentoRepository.saveAndFlush(deptoSC);
        System.out.println("Departamento creado con ID: " + deptoGuardado2.getIdDepto());

        //Creamos los empleados, uno para cada departamento
        //Empleado 1
        Empleado nuevoEmpleado = new Empleado();
        nuevoEmpleado.setDni("12345678A");
        nuevoEmpleado.setNomEmp("Ana López");
        nuevoEmpleado.setDepartamento(deptoGuardado); // La relación se estableceen el lado "dueño".
        empleadoRepository.save(nuevoEmpleado); // Para el último, un .save() es suficiente.
        System.out.println("Empleado creado con DNI: " + nuevoEmpleado.getDni());
        System.out.println("--- DATOS INICIALES CREADOS CON ÉXITO ---");
        //Empleado 2
        Empleado empleado2 = new Empleado();
        empleado2.setDni("12345678A");
        empleado2.setNomEmp("Ana López");
        empleado2.setDepartamento(deptoGuardado2); // La relación se estableceen el lado "dueño".
        empleadoRepository.save(empleado2); // Para el último, un .save() es suficiente.
        System.out.println("Empleado creado con DNI: " + empleado2.getDni());
        System.out.println("--- DATOS INICIALES CREADOS CON ÉXITO ---");

    }
}
