package me.zjor.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: Sergey Royz
 * @since: 06.11.2013
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "session_data")
//TODO: consider versioning and optimistic lock
//TODO: the same session might be modified from different threads
public class SessionModel {

    @Id
    @Column(name = "session_id", unique = true)
    private String sessionId;

    @Column(name = "session_data", columnDefinition = "TEXT")
    private String sessionData;

    @Column(name = "expiration_date")
    private Date expirationDate;

}
