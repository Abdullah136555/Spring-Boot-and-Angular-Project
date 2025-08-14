package com.linkup.dao;



import com.linkup.model.Group;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
	Optional<Group> findByOwnerUserName(String ownerUserId);

}

