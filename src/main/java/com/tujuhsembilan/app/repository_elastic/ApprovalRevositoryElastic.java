package com.tujuhsembilan.app.repository_elastic;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.tujuhsembilan.app.model_elastic.ApprovalDTOElastic;

public interface ApprovalRevositoryElastic extends ElasticsearchRepository<ApprovalDTOElastic, UUID> {

   // --> FILTER

   @Query("{\"query_string\": {\"query\": \"*?0*\", \"default_operator\": \"AND\", \"default_field\": \"?1\"}}")
   Page<ApprovalDTOElastic> searchByKeyword(String keyword, String category, Pageable pageable);

   @Query("{\n" +
         "  \"bool\": {\n" +
         "    \"must\": [\n"
         +
         "      {\"query_string\": {\"query\": \"*?0*\", \"default_operator\": \"AND\", \"default_field\": \"?2\"}},\n"
         +
         "      {\"range\": {\"request_date\": {\n" +
         "          \"gte\": \"?1T00:00:00.000Z\",\n" +
         "          \"lte\": \"?1T23:59:59.999Z\"\n" +
         "      }}\n"
         +
         "      }\n" +
         "    ]\n" +
         "  }\n" +
         "}")
   Page<ApprovalDTOElastic> findByTanggal(String keyword, String requestDate, String category, Pageable pageable);

   @Query("{\n" +
         "  \"bool\": {\n" +
         "    \"must\": [\n"
         +
         "        {\"query_string\": {\"query\": \"*?0*\", \"default_operator\": \"AND\", \"default_field\": \"?2\"}},\n"
         +
         "        {\"term\": {\"talent_request_status_name.keyword\": \"?1\"}}\n"
         +
         "    ]\n" +
         "  }\n" +
         "}")
   Page<ApprovalDTOElastic> findByStatus(String keyword, String status, String category, Pageable pageable);


   @Query("{\n" +
       "  \"bool\": {\n" +
       "    \"must\": [\n" +
       "      {\"query_string\": {\n" +
       "        \"query\": \"*?0*\",\n" +
       "        \"default_operator\": \"AND\",\n" +
       "        \"default_field\": \"?3\"\n" +
       "      }},\n" +
       "      {\"range\": {\n" +
       "        \"request_date\": {\n" +
       "          \"gte\": \"?1T00:00:00.000Z\",\n" +
       "          \"lte\": \"?1T23:59:59.999Z\"\n" +
       "        }\n" +
       "      }},\n" +
       "      {\"term\": {\n" +
       "        \"talent_request_status_name.keyword\": \"?2\"\n" +
       "      }}\n" +
       "    ]\n" +
       "  }\n" +
       "}")
   Page<ApprovalDTOElastic> findByTanggalAndStatus(String keyword, String requestDate, String status, String category, Pageable pageable);


}