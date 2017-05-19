package com.framework.module.common.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 文件上传 Controller
 * 
 * @author qq
 * 
 */
@Controller
@RequestMapping("/UploadController")
public class UploadController {
	private final static Logger log = LoggerFactory.getLogger(UploadController.class);

	/**
	 *  文件池名称
	 */
	private static String FILE_POOL_NAME = "uploadFiles";

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @param path
	 *            文件保存相对路径(为空则保存在FILE_POOL_NAME文件池根目录下)
	 * @return FILE_POOL_NAME/文件保存相对路径
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public List<String> upload(HttpServletRequest request, @RequestParam(name = "path", required = false) String path) {
		List<String> relativePaths = new ArrayList<String>(); // 文件保存相对路径
		String projectRootPath = request.getSession().getServletContext().getRealPath(""); // 项目根目录

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		if (!multipartResolver.isMultipart(request)) {
			log.error("不是multipart类型");
			throw new IllegalArgumentException("不是multipart类型");
		}

		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Iterator<String> fileNameIterator = multiRequest.getFileNames();

		while (fileNameIterator.hasNext()) {
			MultipartFile uploadFile = multiRequest.getFile(fileNameIterator.next());
			if (uploadFile == null) {
				continue;
			}

			String fileName = uploadFile.getOriginalFilename(); // 上传文件名
			if (StringUtils.isBlank(fileName)) {
				continue;
			}

			// 构建相对路径；格式: /path/
			path = StringUtils.isBlank(path) ? "/" : path;
			path = path.indexOf("/") == 0 ? path : "/" + path;
			path = path.lastIndexOf("/") == path.length() - 1 ? path : path + "/";

			String relativePath = FILE_POOL_NAME + path + (new Date()).getTime() + fileName;
			log.info("文件保存相对路径：{}", relativePath);

			File file = new File(projectRootPath + File.separator + relativePath);
			file.getParentFile().mkdirs();
			try {
				uploadFile.transferTo(file);
			} catch (IllegalStateException e) {
				log.error("文件上传失败", e);
				throw new IllegalArgumentException("文件上传失败");
			} catch (IOException e) {
				log.error("文件上传失败", e);
				throw new IllegalArgumentException("文件上传失败");
			}

			relativePaths.add(relativePath);
		}

		if (relativePaths.size() <= 0) {
			throw new IllegalArgumentException("请选择至少一个文件");
		}

		return relativePaths;
	}

}