package com.userfront.service.UserServiceImpl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.userfront.dao.UserDao;
import com.userfront.domain.User;

//class for UserService used in spring security
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username1) throws UsernameNotFoundException {
		
		User user = userDao.findByUsername(username1);
		if(null == user){
			LOG.warn("Username {} not found",username1);
			throw new UsernameNotFoundException("Username "+ username1 + " not found" );
		}
		return user;
	}

}
