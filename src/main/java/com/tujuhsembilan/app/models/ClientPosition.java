package com.tujuhsembilan.app.models;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

@Table(name = "client_position")
public class ClientPosition {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "client_position_id")
   private UUID clientPositionId;

   @Column(name = "client_position_name", length = 100)
   @Size(max = 100)
   private String clinetPositionName;

   @Column(name = "is_active")
   private Boolean isActive;

   // --> creation
   @Embedded
   private Creation creation;

   //--> relation

   @OneToMany(mappedBy = "clientPosition", fetch = FetchType.LAZY)
   private List<Client> clients;

}
