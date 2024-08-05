package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.app.models.TalentWishlist;

@Repository
public interface TalentWishlistRepository extends JpaRepository<TalentWishlist, UUID> {

   @Query("SELECT tr.talent.talentId FROM TalentWishlist tr WHERE tr.talentWishlistId = :talentWishlistId")
   UUID findTalentIdByTalentWishlistId(@Param("talentWishlistId") UUID talentWishlistId);

}
