<#import "masterTemplate.ftl" as layout />

<@layout.masterTemplate title="Users">
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <h3 class="text-center text-info">
                Users
            </h3>
            <table class="table table-bordered table-condensed table-hover">
                <thead>
                <tr>
                    <th>
                        #
                    </th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>User Name</th>
                    <th>EMAIL</th>
                    <th>Role</th>
                </tr>
                </thead>
                <tbody>
                    <#list users as user>
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.userName}</td>
                        <td>${user.emailId}</td>
                        <td>${user.role.role}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</@layout.masterTemplate>