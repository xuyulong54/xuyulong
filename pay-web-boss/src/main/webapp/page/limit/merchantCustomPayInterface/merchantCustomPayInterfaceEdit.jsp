<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
		<form method="post"  class="pageForm" name="merchantCustomPayInterfaceEdit" 
		action="merchantCustomPayInterface_editMerchantCustomPayInterface.action" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<!-- 基本信息 -->
		<div class="pageFormContent">
  			<input type="hidden" name="navTabId" value="listMerchantCustomPayInterface">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id}">
			<p>
				<label>商户编号：</label>
				<s:textfield name="merchantNo" minlength="1" maxlength="100" cssClass="required" size="20"  readonly="true"/>
			</p>
			<p>
				<label>支付方式：</label>
				<select name="payWay" id="payWay" class="required" onchange="getPayInterface(this.value)">
						<c:forEach items="${listFrp}" var="model">
							<option value="${model.frpCode}" <c:if test="${payWay == model.frpCode }">selected="selected"</c:if>>${model.frpCode}</option>
						</c:forEach>
				</select>		
				
			</p>
			<p>
				<label>支付接口编码：</label>
				<select name="payInterface" id="payInterface" class="required">
						<c:forEach items="${listPayInterface}" var="model">
							<option value="${model}" <c:if test="${payInterface == model }">selected="selected"</c:if>>${model}</option>
						</c:forEach>
				</select>	
			</p>
		</div>	
		<div class="formBar">
			<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
							</div>
						</div>
					</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
		
	</form>
		 <div class="tabs" currentIndex="0">
                <div class="tabsHeader">
                    <div class="tabsHeaderContent">
                        <ul>
                            <li><a href="#"><span>支付接口路由</span>
					</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="tabsContent" style="height:300px">

                   <!-- 支付接口路由 -->
                    <div>
                        <table class="table" >
                            <thead>
                                <tr>
								<th style="width: 160px;">商户编号</th>
								<th style="width: 160px;">支付方式</th>
								<th style="width: 160px;">支付接口</th>
								<th style="width: 160px;">创建时间</th>
								<th style="width: 160px;">操作</th>
                                </tr>
                            </thead>
                 <tbody id="merchantCustomPayInterfacebody">
				</tbody>
                        </table>
                    </div>

                </div>

                <div class="tabsFooter">
                    <div class="tabsFooterContent"></div>
                </div>

            </div>
</div>


<script type="text/javascript">

	// 加载支付接口
	function getPayInterface(payWay) {
		$.post("merchantCustomPayInterface_loadPayInterface.action", {
			'payWay' : payWay
		}, function(objarray) {
			if(objarray == null || objarray == ""){
				alertMsg.error("该支付方式下未设置支付接口");
			}else{
				$("#payInterface").empty();
					document.getElementById("payInterface").options.add(new Option("请选择", ""));
					for ( var i = 0; i < objarray.length; i++) {
						document.getElementById("payInterface").options.add(new Option(objarray[i], objarray[i]));
					}
			}
		}, "json");
	}

	function deleteMerchantCustomPayInterface(id){
		var merchantNo = document.getElementById("merchantNo").value;
		if(confirm("你确定要删除吗？")){
			$.post("merchantCustomPayInterface_deleteMerchantCustomPayInterface.action",{id:id},
				function(res){
					if(res.STATE=="SUCCESS"){
						alertMsg.info("删除成功！");
						refreshMerchantCustomPayInterface();
					}else{
						alertMsg.info("删除失败！");
						refreshMerchantCustomPayInterface();
					}
			},"json");
		}
	}	
	//添加支付接口路由限制 
	function addMerchantCustomPayInterface(){
		var merchantNo = document.getElementById("merchantNo").value;
		var payWay = document.getElementById("payWay").value;
		var payInterface = document.getElementById("payInterface").value;
		$.post("merchantCustomPayInterface_addMerchantCustomPayInterface.action",{merchantNo:merchantNo,payWay:payWay,payInterface:payInterface},
		function(res) {
			if(res.STATE=="SUCCESS"){
				alertMsg.info("添加成功！");
				refreshMerchantCustomPayInterface();
			}else{
				alertMsg.error(res.MSG);
			}
	},"json");
	}
		//刷新页面
	function refreshMerchantCustomPayInterface(){
			var merchantNo = document.getElementById("merchantNo").value;
			$("#merchantCustomPayInterfacebody").html(" ");
          //添加数据
          $.post("merchantCustomPayInterface_refreshMerchantCustomPayInterface.action", {
        	  merchantNo: merchantNo
          }, function (res) {
              if (res == null || res.MerchantCustomPayInterfaceList == null) {
                  return;
              }			
				var AmountHTML="";
              for (var k = 0; k < res.MerchantCustomPayInterfaceList.length; k++) {

                  var merchantCustomPayInterface = res.MerchantCustomPayInterfaceList[k];
                   AmountHTML += "<tr><td >" + merchantCustomPayInterface.merchantNo  + "</td><td >" + merchantCustomPayInterface.payWay  + "</td>" +
                      "<td >" + merchantCustomPayInterface.payInterface +"</td><td >" + merchantCustomPayInterface.createTime + "</td><td >" + "<a href='javascript:deleteMerchantCustomPayInterface("+ merchantCustomPayInterface.id+ ");'>删除</a></td></tr>";
              }
              
               $("#merchantCustomPayInterfacebody").html(AmountHTML);

          }, "json");
	
		}
		
	//自动刷新数据	
	$(function(){
		refreshMerchantCustomPayInterface();
	});
</script>
