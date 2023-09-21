package com.onebyte.life4cut.album.domain;

import com.onebyte.life4cut.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Slot extends BaseEntity {
    @Column(nullable = false)
    private Long albumId;

    @Column
    private Long pictureId;

    @Column(nullable = false)
    private Long page;

    @Column(nullable = false)
    private String layout;

    @Column(nullable = false)
    private String location;

    @Column
    private LocalDateTime deletedAt;
}
