package com.paypal.bfs.test.employeeserv.entity;

import com.paypal.bfs.test.employeeserv.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = Constants.IDEMPOTENCY_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdempotencyEntity implements Serializable {

    @Id
    @Column(name = "idempotency_key", length = 255, nullable = false)
    private String idempotencyKey;

    @Column(name = "entity_id", length = 255, nullable = false)
    private String entityId;

    @Column(name = "entity_type", length = 127, nullable = false)
    private String entityType;

    @Column(name = "time_created", length = 31, nullable = false)
    private Date timeCreated;

}
