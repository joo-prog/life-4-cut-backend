package com.onebyte.life4cut.sample.repository;

import com.onebyte.life4cut.common.vo.Email;
import com.onebyte.life4cut.sample.domain.Sample;
import com.onebyte.life4cut.sample.repository.dto.SampleSearchDto;

import java.util.List;

public interface SampleRepository {

    Sample save(Sample sample);

    List<Sample> findByEmail(Email email);

    List<SampleSearchDto> search(Email email);
}
