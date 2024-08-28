package com.tujuhsembilan.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.app.models.Position;
import com.tujuhsembilan.app.models.Talent;
import com.tujuhsembilan.app.models.TalentPosition;
import com.tujuhsembilan.app.models.TalentPositionId;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

@Repository
public interface TalentPositionRepository
      extends JpaRepository<TalentPosition, TalentPositionId>, JpaSpecificationExecutor<TalentPosition> {

   @Query("SELECT f FROM TalentPosition f WHERE f.talent.talentId = :talentId")
   List<TalentPosition> findTalentPositionByTalentId(@Param("talentId") UUID talentId);

   @Transactional
   @Modifying
   @Query("DELETE FROM TalentPosition tp WHERE tp.talent.talentId = :talentId")
   void deleteByTalentId(@Param("talentId") UUID talentId);


}