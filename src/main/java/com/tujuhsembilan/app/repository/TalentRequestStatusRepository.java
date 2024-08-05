package com.tujuhsembilan.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.app.models.TalentRequestStatus;

@Repository
public interface TalentRequestStatusRepository extends JpaRepository<TalentRequestStatus, UUID> {


   // @EntityGraph(attributePaths = {  "talentRequest.talentWishlist.talent.talentMetadata" })
   @Query("SELECT s FROM TalentRequestStatus s WHERE s.talentRequestStatusName = :talentRequestStatusName")
   Optional<TalentRequestStatus> findTalentRequestStatusByName(String talentRequestStatusName);
   
}
