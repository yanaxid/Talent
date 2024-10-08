package com.tujuhsembilan.app.dtos.response;


import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalentLevelResponsetDTO {
   
   private UUID talentLevelId;
   private String talentLevelName;
  
}
