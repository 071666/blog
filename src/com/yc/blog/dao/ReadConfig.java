package com.yc.blog.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 采用单例模拟来读取配置文件的方法
 * @author navy
 */
public class ReadConfig extends Properties{
	private static final long serialVersionUID = 8692456923187173837L;
	private static ReadConfig instance = new ReadConfig(); // 饿汉式
	
	private ReadConfig() { // 构造方法私有化
		// 这种写法会自动帮你关闭流，而且一定会关闭  JDK1.8
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("db.properties")) { 
			this.load(is); // 读取配置文件
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 因为构造方法已经私有化，也就意味着不能在外面来创建这个类的对象，所以我们必须创建好一个对象，然后将这个对象
	// 通过一个公有方法来返回。并且这个公有的方法必须是静态的，因为不能通过类的对象来方法，只能通过类名直接访问
	public static ReadConfig getInstance() {
		// 如果判断这个类的对象已经存在，这个时候我们需要声明一个变量，来存储。
		// 因为这个变量必须在静态方法中使用，所以必须将这个变量定义为静态的
		return instance;
	}
}
