package net.cpszju.localization.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.cpszju.localization.algorithm.TDOA;
import net.cpszju.localization.dao.LoginDao;
import net.cpszju.localization.dao.PositionDao;
import net.cpszju.localization.dao.UserDao;
import net.cpszju.localization.domain.Login;
import net.cpszju.localization.domain.Position;
import net.cpszju.localization.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private PositionDao positionDao;
	
	public float[] beginTDOALocalization(String[] path,int[][] beacon){
		TDOA tdoa = new TDOA(path,beacon);
		float[] res = tdoa.getResult();
		User user = userDao.get(1);
		Position p = new Position();
		p.setX(res[0]);
		p.setY(res[1]);
		p.setUser(user);
		positionDao.save(p);
		return res;
	}
	
	public boolean checkUserByNamePassword(String name,String password){
		User user = userDao.findUserByName(name);
		if(user==null)
			return false;
		if(password.equals(user.getPassword())){
			Login login = new Login();
			login.setLoginTime(new Date(System.currentTimeMillis()));
			login.setUser(user);
			loginDao.save(login);
			return true;
		}
		return false;
	}
	
	public List<Position> queryUserPosition(String name){
		return userDao.queryUserPosition(name);
	}
}
