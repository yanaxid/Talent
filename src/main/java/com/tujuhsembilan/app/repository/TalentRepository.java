package com.tujuhsembilan.app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.app.dtos.response.TalentResponseDTO;
import com.tujuhsembilan.app.models.Talent;

@Repository
public interface TalentRepository extends JpaRepository<Talent, UUID>, JpaSpecificationExecutor<Talent> {

   // @EntityGraph(attributePaths = { "talentLevel","talentStatus","employeeStatus","talentMetadata" })
   // Page<Talent> findAll(Specification<Talent> spec, Pageable pageable);

   // @Query("SELECT new com.tujuhsembilan.app.dtos.response.TalentResponseDTO(" +
   // "t.talentId, t.talentPhotoFilename, t.talentName," +
   // "ts.talentStatusName, es.employeeStatusName, t.talentAvailability,
   // t.talentExperience," +
   // "tl.talentLevelName)" +
   // "FROM Talent t " +
   // "LEFT JOIN FETCH t.talentLevel tl " +
   // "LEFT JOIN FETCH t.talentStatus ts " +
   // "LEFT JOIN FETCH t.employeeStatus es ")
   // Page<TalentResponseDTO> findT(Pageable page);

   // -> not efectif when get 1000 data
   // @Query("SELECT new
   // com.tujuhsembilan.app.dtos.response.PositionResponseDTO(p.positionId,p.positionName)"
   // +
   // "FROM TalentPosition tp " +
   // "JOIN tp.position p " +
   // "WHERE tp.talent.talentId = :talentId")
   // List<PositionResponseDTO> findPositionsByTalentId(@Param("talentId") UUID
   // talentId);

   // @Query("SELECT new
   // com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO(s.skillsetId,s.skillsetName)"
   // + "FROM TalentSkillset ts " +
   // "JOIN ts.skillset s " +
   // "WHERE ts.talent.talentId = :talentId")
   // List<SkillsetResponseDTO> findSkillsetsByTalentId(@Param("talentId") UUID
   // talentId);

   // @Query("SELECT new
   // com.tujuhsembilan.app.dtos.response.PositionResponseDTO(tp.position.positionId,
   // tp.position.positionName) " +
   // "FROM TalentPosition tp WHERE tp.talent.talentId IN :talentIds")
   // List<PositionResponseDTO> findPositionsByTalentIds(@Param("talentIds")
   // List<UUID> talentIds);

   // @Query("SELECT new
   // com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO(ts.skillset.skillsetId,
   // ts.skillset.skillsetName) " +
   // "FROM TalentSkillset ts WHERE ts.talent.talentId IN :talentIds")
   // List<SkillsetResponseDTO> findSkillsetsByTalentIds(@Param("talentIds")
   // List<UUID> talentIds);

   @Query("SELECT tp.position.positionId, tp.position.positionName, tp.talent.talentId " +
         "FROM TalentPosition tp WHERE tp.talent.talentId IN :talentIds")
   List<Object[]> findPositionsByTalentIds(@Param("talentIds") List<UUID> talentIds);

   @Query("SELECT ts.skillset.skillsetId, ts.skillset.skillsetName, ts.talent.talentId " +
         "FROM TalentSkillset ts WHERE ts.talent.talentId IN :talentIds")
   List<Object[]> findSkillsetsByTalentIds(@Param("talentIds") List<UUID> talentIds);

   // @EntityGraph(attributePaths = { "talentLevel","talentStatus","employeeStatus","talentMetadata" })
   // @Query(value = "SELECT * FROM talent " +
   //             "WHERE to_tsvector('english', talent_name) @@ to_tsquery('english', :query)", nativeQuery = true)
   // Page<Talent> searchByFullText(String query, Pageable pageable);

}
