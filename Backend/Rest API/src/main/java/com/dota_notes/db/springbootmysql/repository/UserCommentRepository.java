package com.dota_notes.db.springbootmysql.repository;


import com.dota_notes.db.springbootmysql.model.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserCommentRepository extends JpaRepository<UserComment, Integer> {

    @Query(value= "SELECT * FROM dota_notes.user_comment WHERE steam_id = :steam_id", nativeQuery = true)
    List<UserComment> getUserCommentsBySteamID(@Param("steam_id") String steam_id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO dota_notes.user_comment (comment, recipient_steam_id, steam_id)" +
            "VALUES (:comment, :recipient_steam_id, :steam_id)", nativeQuery = true)
    void addUserComment(@Param("comment") String match_details,
                        @Param("recipient_steam_id") String steam_id,
                        @Param("steam_id") String hash_key);
}
