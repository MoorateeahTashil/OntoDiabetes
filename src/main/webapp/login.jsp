<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" type="image/x-icon" href="Images/medical.ico" />
<title>OntoDiabetes</title>


<!-- Custom fonts for this template-->
<link href="css/all.min.css" rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300&display=swap"
	rel="stylesheet">
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

.bg-login-image {
	background: url(Images/doctorlogin.jpg);
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
</style>


</head>
<body class="bg-gradient-primary">
	<div class="container">

		<!-- Outer Row -->
		<div class="row justify-content-center">

			<div class="col-xl-10 col-lg-12 col-md-9">

				<div class="card o-hidden border-0 shadow-lg my-5">
					<div class="card-body p-0">
						<!-- Nested Row within Card Body -->
						<div class="row">
							<div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
							<div class="col-lg-6">
								<div class="p-5">
									<div class="text-center">
										<h1 class="h4 text-gray-900 mb-4">Welcome Back to
											OntoDiabetes!</h1>
									</div>

									<form action="Login" method="post">

										<div class="user">
											<div class="form-group">
												<input type="email" class="form-control form-control-user"
													ID="emailTextBox" name="emailTextBox"
													aria-describedby="emailHelp"
													placeholder="Enter Email Address..." required/>
											</div>
											<div class="form-group">
												<input type="password"
													class="form-control form-control-user"
													name="passwordTextBox" ID="passwordTextBox"
													placeholder="Password" required />
											</div>

											<%
											String message = (String)request.getAttribute("errorMessage");
;
											%>


											<%
											if (message == null || message.isEmpty()) {
											} else {
											%>

											<div class="card mb-4">
												<div class="card-header">Error Message</div>
												<div class="card-body">
													<p ID="errorMessageLabel" /><%= message %>
												</div>
											</div>
											<%
											}
											%>

											<input type="submit" ID="buttonLogin"
												class="btn btn-primary btn-user btn-block"
												value="Login" />
										</div>
									</form>

									<hr>

									<div class="text-center">
										<a class="small" href="register.jsp">Create an Account!</a>
									</div>
								</div>
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
