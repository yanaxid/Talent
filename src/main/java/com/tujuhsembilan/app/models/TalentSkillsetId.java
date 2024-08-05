package com.tujuhsembilan.app.models;

import java.util.UUID;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TalentSkillsetId {
    private UUID talentId;
    private UUID skillsetId;
}
