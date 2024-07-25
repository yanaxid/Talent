package com.tujuhsembilan.app.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dtos.request.CustomPageRequest;
import com.tujuhsembilan.app.dtos.request.TalentRequestDTO;
import com.tujuhsembilan.app.services.TalentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/talent-management")
public class TalentController {

   @Autowired
   private TalentService talentService;

   // --> get :: daftar talents
   @GetMapping("/talents")
   public ResponseEntity<?> getTalents(@ModelAttribute TalentRequestDTO request,
         CustomPageRequest customPageRequest) {

      return talentService.getTalents(request,
            customPageRequest.getPage("talentExperience,asc;talentLevel,asc;talentName,asc"));
   }

   // --> get :: details talent
   @GetMapping("/talents/{talentId}")
   public ResponseEntity<?> getTalentById(@PathVariable UUID talentId) {
      return talentService.getTalentById(talentId);
   }

}
