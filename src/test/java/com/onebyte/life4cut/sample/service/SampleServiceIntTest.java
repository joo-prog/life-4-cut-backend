package com.onebyte.life4cut.sample.service;


import com.onebyte.life4cut.common.vo.Email;
import com.onebyte.life4cut.sample.repository.SampleRepository;
import com.onebyte.life4cut.sample.repository.SampleRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableJpaAuditing
public class SampleServiceIntTest {

    private final EntityManager entityManager;
    private final SampleService sampleService;
    private final SampleRepository sampleRepository;

    @Autowired
    public SampleServiceIntTest(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.sampleRepository = new SampleRepositoryImpl(entityManager);
        this.sampleService = new SampleService(sampleRepository);
    }


    @Nested
    class Save {

        @Test
        @DisplayName("샘플을 저장한다")
        void saveSample() {
            // given
            Email email = Email.of("test@gmail.com");
            String nickname = "nickname";

            // when
            Long save = sampleService.save(email, nickname);

            // then
            assertThat(save).isNotNull();
        }
    }
}
