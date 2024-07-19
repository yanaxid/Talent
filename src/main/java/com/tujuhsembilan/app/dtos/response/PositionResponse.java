package com.tujuhsembilan.app.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor 

public class PositionResponse {
   

    private PositionResponseDTO data;
    private String message;
    private int statusCode;
    private String status;
}