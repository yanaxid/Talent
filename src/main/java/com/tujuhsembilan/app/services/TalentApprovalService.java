package com.tujuhsembilan.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dtos.request.TalentApprovalFilterDTO;
import com.tujuhsembilan.app.dtos.response.ApprovalDTO;
import com.tujuhsembilan.app.dtos.response.TalentResponseDTO;
import com.tujuhsembilan.app.models.Talent;
import com.tujuhsembilan.app.models.TalentRequest;
import com.tujuhsembilan.app.repository.TalentRequestRepository;
import com.tujuhsembilan.app.services.spesification.TalentApprovalSpesicication;
import com.tujuhsembilan.app.services.spesification.TalentSpecification;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class TalentApprovalService {

   @Autowired
   private TalentRequestRepository talentRequestRepository;

   // --> get :: daftar persetujuan talent
   public ResponseEntity<?> getListApprovals(TalentApprovalFilterDTO filter, Pageable pageable) {

      Specification<TalentRequest> spec = TalentApprovalSpesicication.filter(filter);

      Page<TalentRequest> talentRequests  = talentRequestRepository.findAll(spec, pageable);



      List<ApprovalDTO> approvalDTOs = talentRequests.stream().map(
         t -> {
            ApprovalDTO a = new ApprovalDTO();
            a.setTalentRequestId(t.getTalentRequestId());
            a.setAgencyName(t.getTalentWishlist().getClient().getAgencyName());
            a.setTalentName(t.getTalentWishlist().getTalent().getTalentName());
            a.setApprovalStatus(t.getTalentRequestStatus().getTalentRequestStatusName());
            a.setRequestDate(t.getRequestDate());

            return a;
         }
      ).collect(Collectors.toList());

      
      return ResponseEntity.ok(approvalDTOs);
   }

}
