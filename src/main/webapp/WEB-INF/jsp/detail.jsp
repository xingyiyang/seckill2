<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h2>${detail.name}</h2>
        </div>
    </div>
    <div class="panel-body">
        <h2 class="text-danger text-center">
            <%--显示time图标--%>
            <span class="glyphicon glyphicon-time"></span>
            <%--显示倒计时--%>
            <span class="glyphicon" id="seckill-box"></span>
        </h2>
    </div>
</div>
<%--登录弹出层，输入电话--%>
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>秒杀电话：
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killphone" id="killphoneKey" placeholder="请填写手机号"
                               class="form-control"/>
                    </div>
                </div>
            </div>
            <%--验证信息--%>
            <div class="modal-footer">
                <span id="killphoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    Submit
                </button>
            </div>
        </div>
    </div>
</div>

<script src="<%=basePath %>/js/jquery-2.0.3.min.js"></script>
<script src="<%=basePath %>/js/bootstrap.min.js"></script>
<script src="<%=basePath %>/js/jquery.cookie-1.4.1.min.js"></script>
<script src="<%=basePath %>/js/jquery.countdown.min.js"></script>
<script src="<%=basePath %>/js/seckill.js" type="text/javascript"></script>

<!-- 使用EL表达式传入参数 -->
<script type="text/javascript">
$(function(){
	seckill.detail.init({
        seckillId : ${detail.seckillId},
        startTime : ${detail.startTime.time},
        endTime : ${detail.endTime.time}
    });
});
</script>
</body>
</html>