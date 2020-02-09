package com.jt.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.utils.ObejctMapperUtils;

import redis.clients.jedis.JedisCluster;

@Service	//dubbo注解
public class DubboUserServiceImpl implements DubboUserService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedisCluster;
	/**
	 * 1.密码明文 需要加密  md5/md5Hash
	 * 2.需要添加创建/更新时间
	 * 3.暂时使用phone代替email   email为非null
	 */
	@Override
	public void saveUser(User user) {
		String password = user.getPassword();
		String md5Pass = DigestUtils
							.md5DigestAsHex
							(password.getBytes());
		user.setPassword(md5Pass)
			.setEmail(user.getPhone())
			.setCreated(new Date())
			.setUpdated(user.getCreated());
				
		userMapper.insert(user);
	}

	/**
	 * 1.查询用户信息
	 * 2.将user对象转化为JSON
	 * 3.保存到redis中
	 */
	@Override
	public String findUserByUP(User user) {
		//将密码加密处理
		String md5Pass = DigestUtils.md5DigestAsHex(
						user.getPassword().getBytes());
		user.setPassword(md5Pass);
		//利用对象中不为null的属性当做where条件
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>(user);
		User userDB = userMapper.selectOne(queryWrapper);
		if(userDB == null) {
			//用户名和密码不正确
			return null;
		}
		
		//用户输入的信息正确
		String ticket = UUID.randomUUID().toString();
		//防止用户的敏感数据泄露,一般会对数据进行脱敏处理.
		userDB.setPassword("123456你信吗?");
		String json = ObejctMapperUtils.toJson(userDB);


		jedisCluster.setex(ticket, 7*24*3600, json);//存到redis内存上
		
		return ticket;
	}
	
}
