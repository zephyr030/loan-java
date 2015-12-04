package test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.model.SysUser;
import com.service.UserServiceI;
import com.utils.PropUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class JunitTest {
	
	@Autowired
	private UserServiceI userService;

	@Test
	public void test() {
		Map<String, Object> map = userService.getUser("1");
		
		System.out.println(map.get("username") + "1");
		
		SysUser user = userService.getSysUser("1");
		System.out.println(user.getUsername() + "2");
		
		user = userService.getSysUser("1");
		System.out.println(user.getUsername() + "3");
		
		System.out.println(PropUtils.getProperty("loan.warring"));
		
		userService.getSys("1");
	}
	
	@Test
	public void getSysUser() {
		SysUser user = userService.getSysUser("1");
		System.out.println(user.getUsername());
		
		Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH ,-15);
        System.out.println(cal.getTime());
        
        JSON.toJSONString(user);
	}
	
	@Test
	public void getConnection() {
		int [] a = {1,2,3,4,5,6,7,1,2,1,2,2,2,3,3,4};
		ArrayList<Integer> b = new ArrayList<Integer>();
		ArrayList<Integer> c = new ArrayList<Integer>();
		for(int i = 0; i < a.length; i++) {
			if(i == 0) {
				b.add(a[i]);
				continue;
			} else {
				if(!b.contains(a[i])) {
					b.add(a[i]);
					continue;
				} else {
					if(!c.contains(a[i])) {
						c.add(a[i]);
					}
					continue;
				}
			}
		}
		
		System.out.println(c);
	}
	
	@Test
	public void valueTest() {
		ResourceBundle rb = ResourceBundle.getBundle("loan");
		System.out.println(rb.getString("loan.warring"));
	}
	
	@Test
	public void cache() {
		userService.getSys("1");
	}
	
	@Test
	public void listTest() {
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		List<String> list1 = new ArrayList<>();
		list1.add("3");
		list.addAll(list1);
		System.out.println("************************" + list);
	}
}
