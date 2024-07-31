package com.tujuhsembilan.app.dtos.request;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TalentApprovalFilterDTO {

   
   private String agencyName;
   private LocalDate requestDate;
   private String talentRequestStatus;

}
