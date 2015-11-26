<#import "masterTemplate.ftl" as layout />

<@layout.masterTemplate title="Bug Miner/ Bug Tracker" moduleName="crowdtesterApp">

<div id="headerSection">
    <div class="container">
        <div class="span3 logo"><img src="img/logo.png" alt="Buglogo"/></a></div>
        <div class="container" style="padding-top: 100px;">
            <div class="row">
                <div class="table-responsive">
                    <table id="mytable" class="table table-bordered vd-table-hover table-responsive vd-table-striped">
                        <caption><h3>BUGS TABLE</h3></caption>
                        <thead>

                        <th style : border: 3px;><input type="checkbox" id="checkall"/></th>
                        <th>Bug Id</th>
                        <th>AppName</th>
                        <th>Summary</th>
                        <th>Description</th>
                        <th>Tester</th>
                        <th>Severity</th>
                        <th>Status</th>
                        <th>Date</th>
                        <th>Valid</th>
                        <th>Delete</th>
                        </thead>
                        <tbody>
                            <#list bugs as bug>
                            <tr>
                                <td><input type="checkbox" class="checkthis"/></td>
                                <td>${bug.id}</td>
                                <td>${bug.appName} 1</td>
                                <td>${bug.summary}</td>
                                <td>${bug.description}</td>
                                <td>${bug.tester}</td>
                                <td>${bug.severity}</td>
                                <td>${bug.status}</td>
                                <td>${bug.creationDate}</td>
                                <td>${bug.valid}</td>
                                <td>
                                    <p data-placement="top" data-toggle="tooltip" title="Delete">
                                        <button class="btn btn-danger btn-xs" data-title="Delete" data-toggle="modal"
                                                data-target="#delete"><span class="glyphicon glyphicon-trash"></span>
                                        </button>
                                    </p>
                                </td>
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
    </div>

    <div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span
                            class="glyphicon glyphicon-remove" aria-hidden="true"></span></button>
                    <h4 class="modal-title custom_align" id="Heading">Delete this entry</h4>
                </div>
                <div class="modal-body">

                    <div class="alert alert-danger"><span class="glyphicon glyphicon-warning-sign"></span> Are you sure
                        you want to delete this Record?
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

                    <form class="form-horizontal" role="form">
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
                                   for="inputsum">Summary</label>

                            <div class="col-sm-8">
                                <input type="text" class="form-control"
                                       id="inputsum" placeholder="Summary"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"
                                   for="inputdesc">Description</label>

                            <div class="col-sm-10">
                                <input type="password" class="form-control"
                                       id="inputdesc" placeholder="Description"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label" for="inputsev">Severity</label>

                            <div class="col-sm-10">
                                <select name="severity">
                                    <option value="high">High</option>
                                    <option value="medium">Medium</option>
                                    <option value="low">Low</option>
                                </select>
                                <br><br></div>
                        </div>
                        <br/>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"
                                   for="inputstat">Status</label>

                            <div class="col-sm-10"><label class="radio-inline"><input type="radio" name="optradio">Open</label>
                                <label class="radio-inline"><input type="radio" name="optradio" disabled>Close</label>
                            </div>
                        </div>


                        <div class="container" id="sandbox-container">
                            <div class="input-daterange input-group" id="datepicker">
                                <label class="col-sm-2 control-label"
                                       for="inputstartdate">Date</label>

                                <div class="col-sm-6"><input class="form-control" name="start" type="text"></div>

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
                        <div class="form-group">
                            <label class="col-sm-2 control-label"
                                   for="inputtester">Valid</label>

                            <div class="col-sm-8">
                                <div class="checkbox">
                                    <label><input type="checkbox" value=""></label></div>
                            </div>

                        </div>
                </div>


                <!-- Modal Footer -->
                <div class="modal-footer">
                    <button type="button" class="btn-vendor-dashboard"
                            data-dismiss="modal">
                        Close
                    </button>
                    <button type="button" class="btn-vendor-dashboard">
                        Submit
                    </button>
                </div>
                </form>
            </div>
        </div>
    </div>


    <div class="container">

        <button class="btn btn-vendor-dashboard btn-lg" data-toggle="modal" data-target="#myModalHorizontal">
            ADD
        </button>
        <button type="button" class="btn btn-vendor-dashboard btn-sm">EDIT</button>
    </div>
</@layout.masterTemplate>