<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" type="image/x-icon"
	href="Images/Icons/medical.ico" />
<title>MAUDiabetes</title>


<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet"/>

<!-- Custom styles for this template-->
<link href="css/sb-admin-2.min.css" rel="stylesheet">


<!-- Overwriting CSS -->
<style>
.bg-gradient-primary {
	background-color: #23408e;
	background-image: linear-gradient(180deg, #23408e 10%, #224abe 100%);
	background-size: cover;
}

body {
	font-family: 'Montserrat', sans-serif;
}

.bg-register-image {
	background: url(Images/doctorregister.jpg);
	background-position: center;
	background-size: cover;
}

.card-header {
	padding: .75rem 1.25rem;
	margin-bottom: 0;
	background-color: #ff0033;
	border-bottom: 1px solid #e3e6f0;
	color: white;
	font-weight: bold;
}

.btn-switch {
	font-size: 1.25em;
	position: relative;
	display: inline-block;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

.btn-switch__radio {
	display: none;
}

.btn-switch__label {
	display: inline-block;
	padding: .75em .5em .75em .75em;
	vertical-align: top;
	font-size: 1em;
	font-weight: 700;
	line-height: 1.5;
	color: #666;
	cursor: pointer;
	transition: color .2s ease-in-out;
}

.btn-switch__label+.btn-switch__label {
	padding-right: .75em;
	padding-left: 0;
}

.btn-switch__txt {
	position: relative;
	z-index: 2;
	display: inline-block;
	min-width: 1.5em;
	opacity: 1;
	pointer-events: none;
	transition: opacity .2s ease-in-out;
}

.btn-switch__radio_no:checked ~ .btn-switch__label_yes .btn-switch__txt,
	.btn-switch__radio_yes:checked ~ .btn-switch__label_no .btn-switch__txt
	{
	opacity: 0;
}

.btn-switch__label:before {
	content: "";
	position: absolute;
	z-index: -1;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	background: #f0f0f0;
	border-radius: 1.5em;
	box-shadow: inset 0 .0715em .3572em rgba(43, 43, 43, .05);
	transition: background .2s ease-in-out;
}

.btn-switch__radio_yes:checked ~ .btn-switch__label:before {
	background: #23408e;
}

.btn-switch__label_no:after {
	content: "";
	position: absolute;
	z-index: 2;
	top: .5em;
	bottom: .5em;
	left: .5em;
	width: 2em;
	background: #fff;
	border-radius: 1em;
	pointer-events: none;
	box-shadow: 0 .1429em .2143em rgba(43, 43, 43, .2), 0 .3572em .3572em
		rgba(43, 43, 43, .1);
	transition: left .2s ease-in-out, background .2s ease-in-out;
}

.btn-switch__radio_yes:checked ~ .btn-switch__label_no:after {
	left: calc(100% - 2.5em);
	background: #fff;
}

.btn-switch__radio_no:checked ~ .btn-switch__label_yes:before,
	.btn-switch__radio_yes:checked ~ .btn-switch__label_no:before {
	z-index: 1;
}

.btn-switch__radio_yes:checked ~ .btn-switch__label_yes {
	color: #fff;
}
</style>

</head>
<body class="bg-gradient-primary">
	<div class="container">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">
				<!-- Nested Row within Card Body -->
				<div class="row">
					<div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
					<div class="col-lg-7">
						<div class="p-5">
							<div class="text-center">
								<h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
							</div>


							<form action="Register" method="post">



								<div class="user">
									<div class="form-group">
										<input type="email" class="form-control form-control-user"
											ID="emailTextBox" name="emailTextBox"
											aria-describedby="emailHelp"
											placeholder="Enter Email Address..." required />
									</div>

									<div class="form-group row">
										<div class="col-sm-6 mb-3 mb-sm-0">
											<div class="form-group">
												<input type="password"
													class="form-control form-control-user"
													name="passwordTextBox" ID="passwordTextBox"
													placeholder="Password" required />
											</div>
										</div>
										<div class="col-sm-6">
											<input name="repeatPasswordTextBox" type="password"
												class="form-control form-control-user"
												ID="repeatPasswordTextBox" placeholder="Repeat Password.."
												required />
										</div>
									</div>
									<div class="form-group row">

										<div class="col-sm-12 mb-12 mb-sm-12 offset-3">
											<p class="btn-switch text-center">


												<input type="radio" id="yes" name="switch" value="Doctor"
													class="btn-switch__radio btn-switch__radio_yes" /> 
													
													<input
													type="radio" checked id="no" name="switch" value="Patient"
													class="btn-switch__radio btn-switch__radio_no" />
													
													 <label
													for="yes" class="btn-switch__label btn-switch__label_yes">
													<span class="btn-switch__txt">Doctor <i
														class="fa fa-user-md"></i>
												</span>
												</label> <label for="no"
													class="btn-switch__label btn-switch__label_no"> <span
													class="btn-switch__txt">Patient <i
														class="fa fa-heartbeat"></i>

												</span>
												</label>
											</p>
										</div>
									</div>

									<div class="form-group">
										<input class="form-control form-control-user"
											ID="txtSpecialDocCode" name="txtSpecialDocCode"
											placeholder="Enter Special Code (For Doctors only)" />
									</div>


									<%
									String message = (String) request.getAttribute("errorMessage");
									;
									%>


									<%
									if (message == null || message.isEmpty()) {
									} else {
									%>

									<div class="card mb-4">
										<div class="card-header">Error Message</div>
										<div class="card-body">
											<p ID="errorMessageLabel" /><%=message%>
										</div>
									</div>
									<%
									}
									%>

									<input type="submit" ID="buttonLogin"
										class="btn btn-primary btn-user btn-block"
										value="Register Account" />



								</div>

							</form>
							<hr>

							<div class="text-center">
								<a class="small" href="login.jsp">Already have an account?
									Login!</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Bootstrap core JavaScript-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="js/sb-admin-2.min.js"></script>
</body>
</html>
