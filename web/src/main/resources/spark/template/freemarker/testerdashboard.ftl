<#import "masterTemplate.ftl" as layout />

<@layout.masterTemplate title="Bug Miner Tester Dashboard" moduleName="crowdtesterApp">

    <#include "postlogin_header.ftl">

<div id="parent" class="container">

    <div class="vd-jumbotron">
        <h3><b><i>Your Profile Info</b></i></h3>

        <p>Skills: ${tester.skills} </p>
        <!-- TODO: change for credits -->
        <p>Credits:
            <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
            <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
            <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
            <span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
            <span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
        </p>
        <p>Incentives: ${tester.incentive}</p>
    </div>
</div>
<div class="container" style="padding-top: 25px;">
    <div class="row">


        <div class="table-responsive">


            <table id="mytable" class="table table-bordered vd-table-hover table-responsive vd-table-striped">


                <th>Name</th>
                <th>Description</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Vendor</th>
                <th>Testers</th>
                <th>Platform</th>
                <th>File</th>
                </thead>
                <tbody>
                    <#list apps as app>
                    <tr>
                        <td>${app.name}</td>
                        <td>${app.description}</td>
                        <td>${app.startDate}</td>
                        <td>${app.endDate}</td>
                        <td>${app.vendor}</td>
                        <td>${app.testers}</td>
                        <td>${app.platform}</td>
                        <td><a href="${app.filePath}">Download File</a></td>

                    </tr>
                    </#list>
                </tbody>

            </table>

            <div class="clearfix"></div>
            <ul class="pagination pull-right">
                <li class="disabled"><a href="#"><span class="glyphicon glyphicon-chevron-left"></span></a></li>
                <li class="active"><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span></a></li>
            </ul>

        </div>

    </div>
</div>

<div class="container" style="text-align: center;">
    <a href="bugtracker.ftl">
        <button type="button" class="btn btn-vendor-dashboard btn-sm">BUG TRACKER</button>
    </a>
</div>
</@layout.masterTemplate>