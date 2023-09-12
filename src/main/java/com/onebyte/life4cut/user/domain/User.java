package com.onebyte.life4cut.user.domain;

import com.onebyte.life4cut.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "unique_user_oauth_index",
            columnNames = {
                "oauth_type",
                "oauth_id",
            }
        ),
        @UniqueConstraint(
            name = "unique_user_nickname_index",
            columnNames = {"nickname"}
        )
    }
)
public class User extends BaseEntity {

  @Column(unique = true, length = 30)
  private String nickname;

  @Column(length = 255)
  private String profile_path;

  private String email;

  @Column(name = "oauth_type", length = 30)
  private String oauthType;

  @Column(name = "oauth_id", length = 30)
  private String oauthId;

  private LocalDateTime deletedAt;

  public void deleteSoftly(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }
}
