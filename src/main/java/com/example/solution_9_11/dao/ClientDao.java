package com.example.solution_9_11.dao;

import com.example.solution_9_11.domain.Client;
import com.example.solution_9_11.domain.Tour;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.query.SelectionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDao {
    private final EntityManager entityManager;

    @Autowired
    public ClientDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Client findClientById (long id) {
        return entityManager.find(Client.class, id);
    }

    public List<Client> listAll(Integer page, Integer limit, Integer offset) {
        page = page == null || page == 1 ? 0 : page - 1;
        limit = limit == null ? 10 : limit;
        offset = offset == null ? 0 : offset;

        CriteriaBuilder qb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Client> selectQuery = qb.createQuery(Client.class);

        selectQuery.from(Client.class);
        SelectionQuery<Client> query = (SelectionQuery<Client>) entityManager.createQuery(selectQuery);

        query.setFirstResult((page == 0 ? 0 : page * limit) + offset);
        query.setMaxResults(limit);

        return query.list();
    }

    public void save(Client client) {
        entityManager.persist(client);
    }

    public Client update(Client client) {
        return entityManager.merge(client);
    }

    public void delete(Client client) {
        entityManager.remove(client);
        entityManager.flush();
        entityManager.clear();
    }

    public boolean isClientExist(long id) {
        return findClientById(id) == null;
    }
}
