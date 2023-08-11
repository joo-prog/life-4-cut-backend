package com.onebyte.life4cut.sample.repository;

import com.onebyte.life4cut.common.vo.Email;
import com.onebyte.life4cut.fixture.SampleFixtureFactoryFactory;
import com.onebyte.life4cut.sample.domain.Sample;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@EnableJpaAuditing
class SampleRepositoryImplTest {

    private final EntityManager entityManager;

    private final SampleFixtureFactoryFactory sampleFixtureFactory;

    private final SampleRepository sampleRepository;

    @Autowired
    public SampleRepositoryImplTest(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.sampleFixtureFactory = new SampleFixtureFactoryFactory(entityManager);
        this.sampleRepository = new SampleRepositoryImpl(entityManager);
    }

    @Nested
    class Save {

        @Test
        @DisplayName("샘플을 저장한다")
        void saveSample() {
            // given
            Sample sample = sampleFixtureFactory.make((entity, builder) -> {
                builder.setNull("id");
                builder.set("email", Email.of("test@gmail.com"));
                builder.set("nickname", "nickname");
                builder.setNull("createdAt");
                builder.setNull("updatedAt");
            });

            // when
            Sample save = sampleRepository.save(sample);

            // then
            assertThat(save.getId()).isNotNull();
            assertThat(save.getId()).isEqualTo(sample.getId());
            assertThat(save.getEmail()).isEqualTo(sample.getEmail());
            assertThat(save.getNickname()).isEqualTo(sample.getNickname());
            assertThat(save.getCreatedAt()).isNotNull();
            assertThat(save.getUpdatedAt()).isNotNull();
        }
    }

}