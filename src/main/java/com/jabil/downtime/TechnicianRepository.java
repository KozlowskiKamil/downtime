package com.jabil.downtime;


import com.jabil.downtime.dto.TechnicianDto;
import com.jabil.downtime.model.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician , Long> {


    Technician findByBadgeNumber(int badgeNumber);

}
