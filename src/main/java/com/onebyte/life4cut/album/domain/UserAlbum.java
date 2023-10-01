package com.onebyte.life4cut.album.domain;

import com.onebyte.life4cut.album.domain.vo.UserAlbumRole;
import com.onebyte.life4cut.common.entity.BaseEntity;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class UserAlbum extends BaseEntity {

    @Nonnull
    @Column(nullable = false)
    private Long userId;

    @Nonnull
    @Column(nullable = false)
    private Long albumId;

    @Nonnull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserAlbumRole role;

    @Nullable
    @Column
    private LocalDateTime deletedAt;


    public boolean isGuest() {
        return role == UserAlbumRole.GUEST;
    }
}
