<meta charset="utf-8" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
	<title>数据管理平台</title>
	<meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<!-- basic styles -->
	<link href="<%=basePath %>static/css/bootstrap.min.css" rel="stylesheet" />
	<link href="<%=basePath %>static/css/bootstrap-responsive.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="<%=basePath %>static/css/font-awesome.min.css" />
	<!-- page specific plugin styles -->
	<!-- 下拉框-->
	<link rel="stylesheet" href="<%=basePath %>static/css/chosen.css" />
	<!-- ace styles -->
	<link rel="stylesheet" href="<%=basePath %>static/css/ace.min.css" />
	<link rel="stylesheet" href="<%=basePath %>static/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="<%=basePath %>static/css/ace-skins.min.css" />
	<script type="text/javascript" src="<%=basePath %>static/js/jquery-1.7.2.js"></script>
	<link rel="stylesheet" href="<%=basePath %>static/css/datepicker.css" /><!-- 日期框 -->
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="<%=basePath %>plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="<%=basePath %>plugins/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件end-->
	<script type="text/javascript" src="<%=basePath %>static/js/jquery.tips.js"></script>
	
	