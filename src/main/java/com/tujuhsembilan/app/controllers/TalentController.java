package com.tujuhsembilan.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

      return talentService.getTalents(request, customPageRequest.getPage("talentId"));
   }

}
