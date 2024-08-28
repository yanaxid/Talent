package com.tujuhsembilan.app.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "talent_idx")
public class Talent2 {

   @Id
   private UUID talentId;

   @Field(type = FieldType.Text, name = "talent_name")
   private String talentName;

   private List<Skillset> skillsets;

   @Field(type = FieldType.Text, name = "employee_status_name")
   private String employeeStatusName;

   @Field(type = FieldType.Text, name = "talent_cv_url")
   private String talentCvUrl;

   @Field(type = FieldType.Text, name = "talent_level_name")
   private String talentLevelName;

   @Field(type = FieldType.Text, name = "last_modified_by")
   private String lastModifiedBy;

   @Field(type = FieldType.Boolean, name = "is_add_toList_enable")
   private Boolean isAddToListEnable;

   @Field(type = FieldType.Boolean, name = "talent_availability")
   private Boolean talentAvailability;

   @Field(type = FieldType.Text, name = "talent_cv_filename")
   private String talentCvFilename;

   @Field(type = FieldType.Text, name = "biography_video_url")
   private String biographyVideoUrl;

   @Field(type = FieldType.Text, name = "gender")
   private String gender;

   @Field(type = FieldType.Text, name = "experience")
   private Integer experience;

   @Field(type = FieldType.Text, name = "talent_photo_filename")
   private String talentPhotoFilename;

   @Field(type = FieldType.Text, name = "talent_photo_url")
   private String talentPhotoUrl;

   @Field(type = FieldType.Text, name = "email")
   private String email;

   @Field(type = FieldType.Text, name = "employee_number")
   private String employeeNumber;

   @Field(type = FieldType.Boolean, name = "is_active")
   private Boolean isActive;

   @Field( name = "birth_date")
   private Instant birthDate;

   @Field(type = FieldType.Text, name = "talent_description")
   private String talentDescription;

   @Field(type = FieldType.Text, name = "talent_status_name")
   private String talentStatusName;





   @Field(name = "last_modified_time")
   private Instant lastModifiedTime;

   @Field( name = "created_time")
   private Instant createdTime;

   @Field(type = FieldType.Text, name = "cellphone")
   private String cellphone;

   private List<Position> positions;

   // Nested classes for Skillset and Position
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @Builder
   public static class Skillset {
      @Field(type = FieldType.Text, name = "skillset_name")
      private String skillsetName;
      @Field(type = FieldType.Text, name = "skillset_id")
      private UUID skillsetId;
   }

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @Builder
   public static class Position {
      @Field(type = FieldType.Text, name = "position_name")
      private String positionName;
      @Field(type = FieldType.Text, name = "position_id")
      private UUID positionId;
   }
}