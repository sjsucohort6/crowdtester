<#import "protectedMasterTemplate.ftl" as layout />

<@layout.masterTemplate title="Bug Miner Vendor Dashboard" moduleName="crowdtesterApp">
<#include "postlogin_header.ftl">
<div class="container">
    <div class="vd-jumbotron">
        <h3><b><i>Welcome</b> ${vendor.user.firstName} ${vendor.user.lastName}</i></h3>

        <p>Email Id:${vendor.user.emailId}</p>

        <p>Your Charge Back: ${vendor.chargeBackPolicy}</p>

        <p>Incentives :${vendor.incentivePolicy}</p>

    </div>
</div>
<div class="container" style="padding-top: 25px;">
    <div class="row">
        <div class="table-responsive">
            <table id="vendorTable" class="table table-striped table-bordered" cellspacing="0" width="100%">
                <thead>
                <th style : 3px;>Select</th>
                <th>Name</th>
                <th>Description</th>
                <th>Platform</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>File</th>
                </thead>
                <tfoot>
                <th style : 3px;>Select</th>
                <th>Name</th>
                <th>Description</th>
                <th>Platform</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>File</th>
                </tfoot>
                <tbody>
                    <#list vendor.apps as app>
                    <tr>
                        <td><input type="radio" name="apps" value="${app.name}"
                                   style="vertical-align: middle; margin: 0px;"></td>
                        <td>${app.name}</td>
                        <td>${app.description}</td>
                        <td>${app.supportedPlatforms}</td>
                        <td>${app.projectStartDate}</td>
                        <td>${app.projectEndDate}</td>
                        <td><a href="#">${app.appFileName}</a></td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>


<div class="modal fade" id="selectTesterModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 class="modal-title">Select Tester</h3>
            </div>
            <div class="modal-body">
                <table class="table table-striped table-bordered" id="testerSelectionTable">
                    <thead>
                    <tr>
                        <th>Select</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Skills</th>
                        <th>Credit</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th>Select</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Skills</th>
                        <th>Credit</th>
                    </tr>
                    </tfoot>
                    <tbody>
                    <#list testers as tester>
                    <tr>
                        <td><input type="radio" name="testers" value="${tester.user.userName}"
                                   style="vertical-align: middle; margin: 0px;"></td>
                        <td>${tester.user.firstName} ${tester.user.lastName}</td>
                        <td>${tester.user.emailId}</td>
                        <td>${tester.skills?join(", ")}</td>
                        <td>Credit Points: ${tester.creditPoint}<br>
                            <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
                            <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
                            <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
                            <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
                            <span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>
                        </td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default " data-dismiss="modal">Close</button>
                <input type="button" class="btn btn-warning btn-sm pull-right" value="Select" id="modalSelectTesterBtn">
            </div>
        </div>

    </div><!-- /.modal-content -->
</div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span
                        class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                <h4 class="modal-title custom_align" id="Heading">Delete this entry</h4>
            </div>
            <div class="modal-body">

                <div class="alert alert-danger"><span class="glyphicon glyphicon-warning-sign"></span> Are you sure you
                    want to delete this Record?
                </div>

            </div>
            <div class="modal-footer ">
                <button type="button" class="btn btn-success"><span class="glyphicon glyphicon-ok-sign"></span> Yes
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><span
                        class="glyphicon glyphicon-remove"></span> No
                </button>
            </div>
        </div>

    </div>
    <!-- /.modal-dialog -->
</div>

<!-- Modal -->
<div class="modal fade" id="myModalHorizontal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Modal Body -->
            <div class="modal-body">

                <form class="form-horizontal" role="form" method="post" action="/crowdtester/api/v1.0/app">
                    <div class="form-group">
                        <label class="col-sm-2 control-label"
                               for="inputname">AppName</label>

                        <div class="col-sm-8">
                            <input type="text" class="form-control"
                                   id="inputname" placeholder="Application Name"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label"
                               for="inputdesc">Description</label>

                        <div class="col-sm-10">
                            <input type="text" class="form-control"
                                   id="inputdesc" placeholder="Description"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="inputsev">Platform</label>

                        <div class="col-sm-10">
                            <select name="platform">
                                <option value="android">Android</option>
                                <option value="ios">IOS</option>

                            </select>
                            <br><br></div>
                    </div>
                    <div class="form-group">
                        <form enctype="multipart/form-data" method="post" action="/crowdtester/api/v1.0/upload">
                            <p>Select Application Binary to upload: <input type="file" name="appFile"></p>
                            <div>
                                <input type="submit" value="submit">
                            </div>
                        </form>
                    </div>
                    <br/>


                    <div class="container" id="sandbox-container">
                        <div class="input-daterange input-group" id="datepicker">
                            <label class="col-sm-1 control-label"
                                   for="inputstartdate">Start:</label>

                            <div class="col-sm-4"><input class="form-control" name="start" type="text"></div>

                            <label class="col-sm-1 control-label"
                                   for="inputenddate">End:</label>

                            <div class="col-sm-4"><input class="form-control" name="end" type="text">
                            </div>
                        </div>
                    </div>
                    <br/>

                    <div class="form-group">
                        <label class="col-sm-2 control-label"
                               for="inputtester">TesterName</label>

                        <div class="col-sm-8">
                            <input type="email" class="form-control"
                                   id="inputtester" placeholder="Tester Name"/>
                        </div>
                    </div>
            </div>

            <!-- Modal Footer -->
            <div class="modal-footer">
                <button type="button" class="btn-vendor-dashboard"
                        data-dismiss="modal">
                    Close
                </button>
                <button type="button" class="btn-vendor-dashboard" id="addAppBtn">
                    Add Application
                </button>
            </div>
            </form>
        </div>
    </div>
</div>
<div class="container">

    <button class="btn btn-vendor-dashboard btn-lg" data-toggle="modal" data-target="#myModalHorizontal" id="addAppBtn">
        ADD
    </button>
    <button type="button" class="btn btn-vendor-dashboard btn-sm" id="deleteAppBtn">DELETE</button>
    <button type="button" class="btn btn-vendor-dashboard btn-sm" href="#selectTesterModal" data-toggle="modal" id="selectTesterBtn">
        SELECT TESTER
    </button>
    <a href="bugtracker.ftl">
        <button type="button" class="btn btn-vendor-dashboard btn-sm">BUG TRACKER</button>
    </a>

</div>
</@layout.masterTemplate>