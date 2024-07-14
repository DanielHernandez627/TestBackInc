package com.desarrollo.bankinc.repositorios;

import com.desarrollo.bankinc.entidades.controlTransacciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface repositorioCTransaccion extends JpaRepository<controlTransacciones,Long>, JpaSpecificationExecutor<controlTransacciones> {

    @Query("SELECT MAX(it.id) FROM controlTransacciones it")
    Long findMaxId();

}
