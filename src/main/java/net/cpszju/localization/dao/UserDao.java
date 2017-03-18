package net.cpszju.localization.dao;



import java.util.LinkedList;
import java.util.List;

import net.cpszju.localization.domain.Position;
import net.cpszju.localization.domain.User;

import org.springframework.stereotype.Repository;


@Repository
public class UserDao extends BaseDao<User> {
	
	private final String QUERY_USER_POSITION = "from User u inner join u.positons where u.name=?";
	private final String FIND_USER_BY_NAME = "from User u where u.name=?";
	
	/**
	 * 根据用户姓名查找用户并返回所有定位信息
	 * @param name
	 * @return
	 */
	public List<Position> queryUserPosition(String name){
		List<Object[]> list = getHibernateTemplate().find(QUERY_USER_POSITION,name);
		List<Position> result = new LinkedList<Position>();
		for(Object[] o:list){
			result.add((Position)o[1]);
		}
		return result;
	}
	/**
	 * 根据用户姓名查找用户
	 * @param name
	 * @return
	 */
	public User findUserByName(String name){
		List<User> list = getHibernateTemplate().find(FIND_USER_BY_NAME,name);
		if(list.isEmpty()){
			return null;
		}
		else{
			return list.get(0);
		}
	}
}
