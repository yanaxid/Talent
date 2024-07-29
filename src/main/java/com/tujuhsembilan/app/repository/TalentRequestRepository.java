package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.app.models.TalentRequest;

@Repository
public interface TalentRequestRepository extends JpaRepository<TalentRequest, UUID> {

    @Query("SELECT COUNT(tr) FROM TalentRequest tr WHERE tr.talentWishlist.talent.talentId = :talentId")
    Integer countRequestsByTalentId(@Param("talentId") UUID talentId);
}
