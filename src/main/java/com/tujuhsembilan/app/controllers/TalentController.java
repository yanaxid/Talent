package com.tujuhsembilan.app.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dtos.request.CustomPageRequest;
import com.tujuhsembilan.app.dtos.request.TalentFilterDTO;
import com.tujuhsembilan.app.services.TalentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/talent-management")
@Validated
public class TalentController {

   @Autowired
   private TalentService talentService;

   // --> get :: daftar talents
   @GetMapping(path = "/talents", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> getTalents(@Valid @ModelAttribute TalentFilterDTO filter,
         CustomPageRequest customPageRequest) {
      return talentService.getTalents(filter,
            customPageRequest.getPage("talentExperience,desc;talentLevel,asc;talentName,asc"));
   }

   // --> get :: details talent
   @GetMapping(path = "/talent/{talentId}", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> getTalentById(@PathVariable UUID talentId) {
      return talentService.getTalentById(talentId);
   }

}
