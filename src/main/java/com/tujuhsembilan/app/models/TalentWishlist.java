package com.tujuhsembilan.app.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)

@Table(name = "talent_wishlist")
public class TalentWishlist {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "talent_wishlist_id")
   private UUID talentWishlistId;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "talent_id", nullable = false)
   private Talent talent;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "client_id", nullable = false)
   private Client client;

   @Column(name = "wishlist_date", nullable = false)
   private Date wishlistDate;

   @Column(name = "is_active")
   private Boolean is_Active;

   // --> creation
   @Embedded
   private Creation creation;

   // --> relation
   @OneToMany(mappedBy = "talentWishlist", fetch = FetchType.LAZY)
   private List<TalentRequest> talentRequests;


}
