<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	response.setHeader("refresh","10");// 5秒刷新
%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<meta charset="utf-8" />
		<title></title>
		<%@ include file="../../common/top.jsp"%>
	</head>
	
	<body id="wrapper">
		<div class="container-fluid animated bounceIn cardBg" id="main-container" >
			<div id="page-content" class="clearfix " style="background:#fff;margin:20px 31px; padding-top: 0px">
				<h2>总记录：【${betList.size()}】</h2>
				<div class="row-fluid">
					<div class="row-fluid">
						<form id="userCriteriaQuery" method="post" action="<%=basePath%>querybetList.do">
							<c:if test="${status == 'stop'}">
								<a class='btn btn-mini btn-info btn-rbg' style="margin-top:0" href="bet.do?platform=yicai&lotCode=FKSC">开始</a>
							</c:if>
							<c:if test="${status == 'start'}">
								<a class='btn btn-mini btn-info btn-rbg' style="margin-top:0" href="stop.do">停止</a>
							</c:if>
							<table id="table_report" class="table table-striped table-bordered table-hover"
									style="margin:10px 0 0 0; padding-top: 0px">
		                    	<thead>
		                        	<tr>
										<th class="center">彩种</th>
										<th class="center">日期</th>
										<th class="center">投注时间</th>
										<th class="center">期号</th>
										<th class="center">倍数</th>
										<th class="center">投注金额</th>
										<th class="center">投注内容</th>
										<th class="center">中奖号</th>
										<th class="center">中奖内容</th>
										<th class="center">中奖金额</th>
										<th class="center">账户余额</th>
										<th class="center">状态</th>
										<th class="center">投注轮次</th>
		                            </tr>
		                   		</thead>
		                        <c:choose>
									<c:when test="${!empty betList}">
				                    	<tbody>
				                        	<c:forEach items="${betList}" var="bet" varStatus="vs">
				                            	<tr class="odd gradeX">
				                            		<td class="center">${bet.lotCode}</td>
													<td class="center"><fmt:formatDate value='${bet.betDate}' pattern='yyyy-MM-dd'/></td>
													<td class="center"><fmt:formatDate value='${bet.betTime}' pattern='HH:mm:ss'/></td>
													<td class="center">${bet.batchNo}</td>
													<td class="center">${bet.multiple}</td>
													<td class="center">${bet.betAmt}</td>
													<td class="center">${bet.betContent}</td>
													<td class="center">${bet.winNo}</td>
													<td class="center">${bet.winContent}</td>
													<td class="center">${bet.winAmt}</td>
													<td class="center">${bet.balance}</td>
													<c:if test="${bet.status == '已中奖'}">
													<td class="center" style="color:blue;">${bet.status}</td>
													</c:if>
													<c:if test="${bet.status == '未中奖'}">
													<td class="center" style="color:red;">${bet.status}</td>
													</c:if>
													<c:if test="${bet.status == '未开奖'}">
													<td class="center" style="color:green;">${bet.status}</td>
													</c:if>
													<td class="center">${bet.sort}</td>
				                                </tr>
				                            </c:forEach>
				                        </tbody>
				                    </c:when>
									<c:otherwise>
										<tbody>
											<tr>
												<td colspan="100" style="text-align: center;">
													<font color="red">此页没有相关数据！</font>
												</td>
											</tr>
										</tbody>
									</c:otherwise>
								</c:choose>
		                    </table>
						</form>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			function getSelectedValue(){
				var oSelected = document.getElementById('age');//获得select对象
				var selectedValue = oSelected.options[oSelected.selectedIndex].value;//获得选中的option的value
				return selectedValue;
			}
</script>
	</body>
</html>

