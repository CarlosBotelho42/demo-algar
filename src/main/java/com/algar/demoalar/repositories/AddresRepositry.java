package com.algar.demoalar.repositories;

import com.algar.demoalar.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddresRepositry extends JpaRepository<Address, Integer> {

}
