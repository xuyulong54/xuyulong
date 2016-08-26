<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post"
		action="remitOrder_reRemitOrderSingle.action"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="navTabId" value="reRemitRequestList">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value=""> <input
			type="hidden" name="id" value="${id}" />
		<div class="pageFormContent" layoutH="60">
			<div class="unit">
				<p>
					<label>打款请求号：</label> <input type='text' id='requestNo'
						value='${requestNo}' size='30' class="required" name='requestNo' readonly="readonly"/>
				</p>	
				<p>
					<label>业务发起方：</label>
					<c:forEach items="${TradeSourcesEnums }" var="v"> 
					<c:if test="${v.value== tradeInitiator}">
					<input type='text' id='tradeInitiator'
						value='${v.desc}' size='30' class="required" name='tradeInitiator' readonly="readonly"/>
					</c:if>
					</c:forEach>
				</p>
			</div>
			<div class="unit">
				<p>
				<label>帐户类型：</label> 
				<select id="accountType" name="accountType">
					<c:forEach items="${AccountTypeEnums}" var="v">
						<option value="${ v.value }" <c:if test="${v.value==accountType }"> selected="selected"</c:if> >${v.desc }</option>
					</c:forEach>
				</select>
				</p>
				<p>
					<label>开户名：</label> <input type='text' class="required" id='accountName'
						value='${accountName}' size='30' name='accountName' />
				</p>
			</div>
			<div class="unit">
				<p>
					<label>收款帐户账号：</label> <input type='text' class="required" id='accountNo'
						value='${accountNo}' size='30' name='accountNo' />
				</p>
				<p>
					<label>请求金额：</label> <input type='text' class="required" id='amount'
					value='${amount}' size='30' name='amount' />
				</p>
			</div>
			<div class="unit">
				<p>
				<label>省份：</label> 
					<select name="province" id="orderProvince" class="required">
						<option value="">请选择</option>
						<c:forEach items="${provinceList }" var="provinceVar">
							<option value="${provinceVar.province }" <c:if test="${province eq provinceVar.province }"> selected="selected"</c:if> >${provinceVar.province} </option>
						</c:forEach>
					</select>
				</p>
				<p>
				<label>城市：</label> 
					<select name="city" id="orderCity" class="required">
						<option value="">请选择</option>
						<c:forEach items="${cityList }" var="cityVar">
							<option value="${cityVar.city }" <c:if test="${city eq cityVar.city }"> selected="selected"</c:if> >${cityVar.city} </option>
						</c:forEach>
					</select>
				</p>
			</div>
			<div>
				<p>
					<label>银行名称</label>
					<select name="typeCode" id="typeCode" class="required">
							<option value="">请选择</option>
							<c:forEach items="${bankTypeList }" var="bankTypeVar">
								<option value="${bankTypeVar.typeCode }" <c:if test="${typeCode eq bankTypeVar.typeCode }"> selected="selected"</c:if> >${bankTypeVar.typeName} </option>
							</c:forEach>
					</select>
				</p>
				<p>
					<label>业务类型：</label> 
					<c:forEach items="${TradeTypeEnums }" var="v">
					<c:if test="${v.value==businessType }">
					<input type='text' id='businessType'
						value='${v.desc}' size='30' name='businessType' readonly="readonly"/>
					</c:if></c:forEach>
				</p>
			</div>
			<div class="unit">
					<label>开户银行名称：</label> 
					<select name="bankName" id="bankName" class="required">
							<option value="">请选择</option>
							<c:forEach items="${bankInfoList }" var="bankInfoVar">
								<option value="${bankInfoVar.bankName }" <c:if test="${bankName eq bankInfoVar.bankName }"> selected="selected"</c:if> >${bankInfoVar.bankName} </option>
							</c:forEach>
					</select>
				<input type="hidden" name="bankChannelNo" id="bankChannelNo" value="${ bankChannelNo}">
			</div>
			<div class="unit">
				<p>
					<label>状态：</label> 
					<c:forEach items="${RemitProcessStatusEnums }" var="v">
						<c:if test="${v.value==status }">
						<input type='text' id='status' value='${v.desc}' size='30' name='status' readonly="readonly" />
						</c:if>
					</c:forEach>
				</p>
				<p>
					<label>是否自动处理：</label>
					<c:forEach items="${PublicStatusEnums }" var="v">
					<c:if test="${v.value==isAutoProcess }">
					<input type='text' id='isAutoProcess'
						value='${v.desc}' size='30' name='isAutoProcess' readonly="readonly"/>
					</c:if>
					</c:forEach>
				</p>
			</div>
			<div class="unit">
			<p>
				<label>创建人：</label> <input type='text' id='creator'
					value='${creator}' size='30' name='creator' readonly="readonly"/>
			</p>
			<p>
					<label>创建时间：</label>
					<fmt:formatDate value="${createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
				</p>
			</div>
			<div class="unit">
				<p>
					<label>审核人：</label> <input type='text' id='confirm'
						value='${confirm}' size='30' name='confirm' readonly="readonly"/>
				</p>
				<p>
				<label>审核时间：</label>
				<fmt:formatDate value="${confirmDate }" pattern="yyyy-MM-dd HH:mm:ss" />
				</p>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" id="reRequest" >重新制单</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	
	$(function(){
		//绑定时间
		$("#orderProvince").change(function(){
			changeBankInfo();
		});
		//绑定时间
		$("#orderCity").change(function(){
			changeBankInfo();
		});
		//绑定时间
		$("#typeCode").change(function(){
			changeBankInfo();
		});
		
	});
	
	function changeBankInfo(){
		
		$.post("remitRequest_getBankInfoListByBankTypeCodeAndArea.action",{
					bankTypeCode:$("#typeCode").val(),
					province:$("#orderProvince").val(),
					city:$("#orderCity").val()
					},function(result){
						
						$("#orderCity").empty();
						$("#orderCity").append("<option value=''>请选择</option>");
						for(var i=0;i<result.cityList.length;i++){
							if(result.city == result.cityList[i].city){
								$("#orderCity").append("<option value='"+result.cityList[i].city+"' selected='selected' >"+result.cityList[i].city+"</option>");
							}else{
								$("#orderCity").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
							}
						}
						
						$("#bankName").empty();
						$("#bankName").append("<option value=''>请选择</option>");
						for(var i=0;i<result.remitBankInfoList.length;i++){
							$("#bankName").append("<option value='"+result.remitBankInfoList[i].bankName+"'>"+result.remitBankInfoList[i].bankName+"</option>");
						}
		},"json");
		
	}
</script>
