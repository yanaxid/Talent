package com.tujuhsembilan.app.models;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.BatchSize;
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

@Table(name = "employee_status")
public class EmployeeStatus {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "employee_status_id")
   private UUID employeeStatusId;

   @Column(name = "employee_status_name", length = 50)
   @Size(max = 50)
   private String employeeStatusName;

   // --> creation
   @Embedded
   private Creation creation;

   // --> relation
   @OneToMany(mappedBy = "employeeStatus", fetch = FetchType.LAZY)
   private List<Talent> talents;

}
