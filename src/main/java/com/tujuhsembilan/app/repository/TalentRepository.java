package com.tujuhsembilan.app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.app.dtos.response.PositionResponseDTO2;
import com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO;
import com.tujuhsembilan.app.models.Talent;

@Repository
public interface TalentRepository extends JpaRepository<Talent, UUID>, JpaSpecificationExecutor<Talent> {

   @Query("SELECT new com.tujuhsembilan.app.dtos.response.PositionResponseDTO2(p.positionId, p.positionName) " +
         "FROM Talent t " +
         "JOIN t.talentPositions tp " +
         "JOIN tp.position p " +
         "WHERE t.talentId = :talentId")
   List<PositionResponseDTO2> findPositionsByTalentId(@Param("talentId") UUID talentId);

   @Query("SELECT new com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO(s.skillsetId, s.skillsetName) " +
         "FROM Talent t " +
         "JOIN t.talentSkillsets ts " +
         "JOIN ts.skillset s " +
         "WHERE t.talentId = :talentId")
   List<SkillsetResponseDTO> findSkillsetsByTalentId(@Param("talentId") UUID talentId);


   @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END " +
           "FROM Talent t JOIN t.talentLevel tl " +
           "WHERE LOWER(tl.talentLevelName) = LOWER(:talentLevelName)")
    boolean existsByTalentLevelNameIgnoreCase(@Param("talentLevelName") String talentLevelName);
}
