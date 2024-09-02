package com.tujuhsembilan.app;

import com.tujuhsembilan.app.dtos.request.TalentRequestDTO;
import com.tujuhsembilan.app.dtos.response.PositionResponseDTO;
import com.tujuhsembilan.app.dtos.response.SkillsetResponseDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

public class TalentRequestDTOTest {

    private final Validator validator;

    public TalentRequestDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @ParameterizedTest(name = "-> nama={0}, exp={1}, email={2}")
    @CsvSource({
        "budi sidufhsidfjsd sdifjs, 10, john.doe@example.com, 2021-10-10",
        "joko, 5, jane.doe@example.com, 2020-10-10",
        "anwar, 8, mike.ross@example.com, 2020-10-10",
        "majid a, 5, janedoe@e.co, 2020-10-10",
        "yana, 5, ayna@missingusername.com, 2020-10-10",
        "nugraha, 5, missingdomain@ay.com, 2020-10-10"
    })
    void testTalentRequestDTOCreation(String talentName, Integer talentExperience, String email, String dobString) {
        UUID talentStatus = UUID.randomUUID();
        UUID talentLevel = UUID.randomUUID();
        UUID employeeStatus = UUID.randomUUID();
        List<PositionResponseDTO> positions = List.of(new PositionResponseDTO());
        List<SkillsetResponseDTO> skillsets = List.of(new SkillsetResponseDTO());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = null;
        try {
            dob = dateFormat.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TalentRequestDTO dto = TalentRequestDTO.builder()
                .talentPhoto("photo.jpg")
                .talentName(talentName)
                .talentStatus(talentStatus)
                .nip("1234567890")
                .sex('M')
                .dob(dob)
                .talentDescription("Experienced software engineer")
                .cv("cv.pdf")
                .talentExperience(talentExperience)
                .talentLevel(talentLevel)
                .projectCompleted(5)
                .totalRequested(15)
                .positions(positions)
                .skillsets(skillsets)
                .email(email)
                .cellphone("123-456-7890")
                .employeeStatus(employeeStatus)
                .talentAvailability(true)
                .videoUrl("video.mp4")
                .build();

                // validasi file lengkap ?
                //validasi number
                

        Set<ConstraintViolation<TalentRequestDTO>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
      
    }
}
