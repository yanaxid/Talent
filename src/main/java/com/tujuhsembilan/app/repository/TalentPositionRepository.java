package com.tujuhsembilan.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import com.tujuhsembilan.app.models.TalentPosition;
import com.tujuhsembilan.app.models.TalentPositionId;




@Repository
public interface TalentPositionRepository extends JpaRepository<TalentPosition, TalentPositionId>, JpaSpecificationExecutor<TalentPosition> {


}