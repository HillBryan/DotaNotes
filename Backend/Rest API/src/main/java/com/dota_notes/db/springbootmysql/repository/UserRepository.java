package com.dota_notes.db.springbootmysql.repository;

import com.dota_notes.db.springbootmysql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value= "SELECT * FROM dota_notes.user WHERE steam_id = :steam_id", nativeQuery = true)
    User getUserBySteamID(@Param("steam_id") String steam_id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO dota_notes.user (steam_id) VALUES (:steam_id)", nativeQuery = true)
    void addUser(@Param("steam_id") String steam_id);
}
