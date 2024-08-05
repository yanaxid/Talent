package com.tujuhsembilan.app.dtos.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedRequestDTO {

   @NotNull
   private UUID talentRequestId;
   @NotNull
   @Size(min = 1, max = 20)
   private String action;
   @Size(max = 255)
   private String rejectReason;

}
