<%@page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
    <meta charset="utf-8">
<title>Greetings</title>
<style>
.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
}

body{ margin:0 auto; }
div {
    height: 200px;
    width: 400px;
  

    position: fixed;
    top: 50%;
    left: 50%;
    margin-top: -100px;
    margin-left: -200px;
}

</style>
<script type="text/javascript">
function hideShow(){
	document.getElementById("download").style.display = "none";
}
</script>
</head>
<body onload="hideShow()">
<div class="content">
	<form action="DownloadImage" method="post">
	<img src="./img/Logo.png" alt="SAVC" class="center">
		<table border="0" align="center">
			<caption><h2>عيد مبارك</h2></caption>
			<tr>
				<td><bdi>الصفه</bdi></td>
				<td>
					<select id="dropdown" name="dropdown" required>
					<option value="">select</option>
					
						<option value="اخوكم
">اخوكم</option>
						<option value="اختكم
">اختكم</option>				
					</select>
				</td>
			</tr>
			<tr>
				<td dir="rtl" >الاسم</td>
				<td><input type="text" name="recipient" size="50" required/></td>
			</tr>
			<tr>
				<!-- <td colspan="2" align="center"><input type="submit" value="تحميل" /></td> -->
				<td colspan="2" align="center"><input type="submit" value="موافق"/></td>
			</tr>
			
		</table>
		
		
	</form>
	</div>
</body>
</html>