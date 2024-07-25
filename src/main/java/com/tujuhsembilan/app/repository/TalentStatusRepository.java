package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.app.models.TalentStatus;

@Repository
public interface TalentStatusRepository extends JpaRepository<TalentStatus, UUID> {

   @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
         "FROM TalentStatus e " +
         "WHERE LOWER(e.talentStatusName) = LOWER(:talentStatusName)")
   boolean existsByTalentStatusNameIgnoreCase(@Param("talentStatusName") String talentStatusName);
}
