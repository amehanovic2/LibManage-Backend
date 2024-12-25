package com.lms.libmanage.repository;

import com.lms.libmanage.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.role=1")
    public List<User> getAllLibraryMembers();

    @Query("SELECT u FROM User u WHERE u.role=2")
    public List<User> getAllLibraryStaff();

    @Query("SELECT u FROM User u WHERE u.uuid=:uuid")
    public Optional<User> findByUUID(String uuid);

}
