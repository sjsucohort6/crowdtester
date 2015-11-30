<#macro masterTemplate title="OpenStack" moduleName="openstackApp">

<!DOCTYPE html>
<!--
  ~ Copyright (c) 2015 San Jose State University.   
  ~
  ~ Permission is hereby granted, free of charge, to any person obtaining a copy
  ~ of this software and associated documentation files (the "Software"), to deal
  ~ in the Software without restriction, including without limitation the rights
  ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  ~ copies of the Software, and to permit persons to whom the Software is
  ~ furnished to do so, subject to the following conditions:
  ~
  ~ The above copyright notice and this permission notice shall be included in
  ~ all copies or substantial portions of the Software.
  ~
  ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  ~ THE SOFTWARE.
  -->

<html lang="en" ng-app="${moduleName}">
<head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link id="callCss" rel="stylesheet" href="../../css/bootstrap.min.css" type="text/css" media="screen" charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="../../DataTables/datatables.min.css"/>
    <link id="callCss" rel="stylesheet" href="../../css/style.css" type="text/css" media="screen" charset="utf-8"/>

</head>
<body>

    <div class="body">
        <#nested />
    </div>

    <div class="footerSection container">
        <div class="copyright"><p> Copyright 2015 | Developed By <a href="http://www.bugminer.com">Bug Miner</a></p></div>
    </div>

    <a href="#" class="go-top"><i class="glyphicon glyphicon-arrow-up"></i></a>
    <script src="../../js/jquery-1.9.1.min.js"></script>
    <script src="../../js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../../js/jquery.scrollTo-1.4.3.1-min.js" type="text/javascript"></script>
    <script src="../../js/jquery.easing-1.3.min.js"></script>
    <script type="text/javascript" src="../../DataTables/datatables.min.js"></script>
    <script src="../../js/jquery.rest.min.js"></script>
    <script src="../../js/custom.js"></script>
    <script src="../../js/datepicker.js"></script>
</body>
</html>
</#macro>