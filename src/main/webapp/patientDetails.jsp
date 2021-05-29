<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" type="image/x-icon" href="Images/medical.ico" />
<title>OntoDiabetes - Fill in Details</title>




<!-- Custom fonts for this template-->
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap"
	rel="stylesheet" />
<!-- Custom styles for this template-->
<link href="css/sb-admin-2.min.css" rel="stylesheet">
<style>
body {
	font-family: 'Montserrat', sans-serif;
}

.form-control-dropdown {
	font-size: .8rem;
	border-radius: 10rem;
	height: 3rem;
}

.card-headers {
	padding: .75rem 1.25rem;
	margin-bottom: 0;
	background-color: #ff0033;
	border-bottom: 1px solid #e3e6f0;
	color: white;
	font-weight: bold;
}
</style>




</head>
<%
if (session.getAttribute("userID") == null) {

	String redirectURL = "sessionExpired.jsp";
	response.sendRedirect(redirectURL);
}
%>
<body class="bg-gradient-primary">

	<div class="container">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">

				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">Fill in your
							details</h6>
					</div>
					<div class="card-body">

						<div class="row">
							<div class="offset-lg-1 col-lg-9">
								<div class="p-5">
									<form class="user" action="patientDetails" method="post">
										<div class="form-group row">
											<div class="col-lg-4 mb-3 mb-sm-0">
												<input type="text" class="form-control form-control-user"
													id="SurnameTextBox" name="SurnameTextBox"
													placeholder="Surname" required>
											</div>
											<div class="col-lg-4">
												<input type="text" class="form-control form-control-user"
													id="middlenameTextBox" name="middlenameTextBox"
													placeholder="Middle Name">
											</div>

											<div class="col-lg-4">
												<input type="text" class="form-control form-control-user"
													id="forenameTextBox" name="forenameTextBox"
													placeholder="Fore Name" required>
											</div>
										</div>

										<div class="form-group">
											<select id="genderDropDown" name="genderDropDown"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Gender</option>
												<option value="Male">Male</option>
												<option value="Female">Female</option>
												<option value="Others">Others</option>
											</select>
										</div>

										<div class="form-group">
											<input class="form-control form-control-user" id="nicTextBox"
												name="nicTextBox"
												placeholder="National Identification Number" required>
										</div>

										<div class="form-group">
											<input class="form-control form-control-user" id="dobTextBox"
												name="dobTextBox" type="date" placeholder="Date of Birth"
												required>
										</div>

										<div class="form-group">
											<select id="maritalstatusDropDown"
												name="maritalstatusDropDown"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Marital
													Status</option>
												<option value="Single">Single</option>
												<option value="Separated">Separated</option>
												<option value="Married">Married</option>
												<option value="Widowed">Widowed</option>
												<option value="Divorced">Divorced</option>
											</select>
										</div>

										<div class="form-group">
											<select id="haschildrenDropDown"
												name="haschildrenDropDown"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Has child/children?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>

										<div class="form-group">

											<input type="number" step="0.01"
												class="form-control form-control-user" id="heightTextBox"
												name="heightTextBox" placeholder="Height (ms)" required>
										</div>

										<div class="form-group">

											<input type="number" step="0.1"
												class="form-control form-control-user" id="weightTextBox"
												name="weightTextBox" placeholder="Weight (kgs)" required>
										</div>

										<div class="form-group">
											<input type="number" class="form-control form-control-user"
												id="mobilenumber" name="mobilenumber"
												placeholder="Mobile Phone Number" required>
										</div>

										<div class="form-group">
											<input type="number" class="form-control form-control-user"
												id="homenumber" name="homenumber"
												placeholder="Home Phone Number">
										</div>

										<div class="form-group">
											<input type="number" class="form-control form-control-user"
												id="worknumber" name="worknumber"
												placeholder="Work Phone Number">
										</div>

										<div class="form-group">
											<input class="form-control form-control-user" id="Others"
												name="Others" placeholder="Other Detail">
										</div>


										<%
										String message = (String) session.getAttribute("errorMessage");
										session.removeAttribute("errorMessage");										%>


										<%
										if (message == null || message.isEmpty()) {
										} else {
										%>

										<div class="card mb-4">
											<div class="card-headers">Error Message</div>
											<div class="card-body">
												<p ID="errorMessageLabel" /><%=message%>
											</div>
										</div>
										<%
										}
										%>

										<input type="submit" ID="buttonSave"
											class="btn btn-primary btn-user btn-block" value="Save" />

									</form>

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