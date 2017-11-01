package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;

public class UserDao {
	// 获取用户总数
	public int getTotal() {
		int total = 0;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			String sql = "select * count(*) from User";
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	// 添加用户
	public void add(User user) {
		String sql = " insert into user values(null,?,?)";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				user.setId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 更新用户信息
	public void update(User user) {

		String sql = "Update user set name=? , password=? where id=?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getId());

			ps.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除用户
	public void delete(int id) {
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
			String sql = "delete from User where id=" + id;
			s.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据id获取用户
	public User get(int id) throws Exception {
		User user = null;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
			String sql = "select * from User where id=" + id;

			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				user = new User();
				String name = rs.getString("name");
				user.setName(name);
				String password = rs.getString("password");
				user.setPassword(password);
				user.setId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	// 获取用户列表(从某个id开始的多少个)
	public List<User> list(int start, int count) {
		List<User> users = new ArrayList<User>();
		String sql = "select * from User order by id desc limit ?,?";

		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();
				int id = rs.getInt(1);
				
				String name = rs.getString("name");
				user.setName(name);
				String password = rs.getString("password");
				user.setPassword(password);
				user.setId(id);
				
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;
	}
	
	//是否退出
	public boolean isExist(String name){
		User user = get(name);
		return user!=null;
	}

	private User get(String name) {
		
		User user = null;
		String sql = "select * from User where name=?";
		
		try (Connection c = DBUtil.getConnection();PreparedStatement ps = c.prepareStatement(sql)){
			ps.setString(1, name);
			ResultSet rs= ps.executeQuery();
			
			if(rs.next()){
				user = new User();
				int id = rs.getInt("id");
				user.setId(id);
				String password = rs.getString("password");
				user.setPassword(password);
				user.setName(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	//获取用户信息
	public User get(String name, String password){
		
		User user = null;
		String sql = "select * from User where name = ? and password = ?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, name);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				user = new User();
				int id = rs.getInt("id");
				user.setId(id);
				user.setName(name);
				user.setPassword(password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
