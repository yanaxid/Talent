package com.tujuhsembilan.app.dtos.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PositionResponseDTO2 {
   private UUID positionId;
   private String positionName;
}