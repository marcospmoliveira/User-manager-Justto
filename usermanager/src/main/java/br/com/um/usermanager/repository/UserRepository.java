package br.com.um.usermanager.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.um.usermanager.models.User;

public interface UserRepository extends CrudRepository<User, String>{
	User findByCode(long code);
}
