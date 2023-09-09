package com.onebyte.life4cut.user.repository;

import com.onebyte.life4cut.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository{

  private final EntityManager em;
  private final JPAQueryFactory query;

  public UserRepositoryImpl(EntityManager em) {
    this.em = em;
    this.query = new JPAQueryFactory(em);
  }

  @Override
  public Optional<User> findUser(int id) {
    String jpql = "select u from User u where u.id = :id";
    List<User> userList = em.createQuery(jpql, User.class)
        .setParameter("id", id)
        .getResultList();
    return userList.stream().findAny();
  }
}
