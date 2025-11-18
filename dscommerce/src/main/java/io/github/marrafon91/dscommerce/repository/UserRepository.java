package io.github.marrafon91.dscommerce.repository;

import io.github.marrafon91.dscommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
