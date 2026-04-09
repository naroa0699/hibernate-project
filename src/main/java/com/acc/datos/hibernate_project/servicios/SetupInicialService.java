package com.acc.datos.hibernate_project.servicios;

import com.acc.datos.hibernate_project.pojos.Departamento;
import com.acc.datos.hibernate_project.pojos.Empleado;
import com.acc.datos.hibernate_project.pojos.Sede;
import com.acc.datos.hibernate_project.repositorios.DepartamentoRepository;
import com.acc.datos.hibernate_project.repositorios.EmpleadoRepository;
import com.acc.datos.hibernate_project.repositorios.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // El import correcto


@Service
public class SetupInicialService {
    @Autowired
    private SedeRepository sedeRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Transactional
    public void crearDatosDeEjemplo() {
        System.out.println("--- CREANDO DATOS INICIALES DE FORMA EXPLÍCITA ---");
        // 1. Creamos y guardamos la Sede para obtener su ID.
        Sede sedeCentral = new Sede();
        sedeCentral.setNomSede("Madrid Central");
        // Usamos saveAndFlush para forzar el INSERT y obtener el ID generado.
        Sede sedeGuardada = sedeRepository.saveAndFlush(sedeCentral);
        System.out.println("Sede creada con ID: " + sedeGuardada.getIdSede());
        // 2. Creamos el Departamento, lo asociamos con la Sede (que ya tiene ID) y lo guardamos.
                Departamento deptoIT = new Departamento();
        deptoIT.setNomDepto("Tecnologías de la Información");
        deptoIT.setSede(sedeGuardada); // La relación se establece en el lado"dueño".
                Departamento deptoGuardado = departamentoRepository.saveAndFlush(deptoIT);
        System.out.println("Departamento creado con ID: " + deptoGuardado.getIdDepto());
        // 3. Creamos el Empleado, lo asociamos con el Departamento (que ya tieneID) y lo guardamos.
                Empleado nuevoEmpleado = new Empleado();
        nuevoEmpleado.setDni("12345678A");
        nuevoEmpleado.setNomEmp("Ana López");
        nuevoEmpleado.setDepartamento(deptoGuardado); // La relación se estableceen el lado "dueño".
                empleadoRepository.save(nuevoEmpleado); // Para el último, un .save() es suficiente.
                System.out.println("Empleado creado con DNI: " + nuevoEmpleado.getDni());
        System.out.println("--- DATOS INICIALES CREADOS CON ÉXITO ---");
    }
}

