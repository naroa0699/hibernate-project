package com.acc.datos.hibernate_project.servicios;

import com.acc.datos.hibernate_project.pojos.Departamento;
import com.acc.datos.hibernate_project.pojos.Sede;
import com.acc.datos.hibernate_project.repositorios.DepartamentoRepository;
import com.acc.datos.hibernate_project.repositorios.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SetupService56 {

    @Autowired
    private SedeRepository sedeRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    // ------------------------------------------------------------------
    // CASO 1: Intentar crear dos sedes con el mismo nombre.
    // El índice uq_sede_nombre debe lanzar ConstraintViolationException.
    // ------------------------------------------------------------------
    @Transactional
    public void crearSedeDuplicada() {
        System.out.println("\n[PRUEBA 1] Intentando crear dos sedes con el mismo nombre...");

        Sede sede1 = new Sede();
        sede1.setNomSede("Sede Duplicada");
        sedeRepository.saveAndFlush(sede1);
        System.out.println("  -> Primera sede guardada con ID: " + sede1.getIdSede());

        // Esta segunda inserción debe violar el índice único uq_sede_nombre
        Sede sede2 = new Sede();
        sede2.setNomSede("Sede Duplicada");   // mismo nombre → violación
        sedeRepository.saveAndFlush(sede2);
        System.out.println("  -> Segunda sede guardada con ID: " + sede2.getIdSede());
    }

    // ------------------------------------------------------------------
    // CASO 2: Intentar crear dos departamentos con el mismo nombre
    //         dentro de la misma sede.
    //         El índice uq_depto_nombre_sede debe lanzar la excepción.
    // ------------------------------------------------------------------
    @Transactional
    public void crearDepartamentoDuplicadoEnSede() {
        System.out.println("\n[PRUEBA 2] Intentando crear dos departamentos con el mismo nombre en la misma sede...");

        Sede sede = new Sede();
        sede.setNomSede("Sede Prueba Depto");
        Sede sedeGuardada = sedeRepository.saveAndFlush(sede);
        System.out.println("  -> Sede auxiliar guardada con ID: " + sedeGuardada.getIdSede());

        Departamento depto1 = new Departamento();
        depto1.setNomDepto("Depto Duplicado");
        depto1.setSede(sedeGuardada);
        departamentoRepository.saveAndFlush(depto1);
        System.out.println("  -> Primer departamento guardado con ID: " + depto1.getIdDepto());

        // Esta segunda inserción debe violar el índice único uq_depto_nombre_sede
        Departamento depto2 = new Departamento();
        depto2.setNomDepto("Depto Duplicado");   // mismo nombre en la misma sede → violación
        depto2.setSede(sedeGuardada);
        departamentoRepository.saveAndFlush(depto2);
        System.out.println("  -> Segundo departamento guardado con ID: " + depto2.getIdDepto());
    }
}
