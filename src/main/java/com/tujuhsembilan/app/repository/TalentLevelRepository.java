package com.tujuhsembilan.app.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.tujuhsembilan.app.models.TalentLevel;

@Repository
public interface TalentLevelRepository extends JpaRepository<TalentLevel, UUID> {

   @Query("SELECT CASE WHEN COUNT(tl) > 0 THEN true ELSE false END " +
           "FROM TalentLevel tl " +
           "WHERE LOWER(tl.talentLevelName) = LOWER(:talentLevelName)")
   boolean existsByTalentLevelNameIgnoreCase(@Param("talentLevelName") String talentLevelName);
   
}
