package com.game.repository;

import com.game.entity.PlayerDTO;
import com.game.entity.PlayerPage;
import com.game.entity.PlayerSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class PlayerCriteriaRepository {

    @Qualifier(value = "entityManager")
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public PlayerCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<PlayerDTO> findAllWithFilters(PlayerPage playerPage,
                                              PlayerSearchCriteria playerSearchCriteria) {
        CriteriaQuery<PlayerDTO> criteriaQuery = criteriaBuilder.createQuery(PlayerDTO.class);
        Root<PlayerDTO> playerDTORoot = criteriaQuery.from(PlayerDTO.class);
        Predicate predicate = getPredicate(playerSearchCriteria, playerDTORoot);
        criteriaQuery.where(predicate);
        setOrder(playerPage, criteriaQuery, playerDTORoot);

        TypedQuery<PlayerDTO> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(playerPage.getPageNumber() * playerPage.getPageSize());
        typedQuery.setMaxResults(playerPage.getPageSize());

        Pageable pageable = getPageable(playerPage);

        long playersCount = getPlayersCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, playersCount);
    }

    public Integer sizeQuery(PlayerSearchCriteria playerSearchCriteria) {
        CriteriaQuery<PlayerDTO> criteriaQuery = criteriaBuilder.createQuery(PlayerDTO.class);
        Root<PlayerDTO> playerDTORoot = criteriaQuery.from(PlayerDTO.class);
        Predicate predicate = getPredicate(playerSearchCriteria, playerDTORoot);
        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList().size();
    }

    private Predicate getPredicate(PlayerSearchCriteria playerSearchCriteria,
                                   Root<PlayerDTO> playerDTORoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(playerSearchCriteria.getName())) {
            predicates.add(
                    criteriaBuilder.like(playerDTORoot.get("name"),
                            "%"+playerSearchCriteria.getName()+"%")
            );
        }
        if(Objects.nonNull(playerSearchCriteria.getTitle())) {
            predicates.add(
                    criteriaBuilder.like(playerDTORoot.get("title"),
                            "%"+playerSearchCriteria.getTitle()+"%")
            );
        }
        if(Objects.nonNull(playerSearchCriteria.getRace())) {
            predicates.add(
                    criteriaBuilder.equal(playerDTORoot.get("race"),
                            playerSearchCriteria.getRace())
            );
        }
        if(Objects.nonNull(playerSearchCriteria.getProfession())) {
            predicates.add(
                    criteriaBuilder.equal(playerDTORoot.get("profession"),
                            playerSearchCriteria.getProfession())
            );
        }
        if(Objects.nonNull(playerSearchCriteria.getAfter())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(playerDTORoot.get("birthday"),
                            new Date(playerSearchCriteria.getAfter()))
            );
        }
        if(Objects.nonNull(playerSearchCriteria.getBefore())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(playerDTORoot.get("birthday"),
                            new Date(playerSearchCriteria.getBefore()))
            );
        }
        if(Objects.nonNull(playerSearchCriteria.getBanned())) {
            predicates.add(
                    criteriaBuilder.equal(playerDTORoot.get("banned"),
                            playerSearchCriteria.getBanned())
            );
        }
        if(Objects.nonNull(playerSearchCriteria.getMinExperience())) {
            predicates.add(
                    criteriaBuilder.ge(playerDTORoot.get("experience"),
                            playerSearchCriteria.getMinExperience())
            );
        }
        if(Objects.nonNull(playerSearchCriteria.getMaxExperience())) {
            predicates.add(
                    criteriaBuilder.le(playerDTORoot.get("experience"),
                            playerSearchCriteria.getMaxExperience())
            );
        }
        if(Objects.nonNull(playerSearchCriteria.getMinLevel())) {
            predicates.add(
                    criteriaBuilder.ge(playerDTORoot.get("level"),
                            playerSearchCriteria.getMinLevel())
            );
        }
        if(Objects.nonNull(playerSearchCriteria.getMaxLevel())) {
            predicates.add(
                    criteriaBuilder.le(playerDTORoot.get("level"),
                            playerSearchCriteria.getMaxLevel())
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(PlayerPage playerPage,
                          CriteriaQuery<PlayerDTO> criteriaQuery,
                          Root<PlayerDTO> playerDTORoot) {
        criteriaQuery.orderBy(criteriaBuilder.asc(playerDTORoot.get(playerPage.getSortBy())));
    }

    private Pageable getPageable(PlayerPage playerPage) {
        Sort sort = Sort.by(playerPage.getDirection(), playerPage.getSortBy());
        return PageRequest.of(playerPage.getPageNumber(), playerPage.getPageSize(), sort);
    }

    private long getPlayersCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<PlayerDTO> countRoot = countQuery.from(PlayerDTO.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
