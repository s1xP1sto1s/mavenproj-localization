package net.cpszju.service.test;

import net.cpszju.localization.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void beginTDOALocalizationTest(){
		String path1 = "e:/tdoadata/sigB.wav";
		String path2 = "e:/tdoadata/sigA.wav";
		String path3 = "e:/tdoadata/sigC.wav";
		String path4 = "e:/tdoadata/sigD.wav";
		String[] path = new String[]{path1,path2,path3,path4};
		int[][] beacon = new int[][]{{4,0},{0,0},{4,4},{0,4}}; 
		float[] result= userService.beginTDOALocalization(path, beacon);
		System.out.println("X:"+result[0]+"Y:"+result[1]);
	}
	
	@Test
	public void checkUserTest(){
		System.out.println(userService.checkUserByNamePassword("аж╥Е", "666666"));
	}
}
