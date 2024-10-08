package com.tujuhsembilan.app.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.BatchSize;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToOne;
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
public class Talent {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "talent_id", nullable = false)
   private UUID talentId;

   @ManyToOne(fetch = FetchType.LAZY)
   // @BatchSize(size = 10)
   @JoinColumn(name = "talent_level_id")
   private TalentLevel talentLevel;

   @ManyToOne(fetch = FetchType.LAZY)
   // @BatchSize(size = 10)
   @JoinColumn(name = "talent_status_id")
   private TalentStatus talentStatus;

   @ManyToOne(fetch = FetchType.LAZY)
   // @BatchSize(size = 10)
   @JoinColumn(name = "employee_status_id")
   private EmployeeStatus employeeStatus;

   @Column(name = "talent_name")
   private String talentName;

   @Column(name = "talent_photo_filename")
   private String talentPhotoFilename;

   @Column(name = "employee_number", length = 50)
   @Size(max = 50)
   private String employeeNumber;

   @Column(length = 1)
   private Character gender;

   @Column(name = "talent_cv_url")
   private String talentCvUrl;

   @Column(name = "talent_photo_url")
   private String talentPhotoUrl;

   @Column(name = "birth_date")
   private Date birthDate;

   @Column(name = "talent_description", columnDefinition = "text")
   private String talentDescription;

   @Column(name = "talent_cv_filename")
   private String talentCvFilename;

   @Column(name = "experience")
   private Integer talentExperience;

   @Column(length = 100)
   @Size(max = 100)
   private String email;

   @Column(length = 20)
   @Size(max = 20)
   private String cellphone;

   @Column(name = "biography_video_url", columnDefinition = "text")
   private String biographyVideoUrl;

   @Column(name = "is_add_to_list_enable")
   private Boolean isAddToListEnable;

   @Column(name = "talent_availability")
   private Boolean talentAvailability;

   @Column(name = "is_active")
   private Boolean isActive;

   // --> creation
   @Embedded
   private Creation creation;

   // --> relation
   @OneToOne(mappedBy = "talent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private TalentMetadata talentMetadata;

   // @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
   // private TalentMetadata talentMetadata;

   @OneToMany(mappedBy = "talent", fetch = FetchType.LAZY)
   @BatchSize(size = 10)
   private List<TalentPosition> talentPositions;

   @OneToMany(mappedBy = "talent", fetch = FetchType.LAZY)
   private List<TalentWishlist> talentWishlists;

   @OneToMany(mappedBy = "talent", fetch = FetchType.LAZY)
   @BatchSize(size = 10)
   private List<TalentSkillset> talentSkillsets;

}
