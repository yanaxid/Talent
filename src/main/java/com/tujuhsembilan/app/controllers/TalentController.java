package com.tujuhsembilan.app.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dtos.request.CustomPageRequest;
import com.tujuhsembilan.app.dtos.request.TalentFilterDTO;
import com.tujuhsembilan.app.models.Talent;
import com.tujuhsembilan.app.models.Talent2;
import com.tujuhsembilan.app.services.TalentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
   @CrossOrigin(origins = "http://localhost:5173")
   @GetMapping(path = "/talent/{talentId}", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> getTalentById(@PathVariable UUID talentId) {
      return talentService.getTalentById(talentId);
   }

   
   @GetMapping("/talents/search")
   public Page<Talent2> searchTalents(@Valid TalentFilterDTO filter, CustomPageRequest customPageRequest) {
      return talentService.searchTalents(filter, customPageRequest.getPage("experience,desc;talent_level_name.keyword,asc;talent_name.keyword,asc"));
   }

}
