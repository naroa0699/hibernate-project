package com.acc.datos.hibernate_project.repositorios;

import com.acc.datos.hibernate_project.pojos.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
}
