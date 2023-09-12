package com.onebyte.life4cut.user.repository;

import static com.onebyte.life4cut.user.domain.QUser.user;

import com.onebyte.life4cut.auth.dto.OAuthInfo;
import com.onebyte.life4cut.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

  private final EntityManager em;
  private final JPAQueryFactory query;

  public UserRepositoryImpl(EntityManager em) {
    this.em = em;
    this.query = new JPAQueryFactory(em);
  }

  @Override
  public long save(User user) {
    em.persist(user);
    return user.getId();
  }

  @Override
  public Optional<User> findUser(int id) {
    String jpql = "select u from User u where u.id = :id";
    List<User> userList = em.createQuery(jpql, User.class)
        .setParameter("id", id)
        .getResultList();
    return userList.stream().findAny();
  }

  @Override
  public List<User> findUserByOAuthInfo(OAuthInfo oAuthInfo) {
    return query.select(user)
        .from(user)
        .where(
            user.oauthId.eq(oAuthInfo.getOauthId()),
            user.oauthType.eq(oAuthInfo.getOauthType().getType())
        )
        .fetch();
  }


}
