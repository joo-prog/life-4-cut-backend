package com.onebyte.life4cut.sample.domain;

import com.onebyte.life4cut.common.entity.BaseEntity;
import com.onebyte.life4cut.common.vo.Email;
import com.onebyte.life4cut.common.vo.EmailConverter;
import com.onebyte.life4cut.common.vo.UrlPath;
import com.onebyte.life4cut.common.vo.UrlPathConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Sample extends BaseEntity {
    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Convert(converter = EmailConverter.class)
    private Email email;

    @Convert(converter = UrlPathConverter.class)
    @Column(name = "profile_path", nullable = true)
    private UrlPath profile;

    @Column
    private LocalDateTime deletedAt;


    public static Sample create(String nickname, Email email) {
        Sample sample = new Sample();
        sample.nickname = nickname;
        sample.email = email;
        return sample;
    }

}
