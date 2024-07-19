package com.tujuhsembilan.app.services;

import com.tujuhsembilan.app.dtos.request.PositionRequestDTO;
import com.tujuhsembilan.app.dtos.request.PositionRequestDTO2;
import com.tujuhsembilan.app.dtos.response.PositionResponse;
import com.tujuhsembilan.app.dtos.response.PositionResponseDTO;
import com.tujuhsembilan.app.models.Creation;
import com.tujuhsembilan.app.models.Position;
import com.tujuhsembilan.app.repository.PositionRepository;
import com.tujuhsembilan.app.services.spesification.PositionSpecification;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PositionService {

   @Autowired
   private PositionRepository positionRepository;

   @Autowired
   private MessageSource messageSource;

   // --> get :: daftar posisi
   public List<PositionResponseDTO> getMasterTalentPosition(PositionRequestDTO request, Pageable pageable) {

      Specification<Position> spec = PositionSpecification.filter(request);

      Page<Position> position = positionRepository.findAll(spec, pageable);

      List<PositionResponseDTO> positionResponeDTO = position.getContent().stream().map(

            p -> {

               PositionResponseDTO response = new PositionResponseDTO();
               response.setPositionId(p.getPositionId());
               response.setPositionName(p.getPositionName());
               response.setStatus(p.getIsActive());
               return response;

            }).collect(Collectors.toList());

      return positionResponeDTO;

   }

   // --> post :: position
   public ResponseEntity<PositionResponse> postTalentPosition(String positionName) {
      try {

         // --> ubah string ke JSON
         JSONObject jsonObject = new JSONObject(positionName);
         String extractedPositionName = jsonObject.getString("positionName");

         // --> get data by name
         Optional<Position> positionOPT = positionRepository.findPositionByPositionName(extractedPositionName);

         // --> cek apakah posisi sudah terdaftar ?
         if (positionOPT.isPresent()) {

            String message = messageSource.getMessage("position.is.exist", null, Locale.getDefault());
            String formatMessage = MessageFormat.format(message, positionOPT.get().getPositionName());
            return ResponseEntity
                  .internalServerError()
                  .body(new PositionResponse(null, formatMessage, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
         }

         // --> save position

         Position position = Position.builder()
               .positionName(extractedPositionName)
               .isActive(true)
               .creation(new Creation())
               .build();

         positionRepository.save(position);

         // --> convert to DTO
         PositionResponseDTO response = new PositionResponseDTO(
               position.getPositionId(),
               position.getPositionName(),
               position.getIsActive());

         String message = messageSource.getMessage("position.add.success", null, Locale.getDefault());
         String formatMessage = MessageFormat.format(message, position.getPositionName());
         return ResponseEntity.ok().body(new PositionResponse(response, formatMessage, HttpStatus.OK.value(),
               HttpStatus.OK.getReasonPhrase()));

      } catch (Exception e) {

         String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
         return ResponseEntity.internalServerError().body(new PositionResponse(null, message,
               HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
      }
   }

   // --> get :: position by id
   public PositionResponseDTO getTalentPositionById(UUID positionId) {

      if (positionId == null) {
         log.error("Parameter tidak boleh kosong");
         throw new IllegalArgumentException("Parameter positionId tidak boleh kosong");
      }

      Optional<Position> positionOPT = positionRepository.findById(positionId);

      if (positionOPT.isEmpty()) {
         return null;
      }

      PositionResponseDTO positionResponeDTO = PositionResponseDTO.builder()
            .positionId(positionOPT.get().getPositionId())
            .positionName(positionOPT.get().getPositionName())
            .status(positionOPT.get().getIsActive())
            .build();
      return positionResponeDTO;

   }

   // --> put :: position
   public ResponseEntity<PositionResponse> putTalentPosition(PositionRequestDTO2 request) {
      try {

         // --> get data by id
         Optional<Position> positionOPT = positionRepository.findById(request.getPositionId());

         // --> jika posisin tida ada
         if (positionOPT.isEmpty()) {

            String message = messageSource.getMessage("position.not.found", null, Locale.getDefault());
            String formatMessage = MessageFormat.format(message, request.getPositionName());

            return ResponseEntity
                  .status(HttpStatus.NOT_FOUND)
                  .body(new PositionResponse(null, formatMessage, HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()));
         }

         // --> modificate
         Position position = new Position();

         position.setPositionId(request.getPositionId());
         position.setPositionName(request.getPositionName());
         position.setIsActive(positionOPT.get().getIsActive());

         Creation creation = new Creation();
         creation.setCreatedBy(positionOPT.get().getCreation().getCreatedBy());
         creation.setCreatedTime(positionOPT.get().getCreation().getCreatedTime());

         position.setCreation(creation);

         // --> save
         positionRepository.save(position);

         // --> convert to DTO
         PositionResponseDTO response = new PositionResponseDTO(
               position.getPositionId(),
               position.getPositionName(),
               position.getIsActive());

         String message = messageSource.getMessage("position.put.success", null, Locale.getDefault());
         String formatMessage = MessageFormat.format(message, position.getPositionName());
         return ResponseEntity.ok().body(new PositionResponse(response, formatMessage, HttpStatus.OK.value(),
               HttpStatus.OK.getReasonPhrase()));

      } catch (Exception e) {

         String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
         return ResponseEntity.internalServerError().body(new PositionResponse(null, message,
               HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
      }
   }

   // --> del :: position
   public ResponseEntity<PositionResponse> delTalentPosition(UUID positionId) {
      try {

         // --> get data by id
         Optional<Position> positionOPT = positionRepository.findById(positionId);

         // --> jika posisi tida ada
         if (positionOPT.isEmpty()) {

            String message = messageSource.getMessage("position.not.found", null, Locale.getDefault());
            String formatMessage = MessageFormat.format(message, positionId);

            return ResponseEntity
                  .status(HttpStatus.NOT_FOUND)
                  .body(new PositionResponse(null, formatMessage, HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()));
         }

         // --> modificate
         Position position = new Position();

         position.setPositionId(positionOPT.get().getPositionId());
         position.setPositionName(positionOPT.get().getPositionName());
         position.setIsActive(false);

         Creation creation = new Creation();
         creation.setCreatedBy(positionOPT.get().getCreation().getCreatedBy());
         creation.setCreatedTime(positionOPT.get().getCreation().getCreatedTime());

         position.setCreation(creation);

         // --> save
         positionRepository.save(position);

         // --> convert to DTO
         PositionResponseDTO response = new PositionResponseDTO(
               position.getPositionId(),
               position.getPositionName(),
               position.getIsActive());

         String message = messageSource.getMessage("position.del.success", null, Locale.getDefault());
         String formatMessage = MessageFormat.format(message, position.getPositionName());
         return ResponseEntity.ok().body(new PositionResponse(response, formatMessage, HttpStatus.OK.value(),
               HttpStatus.OK.getReasonPhrase()));

      } catch (Exception e) {

         String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
         return ResponseEntity.internalServerError().body(new PositionResponse(null, message,
               HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
      }
   }

}