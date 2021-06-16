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
final String id = request.getParameter("patientid");
// establish the connection
%>


<div class="container-fluid">

	<!-- Page Heading -->
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">Treatment</h1>
	</div>

	<div class="container">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">

				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">Kindly perform
							all the tests with your doctor and the doctor will input your
							result</h6>
					</div>
					<div class="card-body">

						<div class="row">
							<div class="col-lg-12">
								<div class="p-5">
									<form class="user" action="treatment" method="post">
										<input type="text" id="patientID" name="patientID"
											style="display: none;" value="<%=id%>"> <br />

										<p>Measurements</p>
										<hr class="sidebar-divider my-0">
										<br />
										<div class="form-group">

											<input type="number" step="0.01"
												class="form-control form-control-user" id="height"
												name="height" placeholder="Height (ms)" required>
										</div>

										<div class="form-group">

											<input type="number" step="0.1"
												class="form-control form-control-user" id="weight"
												name="weight" placeholder="Weight (kgs)" required>
										</div>



										<div class="form-group">

											<input type="number" step="0.1"
												class="form-control form-control-user" id="waist"
												name="waist" placeholder="Waist (cms)" required>
										</div>


										<br />
										<hr class="sidebar-divider my-0">
										<br />

										<p>Examination of Feet</p>
										<div class="form-group">
											<select id="sensation" name="sensation"
												class="form-control form-control-dropdown">
												<option value="" disabled selected hidden>Loss of
													Sensation</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>

											</select>
										</div>


										<div class="form-group">
											<select id="infection" name="infection"
												class="form-control form-control-dropdown">
												<option value="" disabled selected hidden>Signs of
													Infection</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>


										<br />
										<hr class="sidebar-divider my-0">
										<br />

										<p>Eye Test</p>
										<div class="form-group">
											<input class="form-control form-control-user" id="acuity"
												name="acuity" placeholder="Fraction for visual acuity test"
												required>
										</div>

										<p>Dilated Eye Exams</p>


										<div class="form-group">
											<select id="vessel" name="vessel"
												class="form-control form-control-dropdown">
												<option value="" disabled selected hidden>Blood
													Vessel Damaged</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>


										<br />
										<hr class="sidebar-divider my-0">
										<br />

										<p>Blood Pressure</p>
										<div class="form-group">

											<input type="number" step="0.1"
												class="form-control form-control-user" id="systolic"
												name="systolic" placeholder="Systolic Blood Pressure (mmHg)"
												required>
										</div>


										<div class="form-group">

											<input type="number" step="0.1"
												class="form-control form-control-user" id="diastolic"
												name="diastolic"
												placeholder="Diastolic Blood Pressure (mmHg)" required>
										</div>


										<br />
										<hr class="sidebar-divider my-0">
										<br />

										<p>Cholesterol Test</p>

										<div class="form-group">

											<input type="number" step="0.1"
												class="form-control form-control-user" id="hdl" name="hdl"
												placeholder="High Density LipoProtein (mmol/L)" required>
										</div>


										<div class="form-group">

											<input type="number" step="0.1"
												class="form-control form-control-user" id="ldl" name="ldl"
												placeholder="Low Density LipoProtein (mmol/L)" required>
										</div>


										<div class="form-group">

											<input type="number" step="0.1"
												class="form-control form-control-user" id="triglycerides"
												name="triglycerides"
												placeholder="Serum Triglycerides (mmol/L)" required>
										</div>

										<div class="form-group">

											<input type="number" step="0.1"
												class="form-control form-control-user" id="cholesterol"
												name="cholesterol"
												placeholder="Serum Total Cholesterol  (mmol/L)" required>
										</div>



										<br />
										<hr class="sidebar-divider my-0">
										<br />

										<p>Urine Test</p>

										<div class="form-group">
											<select id="albuminuria" name="albuminuria"
												class="form-control form-control-dropdown">
												<option value="" disabled selected hidden>Presence
													of Albuminuria</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>

										<div class="form-group">
											<select id="ketones" name="ketones"
												class="form-control form-control-dropdown">
												<option value="" disabled selected hidden>Presence
													of Ketones</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>

										<p>Serum Creatine Test</p>

										<div class="form-group">

											<input type="number" step="0.1"
												class="form-control form-control-user" id="creatine"
												name="creatine"
												placeholder="Results for Creatine Test (mg/dl)" required>
										</div>

										<p>-ve Dipstick Test</p>

										<div class="form-group">

											<input type="number" step="0.1"
												class="form-control form-control-user" id="microalbuminuria"
												name="microalbuminuria"
												placeholder="Results for microalbuminuria (mg/dl)" required>
										</div>


										<br />
										<hr class="sidebar-divider my-0">
										<br />

										<p>Glycosylated Hemoglobin</p>

										<div class="form-group">

											<input type="number" step="0.1"
												class="form-control form-control-user" id="hbalc"
												name="hbalc" placeholder="Glycosylated Hemoglobin (%)"
												required>
										</div>


										<br />
										<hr class="sidebar-divider my-0">
										<br />

										<p>Blood Glucose Levels</p>


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
												name="valueText" placeholder="Value (mmol/1-1)" required>
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

