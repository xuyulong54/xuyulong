<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<body>
	<form method="post" name="myform">
		<table border="0" width="100%" height="100%">
			<tr>
				<td width="30%" rowspan="2">
					<select style="WIDTH:100%;height: 100%" multiple
					name="list1" size="12"
					ondblclick="moveOption(document.myform.list1, document.myform.list2)" id="activeBankList">
						<c:forEach items="${allActiveBankList }" var="activeBank" >
							<option value="${activeBank.bankCode }" typeCode="${activeBank.typeCode }">${activeBank.typeName }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<input type="button"
					value="添加到包含框==》"
					onclick="moveOption(document.myform.list1, document.myform.list2)"><br />
					<input type="button" value="《==删除"
					onclick="moveOption(document.myform.list2, document.myform.list1)">
				</td>
				<td  width="30%"><select style="WIDTH:100%" multiple name="list2" id="includeBankList"
					size="12"
					ondblclick="moveOption(document.myform.list2, document.myform.list1)">
					<c:forEach items="${includeList }" var="include">
							<option value="${include.bankCode }" typeCode="${include.typeCode }">${include.typeName }</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>
					<input type="button" value="添加到非包含框==》"
					onclick="moveOption(document.myform.list1, document.myform.list3)">
					<br />
					<input type="button" value="《==删除"
					onclick="moveOption(document.myform.list3, document.myform.list1)">
				</td>
				<td  width="30%"><select style="WIDTH:100%" multiple name="list3" id="excludeBankList"
					size="12"
					ondblclick="moveOption(document.myform.list3, document.myform.list1)">
					<c:forEach items="${excludeList }" var="exclude">
							<option value="${exclude.bankCode }" typeCode="${exclude.typeCode }">${exclude.typeName }</option>
						</c:forEach>
				</select></td>
			</tr>
		</table>
		<!-- 包含的值：<input type="text" name="city" size="40"><br>
		不包含的值：<input type="text" name="city2" size="40"> -->
	</form>
	<script language="JavaScript">
		function moveOption(e1, e2) {
			for ( var i = 0; i < e1.options.length; i++) {
				if (e1.options[i].selected) {
					var e = e1.options[i];
					e2.options.add(new Option(e.text, e.value));
					for(var j=0;j<e.attributes.length;j++){
						var pattern = new RegExp("^[0-9]*$");
						if(pattern.test(e.attributes.item(j).value)){
							e2.options[e2.length-1].typeCode = e.attributes.item(j).value;
						}
					}
					e1.remove(i);
					ii = i - 1
				}
			}
			window.opener.document.getElementById("includeBankCode").value = getvalue(document.myform.list2); 
			window.opener.document.getElementById("excludeBankCode").value = getvalue(document.myform.list3); 
			window.opener.document.getElementById("includeBankTypeCode").value = getTypeCode(document.myform.list2); 
			window.opener.document.getElementById("excludeBankTypeCode").value = getTypeCode(document.myform.list3); 
			document.myform.city.value = getvalue(document.myform.list2);
			document.myform.city2.value = getvalue(document.myform.list3);
		}
		function getvalue(geto) {
			var allvalue = "";
			for ( var i = 0; i < geto.options.length; i++) {
				allvalue += geto.options[i].value + ",";
			}
			return allvalue;
		}
		function getTypeCode(geto) {
			var allvalue = "";
			for ( var i = 0; i < geto.options.length; i++) {
				if(geto.options[i].typeCode){
					allvalue += geto.options[i].typeCode + ",";
				}else{
					var tempValue;
					for(var j=0;j<geto.options[i].attributes.length;j++){
						var pattern = new RegExp("^[0-9]*$");
						if(pattern.test(geto.options[i].attributes.item(j).value)){
							tempValue = geto.options[i].attributes.item(j).value;
						}
					}
					allvalue += tempValue+",";
				}
				
			}
			return allvalue;
		}
	</script>
</body>
