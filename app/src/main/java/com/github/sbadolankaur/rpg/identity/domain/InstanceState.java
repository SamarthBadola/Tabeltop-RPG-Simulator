package com.github.sbadolankaur.rpg.identity.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "instance_state")
public class InstanceState {
    @Id
    @Column(name = "id", nullable = false)
    private Short id;

    @Column(name = "initialized", nullable = false)
    private boolean initialized;

    protected InstanceState() {}

    public boolean isInitialized() {
        return initialized;
    }

    public void initialize() {
        initialized = true;
    }
}
