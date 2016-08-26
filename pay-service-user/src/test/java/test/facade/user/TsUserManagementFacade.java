package test.facade.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wusc.edu.pay.facade.user.service.UserManagementFacade;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:consumer.xml" })
public class TsUserManagementFacade {

	@Autowired
	private UserManagementFacade userManagementFacade;

	@Test
	public void testRegisterMerchantFist() {

		String loginName = "lichao@gzzyzz.com";
		String loginPwd = DigestUtils.sha1Hex("1234qwer!");
		String payPwd = DigestUtils.sha1Hex("qwer1234!");
		int question = 2;
		String answer = "apple";
		String greeting = "测试测试";
		String bindMobileNo = "13911111111";
		int userType = 1;

//		userManagementFacade.registerMerchantFirst(loginName, loginPwd, payPwd, question, answer, greeting, bindMobileNo, userType, 0, null);

	}

	@Test
	public void testRegisterMerchantSecond() {

		String loginName = "lanzy-1@gzzyzz.com";
		int merchantType = 1;
		String fullName = "中益智正信息科技有限公司";
		String shortName = "中益";
		String licenseNo = "111111111111111";
		String url = "www.gzzyzz.com";
		String icp = "192.168.88.100";
		String busiContactName = "美女";// ??
		String busiContactMobileNo = "13911111111";
		String scope = "广东省";
		String province = "广东";
		String city = "广州";
		String area = "海珠";
		String address = "聚得西路";
		String legalPerson = "法人姓名";
		String cardNo = "123456666666666666";
//		userManagementFacade.registerMerchantSecond(loginName, merchantType, fullName, shortName, licenseNo, url, icp, busiContactName, busiContactMobileNo, scope, province, city, area, address, legalPerson,cardNo);
	}

	@Test
	public void testRegisterPosMerchantSecond() {

		String loginName = "lanzy@gzzyzz.com";
		int merchantType = 2;
		String fullName = "中益智正信息科技有限公司";
		String shortName = "中益";
		String licenseNo = "111111111111111";
		String url = "www.gzzyzz.com";
		String icp = "192.168.88.100";
		String busiContactName = "美女";// ??
		String busiContactMobileNo = "13911111111";
		String scope = "广东省";
		String province = "广东";
		String city = "广州";
		String area = "海珠";
		String orgCode = "gzzyzz";// 组织结构代码
		String address = "广东省广州市海珠区";
		String legalPerson = "法人姓名";
		String cardNo = "430621199301159425";
//		userManagementFacade.registerPosMerchantSecond(loginName, merchantType, fullName, shortName, licenseNo, url, icp, busiContactName, busiContactMobileNo, scope, province, city, area, orgCode, address, legalPerson, cardNo);
	}

	@Test
	public void testRegisterMember() {

		String loginName = "lanzy-6@gzzyzz.com";
		String loginPwd = DigestUtils.sha1Hex("1234qwer!");
		String payPwd = DigestUtils.sha1Hex("qwer1234!");
		String question = "最喜欢的水果";
		String answer = "apple";
		String greeting = "测试测试";
		String realName = "振源";
		String cardNo = "441111111111111111";
		String fax = "020-6272828";
		String qq = "835450258";
		String telNo = "13911111111";
		String address = "广东省广州市海珠区";
		userManagementFacade.registerMember(loginName, loginPwd, payPwd, question, answer, greeting, realName, cardNo, fax, qq, telNo, address);
	}

	@Test
	public void testMemberLogin() {

		String userName = "lanzy-6@gzzyzz.com";
		String passWord = DigestUtils.sha1Hex("2234qwer!");
		int pwdErrMaxTimes = 100;
		int validMinute = 1000;
		// 会返回userinfo
		userManagementFacade.memberLogin(userName, passWord, pwdErrMaxTimes, validMinute);
	}

	@Test
	public void testMerchantLogin() {

		String userName = "lanzy-3@gzzyzz.com";
		String passWord = DigestUtils.sha1Hex("1234qwer!");
		int pwdErrMaxTimes = 2;
		int validMinute = 1;
		// 会返回userinfo
		userManagementFacade.merchantLogin(userName, passWord, pwdErrMaxTimes, validMinute);
	}
}
