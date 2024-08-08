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

import com.tujuhsembilan.app.models.Talent;

@Repository
public interface TalentRepository extends JpaRepository<Talent, UUID>, JpaSpecificationExecutor<Talent> {

   // @EntityGraph(attributePaths = { "talentMetadata", "talentStatus", "talentLevel", "employeeStatus" })
   @EntityGraph(attributePaths = { "talentMetadata"})
   Page<Talent> findAll(Specification<Talent> spec, Pageable pageable);

   @Query("SELECT tp.position.positionId, tp.position.positionName, tp.talent.talentId " +
         "FROM TalentPosition tp WHERE tp.talent.talentId IN :talentIds")
   List<Object[]> findPositionsByTalentIds(@Param("talentIds") List<UUID> talentIds);

   @Query("SELECT ts.skillset.skillsetId, ts.skillset.skillsetName, ts.talent.talentId " +
         "FROM TalentSkillset ts WHERE ts.talent.talentId IN :talentIds")
   List<Object[]> findSkillsetsByTalentIds(@Param("talentIds") List<UUID> talentIds);

   @Query("SELECT tp.position.positionId, tp.position.positionName, tp.talent.talentId " +
           "FROM TalentPosition tp WHERE tp.talent.talentId = :talentId")
    List<Object[]> findPositionsByTalentId(@Param("talentId") UUID talentId);

    @Query("SELECT ts.skillset.skillsetId, ts.skillset.skillsetName, ts.talent.talentId " +
           "FROM TalentSkillset ts WHERE ts.talent.talentId = :talentId")
    List<Object[]> findSkillsetsByTalentId(@Param("talentId") UUID talentId);


   // @EntityGraph(attributePaths = { "talentLevel","talentStatus","employeeStatus","talentMetadata" })
   // @Query(value = "SELECT * FROM talent " +
   //             "WHERE to_tsvector('english', talent_name) @@ to_tsquery('english', :query)", nativeQuery = true)
   // Page<Talent> searchByFullText(String query, Pageable pageable);

}
