package com.tujuhsembilan.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dtos.request.CustomPageRequest;
import com.tujuhsembilan.app.dtos.request.TalentRequestDTO;
import com.tujuhsembilan.app.dtos.response.TalentResponseDTO;
import com.tujuhsembilan.app.services.TalentService;

@RestController
@RequestMapping("/talent-management")
public class TalentController {

   @Autowired
   private TalentService talentService;

   // --> get :: daftar talents
   @GetMapping("/talents")
   public List<TalentResponseDTO> getTalents(@ModelAttribute TalentRequestDTO request,
         CustomPageRequest customPageRequest) {

      return talentService.getTalents(request,
            customPageRequest.getPage("talentId"));
   }

   // @GetMapping("/talents")
   // public ResponseEntity<?> searchTalents(@ModelAttribute TalentRequestDTO
   // request, CustomPageRequest customPageRequest) {
   // try {
   // List<TalentResponseDTO> results = talentService.getTalents(request,
   // customPageRequest.getPage("talentId"));
   // if (results.isEmpty()) {
   // // Mengembalikan HTTP status 404 (Not Found) ketika hasil pencarian kosong
   // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No talents found for
   // the given criteria.");
   // }
   // // Mengembalikan HTTP status 200 (OK) dengan daftar hasil pencarian
   // return ResponseEntity.ok(results);
   // } catch (IllegalArgumentException e) {
   // // Mengembalikan HTTP status 400 (Bad Request) ketika terjadi
   // IllegalArgumentException
   // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
   // }
   // }

   // // --> get :: daftar talents
   // @GetMapping("/talents")

   // public ResponseEntity<?> getTalents(@ModelAttribute TalentRequestDTO request,
   // CustomPageRequest customPageRequest) {

   // return talentService.getTalents(request,
   // customPageRequest.getPage("talentId"));
   // }

}
