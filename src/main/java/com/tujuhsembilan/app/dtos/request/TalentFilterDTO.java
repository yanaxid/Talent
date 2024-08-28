package com.tujuhsembilan.app.dtos.request;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TalentFilterDTO {

   @Size(max = 50)
   private String keyword;
   @Size(max = 50)
   private String talentLevel;

   private Integer talentExperience;
   @Size(max = 50)
   private String talentStatus;
   @Size(max = 50)
   private String employeeStatus;

}
