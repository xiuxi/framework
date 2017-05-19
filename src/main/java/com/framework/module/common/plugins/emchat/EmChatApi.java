package com.framework.module.common.plugins.emchat;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.framework.module.common.plugins.emchat.constant.HttpRequestMethod;
import com.framework.module.common.plugins.emchat.exception.DuplicateUniquePropertyExistsException;
import com.framework.module.common.plugins.emchat.exception.EmChatException;
import com.framework.module.common.plugins.emchat.exception.InvalidGrantException;
import com.framework.module.common.plugins.emchat.exception.ServiceResourceNotFound;
import com.framework.module.common.plugins.emchat.exception.UserAlreadyRegistException;
import com.framework.module.common.plugins.emchat.exception.UserUnRegistException;
import com.framework.module.common.plugins.emchat.httpClient.HttpClientUtil;
import com.framework.module.common.plugins.emchat.httpClient.http.HttpRequestMessage;
import com.framework.module.common.plugins.emchat.httpClient.http.HttpResponseMessage;
import com.framework.module.common.plugins.emchat.vo.VExt;
import com.framework.module.common.plugins.emchat.vo.VSendMsgRequestBody;
import com.framework.module.common.plugins.emchat.vo.VRegistRequestBody;

/**
 * 环信聊天API
 * 
 * 用户体系集成API: http://docs.easemob.com/start/100serverintegration/20users
 * 
 * 发送消息API: http://docs.easemob.com/start/100serverintegration/50messages
 * 
 * @author qq
 */
public class EmChatApi {
	private final static Logger log = LoggerFactory.getLogger(EmChatApi.class);

	private static EmchatContext context;
	private static String serviceUrl;

	static {
		context = EmchatContext.newInstance();
		serviceUrl = context.getService_Url();
	}

	/*
	 * **************************************************
	 * 
	 * **************************************************
	 */

	/**
	 * 注册
	 * 
	 * @param userName
	 *            用户名
	 * @return 用户名
	 * @throws UserAlreadyRegistException
	 *             用户已被注册
	 */
	public static String regist(String userName) throws UserAlreadyRegistException {
		Assert.hasText(userName, "用户名不能为空");

		VRegistRequestBody registBody = new VRegistRequestBody(userName, context.getPassword());
		String url = serviceUrl + "users";

		HttpRequestMessage request = new HttpRequestMessage(HttpRequestMethod.POST, url);
		request.addDefaultHeaderWithToken(context.getToken().getRestApiToken(false));
		request.setBody(registBody);

		HttpResponseMessage response = HttpClientUtil.sendHttpRequest(request);

		try {
			checkResponse(response);

			return userName;
		} catch (DuplicateUniquePropertyExistsException e) {
			throw new UserAlreadyRegistException();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 批量注册(建议在20-60之间)
	 * 
	 * 处理建议
	 * 
	 * 一个用户注册失败,则全部回滚
	 * 
	 * @param userNames
	 *            用户名
	 * @return 用户名
	 * @throws UserAlreadyRegistException
	 *             用户已被注册
	 */
	// public static Set<String> batchRegit(Set<String> userNames) throws
	// UserAlreadyRegistException {
	// Assert.notNull(userNames, "参数不能为空");
	//
	// Set<VRegistBody> registBodys = new HashSet<VRegistBody>();
	// for (String userName : userNames) {
	// registBodys.add(new VRegistBody(userName, context.getPassword()));
	// }
	//
	// String url = serviceUrl + "users";
	//
	// HttpRequestMessage request = new
	// HttpRequestMessage(HttpRequestMethod.POST, url);
	// request.addDefaultHeaderWithToken(context.getRestApiToken(false));
	// request.setBody(registBodys);
	//
	// HttpResponseMessage response = HttpClientUtil.sendHttpRequest(request);
	// try {
	// checkResponse(response);
	// return userNames;
	// } catch (DuplicateUniquePropertyExistsException e) {
	// throw new UserAlreadyRegistException();
	// } catch (Exception e) {
	// throw new IllegalArgumentException(e);
	// }
	// }

	/**
	 * 获取用户
	 * 
	 * @param userName
	 *            环信用户名
	 * @throws UserUnRegistException
	 *             用户未注册
	 */
	// public static void getUser(String userName) throws UserUnRegistException
	// {
	// String url = serviceUrl + "users/" + userName;
	//
	// HttpRequestMessage request = new
	// HttpRequestMessage(HttpRequestMethod.GET, url);
	// request.addDefaultHeaderWithToken(context.getRestApiToken(false));
	//
	// HttpResponseMessage response = HttpClientUtil.sendHttpRequest(request);
	// try {
	// checkResponse(response);
	// } catch (ServiceResourceNotFound e) {
	// throw new UserUnRegistException();
	// } catch (Exception e) {
	// throw new IllegalArgumentException(e);
	// }
	//
	// }

	/**
	 * 批量获取用户
	 * 
	 * @param limit
	 *            批量用户数
	 * @param cursor
	 *            游标(为null为第一页)
	 * @return members:用户List;cursor:游标
	 */
	// public static Map<String, Object> batchGetMember(Long limit, String
	// cursor) {
	// Map<String, Object> result = new HashMap<String, Object>();
	// List<String> mobiles = new ArrayList<String>();
	//
	// limit = (limit == null) ? 10L : limit;
	// String url = serviceUrl + "users?limit=" + limit;
	// if (StringUtils.isNotBlank(cursor)) {
	// url += "&cursor=" + cursor;
	// }
	//
	// HttpRequestMessage request = new
	// HttpRequestMessage(HttpRequestMethod.GET, url);
	// request.addDefaultHeaderWithToken(context.getRestApiToken(false));
	// HttpResponseMessage response = HttpClientUtil.sendHttpRequest(request);
	//
	// ObjectNode responseBody = (ObjectNode) response.getBody();
	// ArrayNode data = (ArrayNode) responseBody.get("entities");
	// Iterator<JsonNode> iterator = data.iterator();
	// while (iterator.hasNext()) {
	// JsonNode item = iterator.next();
	// mobiles.add(item.get("username").asText());
	// }
	//
	// result.put("members", mobiles);
	// result.put("cursor", (responseBody.get("cursor") == null) ? null :
	// responseBody.get("cursor").asText());
	//
	// return result;
	// }

	/**
	 * 删除用户(删除一个用户会删除以该用户为群主的所有群组)
	 * 
	 * @param userName
	 *            用户名
	 * @throws UserUnRegistException
	 *             用户未注册
	 */
	public static void deleteUser(String userName) throws UserUnRegistException {
		String url = serviceUrl + "users/" + userName;

		HttpRequestMessage request = new HttpRequestMessage(HttpRequestMethod.DELETE, url);
		request.addDefaultHeaderWithToken(context.getToken().getRestApiToken(false));

		HttpResponseMessage response = HttpClientUtil.sendHttpRequest(request);

		try {
			checkResponse(response);
		} catch (ServiceResourceNotFound e) {
			throw new UserUnRegistException();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 批量删除用户(删除某个app下指定数量的环信账号。可一次删除N个用户,建议100-500之间)
	 * 这里只是批量的一次性删除掉N个用户,具体删除哪些并没有指定, 可以在返回值中查看到哪些用户被删除掉了
	 * 
	 * 处理建议
	 * 
	 * @param limit
	 *            删除条数
	 * @throws UserUnRegistException
	 *             用户未注册
	 */
	// public static void batchDeleteUser(Long limit) throws
	// UserUnRegistException {
	// limit = (null == limit) ? 10L : limit;
	// String url = serviceUrl + "users?limit=" + limit;
	//
	// HttpRequestMessage request = new
	// HttpRequestMessage(HttpRequestMethod.DELETE, url);
	// request.addDefaultHeaderWithToken(context.getRestApiToken(false));
	//
	// HttpResponseMessage response = HttpClientUtil.sendHttpRequest(request);
	//
	// try {
	// checkResponse(response);
	// } catch (ServiceResourceNotFound e) {
	// throw new UserUnRegistException();
	// } catch (Exception e) {
	// throw new IllegalArgumentException(e);
	// }
	// }

	/**
	 * 查看用户在线状态
	 * 
	 * @param userName
	 *            用户名
	 * @return true：在线
	 * @throws UserUnRegistException
	 *             用户未注册
	 */
	public static boolean isOnLine(String userName) throws UserUnRegistException {
		String url = serviceUrl + "users/" + userName + "/status";

		HttpRequestMessage request = new HttpRequestMessage(HttpRequestMethod.GET, url);
		request.addDefaultHeaderWithToken(context.getToken().getRestApiToken(false));
		HttpResponseMessage response = HttpClientUtil.sendHttpRequest(request);

		try {
			checkResponse(response);
		} catch (ServiceResourceNotFound e) {
			throw new UserUnRegistException();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectNode objectNode = parseObjectNode(response.getBody().toString());
		JsonNode data = objectNode.get("data");

		return "online".equalsIgnoreCase(data.get(userName).asText()) ? true : false;
	}

	/*
	 * **************************************************
	 * 
	 * **************************************************
	 */
	/**
	 * 发送消息
	 * 
	 * @param ext
	 *            扩展对象
	 * @param receivers
	 *            消息接收者
	 */
	public static void sendMessage(VExt ext, String... receivers) {
		Assert.isTrue(receivers != null && receivers.length > 0, "消息接收者不能为空");
		Assert.notNull(ext, "扩展对象不能为空");

		String url = serviceUrl + "messages";
		VSendMsgRequestBody messageBody = new VSendMsgRequestBody(receivers, ext);

		HttpRequestMessage request = new HttpRequestMessage(HttpRequestMethod.POST, url);
		request.addDefaultHeaderWithToken(context.getToken().getRestApiToken(false));
		request.setBody(messageBody);

		HttpClientUtil.sendHttpRequest(request);
	}

	/*
	 * **************************************************
	 * 
	 * **************************************************
	 */
	/**
	 * 获取webIM token(用于前端JS使用token登录)
	 * 
	 * @param userName
	 *            用户名
	 * @return webIM token
	 * @throws UserUnRegistException
	 *             用户未注册
	 */
	public static String getWebIMToken(String userName) throws UserUnRegistException {
		return context.getToken().getWebIMToken(userName);
	}

	/**
	 * 检测环信响应异常
	 * 
	 * @param httpResponseMessage
	 * @throws DuplicateUniquePropertyExistsException
	 * @throws InvalidGrantException
	 * @throws ServiceResourceNotFound
	 * @throws EmChatException
	 */
	public static void checkResponse(HttpResponseMessage httpResponseMessage)
			throws DuplicateUniquePropertyExistsException, InvalidGrantException, ServiceResourceNotFound,
			EmChatException {
		ObjectNode objectNode = parseObjectNode(httpResponseMessage.getBody().toString());

		if (httpResponseMessage.getStatus() != 200) {
			String error = objectNode.get("error").asText();
			String error_description = objectNode.get("error_description").asText();
			log.error("error: {}", error);
			log.error("error_description: {}", error_description);

			if (error.contains("duplicate_unique_property_exists")) { // 用户已存在
				throw new DuplicateUniquePropertyExistsException();
			}
			if (error.contains("invalid_grant")) { // 用户名或者密码输入错误
				throw new InvalidGrantException();
			}
			if (error.contains("service_resource_not_found")) { // 找不到服务
				throw new ServiceResourceNotFound();
			}

			throw new EmChatException();
		}
	}

	/*
	 * **************************************************
	 * 
	 * 辅助方法
	 * 
	 * **************************************************
	 */
	/**
	 * json字符串转换ObjectNode对象
	 * 
	 * @param jsonString
	 *            json字符串
	 * @return ObjectNode对象
	 */
	public static ObjectNode parseObjectNode(String jsonString) {
		Assert.hasText(jsonString, "参数不能为空");

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonFactory jsonFactory = objectMapper.getFactory();
			JsonParser jsonParser = jsonFactory.createParser(jsonString);
			ObjectNode objectNode = objectMapper.readTree(jsonParser);

			return objectNode;
		} catch (JsonParseException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/*
	 * **************************************************
	 * 
	 * **************************************************
	 */
	/**
	 * 从文件读取手机号
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 * @throws IOException
	 */
	// public static List<String> getMobilesFromFile(String path) throws
	// IOException {
	// List<String> mobiles = new ArrayList<String>();
	//
	// File file = new File(path);
	// FileReader reader = new FileReader(file);
	//
	// BufferedReader bufferReader = new BufferedReader(reader);
	//
	// String line = null;
	// while ((line = bufferReader.readLine()) != null) {
	// if (StringUtils.isNotBlank(line)) {
	// mobiles.add(line.trim());
	// }
	// }
	//
	// return mobiles;
	// }

	/**
	 * 从指定数据库读取手机号
	 * 
	 * @return
	 * @throws SQLException
	 */
	// public static List<String> getMobilesFromAssignDb(String url, String
	// user, String pass) throws SQLException {
	// List<String> mobiles = new ArrayList<String>();
	//
	// Connection conn = null;
	// Statement stm = null;
	// ResultSet rs = null;
	// try {
	// conn = DriverManager.getConnection(url, user, pass);
	// stm = conn.createStatement();
	// rs = stm.executeQuery("select mobile from t_member");
	//
	// while (rs.next()) {
	// String mobile = rs.getString("mobile");
	// mobiles.add(mobile);
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// } finally {
	// if (rs != null) {
	// rs.close();
	// }
	// if (stm != null) {
	// stm.close();
	// }
	// if (conn != null) {
	// conn.close();
	// }
	// }
	//
	// return mobiles;
	// }

	/**
	 * 导入用户
	 * 
	 * @param mobiles
	 * @throws InterruptedException
	 * @throws SQLException
	 */
	// public static void importMembers(List<String> mobiles) throws
	// InterruptedException, SQLException {
	// int limit = 30; // 批量条数
	//
	// List<VRegistBody> failsResult = new ArrayList<VRegistBody>(); //
	// 批量注册时失败的集合
	// if (mobiles != null && !mobiles.isEmpty()) {
	// List<VRegistBody> bodys = new ArrayList<VRegistBody>();
	//
	// int memberSize = mobiles.size();
	// System.out.println("需注册用户总条数：" + memberSize);
	// for (int i = 0; i < memberSize; i++) {
	// bodys.add(new VRegistBody(mobiles.get(i), EmChatConfig.Password));
	//
	// if (i > 0 && i % limit == 0) { // 每limit条执行一次
	// try {
	// batchRegit(bodys);
	//
	// bodys.clear();
	// Thread.sleep(3000);
	// } catch (Exception e) {
	// e.printStackTrace();
	//
	// failsResult.addAll(bodys); //
	// bodys.clear();
	// }
	// } else if (i == (memberSize - 1)) { // 最后一次,但不够limit条
	// try {
	// batchRegit(bodys);
	// } catch (Exception e) {
	// e.printStackTrace();
	//
	// failsResult.addAll(bodys); //
	// bodys.clear();
	// }
	// }
	// }
	//
	// List<String> errors = new ArrayList<String>();
	//
	// System.out.println("==============================================================================\n");
	// int hasRegistMemberCount = exportMembers().size();
	// System.out.println("批量注册---------->成功用户个数：" + hasRegistMemberCount);
	// System.out.println("批量注册---------->注册失败用户个数：" + failsResult.size());
	// System.out.println("\n==============================================================================");
	//
	// if (!failsResult.isEmpty()) {
	// VRegistBody item = null;
	// for (int j = 0; j < failsResult.size(); j++) {
	// item = failsResult.get(j);
	//
	// try {
	// regist(item);
	// Thread.sleep(1000);
	// } catch (UserAlreadyRegistException e) { // 用户已注册
	//
	// } catch (Exception e3) {
	// errors.add(item.getUsername()); // 注册失败用户
	// e3.printStackTrace();
	// System.out.println(
	// "==============================================================================\n");
	// System.out.println("第二次注册失败的用户：" + item.getUsername());
	// System.out.println(
	// "\n==============================================================================");
	// }
	// }
	// }
	//
	// int lastRegistMemberCount = exportMembers().size();
	// System.out.println("最终注册成功用户个数：" + lastRegistMemberCount);
	// System.out.println("最终注册失败用户个数：" + errors.size());
	// System.out.println("最终注册失败用户：" + errors);
	// }
	// }

	/**
	 * 导出用户
	 * 
	 * @return
	 */
	// public static List<String> exportMembers() {
	// Long limit = 100L; // 批量数
	// List<String> mobiles = new ArrayList<String>();
	//
	// String cursor = null;
	// while (true) {
	// Map<String, Object> result = batchGetMember(limit, cursor);
	// List<String> members = (List<String>) result.get("members");
	// cursor = result.get("cursor") == null ? null : (String)
	// result.get("cursor");
	// mobiles.addAll(members);
	// if (StringUtils.isBlank(cursor)) {
	// break;
	// }
	// }
	//
	// return mobiles;
	// }

	/**
	 * 清空用户
	 * 
	 * @throws UserUnRegistException
	 */
	// public static void clearMembers() throws UserUnRegistException {
	// List<String> members = exportMembers();
	//
	// if (members != null && !members.isEmpty()) {
	// Long limit = 300L; // 一次删除用户数
	// Long count = (members.size() / limit) + 1;
	//
	// for (int i = 0; i < count; i++) {
	// batchDeleteUser(limit);
	// }
	// }
	//
	// }

	/**
	 * 获取数据库与环信用户不同的用户
	 * 
	 * @param mobiles
	 * @return
	 */
	// public static List<String> getDifferentMobiles(List<String> mobiles) {
	// int dbSize = mobiles.size();
	//
	// List<String> mobilesOfEm = exportMembers(); // 环信
	// int emSize = mobilesOfEm.size();
	//
	// System.out.println("数据库用户个数：" + dbSize);
	// System.out.println("环信用户个数：" + emSize);
	//
	// if (dbSize > emSize) {
	// mobiles.removeAll(mobilesOfEm);
	// return mobiles;
	// } else if (dbSize < emSize) {
	// mobilesOfEm.removeAll(mobiles);
	// return mobilesOfEm;
	// }
	// return null;
	// }

	public static void main(String[] args) {
		// 注册
		// try {
		// regist("132456");
		// } catch (UserAlreadyRegistException e) {
		// e.printStackTrace();
		// }

		// 删除用户
		// try {
		// deleteUser("132456");
		// } catch (UserUnRegistException e) {
		// e.printStackTrace();
		// }

		// 是否在线
		// try {
		// boolean isOnLine = isOnLine("13512789001");
		// System.out.println(isOnLine);
		// } catch (UserUnRegistException e) {
		// e.printStackTrace();
		// }

		// 发送消息
		// VExt ext = new VExt();
		// ext.setFrom("abc");
		// ext.setMsg("你好");
		// ext.setMsgType(MsgType.TEXT_GENERAL);
		// sendMessage(ext, "13512789001");

		// 获取webIM token
		// try {
		// System.out.println(getWebIMToken("13512789001"));
		// } catch (UserUnRegistException e) {
		// e.printStackTrace();
		// }
	}

}
