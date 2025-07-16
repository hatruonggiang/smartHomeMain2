package com.smarthome.repository;

import com.smarthome.model.HouseMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseMemberRepository extends JpaRepository<HouseMember, Long> {

    @Query("SELECT hm FROM HouseMember hm WHERE hm.user.id = :userId")
    public List<HouseMember> findByUserId(@Param("userId") Long userId);

    boolean existsByUserIdAndHouseId(Long id, Long id1);
}
