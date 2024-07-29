package com.tujuhsembilan.app.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tujuhsembilan.app.dtos.request.TalentRequestDTO;
import com.tujuhsembilan.app.dtos.response.EmployeeStatusResponseDTO;
import com.tujuhsembilan.app.dtos.response.PositionResponseDTO;
import com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO;
import com.tujuhsembilan.app.dtos.response.TalentLevelResponsetDTO;
import com.tujuhsembilan.app.exception.classes.MinioUploadException;
import com.tujuhsembilan.app.models.EmployeeStatus;
import com.tujuhsembilan.app.models.Position;
import com.tujuhsembilan.app.models.Skillset;
import com.tujuhsembilan.app.models.Talent;
import com.tujuhsembilan.app.models.TalentLevel;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;
import com.tujuhsembilan.app.repository.PositionRepository;
import com.tujuhsembilan.app.repository.SkillsetRepository;
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import com.tujuhsembilan.app.repository.TalentRepository;

import jakarta.transaction.Transactional;
import lib.i18n.utility.MessageUtil;
import lib.minio.MinioService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class TalentMasterService {

   @Autowired
   private TalentRepository talentRepository;

   @Autowired
   private EmployeeStatusRepository employeeStatusRepository;

   @Autowired
   private TalentLevelRepository talentLevelRepository;

   @Autowired
   private PositionRepository positionRepository;

   @Autowired
   private SkillsetRepository skillsetRepository;

   // @Autowired
   // private MinioService minioService;

   @Autowired
   private MessageUtil messageUtil;

   // --> get :: Search Data Master Level Talent
   public ResponseEntity<?> getMasterTalentLevel() {
      List<TalentLevel> talentLevels = talentLevelRepository.findAll();
      List<TalentLevelResponsetDTO> talentLevelResponsetDTOs = new ArrayList<>();

      for (TalentLevel tl : talentLevels) {
         talentLevelResponsetDTOs.add(new TalentLevelResponsetDTO(tl.getTalentLevelId(), tl.getTalentLevelName()));
      }
      return ResponseEntity.ok(talentLevelResponsetDTOs);
   }

   // --> get :: Search Data Master Status Kepegawaian
   public ResponseEntity<?> getMasterEmployeeStatus() {
      List<EmployeeStatus> employeeStatus = employeeStatusRepository.findAll();
      List<EmployeeStatusResponseDTO> employeeStatusResponseDTOs = new ArrayList<>();

      for (EmployeeStatus e : employeeStatus) {
         employeeStatusResponseDTOs
               .add(new EmployeeStatusResponseDTO(e.getEmployeeStatusId(), e.getEmployeeStatusName()));
      }
      return ResponseEntity.ok(employeeStatusResponseDTOs);
   }

   // --> get :: Search Data Master Skill Set
   public ResponseEntity<?> getMasterSkillset() {

      List<Skillset> skillsets = skillsetRepository.findAll();
      List<SkillsetResponseDTO> skillsetResponseDTOs = new ArrayList<>();

      for (Skillset s : skillsets) {
         skillsetResponseDTOs
               .add(new SkillsetResponseDTO(s.getSkillsetId(),s.getSkillsetName()));
      }
      return ResponseEntity.ok(skillsetResponseDTOs);
   }

   // --> get :: Search Data Master Posisi Talent
   public ResponseEntity<?> getMasterTalentPosition() {

      List<Position> positions = positionRepository.findAll();
      List<PositionResponseDTO> positionResponseDTOs = new ArrayList<>();

      for (Position p : positions) {
         positionResponseDTOs
               .add(new PositionResponseDTO(p.getPositionId(), p.getPositionName()));
      }
      return ResponseEntity.ok(positionResponseDTOs);
   }

// 
// --> post :: save data talent
// @Transactional
// public ResponseEntity<?> saveDataTalent(TalentRequestDTO request, MultipartFile imageFile) {

//    //--> image

//    String imageFilename;
//       try {
//          imageFilename = minioService.uploadImageToMinio(request, imageFile);
//       } catch (IOException e) {
//          String errorMessage = messageUtil.get("application.error.upload.minio");
//          log.info("error minio -----!!!!");
//          log.error(errorMessage, e);
//          throw new MinioUploadException(errorMessage, e);
//       }


//    Talent talent = Talent.builder()
//       .talentLevel(request.getTalentLevel())
//       .talentStatus(null)
//       .employeeStatus(null)
//       .talentName(request.getTalentName())
//       .talentPhotoFilename(imageFilename)
//       .employeeNumber("0189")
//       .gender(request.getSex())
//       .birthDate(request.getDob())
//       .talentDescription(request.getTalentDescription())
//       .talentCvFilename("cv")
//       .talentExperience(request.getTalentExperience())
//       .email(request.getEmail())
//       .cellphone(request.getCellphone())
//       .biographyVideoUrl(request.getVideoUrl())
//       .isAddToListEnable(null)
//       .talentAvailability(null)
//       .build();


//    talentRepository.save(talent);


//    // String responseMessage = messageUtil.get("application.success.add.resep");
//    //       int statusCode = HttpStatus.OK.value();
//    //       String status = HttpStatus.OK.getReasonPhrase();
   
//    //       log.info(responseMessage, statusCode, status);
  
//    return ResponseEntity.ok(talent);
// }




//    public MessageResponse create(CreateRecipeRequest request, MultipartFile imageFile, int userId) {

//       validationService.validate(request);

//       Users createdByUser = usersRepository.findById(userId)
//             .orElseThrow(() -> new EntityNotFoundException(
//                   messageUtil.get("application.error.user.not-found", userId)));

//       Categories categories = categoriesRepository.findById(request.getCategories().getCategoryId())
//             .orElseThrow(() -> new EntityNotFoundException(
//                   messageUtil.get("application.error.category.not-found",
//                         request.getCategories().getCategoryId())));

//       Levels levels = levelsRepository.findById(request.getLevels().getLevelId())
//             .orElseThrow(() -> new EntityNotFoundException(
//                   messageUtil.get("application.error.level.not-found", request.getLevels().getLevelId())));

//       String imageFilename;
//       try {
//          imageFilename = minioService.uploadImageToMinio(request, imageFile);
//       } catch (IOException e) {
//          String errorMessage = messageUtil.get("application.error.upload.minio");
//          log.error(errorMessage, e);
//          throw new MinioUploadException(errorMessage, e);
//       }

//       log.info(imageFilename);

//       Recipes newRecipe = Recipes.builder()
//             .users(createdByUser)
//             .categories(categories)
//             .levels(levels)
//             .recipeName(request.getRecipeName())
//             .imageFilename(imageFilename)
//             .timeCook(request.getTimeCook())
//             .ingredient(request.getIngredient())
//             .howToCook(request.getHowToCook())
//             .createdBy(createdByUser.getUsername())
//             .modifiedBy(createdByUser.getUsername())
//             .isDeleted(false)
//             .createdTime(new Timestamp(System.currentTimeMillis()))
//             .modifiedTime(new Timestamp(System.currentTimeMillis()))
//             .build();

//       recipesRepository.save(newRecipe);

//       String responseMessage = messageUtil.get("application.success.add.resep", request.getRecipeName());
//       int statusCode = HttpStatus.OK.value();
//       String status = HttpStatus.OK.getReasonPhrase();

//       log.info(responseMessage, statusCode, status);

//       return new MessageResponse(responseMessage, statusCode, status);
//    }




}
