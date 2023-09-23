package com.onebyte.life4cut.album.domain;

import com.onebyte.life4cut.common.entity.BaseEntity;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Slot extends BaseEntity {
    @Nonnull
    @Column(nullable = false)
    private Long albumId;

    @Nullable
    @Column
    private Long pictureId;

    @Nonnull
    @Column(nullable = false)
    private Long page;

    @Nonnull
    @Column(nullable = false)
    private String layout;

    @Nonnull
    @Column(nullable = false)
    private String location;

    @Nullable
    @Column
    private LocalDateTime deletedAt;

    public void addPicture(Long pictureId) {
        this.pictureId = pictureId;
    }

    public boolean isIn(@Nonnull Album album) {
        return albumId.equals(album.getId());
    }
}
