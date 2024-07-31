package com.tujuhsembilan.app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.app.models.Client;
import com.tujuhsembilan.app.models.Talent;
import com.tujuhsembilan.app.models.TalentRequest;

@Repository
public interface TalentRequestRepository extends JpaRepository<TalentRequest, UUID>,JpaSpecificationExecutor<TalentRequest>  {

   @Query("SELECT COUNT(tr) FROM TalentRequest tr WHERE tr.talentWishlist.talent.talentId = :talentId")
   Integer countRequestsByTalentId(@Param("talentId") UUID talentId);

  
}
