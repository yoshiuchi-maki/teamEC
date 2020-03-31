<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./css/productList.css">
<link rel="stylesheet" type="text/css" href="./css/header.css">
<link rel="stylesheet" type="text/css" href="./css/galaxy.css">
<link rel="stylesheet" type="text/css" href="./css/style.css">
<link rel="stylesheet" type="text/css" href="./css/table.css">


<title>商品一覧</title>
</head>
<body>

<jsp:include page="header.jsp" />

	<div id="contents">

	<h1>商品一覧画面</h1>

	<s:if test="errorMessageList != null && errorMessageList.size() > 0">
		<s:iterator value="errorMessageList">
			<div class="error">
			<div class="error-message">
				<s:property /><br>
			</div>
			</div>
		</s:iterator>
	</s:if>

	<s:elseif test="productInfoList !=null && productInfoList.size() > 0">
		<table  class="product-list-table">
			<s:iterator value="productInfoList" status="st">
				<s:if test="#st.index%3 == 0"><tr></s:if>
					<td>
						<a href=
							'<s:url action="ProductDetailsAction">
								<s:param name="productId" value="%{productId}"/>
							</s:url>'>
							<img src='<s:property value="imageFilePath"/><s:property value="imageFileName"/>'><br>
							<s:property value="productName"/><br>
							<s:property value="productNameKana"/><br>
							<s:property value="price"/>円<br>
						</a>
					</td>
				<!-- この警告はifタグに囲まれていて開始タグが見つからなく、文法上問題ないため、無視する。 -->
				<s:if test="#st.index%3 ==2"></tr></s:if>
			</s:iterator>
		</table>
	</s:elseif>

	<s:else>
		<div class="info">
			検索結果がありません。
		</div>
	</s:else>

	</div>

</body>
</html>