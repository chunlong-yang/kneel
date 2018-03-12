package com.kneel.core.h2;

import java.sql.SQLException;

import org.h2.tools.Console;
import org.h2.tools.Server;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * http://aub.iteye.com/blog/1882650
 * 
 * 
 * java org.h2.tools.Script -url jdbc:h2:~/test -user sa -script test.zip -options compression zip
 * 
 * java org.h2.tools.RunScript -url jdbc:h2:~/test -user sa -script test.zip -options compression 
 * 
 * @author e557400
 *
 */
public class H2Server extends TestCase{

	/**
	 * start console and server.
	 * 
	 * http://10.248.65.210:8082/login.do?jsessionid=e9116885f2510dbb5362ad035ff28b89
	 */ 
	public void testConsole(){ 
		try {
			testServer();
			Console.main(new String[]{"-browser"});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		try {
			Thread.sleep(24*60*60*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Server testServer(){
		Server server = new Server();
		try {
			server = Server.createTcpServer(new String []{"-tcp","-tcpAllowOthers","-tcpPort","8043"});
			server.start();
		} catch (SQLException e) { 
			e.printStackTrace();
			System.out.println("===============================>create server fail!");
		}
		System.out.println("===============================>create server success!"); 
		return server;
	}
}
