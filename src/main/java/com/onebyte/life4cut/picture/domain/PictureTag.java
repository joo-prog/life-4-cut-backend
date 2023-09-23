package com.onebyte.life4cut.picture.domain;

import com.onebyte.life4cut.common.entity.BaseEntity;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
    indexes = {
        @Index(name = "idx_picture_tag_1", columnList = "albumId,name", unique = true),
        @Index(name = "idx_picture_tag_2", columnList = "authorId")
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PictureTag extends BaseEntity {
    @Nonnull
    @Column(nullable = false)
    private Long albumId;

    @Nonnull
    @Column(nullable = false)
    private Long authorId;

    @Nonnull
    @Column(nullable = false)
    private String name;

    @Nullable
    @Column
    private LocalDateTime deletedAt;

    @Nonnull
    public static PictureTag create(@Nonnull Long albumId, @Nonnull Long authorId, @Nonnull String name) {
        PictureTag pictureTag = new PictureTag();
        pictureTag.albumId = albumId;
        pictureTag.authorId = authorId;
        pictureTag.name = name;
        return pictureTag;
    }
}
