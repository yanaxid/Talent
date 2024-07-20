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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Client {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "client_id")
   private UUID clientId;

   @ManyToOne
   @JoinColumn(name = "client_position_id")
   private ClientPosition clientPosition;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

   @Column(length = 1)
   @Size(max = 1)
   private String gender;

   @Column(name = "birth_date")
   private Date birthDate;

   @Column(length = 100)
   @Size(max = 100)
   private String email;

   @Column(name = "agency_name", length = 100)
   @Size(max = 100)
   private String agencyName;

   @Column(name = "agency_address")
   private String agencyAddress;

   // --> creation
   @Embedded
   private Creation creation;


   //--> relation
   @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
   private List<TalentWishlist> talentWishlists;
}
