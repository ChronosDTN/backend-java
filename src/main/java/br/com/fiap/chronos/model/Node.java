package br.com.fiap.chronos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "T_CDTN_NODE_REGISTRY")
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_node")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String location;

    @Column(name = "network_address", nullable = false, length = 100, unique = true)
    private String networkAddress;

    @Column(name = "created_at", nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    @Transient
    private Integer bufferedPackets = 0;

    @Transient
    private String status = "ACTIVE";

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = java.time.LocalDateTime.now();
        }
        if (this.networkAddress == null || this.networkAddress.trim().isEmpty()) {
            this.networkAddress = "dtn://node-" + java.util.UUID.randomUUID().toString().substring(0, 8);
        }
    }

    public Node() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getNetworkAddress() { return networkAddress; }
    public void setNetworkAddress(String networkAddress) { this.networkAddress = networkAddress; }

    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Integer getBufferedPackets() { return bufferedPackets; }
    public void setBufferedPackets(Integer bufferedPackets) { this.bufferedPackets = bufferedPackets; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
