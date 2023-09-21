package com.onebyte.life4cut.picture.domain;

import com.onebyte.life4cut.common.entity.BaseEntity;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
        indexes = {
                @Index(name = "idx_picture_tag_relation_1", columnList = "pictureId,tagId", unique = true),
                @Index(name = "idx_picture_tag_relation_2", columnList = "albumId"),
                @Index(name = "idx_picture_tag_relation_3", columnList = "tagId")
        }
)
public class PictureTagRelation extends BaseEntity {
    @Nonnull
    @Column(nullable = false)
    private Long pictureId;

    @Nonnull
    @Column(nullable = false)
    private Long albumId;

    @Nonnull
    @Column(nullable = false)
    private Long tagId;

    @Nullable
    @Column
    private LocalDateTime deletedAt;
}
