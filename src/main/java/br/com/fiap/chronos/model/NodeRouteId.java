package br.com.fiap.chronos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class NodeRouteId implements Serializable {

    @Column(name = "source_node_id")
    private Long sourceNodeId;

    @Column(name = "target_node_id")
    private Long targetNodeId;
}