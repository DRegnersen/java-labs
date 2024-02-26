package com.dregnersen.dataaccess.repositories;

import com.dregnersen.dataaccess.entities.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long>, JpaSpecificationExecutor<Cat> {
}
