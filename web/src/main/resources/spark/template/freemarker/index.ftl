<#import "masterTemplate.ftl" as layout />

<@layout.masterTemplate title="Bug Miner" moduleName="crowdtesterApp">

<div id="headerSection">
    <div class="container">
        <div class="span3 logo"><img src="../img/logo.png" alt="Buglogo"/></a></div>

        <div class="navbar">


            <div class="nav-collapse">
                <ul class="nav mynav">
                    <li><a href="#" data-toggle="modal" data-target="#register-modal">Register <span
                            class="glyphicon glyphicon-lock"></span></a></li>
                    <li><a href="#" data-toggle="modal" data-target="#login-modal">Login <span
                            class="glyphicon glyphicon-user"></span></a></li>

                </ul>
            </div>

            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="line"></span>
                <span class="line"></span>
                <span class="line"></span>
            </button>
        </div>
    </div>
</div>
<div class="modal fade" id="register-modal">
    <div class="modal-dialog">
        <div class="loginmodal-container">
            <h1>Join Us</h1><br>

            <div id="regStatus"></div>
            <form role="form" action="/crowdtester/api/v1.0/register" method="POST">
                <div class="form-group">
                    <input type="text" name="firstName" id="regFirstName" placeholder="Firstname">
                </div>
                <div class="form-group">
                    <input type="text" name="lastName" id="regLastName" placeholder="Lastname">
                </div>
                <div class="form-group">
                    <input type="text" name="emailId" id="regEmailId" placeholder="EmailId">
                </div>
                <div class="form-group">
                    <input type="password" name="pass" id="regPasswd" placeholder="Password">
                </div>
                <div class="form-group">
                    <input type="password" name="repass" id="regRePasswd" placeholder="Re-type Password">
                </div>
                <div class="form-group">
                    <label for="regUserRole">Role</label>
                    <select class="form-control" id="regUserRole" name="role">
                        <option>Vendor</option>
                        <option>Tester</option>
                    </select>
                </div>
                <div class="form-group">
                    <input type="text" name="vendorName" id="regVendorName" placeholder="Vendor Name">
                </div>
                <div class="form-group">
                    <label for="regTesterSkills">Tester Skills (Hold Cntl/Cmd key to select multiple skills)</label>
                    <select class="form-control" id="regTesterSkills" nam="testerSkills" multiple>
                        <option>Android </option>
                        <option>iOS </option>
                        <option>Blackberry </option>
                        <option>Windows </option>
                        <option>Whitebox </option>
                        <option>Automation </option>
                        <option>Manual </option>
                    </select>
                </div>
                <#-- </div>-->
                <br/>
                <input type="submit" name="register" class="login loginmodal-submit" id="registerBtn" value="Register">
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="login-modal">
    <div class="modal-dialog">
        <div class="loginmodal-container">
            <h1>Login to Your Account</h1><br>

            <form role="form" action="/crowdtester/api/v1.0/login" method="POST">
                <input type="text" name="user" id="user" placeholder="Username">
                <input type="password" name="password" id="password" placeholder="Password">
                <input type="submit" name="login" class="login loginmodal-submit" id="loginBtn" value="Login">
            </form>
        </div>
    </div>
</div>

<!--Header Ends=-->

<div id="carouselSection" class="cntr">
    <div id="myCarousel" class="carousel slide">

        <div class="carousel-inner">
            <div class="item active">
                <a class="cntr" href="#"><img src="../img/img1.png" alt=""></a>
            </div>
            <div class="item">
                <a class="cntr" href="#"><img src="../img/img2.png" alt=""></a>
            </div>


        </div>
        <a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
        <a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
    </div>
</div>

<!-- Sectionone block ends======================================== -->

<!-- Our Services======================================== -->
<div id="ourServices">
    <div class="container">
        <div class="row">

            <div class="col-md-4 col-xs-12">

                <div class="info-img">
                    <img src="../img/img-1.png">
                </div>
                <h4>Draft your Requirements</h4>

                <p>Only takes 2 mintues</p>
            </div>
            <div class="col-md-4 col-xs-12">
                <div class="info-img"><img src="../img/img-2.png"></div>
                <h4>Submit your Test Requests</h4>

                <p>Our professionals get to work </p>
            </div>
            <div class="col-md-4 col-xs-12">
                <div class="info-img"><img src="../img/img-3.png"></div>
                <h4>Get Rapid and Review results</h4>

                <p>Your developers can fix defects</p>
            </div>
        </div>
    </div>
</div>
</@layout.masterTemplate>