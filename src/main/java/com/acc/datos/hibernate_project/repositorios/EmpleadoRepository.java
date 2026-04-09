package com.acc.datos.hibernate_project.repositorios;

import com.acc.datos.hibernate_project.pojos.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {
    // JpaRepository<Empleado, String> -> Manejará objetos 'Empleado'
    // cuya clave primaria ('dni') es de tipo 'String'.
}
