package com.tujuhsembilan.app.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.tujuhsembilan.app.models.Position;




@Repository
public interface PositionRepository extends JpaRepository<Position, UUID>, JpaSpecificationExecutor<Position> {

    @Query("SELECT p FROM Position p WHERE p.positionName = :positionName")
    Optional<Position> findPositionByPositionName(String positionName);

}