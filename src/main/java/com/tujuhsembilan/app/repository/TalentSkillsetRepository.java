package com.tujuhsembilan.app.repository;




import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.tujuhsembilan.app.models.TalentSkillset;
import com.tujuhsembilan.app.models.TalentSkillsetId;

import jakarta.transaction.Transactional;

@Repository
public interface TalentSkillsetRepository extends JpaRepository<TalentSkillset, TalentSkillsetId>,JpaSpecificationExecutor<TalentSkillsetId> {

   @Query("SELECT ts FROM TalentSkillset ts WHERE ts.talent.talentId = :talentId")
   List<TalentSkillset> findTalentSkillsetByTalentId(@Param("talentId") UUID talentId);

   @Modifying
   @Query("DELETE  FROM TalentSkillset ts WHERE ts.talent.talentId = :talentId")
   void deleteByTalentId(@Param("talentId") UUID talentId);
}
