package com.tujuhsembilan.app.repository;

import java.util.Optional;
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

import com.tujuhsembilan.app.models.TalentRequest;

@Repository
public interface TalentRequestRepository
      extends JpaRepository<TalentRequest, UUID>, JpaSpecificationExecutor<TalentRequest> {

   @Query("SELECT COUNT(tr) FROM TalentRequest tr WHERE tr.talentWishlist.talent.talentId = :talentId")
   Integer countRequestsByTalentId(@Param("talentId") UUID talentId);

   @EntityGraph(attributePaths = { "talentRequestStatus", "talentWishlist", "talentWishlist.talent.talentMetadata", "talentWishlist.client.agencyName" })
   Page<TalentRequest> findAll(Specification<TalentRequest> spec, Pageable pageable);

   @Query("SELECT tr FROM TalentRequest tr " +
         "JOIN FETCH tr.talentWishlist tw " +
         "JOIN FETCH tw.talent t")
   Page<TalentRequest> findAllWithJoin(Specification<TalentRequest> spec, Pageable pageable);

   @EntityGraph(attributePaths = { "talentRequestStatus", "talentWishlist", "talentWishlist.talent.talentMetadata", "talentWishlist.client.agencyName" })
   Optional<TalentRequest> findById(UUID id);
}
