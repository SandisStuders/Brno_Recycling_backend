package com.recycleapp.backend.recyclingcontainer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecyclingContainerRepository extends JpaRepository<RecyclingContainer, String> {
}
