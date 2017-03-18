package net.cpszju.dao.test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.cpszju.localization.dao.LoginDao;
import net.cpszju.localization.dao.PositionDao;
import net.cpszju.localization.dao.UserDao;
import net.cpszju.localization.domain.Login;
import net.cpszju.localization.domain.Position;
import net.cpszju.localization.domain.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class BaseTest {

	@Autowired
	UserDao userDao;
	@Autowired
	LoginDao loginDao;
	@Autowired
	PositionDao positionDao;
	
	@Test
	public void saveUserTest(){
		User user = new User();
		user.setName("¡÷∑Â");
		user.setPassword("666666");
		user.setSex(1);
		user.setDesciption("Õ€—Ω—Ω—Ω—Ω—Ω!!!");
		
		Position position1 = new Position();
		position1.setX(0.21345F);
		position1.setY(1.12354F);
		Position position2 = new Position();
		position2.setX(2.21345F);
		position2.setY(3.12564F);
		
		Login login = new Login();
		login.setLoginTime(new Date(System.currentTimeMillis()));
		
		Set<Position> setp = new HashSet<Position>();
		setp.add(position1);
		setp.add(position2);
		Set<Login> setl = new HashSet<Login>();
		setl.add(login);
		
		user.setPositons(setp);
		user.setLogins(setl);
		userDao.save(user);
	}
	
	@Test
	public void getUserFromPosition(){
		Login login = loginDao.get(1);
		System.out.println(login.getUser().getName());
	}
}
