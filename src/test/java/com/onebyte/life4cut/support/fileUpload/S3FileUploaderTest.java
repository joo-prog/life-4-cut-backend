package com.onebyte.life4cut.support.fileUpload;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Disabled
@Testcontainers
class S3FileUploaderTest {

  @Container
  private static final LocalStackContainer localStackContainer =
      new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
          .withServices(LocalStackContainer.Service.S3)
          .withReuse(true);

  private static S3FileUploader s3FileUploader;

  @BeforeAll
  public static void beforeAll() {
    S3Client s3Client =
        S3Client.builder()
            .endpointOverride(
                localStackContainer.getEndpointOverride(LocalStackContainer.Service.S3))
            .region(Region.of(localStackContainer.getRegion()))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        localStackContainer.getAccessKey(), localStackContainer.getSecretKey())))
            .build();

    s3Client.createBucket(builder -> builder.bucket("test-bucket"));

    s3FileUploader = new S3FileUploader(s3Client);
  }

  @Test
  @DisplayName("s3에 파일을 업로드한다.")
  void upload() {
    // given
    FileUploadRequest mockFileUploadRequest =
        new MockFileUploadRequest(
            new MockMultipartFile("test-file", "test-file.txt", "text/plain", "test".getBytes()));

    // when
    FileUploadResponse response = s3FileUploader.upload(mockFileUploadRequest);

    // then
    assertThat(response.key()).isEqualTo("test-file.txt");
  }

  static class MockFileUploadRequest implements FileUploadRequest {

    private final MultipartFile multipartFile;

    public MockFileUploadRequest(MultipartFile multipartFile) {
      this.multipartFile = multipartFile;
    }

    @Override
    public InputStream getInputStream() {
      try {
        return multipartFile.getInputStream();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public String getFileName() {
      return multipartFile.getOriginalFilename();
    }

    @Override
    public String getContentType() {
      return multipartFile.getContentType();
    }

    @Override
    public long getContentLength() {
      return multipartFile.getSize();
    }

    @Override
    public String getBucket() {
      return "test-bucket";
    }
  }
}
