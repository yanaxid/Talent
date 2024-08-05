package com.tujuhsembilan.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dtos.request.ApprovedRequestDTO;
import com.tujuhsembilan.app.dtos.request.TalentApprovalFilterDTO;
import com.tujuhsembilan.app.dtos.response.ApprovalDTO;
import com.tujuhsembilan.app.models.TalentRequest;
import com.tujuhsembilan.app.models.TalentRequestStatus;
import com.tujuhsembilan.app.repository.TalentRequestRepository;
import com.tujuhsembilan.app.repository.TalentRequestStatusRepository;
import com.tujuhsembilan.app.services.spesification.TalentApprovalSpesicication;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class TalentApprovalService {

   @Autowired
   private TalentRequestRepository talentRequestRepository;

   @Autowired
   private TalentRequestStatusRepository talentRequestStatusRepository;

   // --> get :: daftar persetujuan talent
   public ResponseEntity<?> getListApprovals(TalentApprovalFilterDTO filter, Pageable pageable) {

      try {
         Specification<TalentRequest> spec = TalentApprovalSpesicication.filter(filter);
         Page<TalentRequest> talentRequests = talentRequestRepository.findAll(spec, pageable);

         List<ApprovalDTO> approvalDTOs = talentRequests.stream().map(
               t -> {
                  ApprovalDTO approvalDTO = new ApprovalDTO();
                  approvalDTO.setTalentRequestId(t.getTalentRequestId());
                  approvalDTO.setAgencyName(t.getTalentWishlist().getClient().getAgencyName());
                  approvalDTO.setTalentName(t.getTalentWishlist().getTalent().getTalentName());
                  approvalDTO.setApprovalStatus(t.getTalentRequestStatus().getTalentRequestStatusName());
                  approvalDTO.setRequestDate(t.getRequestDate());
                  return approvalDTO;
               }).collect(Collectors.toList());

         return ResponseEntity.ok(approvalDTOs);
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
      }

   }

   // --> get :: edit persetujuan talent
   @Transactional
   public ResponseEntity<?> putApproval(ApprovedRequestDTO request) {

      try {

         //--> fetch 
         TalentRequest talentRequest = talentRequestRepository
               .findById(request.getTalentRequestId())
               .orElseThrow(() -> new EntityNotFoundException("Id " + request.getTalentRequestId() + " not found"));

         TalentRequestStatus talentRequestStatus = talentRequestStatusRepository
               .findTalentRequestStatusByName(request.getAction())
               .orElseThrow(() -> new EntityNotFoundException(request.getAction() + " is not exist"));

         //--> set data
         talentRequest.setTalentRequestStatus(talentRequestStatus);
         talentRequest.setRequestRejectReason(request.getRejectReason());
         talentRequest.getCreation().setLastModifiedBy("yana");

        //--> save
         talentRequestRepository.save(talentRequest);

         return ResponseEntity.ok(talentRequest.getTalentWishlist().getTalent().getTalentName() + " " + request.getAction());
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
      }

   }

}
