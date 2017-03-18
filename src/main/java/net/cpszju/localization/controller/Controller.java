package net.cpszju.localization.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import net.cpszju.localization.domain.Position;
import net.cpszju.localization.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Controller {
		
	public static void main(String[] args){	
		//加载Spring配置文件，启动Spring容器
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		UserService userService = (UserService)ctx.getBean("userService");
		System.out.println("Spring启动成功!");
		
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		
		try{
			ServerSocket ss = new ServerSocket(8888);
			socket = ss.accept();
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
		}catch(IOException e){}

		System.out.println("连接成功！");
		String line = null;
		while(true){
			try {
				line = br.readLine();
				//用户登录检查
				if("login".equals(line)){
					String name = br.readLine();
					String password = br.readLine();
					boolean checked = userService.checkUserByNamePassword(name, password);
					if(checked){
						pw.println("success");
						System.out.println("登录成功！");
						List<Position> pos = userService.queryUserPosition(name);
						for(Position p:pos){
							pw.println(p.getX()+" "+p.getY());
						}
					}
					else{
						pw.println("fail");
						System.out.println("登录失败！");
					}
					pw.flush();
				}
			} catch (IOException e) {
				throw new RuntimeException("读取数据异常"+e.toString());
			} 
		}
	}
}
