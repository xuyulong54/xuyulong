package wusc.edu.pay.web.boss.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.ArticleBusTypeEnum;
import wusc.edu.pay.common.enums.ArticleTypeEnum;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.common.web.file.FastDFSClient;
import wusc.edu.pay.facade.boss.entity.Article;
import wusc.edu.pay.facade.boss.service.ArticleFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;
import wusc.edu.pay.web.permission.entity.PmsOperator;


/**
 * 类描述：
 * 
 * @author: huangbin
 * @date： 日期：2013-12-3 时间：下午5:53:32
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class ArticleAction extends BossBaseAction {
	private File file;// 上传文件本身
	private String fileFileName;// 上传文件的原始文件名
	private String fileContentType;// 上传文件的文件类型

	public final int MAX_FILE_SIZE = 1024 * 1024 * 10;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7356381134861725185L;
	@Autowired
	private ArticleFacade articleFacade;

	/***
	 * 查询文章列表
	 * 
	 * @return
	 */
	@Permission("boss:article:view")
	public String list() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("title", getString("title"));
		paramMap.put("type", getString("type"));
		paramMap.put("status", getString("status"));
		super.pageBean = articleFacade.listPage(getPageParam(), paramMap);
		List articleTypeList = ArticleTypeEnum.toList();
		this.putData("articleTypeList", articleTypeList);
		this.pushData(pageBean);
		this.pushData(paramMap); // 回显查询条件
		return "list";
	}

	/***
	 * 跳转新增文章管理页面
	 * 
	 * @return
	 */
	@Permission("boss:article:add")
	public String toAdd() {
		List articleTypeList = ArticleTypeEnum.toList();
		this.putData("articleTypeList", articleTypeList);
		return "add";
	}

	/**
	 * 文件格式校验
	 */
	public boolean validateUploadFile(File file, String fileFileName) {
		String lastName = fileFileName.substring(fileFileName.lastIndexOf(".") + 1);

		if (ValidateUtils.isEmpty(file)) {
			// 没有上传文件
			return false;
		}
		if (file.length() > MAX_FILE_SIZE) {
			// 上传的文件文件不能大于10M
			return false;
		}
		if (!"jpg".equals(lastName) && !"gif".equals(lastName) && !"jpeg".equals(lastName) && !"png".equals(lastName)) {
			// 上单的文件文件必须是jpg、gif、jpeg、png为后缀名的
			return false;
		}
		return true;
	}

	/***
	 * 文本编辑器文件上传
	 * 
	 * @return
	 */
	@Permission("boss:article:add")
	public void uploadImg() {
		HttpServletResponse response = getHttpResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if (file == null || fileFileName == null) {
				out.write("无法获得该图片，请稍后重试");
			} else {
				if (validateUploadFile(file, fileFileName)) {
					String contractFile = FastDFSClient.uploadFile(file, fileFileName);// 文件地址
					if (contractFile != null) {
						out.write(contractFile);
					}
				} else {
					out.write("上传的文件文件不能大于10M,且后缀名必须为jpg,jpeg,gif,png");
				}
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// HttpServletResponse response = this.getHttpResponse();
		// HttpServletRequest request = this.getHttpRequest();
		// response.setContentType("text/html; charset=UTF-8");
		// response.setHeader("Cache-Control", "no-cache");
		// DiskFileItemFactory fac = new DiskFileItemFactory();
		// ServletFileUpload upload = new ServletFileUpload(fac);
		// upload.setHeaderEncoding("utf-8");
		// String err = "";
		// String newFileName = "";
		// String fileExt = "jpg,jpeg,bmp,gif,png";
		// long maxSize = 1024*1024;
		// ///////////////////////////////////////////////////////////////////
		//
		//
		// try {
		// List<FileItem> items = upload.parseRequest(request);
		// Map<String, Serializable> fields = new HashMap<String,
		// Serializable>();
		// Iterator<FileItem> iter = items.iterator();
		// while (iter.hasNext()) {
		// FileItem item = (FileItem) iter.next();
		// if (item.isFormField()){
		// fields.put(item.getFieldName(), item.getString());
		// } else {
		// fields.put(item.getFieldName(), item);
		// }
		// }
		// String
		// folder=request.getSession().getServletContext().getRealPath("");
		// String savePath =
		// folder+File.separator+"xheditorUpload"+File.separator;
		// File f1 = new File(savePath);
		// System.out.println("---上传保存的目录："+savePath);
		// if (!f1.exists()) {
		// f1.mkdirs();
		// }
		// //获取表单的上传文件
		// FileItem uploadFile = (FileItem)fields.get("filedata");
		// if(uploadFile == null){
		// printInfo(response, "上传文件为空", "");
		// return;
		// }
		// //获取文件上传路径名称
		// String fileNameLong = uploadFile.getName();
		// //获取文件扩展名
		// //索引加1的效果是只取xxx.jpg的jpg
		// String extensionName =
		// fileNameLong.substring(fileNameLong.lastIndexOf(".") + 1);
		// System.out.println("extensionName:" + extensionName);
		// //检查文件类型
		// if (("," + fileExt.toLowerCase() + ",").indexOf("," +
		// extensionName.toLowerCase() + ",") < 0){
		// printInfo(response, "不允许上传此类型的文件", "");
		// return;
		// }
		// //文件是否为空
		// if (uploadFile.getSize() == 0){
		// printInfo(response, "上传文件不能为空", "");
		// return;
		// }
		// //检查文件大小
		// if (uploadFile.getSize() > maxSize){
		// printInfo(response, "上传文件的大小超出限制,最大不能超过"+maxSize+"M", "");
		// return;
		// }
		// //文件存储的相对路径
		// String saveDirPath = "/xheditorUpload/";
		// //文件存储在容器中的绝对路径
		// String saveFilePath =
		// request.getSession().getServletContext().getRealPath("") +
		// saveDirPath;
		// //构建文件目录以及目录文件
		// File fileDir = new File(saveFilePath);
		// if (!fileDir.exists()) {fileDir.mkdirs();}
		// //重命名文件
		// String filename = UUID.randomUUID().toString();
		// File savefile = new File(saveFilePath + filename + "." +
		// extensionName);
		// //存储上传文件
		// uploadFile.write(savefile);
		// //这个地方根据项目的不一样，需要做一些特别的定制。
		// newFileName = "/bmxxfb"+saveDirPath + filename + "." + extensionName;
		// } catch (FileUploadException e) {
		// e.printStackTrace();
		// printInfo(response,"文件上传失败","");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// printInfo(response,"上传失败","");
		// }
		// ///////////////////////////////////////////////////////////////////
		// printInfo(response,err,newFileName);
	}

	/**
	 * 使用I/O流输出 json格式的数据
	 * 
	 * @param response
	 * @param err
	 * @param newFileName
	 * @throws IOException
	 */
	public void printInfo(HttpServletResponse response, String err, String newFileName) {
		String returnStr = "{\"err\":\"" + err + "\",\"msg\":\"" + newFileName + "\"}";
		outPrint(response, returnStr);
	}

	public static String getParameterByName(Iterator<FileItem> it, String key) {
		Iterator i = it;
		String res = "";
		while (i.hasNext()) {
			FileItem fi = (FileItem) i.next();
			// System.out.println(fi.getFieldName()+":"+fi.getString());
			if (fi.getFieldName().equals(key)) {
				res = fi.getString();
				break;
			}
		}
		return res;
	}

	/***
	 * 新增
	 * 
	 * @return
	 */
	@Permission("boss:article:add")
	public String doAdd() {
		Long id = getLong("id");
		Integer type = getInteger("type"); // 类型
		String title = getString("title"); // 文章标题
		String content = getString("content"); // 内容
		Integer status = getInteger("status"); // 状态
		Integer articleType = getInteger("articleType"); // 所属系统

		if (id != null) { // 修改
			Article article = articleFacade.getById(id);
			if (article != null) {
				article.setTitle(title);
				article.setContent(content);
				article.setEditTime(new Date());
				article.setType(type);
				article.setStatus(status); // 状态
				article.setArticleType(ArticleBusTypeEnum.ONLINE.getValue());// 类型
				article.setArticleType(articleType);
				articleFacade.update(article);
				super.logEdit("修改文章.文章标题[" + article.getTitle() + "]");
			}
		} else { // 新增
			Article article = new Article();
			PmsOperator operator = this.getLoginedOperator();
			article.setTitle(title); // 标题
			article.setContent(content); // 内容
			article.setEditTime(new Date());
			article.setOperatorId(operator.getId()); // 操作员ID
			article.setOperatorName(operator.getRealName()); // 操作员姓名
			article.setType(type); // 类型
			article.setStatus(status); // 状态
			article.setArticleType(ArticleBusTypeEnum.ONLINE.getValue());// 类型
			article.setArticleType(articleType);
			articleFacade.create(article);
			super.logSave("新增文章.文章标题[" + article.getTitle() + "]");
		}
		return operateSuccess();
	}

	/***
	 * 跳转到修改页面
	 * 
	 * @return
	 */
	@Permission("boss:article:edit")
	public String toEdit() {
		Long id = getLong("id");
		Article article = articleFacade.getById(id);
		List articleTypeList = ArticleTypeEnum.toList(); // 加载文章类型列表
		this.putData("articleTypeList", articleTypeList);
		this.pushData(article);
		return "add";
	}

	/***
	 * 查看详情
	 * 
	 * @return
	 */
	@Permission("boss:article:view")
	public String toView() {
		Long id = getLong("id");
		Article article = articleFacade.getById(id);
		List articleTypeList = ArticleTypeEnum.toList(); // 加载文章类型列表
		this.putData("articleTypeList", articleTypeList);
		this.pushData(article);
		return "view";
	}
}
