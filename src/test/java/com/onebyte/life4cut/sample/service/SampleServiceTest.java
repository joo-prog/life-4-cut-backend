package com.onebyte.life4cut.sample.service;

import com.onebyte.life4cut.common.vo.Email;
import com.onebyte.life4cut.fixture.SampleFixtureFactoryFactory;
import com.onebyte.life4cut.sample.domain.Sample;
import com.onebyte.life4cut.sample.repository.SampleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SampleServiceTest {
    private final SampleRepository sampleRepository = mock(SampleRepository.class);

    private final SampleService sampleService = new SampleService(sampleRepository);

    private final SampleFixtureFactoryFactory sampleFixtureFactory = new SampleFixtureFactoryFactory(null);


    @Nested
    class Save {

        @Test
        @DisplayName("새로운 샘플을 추가하고 추가된 샘플의 아이디를 반환한다.")
        void saveSample() {
            // given
            Email email = Email.of("test@gmail.com");
            String nickname = "nickname";

            Sample sample = sampleFixtureFactory.make((entity, builder) -> {
                builder.set("id", 1L);
                builder.set("email", email);
                builder.set("nickname", nickname);
            });

            when(sampleRepository.save(any())).thenReturn(sample);

            // when
            Long id = sampleService.save(email, nickname);

            // then
            assertThat(id).isEqualTo(1L);
        }
    }


}