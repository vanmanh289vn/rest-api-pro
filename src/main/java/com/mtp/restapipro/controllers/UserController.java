package com.mtp.restapipro.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mtp.restapipro.dto.request.UserUpdateRequest;
import com.mtp.restapipro.models.User;
import com.mtp.restapipro.repository.IUserRepository;
import com.mtp.restapipro.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IUserRepository userRepository;
	
	private Sort.Direction getSortDirection(String direction) {
	    if (direction.equals("asc")) {
	      return Sort.Direction.ASC;
	    } else if (direction.equals("desc")) {
	      return Sort.Direction.DESC;
	    }

	    return Sort.Direction.ASC;
	  }
	
	@GetMapping("/users")
	@PreAuthorize("hasRole('USER') or hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String email){
		try {
			List<User> users = new ArrayList<User>();
			if(email == null){
				userService.findAll().forEach(users::add);
			} else {
				userService.findByEmailContaining(email).forEach(users::add);
			}
			if(users.isEmpty()){
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/users/{id}")
//	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id){
		Optional<User> user = userService.findById(id);
		if(user.isPresent()){
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/users/{id}")
//	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody UserUpdateRequest userUpdateReq){
		Optional<User> userData = userService.findById(id);
		if(userData.isPresent()){
			User _user = userData.get();
			_user.setUsername(userUpdateReq.getUsername());
			_user.setEmail(userUpdateReq.getEmail());
			_user.setFirst_name(userUpdateReq.getFirst_name());
			_user.setLast_name(userUpdateReq.getLast_name());
			
			return new ResponseEntity<User>(userService.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/users/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id){
		try {
			userService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/users")
	public ResponseEntity<HttpStatus> deleteUsersByIds(@RequestParam(required = false) List<Long> ids) {
		if(ids != null && ids.size() > 0){
			try {
				userService.deleteAllUserByIds(ids);
				return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
			} catch (Exception e) {
				return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/pagingusers")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAllUsersPage(
		      @RequestParam(required = false) String email,
		      @RequestParam(required = true) int page,
		      @RequestParam(required = true) int pageSize,
		      @RequestParam(defaultValue = "id,asc") String[] sort) {

		    try {
		      List<Order> orders = new ArrayList<Order>();

		      if (sort[0].contains(",")) {
		        // will sort more than 2 fields
		        // sortOrder="field, direction"
		        for (String sortOrder : sort) {
		          String[] _sort = sortOrder.split(",");
		          orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
		        }
		      } else {
		        // sort=[field, direction]
		        orders.add(new Order(getSortDirection(sort[1]), sort[0]));
		      }

		      List<User> users = new ArrayList<User>();
		      Pageable pagingSort = PageRequest.of(page - 1, pageSize, Sort.by(orders));

		      Page<User> pageUsers = null;
		      if (email == null)
		        pageUsers = userService.findAll(pagingSort);
		      else
		        pageUsers = userService.findByEmailContaining(email, pagingSort);

		      users = pageUsers.getContent();

		      Map<String, Object> response = new HashMap<>();
		      response.put("totalItems", pageUsers.getTotalElements());
		      response.put("currentPage", pageUsers.getNumber());
		      response.put("pageSize", pageUsers.getSize());
		      response.put("totalPages", pageUsers.getTotalPages());
		      response.put("items", users);
		      return new ResponseEntity<>(response, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		  }
	

}
