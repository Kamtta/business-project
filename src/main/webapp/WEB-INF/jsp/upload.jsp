<%--
  Created by IntelliJ IDEA.
  User: cjr
  Date: 2018/12/10 0010
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>图片上传</title>
</head>
<body>
    <form name="form" action="/manage/product/upload.do" method="post"  enctype="multipart/form-data">
        <input type="file" name="upload_file">
        <input type="submit" value="upload"/>
    </form>
</body>
</html>
