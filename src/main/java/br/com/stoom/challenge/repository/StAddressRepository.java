package br.com.stoom.challenge.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.stoom.challenge.entity.StAddress;

@Repository
public interface StAddressRepository extends JpaRepository<StAddress, UUID> {

}
