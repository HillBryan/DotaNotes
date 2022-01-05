package com.dota_notes.db.springbootmysql.repository;

import com.dota_notes.db.springbootmysql.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LinkRepository extends JpaRepository<Link, Integer> {

    @Query(value= "SELECT * FROM dota_notes.link WHERE hash_key = :hash_key", nativeQuery = true)
    Link getLinkByUser(@Param("hash_key") String hash_key);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO dota_notes.link (match_details, steam_id, hash_key)" +
                   "VALUES (:match_details, :steam_id, :hash_key)", nativeQuery = true)
    void addLink(@Param("match_details") String match_details,
                 @Param("steam_id") String steam_id,
                 @Param("hash_key") String hash_key);
}
