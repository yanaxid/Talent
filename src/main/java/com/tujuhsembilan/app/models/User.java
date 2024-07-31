package com.tujuhsembilan.app.models;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)

public class User {

   @Id
   @Column(name = "user_id",unique = true,nullable = false)
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID userId;

   @ManyToOne
   @JoinColumn(name = "role_id")
   private Role role;

   @Column(name = "username", length = 50)
   @Size(max = 50)
   private String username;

   @Column(length = 100)
   @Size(max = 50)
   private String email;

   @Column(name = "first_name")
   private String firstName;

   @Column(name = "last_name")
   private String lastName;

   private String password;

   @Column(name = "is_active")
   private Boolean isActive;

   // --> creation
   @Embedded
   private Creation creation;

   //--> relation
   @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
   private List<Client> clients;

}
