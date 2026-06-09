package br.com.fiap.chronos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_NODES")
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String location;

    @Column(name = "buffered_packets", nullable = false)
    private Integer bufferedPackets = 0;

    @Column(length = 20)
    private String status = "ACTIVE";

    // Construtor vazio
    public Node() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getBufferedPackets() {
        return bufferedPackets;
    }

    public void setBufferedPackets(Integer bufferedPackets) {
        this.bufferedPackets = bufferedPackets;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
