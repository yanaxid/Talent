package com.tujuhsembilan.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dtos.request.CustomPageRequest;
import com.tujuhsembilan.app.dtos.request.PositionRequestDTO;
import com.tujuhsembilan.app.dtos.request.PositionRequestDTO2;
import com.tujuhsembilan.app.dtos.response.PositionResponse;
import com.tujuhsembilan.app.dtos.response.PositionResponseDTO;
import com.tujuhsembilan.app.services.PositionService;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/position-management")
public class PositionController {

   @Autowired
   private PositionService positionService;

   // --> get :: daftar posisi
   @GetMapping("/positions")
   public List<PositionResponseDTO> getMasterTalentPosition(@ModelAttribute PositionRequestDTO request,
         CustomPageRequest customPageRequest) {

      return positionService.getMasterTalentPosition(request, customPageRequest.getPage("positionId"));
   }

   // --> post :: position
   @PostMapping("/positions")
   public ResponseEntity<PositionResponse> postTalentPosition(@RequestBody String request) {
      return positionService.postTalentPosition(request);
   }

   // --> get :: position by id
   @GetMapping("/positions/{positionId}")
   public PositionResponseDTO getTalentPositionById(@PathVariable UUID positionId) {
      return positionService.getTalentPositionById(positionId);
   }

   // --> put :: position
   @PutMapping("/positions")
   public ResponseEntity<PositionResponse> putTalentPosition(@RequestBody PositionRequestDTO2 request) {
      return positionService.putTalentPosition(request);
   }

   // --> del :: position
   @PutMapping("/positions/{positionId}")
   public ResponseEntity<PositionResponse> delTalentPosition(@PathVariable UUID positionId) {
      return positionService.delTalentPosition(positionId);
   }

}