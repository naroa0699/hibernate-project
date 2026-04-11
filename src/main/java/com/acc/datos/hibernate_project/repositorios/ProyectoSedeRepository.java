package com.acc.datos.hibernate_project.repositorios;

import com.acc.datos.hibernate_project.pojos.ProyectoSede;
import com.acc.datos.hibernate_project.pojos.ProyectoSedeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoSedeRepository extends JpaRepository<ProyectoSede, ProyectoSedeId> {
}