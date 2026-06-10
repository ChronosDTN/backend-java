package br.com.fiap.chronos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_CDTN_NODE_ROUTE")
public class NodeRoute extends BaseEntity {

    @EmbeddedId
    private NodeRouteId id;

    @Column(name = "latency_ms")
    private Double latencyMs;

    @Column(name = "hops")
    private Integer hops;

    @Column(name = "active", nullable = false)
    private Boolean active = true;
}