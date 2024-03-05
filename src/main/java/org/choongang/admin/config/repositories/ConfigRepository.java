package org.choongang.admin.config.repositories;

import org.choongang.admin.config.entities.Configs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<Configs, String> {
}
