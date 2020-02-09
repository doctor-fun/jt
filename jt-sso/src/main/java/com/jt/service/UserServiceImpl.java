package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.UserMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> findAll() {
		return userMapper.selectList(null);
	}

	/**
	 *true已存在
	 * false可以使用
	 * @param param
	 * @param type
	 *
	 * 字段: type
	 * username 1
	 * phone 2
	 * email 3
	 * @return
	 */
	@Override
	public boolean checkUser(String param, Integer type) {

		Map<Integer,String> map=new HashMap<>();
		map.put(1,"username");
		map.put(2,"phone");
		map.put(3,"email");
		String column=map.get(type);
		QueryWrapper<User> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq(column,param);
		User user=userMapper.selectOne(queryWrapper);

		return user==null?false:true;







	}
}
