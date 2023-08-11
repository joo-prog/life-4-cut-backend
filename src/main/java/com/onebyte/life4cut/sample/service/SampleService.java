package com.onebyte.life4cut.sample.service;

import com.onebyte.life4cut.common.vo.Email;
import com.onebyte.life4cut.sample.domain.Sample;
import com.onebyte.life4cut.sample.repository.SampleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    private final SampleRepository sampleRepository;

    public SampleService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    @Transactional
    public Long save(Email email, String nickname) {
        Sample sample = sampleRepository.save(Sample.create(nickname, email));

        return sample.getId();
    }
}
