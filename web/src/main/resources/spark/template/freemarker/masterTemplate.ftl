<#macro masterTemplate title="Welcome">
<!DOCTYPE html
        PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | Crowd Tester</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>
<body>
<div class="page">
    <h1>Crowd Tester</h1>
    <div class="navigation">
        <#if user??>
            <a href="/crowdtester/logout">sign out [${user.userName}]</a>
        <#else>
            <a href="/crowdtester/register">sign up</a> |
            <a href="/crowdtester/login">sign in</a>
        </#if>
    </div>
    <div class="body">
        <#nested />
    </div>
    <div class="footer">
        Crowd Tester &mdash; A Mobile Testing as a Service (MTaaS)
    </div>
</div>
</body>
</html>
</#macro>