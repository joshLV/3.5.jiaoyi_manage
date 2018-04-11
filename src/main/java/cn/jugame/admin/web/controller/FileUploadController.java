package cn.jugame.admin.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
/**
 * 
 * @author Administrator 
 *
 */
@Controller
public class FileUploadController {

	@RequestMapping(value = "/fileupload_save.do", method = RequestMethod.GET)
	public String fileUplad() {
		return "redirect:/fileupload.html";
	}

	@RequestMapping(value = "/fileupload_save.do", method = RequestMethod.POST)
	public void fileUplad(HttpServletResponse response,@RequestParam("file") MultipartFile file) {
		System.out.println("gggg");
		try {
			System.out.println("文件名：" + file.getOriginalFilename());
			// System.out.println(file.getName());
			System.out.println("文件大小：" + file.getSize());
			// 保存到本地
			file.transferTo(new File("c:/1.jpg"));
			PrintWriter out = response.getWriter();
			out.print("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
