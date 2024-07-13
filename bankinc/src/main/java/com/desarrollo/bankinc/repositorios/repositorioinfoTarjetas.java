package com.desarrollo.bankinc.repositorios;

import com.desarrollo.bankinc.entidades.infoTarjetas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface repositorioinfoTarjetas extends JpaRepository<infoTarjetas,Long>, JpaSpecificationExecutor<infoTarjetas> {
}
