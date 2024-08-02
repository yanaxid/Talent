package com.tujuhsembilan.app.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.EntityGraph;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EntityListeners;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EntityListeners(AuditingEntityListener.class)


public class Creation {

   @Column(name = "created_by", length = 50)
   @Size(max = 50)
   @CreatedBy
   private String createdBy;

   @Column(name = "created_time")
   @CreatedDate
   private LocalDateTime createdTime;

   @Column(name = "last_modified_by", length = 50)
   @Size(max = 50)
   @LastModifiedBy
   private String lastModifiedBy;

   @Column(name = "last_modified_time")
   @LastModifiedDate
   private LocalDateTime lastModifiedTime;

}
