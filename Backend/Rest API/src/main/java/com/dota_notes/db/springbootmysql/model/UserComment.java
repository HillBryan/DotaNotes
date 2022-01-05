package com.dota_notes.db.springbootmysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserComment {

    @Id
    @GeneratedValue
    @Column(name = "user_comment_id")
    private Integer link_id;

    @Column(name = "steam_id")
    private Integer steam_id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "recipient_steam_id")
    private String recipient_steam_id;

    public UserComment() {

    }

    public Integer getLink_id() {
        return link_id;
    }

    public void setLink_id(Integer link_id) {
        this.link_id = link_id;
    }

    public Integer getSteam_id() {
        return steam_id;
    }

    public void setSteam_id(Integer steam_id) {
        this.steam_id = steam_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRecipient_steam_id() {
        return recipient_steam_id;
    }

    public void setRecipient_steam_id(String recipient_steam_id) {
        this.recipient_steam_id = recipient_steam_id;
    }
}
