// Initialize JQuery REST client
var restClient = new $.RestClient('/crowdtester/api/v1.0/');
restClient.add("login", {stripTrailingSlash: true, stringifyData: true});
var loginClient = restClient.login; // maps to /login

restClient.add("register", {stripTrailingSlash: true, stringifyData: true});
var registerClient = restClient.register; // maps to /register

restClient.add("logout");
var logoutClient = restClient.logout;

$(document).ready(function(){
/*$('#myCarousel').carousel({
	  interval: 4000
	});*/
$('#vendorTable').DataTable();
$('#testerSelectionTable').DataTable();
$("#mytable #checkall").click(function () {
        if ($("#mytable #checkall").is(':checked')) {
            $("#mytable input[type=checkbox]").each(function () {
                $(this).prop("checked", true);
            });

        } else {
            $("#mytable input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
            });
        }
    });
});

/*$("#logoutBtn").on("click", function(event){
     event.preventDefault();
     var request = logoutClient.create();
});*/

//Perform post for login -- handled by LoginRoute on server
/*$("#loginBtn").on("click", function(event){
     event.preventDefault();
     var user = $('form input#user').val();
     var password = $('form input#password').val();
     var loginPayload = '{' +
                            '"user" : "' + user + '",' +
                            '"password" : "' + password + '"' +
                         '}';
     alert(loginPayload);
     var request = loginClient.create(JSON.parse(loginPayload));
     request.done(function (data, textStatus, xhrObject){
             alert("Login for user " + user + "is success");
             alert(data)
             window.location.assign(data)
             $('#login-modal').modal('hide');
          });
     request.fail(function() {
          alert( "User " + user + " failed to login" );

        });
 });*/

// Perform post for register -- handled by RegisterRoute on server
/*
$("#registerBtn").on("click", function(event){
     event.preventDefault();
     var firstName = $('form input#regFirstName').val();
     var lastName = $('form input#regLastName').val();
     var emailId = $('form input#regEmailId').val();
     var vendorName = $('form input#regVendorName').val();
     var testerSkills = $('form select#regTesterSkills :selected').text();
     alert(testerSkills)
     var password = $('form input#regPasswd').val();
     var rePassword = $('form input#regRePasswd').val();
     var role = $('form select#regUserRole :selected').text();

     // validate user input
     if (password != rePassword) {
        $("#regStatus").text("Passwords don't match!");
        return;
     }

     var regPayload = '{' +
                        '"firstName" : "' + firstName + '",' +
                        '"lastName" : "' + lastName + '",' +
                        '"emailId" : "' + emailId + '",' +
                        '"role" : "' + role + '",' +
                        '"vendorName" : "' + vendorName + '",' +
                        '"testerSkills" : "' + testerSkills + '",' +
                        '"password" : "' + password + '"' +
                     '}';
     alert(regPayload);
     var request = registerClient.create(JSON.parse(regPayload));
     request.done(function (data, textStatus, xhrObject){
             $("#regStatus").text("User " + regEmailId + " was registered successfully.");
             $('#register-modal').modal('hide');
             $('#registerModalCloseBtn').trigger("click");
          });
     request.fail(function() {
          $("#regStatus").text("User " + regEmailId + " registration failed! Contact support.");
        });
 });*/
