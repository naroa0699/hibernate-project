package com.acc.datos.hibernate_project.repositorios;

import com.acc.datos.hibernate_project.pojos.DatosProfesionales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatosProfesionalesRepository extends JpaRepository<DatosProfesionales, String> {
}
