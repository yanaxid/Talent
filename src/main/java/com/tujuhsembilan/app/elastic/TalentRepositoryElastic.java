package com.tujuhsembilan.app.elastic;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.tujuhsembilan.app.models.Talent2;

public interface TalentRepositoryElastic extends ElasticsearchRepository<Talent2, UUID> {

   // --> FILTER
   // level
   // status
   // exp

   // evel-status
   // level-experience
   // status-experience

   // level-status-experience

   // @Query("{\"wildcard\": {\"talent_name\": \"*?0*\"}}")
   // Page<Talent2> searchByKeyword(String keyword, Pageable pageable);


   @Query("{\"query_string\": {\"query\": \"*?0*\", \"default_operator\": \"AND\", \"default_field\": \"talent_name\"}}")
   Page<Talent2> searchByKeyword(String keyword, Pageable pageable);



   

   // @Query("{\n" +
   //       "  \"bool\": {\n" +
   //       "    \"must\": [\n" +
   //       "      {\"wildcard\": {\"talent_name\": \"*?0*\"}},\n" +
   //       "      {\"term\": {\"talent_level_name.keyword\": \"?1\"}}\n" +
   //       "    ]\n" +
   //       "  }\n" +
   //       "}")
   // Page<Talent2> findLevel(String keyword, String talentLevel, Pageable pageable);

   @Query("{\n" +
       "  \"bool\": {\n" +
       "    \"must\": [\n" +
       "      {\"query_string\": {\"query\": \"*?0*\", \"default_operator\": \"AND\", \"default_field\": \"talent_name\"}},\n" +
       "      {\"term\": {\"talent_level_name.keyword\": \"?1\"}}\n" +
       "    ]\n" +
       "  }\n" +
       "}")
   Page<Talent2> findLevel(String keyword, String talentLevel, Pageable pageable);


   // @Query("{\n" +
   //       "  \"bool\": {\n" +
   //       "    \"must\": [\n" +
   //       "      {\"wildcard\": {\"talent_name\": \"*?0*\"}},\n" +
   //       "      {\"term\": {\"talent_status_name.keyword\": \"?1\"}}\n" +
   //       "    ]\n" +
   //       "  }\n" +
   //       "}")
   // Page<Talent2> findStatus(String keyword, String talentStatus, Pageable pageable);

   @Query("{\n" +
       "  \"bool\": {\n" +
       "    \"must\": [\n" +
       "      {\"query_string\": {\"query\": \"*?0*\", \"default_operator\": \"AND\", \"default_field\": \"talent_name\"}},\n" +
       "      {\"term\": {\"talent_status_name.keyword\": \"?1\"}}\n" +
       "    ]\n" +
       "  }\n" +
       "}")
   Page<Talent2> findStatus(String keyword, String talentStatus, Pageable pageable);




   // @Query("{\n" +
   //       "  \"bool\": {\n" +
   //       "    \"must\": [\n" +
   //       "      {\"wildcard\": {\"talent_name\": \"*?0*\"}},\n" +
   //       "      {\"range\": {\"experience\": {\n" +
   //       "        \"gte\": \"?1\",\n" +
   //       "        \"lte\": \"?2\"\n" +
   //       "      }}}\n" +
   //       "    ]\n" +
   //       "  }\n" +
   //       "}")
   // Page<Talent2> findExp(String keyword, int minExperience, int maxExperience, Pageable pageable);
   @Query("{\n" +
       "  \"bool\": {\n" +
       "    \"must\": [\n" +
       "      {\"query_string\": {\"query\": \"*?0*\", \"default_operator\": \"AND\", \"default_field\": \"talent_name\"}},\n" +
       "      {\"range\": {\"experience\": {\n" +
       "        \"gte\": ?1,\n" +
       "        \"lte\": ?2\n" +
       "      }}}\n" +
       "    ]\n" +
       "  }\n" +
       "}")
   Page<Talent2> findExp(String keyword, int minExperience, int maxExperience, Pageable pageable);




   // @Query("{\n" +
   //       "  \"bool\": {\n" +
   //       "    \"must\": [\n" +
   //       "      {\"wildcard\": {\"talent_name\": \"*?0*\"}},\n" +
   //       "      {\"term\": {\"talent_level_name.keyword\": \"?1\"}},\n" +
   //       "      {\"term\": {\"talent_status_name.keyword\": \"?2\"}}\n" +
   //       "    ]\n" +
   //       "  }\n" +
   //       "}")
   // Page<Talent2> findLevelStatus(String keyword, String talentLevel, String talentStatus, Pageable pageable);

   @Query("{\n" +
       "  \"bool\": {\n" +
       "    \"must\": [\n" +
       "      {\"query_string\": {\"query\": \"*?0*\", \"default_operator\": \"AND\", \"default_field\": \"talent_name\"}},\n" +
       "      {\"term\": {\"talent_level_name.keyword\": \"?1\"}},\n" +
       "      {\"term\": {\"talent_status_name.keyword\": \"?2\"}}\n" +
       "    ]\n" +
       "  }\n" +
       "}")
Page<Talent2> findLevelStatus(String keyword, String talentLevel, String talentStatus, Pageable pageable);




   // @Query("{\n" +
   //       "  \"bool\": {\n" +
   //       "    \"must\": [\n" +
   //       "      {\"wildcard\": {\"talent_name\": \"*?0*\"}},\n" +
   //       "      {\"range\": {\"experience\": {\n" +
   //       "        \"gte\": \"?1\",\n" +
   //       "        \"lte\": \"?2\"\n" +
   //       "      }}},\n" +
   //       "      {\"term\": {\"talent_level_name.keyword\": \"?3\"}}\n" +
   //       "    ]\n" +
   //       "  }\n" +
   //       "}")
   // Page<Talent2> findLevelExp(String keyword, int minExperience, int maxExperience, String talentLevel,Pageable pageable);

   @Query("{\n" +
       "  \"bool\": {\n" +
       "    \"must\": [\n" +
       "      {\"query_string\": {\"query\": \"*?0*\", \"default_operator\": \"AND\", \"default_field\": \"talent_name\"}},\n" +
       "      {\"range\": {\"experience\": {\n" +
       "        \"gte\": ?1,\n" +
       "        \"lte\": ?2\n" +
       "      }}},\n" +
       "      {\"term\": {\"talent_level_name.keyword\": \"?3\"}}\n" +
       "    ]\n" +
       "  }\n" +
       "}")
Page<Talent2> findLevelExp(String keyword, int minExperience, int maxExperience, String talentLevel, Pageable pageable);




   // @Query("{\n" +
   //       "  \"bool\": {\n" +
   //       "    \"must\": [\n" +
   //       "      {\"wildcard\": {\"talent_name\": \"*?0*\"}},\n" +
   //       "      {\"range\": {\"experience\": {\n" +
   //       "        \"gte\": \"?1\",\n" +
   //       "        \"lte\": \"?2\"\n" +
   //       "      }}},\n" +
   //       "      {\"term\": {\"talent_status_name.keyword\": \"?3\"}}\n" +
   //       "    ]\n" +
   //       "  }\n" +
   //       "}")
   // Page<Talent2> findStatusExp(String keyword, int minExperience, int maxExperience, String talentStatus,
   //       Pageable pageable);

   @Query("{\n" +
       "  \"bool\": {\n" +
       "    \"must\": [\n" +
       "      {\"query_string\": {\"query\": \"*?0*\", \"default_operator\": \"AND\", \"default_field\": \"talent_name\"}},\n" +
       "      {\"range\": {\"experience\": {\n" +
       "        \"gte\": ?1,\n" +
       "        \"lte\": ?2\n" +
       "      }}},\n" +
       "      {\"term\": {\"talent_status_name.keyword\": \"?3\"}}\n" +
       "    ]\n" +
       "  }\n" +
       "}")
Page<Talent2> findStatusExp(String keyword, int minExperience, int maxExperience, String talentStatus, Pageable pageable);




   // @Query("{\n" +
   //       "  \"bool\": {\n" +
   //       "    \"must\": [\n" +
   //       "      {\"wildcard\": {\"talent_name\": \"*?0*\"}},\n" +
   //       "      {\"range\": {\"experience\": {\n" +
   //       "        \"gte\": \"?1\",\n" +
   //       "        \"lte\": \"?2\"\n" +
   //       "      }}},\n" +
   //       "      {\"term\": {\"talent_level_name.keyword\": \"?3\"}},\n" +
   //       "      {\"term\": {\"talent_status_name.keyword\": \"?4\"}}\n" +
   //       "    ]\n" +
   //       "  }\n" +
   //       "}")
   // Page<Talent2> findLevelStatusExp(String keyword, int minExperience, int maxExperience, String talentLevel,
   //       String talentStatus, Pageable pageable);

   @Query("{\n" +
       "  \"bool\": {\n" +
       "    \"must\": [\n" +
       "      {\"query_string\": {\"query\": \"*?0*\", \"default_operator\": \"AND\", \"default_field\": \"talent_name\"}},\n" +
       "      {\"range\": {\"experience\": {\n" +
       "        \"gte\": ?1,\n" +
       "        \"lte\": ?2\n" +
       "      }}},\n" +
       "      {\"term\": {\"talent_level_name.keyword\": \"?3\"}},\n" +
       "      {\"term\": {\"talent_status_name.keyword\": \"?4\"}}\n" +
       "    ]\n" +
       "  }\n" +
       "}")
Page<Talent2> findLevelStatusExp(String keyword, int minExperience, int maxExperience, String talentLevel, String talentStatus, Pageable pageable);


}