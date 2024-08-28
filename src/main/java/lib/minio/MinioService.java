package lib.minio;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import lib.i18n.utility.MessageUtil;
import lib.minio.configuration.property.MinioProp;
import lib.minio.exception.MinioServiceDownloadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MinioService {

   // private static final Long DEFAULT_EXPIRY = TimeUnit.HOURS.toSeconds(1);
   private static final Long DEFAULT_EXPIRY = TimeUnit.DAYS.toSeconds(7);

   private final MinioClient minio;
   private final MinioProp prop;
   private final MessageUtil message;

   private static String bMsg(String bucket) {
      return "bucket " + bucket;
   }

   private static String bfMsg(String bucket, String filename) {
      return bMsg(bucket) + " of file " + filename;
   }

   // --> create link
   public String getLink(String filename, Long expiry) {

      if (filename == null || filename.trim().isEmpty()) {
         throw new IllegalArgumentException("Filename must not be null or empty");
      }

      try {
         return minio.getPresignedObjectUrl(
               GetPresignedObjectUrlArgs.builder()
                     .method(Method.GET)
                     .bucket(prop.getBucketName())
                     .object(filename)
                     .expiry(Math.toIntExact(expiry), TimeUnit.SECONDS)
                     .build());
      } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
            | InvalidResponseException | NoSuchAlgorithmException | XmlParserException | ServerException
            | IllegalArgumentException | IOException e) {
         log.error(message.get(prop.getGetErrorMessage(), bfMsg(prop.getBucketName(), filename)) + ": "
               + e.getLocalizedMessage(), e);
         throw new MinioServiceDownloadException(
               message.get(prop.getGetErrorMessage(), bfMsg(prop.getBucketName(), filename)), e);
      }
   }

   public String getPublicLink(String filename) {
      return this.getLink(filename, DEFAULT_EXPIRY);
   }

   private String sanitizeForFilename(String input) {
      return input.replaceAll("[^a-zA-Z0-9]", "_");
   }

   public String getFileExtension(String filename) {
      int dotIndex = filename.lastIndexOf('.');
      return (dotIndex == -1) ? "" : filename.substring(dotIndex);
   }

   // // --> check if file exist
   // public boolean fileExists(String filename) {
   // try {
   // minio.getObject(
   // GetObjectArgs.builder()
   // .bucket(prop.getBucketName())
   // .object(filename)
   // .build());
   // return true;
   // } catch (MinioException e) {
   // log.error(filename, e);
   // return false;
   // } catch (Exception e) {
   // if (filename == null) {
   // return false;
   // }
   // log.error(filename, e);
   // throw new RuntimeException("Error checking if file exists in MinIO", e);
   // }
   // }

   // --> delete file
   public void deleteFile(String filename) {
      try {
         minio.removeObject(
               RemoveObjectArgs.builder()
                     .bucket(prop.getBucketName())
                     .object(filename)
                     .build());
      } catch (Exception e) {
         log.error(filename, e);
         throw new RuntimeException("Error deleting file from MinIO", e);
      }
   }

   // --> generate filename
   public String generatedFilename(String talentName, Integer talentExperience, String talentLevelName,
         MultipartFile file) {

      String timestamp = String.valueOf(System.currentTimeMillis());
      String fileExtension = getFileExtension(file.getOriginalFilename());
      return String.format(
            "%s_%s_%s_%s%s",
            sanitizeForFilename(talentName.toLowerCase()),
            sanitizeForFilename(String.valueOf(talentExperience)),
            sanitizeForFilename(talentLevelName.toLowerCase()),
            timestamp,
            fileExtension);
   }

   public void uploadFile(String filename, MultipartFile file) throws IOException {

      try {
         minio.putObject(
               PutObjectArgs.builder()
                     .bucket(prop.getBucketName())
                     .object(filename)
                     .stream(file.getInputStream(), file.getSize(), -1)
                     .contentType(file.getContentType())
                     .build());

         
      } catch (Exception e) {
         log.error(filename, e);
         throw new IOException("Error uploading file to MinIO", e);
      }
   }

   public void updateFile(String prevFilename, String newFilename, MultipartFile file) throws IOException {
      deleteFile(prevFilename);
      uploadFile(newFilename, file);
   }

   // public String uploadFileToMinio(TalentRequestDTO request, MultipartFile
   // talentFile) throws IOException {
   // String talentName = sanitizeForFilename(request.getTalentName());

   // if (talentName.isEmpty()) {
   // log.warn("One or more components for filename are empty. Talent: {},",
   // request.getTalentName());
   // }

   // String timestamp = String.valueOf(System.currentTimeMillis());
   // String fileExtension = getFileExtension(talentFile.getOriginalFilename());

   // String generatedFilename = String.format(
   // "%s_%s%s",
   // talentName,
   // timestamp,
   // fileExtension);

   // try (InputStream inputStream = talentFile.getInputStream()) {
   // minio.putObject(
   // PutObjectArgs.builder()
   // .bucket(prop.getBucketName())
   // .object(generatedFilename)
   // .stream(inputStream, talentFile.getSize(), -1)
   // .contentType(talentFile.getContentType())
   // .build());
   // } catch (Exception e) {
   // throw new IOException("Failed to upload file to MinIO", e);
   // }

   // log.info(generatedFilename);
   // return generatedFilename;
   // }

   // public String updateFileToMinio(TalentRequestDTO request, MultipartFile
   // talentFile) throws IOException {

   // if (talentFile == null || talentFile.isEmpty()) {
   // log.warn("File is null or empty.");
   // return null;
   // }

   // String talentName = sanitizeForFilename(request.getTalentName());

   // if (talentName.isEmpty()) {
   // log.warn("One or more components for filename are empty. Talent: {}",
   // request.getTalentName());
   // }

   // String timestamp = String.valueOf(System.currentTimeMillis());
   // String fileExtension = getFileExtension(talentFile.getOriginalFilename());

   // String generatedFilename = String.format(
   // "%s_%s%s",
   // talentName,
   // timestamp,
   // fileExtension);

   // try (InputStream inputStream = talentFile.getInputStream()) {
   // minio.putObject(
   // PutObjectArgs.builder()
   // .bucket(prop.getBucketName())
   // .object(generatedFilename)
   // .stream(inputStream, talentFile.getSize(), -1)
   // .contentType(talentFile.getContentType())
   // .build());
   // } catch (Exception e) {
   // throw new IOException("Failed to upload file to MinIO", e);
   // }

   // log.info(generatedFilename);
   // return generatedFilename;
   // }
}
