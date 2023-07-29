package com.jabil.downtime;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PostPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

@Component
public class BreakdownListener extends AuditingEntityListener {

    private static EntityManager entityManager;

    @Autowired
    public void init(EntityManager entityManager) {
        BreakdownListener.entityManager = entityManager;
    }

    @PostPersist
    public void onBreakdownCreate(Breakdown breakdown) {
        System.out.println("nowy breakdown = " + breakdown.getFailureName());
    }
}
