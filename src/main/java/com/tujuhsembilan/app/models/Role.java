package com.tujuhsembilan.app.models;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
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

public class Role {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "role_id")
   private UUID roleId;

   @Column(name = "role_name", length = 50)
   @Size(max = 50)
   private String roleName;

   @Column(name = "is_active")
   private Boolean isActive;

   
   // --> creation
   @Embedded
   private Creation creation;

   //--> relation
   @OneToMany(mappedBy = "role")
   private List<User> users;

}
