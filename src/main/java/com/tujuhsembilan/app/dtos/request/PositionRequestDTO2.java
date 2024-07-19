package com.tujuhsembilan.app.dtos.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PositionRequestDTO2 {

   private UUID positionId;
   private String positionName;
}
