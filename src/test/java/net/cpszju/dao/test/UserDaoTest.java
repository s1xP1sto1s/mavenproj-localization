package net.cpszju.dao.test;

import java.util.List;

import net.cpszju.localization.dao.UserDao;
import net.cpszju.localization.domain.Position;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class UserDaoTest {
	@Autowired
	UserDao userDao;
	
	@Test
	public void test1(){
		System.out.println(userDao.findUserByName("аж╥Е"));
	}
	
	@Test 
	public void test2(){
		List<Position> list = userDao.queryUserPosition("аж╥Е");
		for(Position p:list){
			System.out.println("x:"+p.getX()+"y:"+p.getY());
		}
	}
}
