package wusc.edu.pay.timer.report.achieve;
/*package wusc.edu.pay.timer.report.achieve;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.utils.BeanUtils;
import wusc.edu.pay.facade.member.entity.MemberCancellation;
import wusc.edu.pay.facade.member.entity.MemberInfo;
import wusc.edu.pay.facade.member.entity.MemberRealNameAuth;
import wusc.edu.pay.facade.member.entity.MemberStatusAduit;
import wusc.edu.pay.facade.member.service.MemberCancellationFacade;
import wusc.edu.pay.facade.member.service.MemberInfoFacade;
import wusc.edu.pay.facade.member.service.MemberRealNameAuthFacade;
import wusc.edu.pay.facade.member.service.MemberStatusAduitFacade;
import wusc.edu.pay.facade.report.params.MemberReportParam;
import wusc.edu.pay.facade.report.service.MemberReportFacade;
import wusc.edu.pay.timer.report.util.LoadFacade;
import wusc.edu.pay.timer.report.util.QueryReportData;
*//**
 * 添加会员统计
 * @author lichao
 * 测试：新增会员：0---存量数量：4---终止数量：0---审批数量：0
 * select count(*) from TBL_MEMBER_STATUS_ADUIT 
where STATUS=100 and MEMBERID in (
select id from TBL_MEMBER_INFO where STATUS = '100'
)

select count(*) from TBL_MEMBER_REALNAMEAUTH 
where STATUS=100 and MEMBERID in (
select id from TBL_MEMBER_INFO where STATUS = '100'
)

select count(*) from TBL_MEMBER_CANCELLATION 
where STATUS=100 and MEMBERID in (
select id from TBL_MEMBER_INFO where STATUS = '100'
)
 *
 *//*
public class AddMemberReport {
	private static Log log = LogFactory.getLog(AddMemberReport.class);
	MemberInfoFacade memberInfoFacade = LoadFacade.memberInfoFacade;
	MemberRealNameAuthFacade memberRealNameAuthFacade = LoadFacade.memberRealNameAuthFacade;//会员实名认证审核
	MemberCancellationFacade memberCancellationFacade = LoadFacade.memberCancellationFacade;//会员销户审核
	MemberStatusAduitFacade memberStatusAduitFacade = LoadFacade.memberStatusAduitFacade;//会员状态审核
	
	MemberReportFacade memberReportFacade = LoadFacade.memberReportFacade;
	
	public void addMemberReport(){
		List<Object> memberRealNameAuthList = QueryReportData.memberRealNameAuthList;//会员实名认证审核数据
		List<Object> memberCancellationList = QueryReportData.memberCancellationList;//会员销户审核数据
		List<Object> memberStatusAduitList = QueryReportData.memberStatusAduitList;//会员状态审核数据
		List<Object> memberList = QueryReportData.activeMemberList;//会员数据
		try {
			if(memberList == null || memberList.size() <= 0){
				return;
			}
			int createNum = 0;//新增会员数量
			int cancelNum = 0;//终止数量
			int stockNum = 0;// 存量数量
			int checkingNum = 0;// 审批数量
			for(int i = 0; i < memberList.size(); i++){
				Map<String, Object> memberObjMap = (Map)memberList.get(i);
				MemberInfo memberInfo = BeanUtils.toBean(MemberInfo.class, memberObjMap);
				if(memberInfo.getCreateTime().equals(new Date())){
					createNum++;//新增会员数量
				}
				for(int j=0; j<memberCancellationList.size(); j++){
					Map<String, Object> cancellationObjMap = (Map) memberCancellationList.get(j);
					MemberCancellation memberCancellation = BeanUtils.toBean(MemberCancellation.class, cancellationObjMap);
					if( memberCancellation.getMemberId() == memberInfo.getId() && memberCancellation.getStatus() == 100 ){
						cancelNum++;//终止数量
					}
				}
				for(int m=0; m<memberStatusAduitList.size(); m++){
					Map<String, Object> statusAduitObjMap = (Map) memberStatusAduitList.get(m);
					MemberStatusAduit memberStatusAduit = BeanUtils.toBean(MemberStatusAduit.class, statusAduitObjMap);
					if(memberInfo.getId() == memberStatusAduit.getMemberId() && memberStatusAduit.getStatus() == 100 ){
						checkingNum++;//审批数量
					}
				}
				for(int n=0; n<memberRealNameAuthList.size(); n++){
					Map<String, Object> realNameObjMap = (Map) memberRealNameAuthList.get(n);
					MemberRealNameAuth memberRealNameAuth = BeanUtils.toBean(MemberRealNameAuth.class, realNameObjMap);
					if( memberRealNameAuth.getMemberId() == memberInfo.getId() && memberRealNameAuth.getStatus() == 100){
						stockNum++;//存量数量
					}
				}
			}
			
			MemberReportParam memberReportParam = new MemberReportParam();
			memberReportParam.setMemberBusType(1);
			memberReportParam.setCreateNum(createNum);
			memberReportParam.setStockNum(stockNum);
			memberReportParam.setCancelNum(cancelNum);
			memberReportParam.setCheckingNum(checkingNum);
			memberReportParam.setAcountDate(new Date());
			memberReportFacade.create(memberReportParam);
			//System.out.println("新增会员："+createNum+"---存量数量："+stockNum+"---终止数量："+cancelNum+"---审批数量："+checkingNum);
			
		} catch (Exception e) {
			log.error("实现批量会员详情数据错误：", e);
		}
	}
}
*/