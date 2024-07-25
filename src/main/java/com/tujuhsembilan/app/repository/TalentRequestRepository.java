// package com.tujuhsembilan.app.repository;

// import java.util.UUID;

// import org.springframework.data.domain.Page;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// import com.tujuhsembilan.app.models.TalentRequest;

// @Repository
// public interface TalentRequestRepository extends JpaRepository<TalentRequestRepository, UUID> {

//    // // Custom query to join TalentRequest with TalentWishlist
//    // @Query("SELECT tr FROM TalentRequest tr JOIN tr.talentWishlist tw WHERE tw.talent.talentId = :talentId")
//    // Page<TalentRequest> findAllByTalentId(@Param("talentId") UUID talentId);

//    // // Custom query to count TalentRequest with status "approved"
//    // @Query("SELECT COUNT(tr) FROM TalentRequest tr WHERE tr.talentRequestStatus.talentRequestStatusName = 'Approved'")
//    // Integer countApprovedRequests();





// }
