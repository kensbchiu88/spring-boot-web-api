package com.fitfoxconn.npi.dmp.api.entity.api;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "access_log", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessLogEntity {
    /** id 流水號*/
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial", nullable = false)
    private long id;

    /** request url */
    @Column(name = "request_url")
    private String requestUrl;

    /** 呼叫者的username */
    @Column(name = "username")
    private String username;

    /** client ip */
    @Column(name = "client_ip")
    private String clientIp;

    /** response code. ex:200 */
    @Column(name = "response_status_code")
    private String responseStatusCode;

    /** request time */
    @Column(name = "request_time")
    private LocalDateTime requestTime;

    /** 花費時間 ms */
    @Column(name = "elapsed_time")
    private long elapsedTime;
}
