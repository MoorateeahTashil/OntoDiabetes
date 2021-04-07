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

String patientid = request.getParameter("patientid");
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
									<form class="user" action="test" method="post">
										<input type="text" id="patientID" name="patientID"
											style="display: none;" value="<%=patientid%>">
										<div class="form-group row">

											<div class="col-sm-12 mb-12 mb-sm-12 offset-3">
												<p class="btn-switch text-center">


													<input type="radio" id="yes" name="switch" value="fbs"
														class="btn-switch__radio btn-switch__radio_yes" /> <input
														type="radio" checked id="no" name="switch" value="rbs"
														class="btn-switch__radio btn-switch__radio_no" /> <label
														for="yes" class="btn-switch__label btn-switch__label_yes">
														<span class="btn-switch__txt">Fasting blood sugar
															test <i class="fas fa-diagnoses"></i>
													</span>
													</label> <label for="no"
														class="btn-switch__label btn-switch__label_no"> <span
														class="btn-switch__txt">2-h post glucose load test<i
															class="fas fa-capsules"></i>

													</span>
													</label>
												</p>
											</div>
										</div>


										<div class="form-group">

											<input type="number" step="0.1"
												class="form-control form-control-user" id="valueText"
												name="valueText" placeholder="Value (mg dl-1)" required>
										</div>
										<%
										String message = (String) session.getAttribute("errorMessage");
										session.removeAttribute("errorMessage");
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

