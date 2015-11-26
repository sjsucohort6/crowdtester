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
<div class="modal fade" id="register-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="loginmodal-container">
            <h1>Join Us</h1><br>

            <form>
                <input type="text" name="user" placeholder="Firstname">
                <input type="text" name="user" placeholder="Lastname">
                <input type="text" name="user" placeholder="EmailId">
                <input type="password" name="pass" placeholder="Password">
                <input type="password" name="repass" placeholder="Re-type Password">

                <div class="dropdown">
                    I am:
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                        Select
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                        <li><a href="#">Vendor</a></li>
                        <li><a href="#">Tester</a></li>

                    </ul>
                </div>
                <br/>
                <input type="submit" name="login" class="login loginmodal-submit" value="Register">
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
     style="display: none;">
    <div class="modal-dialog">
        <div class="loginmodal-container">
            <h1>Login to Your Account</h1><br>

            <form>
                <input type="text" name="user" placeholder="Username">
                <input type="password" name="pass" placeholder="Password">
                <input type="submit" name="login" class="login loginmodal-submit" value="Login">
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
<script type="text/javascript">

	$(document).ready(function() {
	$('#myCarousel').carousel({
	  interval: 4000
	});

	});

</script>
</@layout.masterTemplate>