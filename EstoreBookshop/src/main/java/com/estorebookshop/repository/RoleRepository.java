package com.estorebookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estorebookshop.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
