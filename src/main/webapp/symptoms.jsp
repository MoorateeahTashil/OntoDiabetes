<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="masterPage.jsp"%>

<%
if (session.getAttribute("userID") == null) {

	String redirectURL = "sessionExpired.jsp";
	response.sendRedirect(redirectURL);
}
%>

<%@ page import="OntoDiabetes.DatabaseConnection"%>


<%
final Connection con;
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
// variables
DatabaseConnection db = new DatabaseConnection();

final String url = db.getUrlConnection();
final String user = db.getUser();
final String password = db.getPassword();
// establish the connection
%>


<div class="container-fluid">

	<!-- Page Heading -->
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">Symptoms</h1>
	</div>

	<div class="container">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">

				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">Kindly answer
							this questionnaire on symptoms</h6>
					</div>
					<div class="card-body">

						<div class="row">
							<div class="col-lg-12">
								<div class="p-5">
									<form class="user" action="symptoms" method="post">


										<div class="form-group">
											<select id="tension" name="tension"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Do you
													have high blood pressure (Hypertension)?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>

										<div class="form-group">
											<select id="fatigue" name="fatigue"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Have you
													been experience unusual fatigue lately?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>

										<div class="form-group">
											<select id="hunger" name="hunger"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Have you
													been experience unusual hunger lately?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>


										<div class="form-group">
											<select id="lossweight" name="lossweight"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Have you
													been losing weight unexpectedly lately?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>


										<div class="form-group">
											<select id="vision" name="vision"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Have you
													been experiencing eyes problems lately (Blurred Vision)?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>


										<div class="form-group">
											<select id="tingling" name="tingling"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Are you
													feeling tingling in your hands or feet?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>


										<div class="form-group">
											<select id="patches" name="patches"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Did
													patches of dark skin form on your body lately?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>



										<div class="form-group">
											<select id="infections" name="infections"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Are you
													more prone to infections lately?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>



										<div class="form-group">
											<select id="healing" name="healing"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Are you
													experiencing slow healing sores and wound lately?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>


										<div class="form-group">
											<select id="relative" name="relative"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Do you
													have a first degree relative with an history of diabetes?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>



										<div class="form-group">
											<select id="polyuria" name="polyuria"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Have you
													been urinating more than usual and passing
													excessive/abnormally large amounts of urine?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>



										<div class="form-group">
											<select id="polydipsia" name="polydipsia"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Have you
													been having excessive thirst or excess drinking?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>


										<br />
										<hr class="sidebar-divider my-0">
										<br />

										<p>For recently pregnant or pregnant women</p>
										<div class="form-group">
											<select id="adverse" name="adverse"
												class="form-control form-control-dropdown" >
												<option value="" disabled selected hidden>Did you
													have any adverse and unwanted conditions in previous
													pregnancies?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
												
											</select>
										</div>


										<div class="form-group">
											<select id="miscarriage" name="miscarriage"
												class="form-control form-control-dropdown" >
												<option value="" disabled selected hidden>Did you
													have any miscarriage in previous pregnancies?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>


										<div class="form-group">
											<select id="stillbirth" name="stillbirth"
												class="form-control form-control-dropdown" >
												<option value="" disabled selected hidden>Did you
													have any stillbirth in previous pregnancies?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>

										<%
										String message = (String) request.getAttribute("errorMessage");
										%>


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

</div>

<%@ include file="masterPage2.jsp"%>

