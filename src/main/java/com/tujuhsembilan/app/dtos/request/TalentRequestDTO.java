package com.tujuhsembilan.app.dtos.request;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.tujuhsembilan.app.dtos.response.PositionResponseDTO;
import com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TalentRequestDTO {

   @Size(max = 250, message = "URL foto talent tidak boleh lebih dari 250 karakter")
   private String talentPhoto;

   @Size(min = 2, max = 10, message = "Nama talent tidak boleh lebih dari 10 karakter")
   private String talentName;


   @NotNull(message = "Status talent tidak boleh kosong")
   private UUID talentStatus;


   @NotBlank(message = "NIP tidak boleh kosong")
   @Size(min = 10, max = 10, message = "NIP harus terdiri dari 10 karakter")
   private String nip;


   @NotNull(message = "Jenis kelamin tidak boleh kosong")
   private Character sex;


   @NotNull(message = "Tanggal lahir tidak boleh kosong")
   @Past(message = "Tanggal lahir harus di masa lalu")
   private Date dob; 


   @Size(max = 500, message = "Deskripsi talent tidak boleh lebih dari 500 karakter")
   private String talentDescription;


   @Size(max = 250, message = "CV tidak boleh lebih dari 250 karakter")
   private String cv;


   @NotNull(message = "Pengalaman talent tidak boleh kosong")
   @Min(value = 0, message = "Pengalaman talent tidak boleh kurang dari 0")
   private Integer talentExperience;


   @NotNull(message = "Level talent tidak boleh kosong")
   private UUID talentLevel;

   @Min(value = 0, message = "Proyek yang diselesaikan tidak boleh kurang dari 0")
   private Integer projectCompleted;

   @Min(value = 0, message = "Total request tidak boleh kurang dari 0")
   private Integer totalRequested;


   @NotEmpty(message = "List posisi tidak boleh kosong")
   private List<PositionResponseDTO> positions;


   @NotEmpty(message = "List skillset tidak boleh kosong")
   private List<SkillsetResponseDTO> skillsets;


   @NotBlank(message = "Email tidak boleh kosong")
   @Email(message = "Format email tidak valid")
   private String email;

   @Size(min = 10, max = 15, message = "Nomor telepon harus terdiri dari 10-15 karakter")
   private String cellphone;

   @NotNull(message = "Status karyawan tidak boleh kosong")
   private UUID employeeStatus;

   @NotNull(message = "Ketersediaan talent tidak boleh kosong")
   private Boolean talentAvailability;

   @Size(max = 250, message = "URL video tidak boleh lebih dari 250 karakter")
   private String videoUrl;
}
