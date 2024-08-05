package com.tujuhsembilan.app.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import com.tujuhsembilan.app.models.TalentSkillset;
import com.tujuhsembilan.app.models.TalentSkillsetId;

@Repository
public interface TalentSkillsetRepository extends JpaRepository<TalentSkillset, TalentSkillsetId>,JpaSpecificationExecutor<TalentSkillsetId> {
}
