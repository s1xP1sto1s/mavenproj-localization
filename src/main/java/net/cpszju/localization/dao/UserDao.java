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
	 * �����û����������û����������ж�λ��Ϣ
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
	 * �����û����������û�
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
