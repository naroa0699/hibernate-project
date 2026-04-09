package com.acc.datos.hibernate_project.repositorios;

import com.acc.datos.hibernate_project.pojos.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Integer> {
    // JpaRepository<Sede, Integer> -> Le decimos que este repositorio manejará
    // objetos de la clase 'Sede', y que la clave primaria ('id_sede') es de tipo 'Integer'.
}