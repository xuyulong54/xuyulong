<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script type="text/javascript">
  $(function(){selectDate('${timeType}');})
  </script>


</head>
    <label>创建时间：</label>
    <div id="select" class="inline">
      <div  class="select_border">
        <div class="select_cont">
          <select id="timeType" name="timeType" class="select selectscon" onchange="selectDate(this.value);" >
            <option value="toDay" <c:if test="${timeType eq 'toDay' }">selected="selected"</c:if>>今天</option>
            <option value="currentMonth" <c:if test="${timeType eq 'currentMonth' }">selected="selected"</c:if>>本月</option>
            <option value="lastMonth" <c:if test="${timeType eq 'lastMonth' }">selected="selected"</c:if>>上月</option>
            <option value="custom" <c:if test="${timeType eq 'custom' }">selected="selected"</c:if>>自定义</option>
          </select>
        </div>
      </div>
    </div>
    <div id="time" class="inline<c:if test="${timeType != 'custom' }"> none</c:if>" style="margin-left: 5px;display:inline-block; *display:inline; *zoom:1;">
      <input name="beginDate" value=${beginDate} readonly="readonly" type="text" class="timeinput" onclick="calendar(this)" id="beginDate" />-<input name="endDate" value="${endDate}" type="text" class="timeinput" onclick="calendar(this)" readonly="readonly" id="endDate"/>
    </div>
</html>
