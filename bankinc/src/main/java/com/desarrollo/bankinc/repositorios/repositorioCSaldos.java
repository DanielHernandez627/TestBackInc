package com.desarrollo.bankinc.repositorios;

import com.desarrollo.bankinc.entidades.controlSaldos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface repositorioCSaldos extends JpaRepository<controlSaldos,Long>, JpaSpecificationExecutor<controlSaldos> {
}
