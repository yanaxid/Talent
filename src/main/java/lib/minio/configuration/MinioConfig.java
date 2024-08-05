package lib.minio.configuration;

import io.minio.MinioClient;
import lib.minio.configuration.property.MinioProp;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MinioConfig {
  @Bean
  public MinioClient minioClient(MinioProp props) {

   log.info("------------------------" + props.getUrl());
    return MinioClient.builder()
        .endpoint(props.getUrl())
        .credentials(props.getUsername(), props.getPassword())
        .build();
  }
}
