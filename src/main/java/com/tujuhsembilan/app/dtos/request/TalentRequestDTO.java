package com.tujuhsembilan.app.dtos.request;

import java.util.Date;
import java.util.List;

import com.tujuhsembilan.app.dtos.response.PositionResponseDTO;
import com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO;
import com.tujuhsembilan.app.models.EmployeeStatus;
import com.tujuhsembilan.app.models.TalentLevel;
import com.tujuhsembilan.app.models.TalentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TalentRequestDTO {

   private String talentPhoto;
   private String talentName;
   private TalentStatus talentStatus;
   private String nip; // employeeNumber
   private Character sex;
   private Date dob; // tanggal lahir
   private String talentDescription;
   private String cv;
   private Integer talentExperience;
   private TalentLevel talentLevel;
   private Integer projectCompleted;
   private Integer totalRequested;
   private List<PositionResponseDTO> position;
   private List<SkillsetResponseDTO> skillSet;
   private String email;
   private String cellphone;
   private EmployeeStatus employeeStatus;
   private Boolean talentAvailability;
   private String videoUrl;

   // private UUID talentId;
   // private String talentPhotoUrl;
   // @NotNull(message = "talentName must not be null")
   // @NotBlank(message = "talentName must not be blank")
   // private String talentName;
   // private UUID talentStatusId;
   // private UUID employeeStatusId;
   // private Boolean talentAvailability;
   // @NotNull(message = "talentExperience must not be null")
   // private Integer talentExperience;
   // @NotNull(message = "talentLevelId must not be null")
   // private UUID talentLevelId;
   // private String nip;
   // private Character sex;
   // private String dob;
   // private String talentDescription;
   // private String cv;
   // private Integer projectCompleted;
   // private String email;
   // private String cellphone;
   // private String videoUrl;
   // private List<PositionCreateTalentRequestDTO> position;
   // private List<SkillsetCreateTalentRequestDTO> skillset;

}
