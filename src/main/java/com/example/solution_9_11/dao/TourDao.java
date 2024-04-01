package com.example.solution_9_11.dao;

import com.example.solution_9_11.domain.Tour;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Selection;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class TourDao {
    private final EntityManager entityManager;

    @Autowired
    public TourDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Tour findTourById(long id) {
        return entityManager.find(Tour.class, id);
    }

    public Optional<Tour> findTourByName(String name) {
        return entityManager.createQuery("from Tour where name=:name", Tour.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .getResultStream().findFirst();
    }

    public List<Tour> listAll(Integer page, Integer limit, Integer offset) {
        page = page == null || page == 1 ? 0 : page - 1;
        limit = limit == null ? 10 : limit;
        offset = offset == null ? 0 : offset;

        CriteriaBuilder qb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tour> selectQuery = qb.createQuery(Tour.class);

        selectQuery.from(Tour.class);
        SelectionQuery<Tour> query = (SelectionQuery<Tour>) entityManager.createQuery(selectQuery);

        query.setFirstResult((page == 0 ? 0 : page * limit) + offset);

        return query.list();
    }

    public Long listCount() {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(Tour.class)));

        return entityManager.createQuery(cq).getSingleResult();
    }

    public void save(Tour tour) {
        entityManager.persist(tour);
    }
}
