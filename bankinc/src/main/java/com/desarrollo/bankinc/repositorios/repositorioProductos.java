package com.desarrollo.bankinc.repositorios;

import com.desarrollo.bankinc.entidades.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface repositorioProductos extends JpaRepository<Productos,Long>, JpaSpecificationExecutor<Productos> {

}
