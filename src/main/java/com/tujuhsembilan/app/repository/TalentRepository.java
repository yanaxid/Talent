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

import com.tujuhsembilan.app.dtos.response.PositionResponseDTO;
import com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO;
import com.tujuhsembilan.app.models.Talent;

@Repository
public interface TalentRepository extends JpaRepository<Talent, UUID>, JpaSpecificationExecutor<Talent> {

   @EntityGraph(attributePaths = { "talentLevel", "talentStatus", "employeeStatus","talentMetadata" })
   Page<Talent> findAll(Specification<Talent> spec, Pageable pageable);

 // -> not efectif when get 1000 data
   @Query("SELECT new com.tujuhsembilan.app.dtos.response.PositionResponseDTO(p.positionId,p.positionName)"
   +
   "FROM TalentPosition tp " +
   "JOIN tp.position p " +
   "WHERE tp.talent.talentId = :talentId")
   List<PositionResponseDTO> findPositionsByTalentId(@Param("talentId") UUID
   talentId);

   @Query("SELECT new com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO(s.skillsetId,s.skillsetName)"
   + "FROM TalentSkillset ts " +
   "JOIN ts.skillset s " +
   "WHERE ts.talent.talentId = :talentId")
   List<SkillsetResponseDTO> findSkillsetsByTalentId(@Param("talentId") UUID
   talentId);

}
