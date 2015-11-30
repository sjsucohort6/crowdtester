<#import "protectedMasterTemplate.ftl" as layout />

<@layout.masterTemplate title="Bug Miner Tester Dashboard" moduleName="crowdtesterApp">

<#include "postlogin_header.ftl">

<div id="parent" class="container">

    <div class="vd-jumbotron">
        <h3><b><i>Your Profile Info</b></i></h3>

        <p>Skills: ${tester.skills?join(", ")} </p>
        <!-- TODO: change for credits -->
        <p>Credits: ${tester.creditPoint}<br>
            <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
            <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
            <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
            <span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
            <span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
        </p>
        <p>Incentives: TODO</p>
    </div>
</div>
<div class="container" style="padding-top: 25px;">
    <div class="row">
        <div class="table-responsive">
            <table id="appTable" class="table table-striped table-bordered" cellspacing="0" width="100%">
                <thead>
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
                        <td>${app.projectStartDate}</td>
                        <td>${app.projectEndDate}</td>
                        <td>${app.appVendor}</td>
                        <td>${app.testers?join(", ")}</td>
                        <td>${app.supportedPlatforms?join(", ")}</td>
                        <td>${app.appFileName}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="container" style="text-align: center;">
    <a href="bugtracker.ftl">
        <button type="button" class="btn btn-vendor-dashboard btn-sm">BUG TRACKER</button>
    </a>
</div>
</@layout.masterTemplate>