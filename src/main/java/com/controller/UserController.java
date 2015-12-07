package com.controller;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSON;
import com.annotation.model.AuthUser;
import com.annotation.model.UserRole;
import com.model.SysUser;
import com.service.UserServiceI;
import com.utils.CookiesUtils;
import com.utils.PropUtils;

@Controller
public class UserController extends BaseController {
	@Autowired
	protected CacheManager cacheManager;
	
	@Autowired
	private UserServiceI userService;
	
	@RequestMapping(value = "user/{id}")
	@ResponseBody
	public String getUser(@AuthUser SysUser auth,
						  @PathVariable String id,
						  @RequestParam(value="type", required=false, defaultValue="20") String type,
						  HttpServletRequest request, 
						  HttpServletResponse response) {
//		response.setContentType("text/html;charset=UTF-8");
		System.out.println(JSON.toJSONString(auth));
		
//		Cache cache  = cacheManager.getCache("ehcache_3600s");
//		System.out.println(JSON.toJSONString(cache.get("user1_" + id).getObjectValue()));
		
		System.out.println(getIpAddr(request));
		
		System.out.println(auth.getUsername());
		com.utils.Logger.info("info go to---------------------->" + type);
		com.utils.Logger.debug("debug go to---------------------->" + type);
		System.out.println(PropUtils.getProperty("loan.warring"));
		
		return auth.getUsername();
	}
	
	@RequestMapping(value = "user2/{id}")
	public String getUser2(@AuthUser SysUser auth,
			  @PathVariable String id,
			  @RequestParam(value="type", required=false, defaultValue="20") String type,
			  Model model,
			  HttpServletRequest request, 
			  HttpServletResponse response){
		String c = CookiesUtils.getCookieValue(request, "user");
		CookiesUtils.setHttpCookie(request, response, "user", c + 1);
		model.addAttribute("user", id);
		//ModelAndView view = new ModelAndView("index", "user", id);
		return "index";
	}
	
	@RequestMapping(value = "pdf")
	public void pdf(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer buf = new StringBuffer();
		buf.append("<html>");
		// put in some style
		buf.append("<head><style language='text/css'>");
		buf.append("h3 { border: 1px solid #aaaaff; background: #ccccff; ");
		buf.append("padding: 1em; text-transform: capitalize; font-family: sansserif; font-weight: normal;}");
		buf.append("p { margin: 1em 1em 4em 3em; } p:first-letter { color: red; font-size: 150%; }");
		buf.append("h2 { background: #5555ff; color: white; border: 10px solid black; padding: 3em; font-size: 200%; }");
		buf.append("</style></head>");
		// generate the body
		buf.append("<body>");
		buf.append("<p><img src='https://www.jintouhui.com/static/front/assets/img/home/wx.gif' width='70' height='90'/></p>");
		for (int i = 2; i > 0; i--) {
			buf.append("<h3>" + i + " 打印PDF, "
					+ i + "äÖÄPÄöäpöäÖÄPÄöäpöäÖÄPÄöäpöäÖÄPÄöäp!</h3>");
			buf.append("<p>TäÖÄPÄöäpöäÖÄPÄöäpöäÖÄPÄöäpöäÖÄPÄöäpund, " + (i - 1)
					+ " boäÖÄPÄöäpöäÖÄPÄöäpöäÖÄPÄöäpöäÖÄPÄöäpll</p>/n");
		}
		buf.append("<h2>No moäÖÄPÄöäpöäÖÄPÄöäpöäÖÄPÄöäpöäÖÄPÄöäp, no more bottles of beer. ");
		buf.append("Go to the store and buy some more, 99 bottles of beer on the wall.</h2>");
		buf.append("</body>");
		buf.append("</html>");
		DocumentBuilder builder;
		BufferedOutputStream bos = null;
		OutputStream out;
		try {
			out = response.getOutputStream();
		
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(buf
					.toString())));
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(doc, null);
			renderer.layout();
			response.setHeader("Content-disposition", "attachment;filename="
					+ "123.pdf");
			response.setContentType("application/pdf");
			renderer.createPDF(out);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}
}
