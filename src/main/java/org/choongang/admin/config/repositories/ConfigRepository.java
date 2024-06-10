package org.choongang.admin.config.repositories;

import org.choongang.admin.config.entities.Configs;
import org.springframework.data.jpa.repository.JpaRepository;

//1차원적인 쿼리만하기 사용할 것이기 때문에 queryDsl 상속X
public interface ConfigRepository extends JpaRepository<Configs, String> {
}
