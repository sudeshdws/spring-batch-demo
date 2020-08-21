package com.demo.springbatch.repository;

import com.demo.springbatch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query("update User u set u.status = :status ,u.action = :action where u.id = :id")
    int deactivateUsersFromRecord(@Param("status") String status, @Param("action") String action , @Param("id") Integer id);

}
