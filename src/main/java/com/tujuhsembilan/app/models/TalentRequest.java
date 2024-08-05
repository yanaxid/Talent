package com.tujuhsembilan.app.models;


import java.util.Date;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)

@Table(name = "talent_request")
public class TalentRequest {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "talent_request_id")
   private UUID talentRequestId;

   @ManyToOne
   @JoinColumn(name = "talent_request_status_id")
   private TalentRequestStatus talentRequestStatus;

   @ManyToOne
   @JoinColumn(name = "talent_wishlist_id")
   private TalentWishlist talentWishlist;

   @Column(name = "request_date")
   private Date requestDate;

   @Column(name = "request_reject_reason")
   private String requestRejectReason;

   // --> creation
   @Embedded
   private Creation creation;
}
