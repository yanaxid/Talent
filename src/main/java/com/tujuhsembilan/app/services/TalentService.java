package com.tujuhsembilan.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dtos.request.TalentRequestDTO;
import com.tujuhsembilan.app.dtos.response.PositionResponseDTO2;
import com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO;
import com.tujuhsembilan.app.dtos.response.TalentResponseDTO;
import com.tujuhsembilan.app.models.Talent;
import com.tujuhsembilan.app.repository.TalentRepository;
import com.tujuhsembilan.app.services.spesification.TalentSpecification;

@Service
public class TalentService {

   @Autowired
   private TalentRepository talentRepository;

   // --> get :: daftar talent
   public List<TalentResponseDTO> getTalents(TalentRequestDTO request, Pageable pageable) {

      Specification<Talent> spec = TalentSpecification.filter(request);

      Page<Talent> talents = talentRepository.findAll(spec, pageable);

      List<TalentResponseDTO> talentResponseDTOs = talents.getContent().stream().map(

            t -> {

               TalentResponseDTO response = new TalentResponseDTO();
               response.setTalentId(t.getTalentId());
               response.setTalentPhotoUrl(t.getTalentPhotoFilename());
               response.setTalentName(t.getTalentName());
               response.setTalentStatus(t.getTalentStatus().getTalentStatusName());
               response.setEmployeeStatus(t.getEmployeeStatus().getEmployeeStatusName());
               response.setTalentAvailability(t.getTalentAvailability());
               response.setTalentExperience(t.getExperience());
               response.setTalentLevel(t.getTalentLevel().getTalentLevelName());

               // Mendapatkan PositionResponseDTO2
               List<PositionResponseDTO2> positions = talentRepository.findPositionsByTalentId(t.getTalentId());


               response.setPosition(  positions.isEmpty() ? null : positions );

               // Mendapatkan SkillsetResponseDTO
               List<SkillsetResponseDTO> skillsets = talentRepository.findSkillsetsByTalentId(t.getTalentId());
               response.setSkillSet(skillsets.isEmpty() ? null :skillsets);

               return response;

            }).collect(Collectors.toList());

      return talentResponseDTOs;

   }

}
