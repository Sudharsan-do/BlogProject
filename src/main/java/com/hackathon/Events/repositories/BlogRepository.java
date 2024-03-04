package com.hackathon.Events.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hackathon.Events.models.Blog;
import com.hackathon.Events.models.UserDetails;

@Repository
public interface BlogRepository extends CrudRepository<Blog, String>{
	
	//@Query("SELECT B FROM Blog B WHERE B.userId = :userId AND B.status = :status")
	List<Blog> findByUserDetailsAndStatus(UserDetails userDetails,String status);
	//SELECT B FROM UserDetails U join U.blogs B WHERE B.userId = :userId AND B.status = :status
	
	List<Blog> findByStatus(String status);
	
}
