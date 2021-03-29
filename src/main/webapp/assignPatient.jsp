<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ include file="masterDoc.jsp"%>

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


<%
con = DriverManager.getConnection(url, user, password);
try {
	Statement st = con.createStatement();

	String query = "SELECT userid,surname + ' ' + lastname + ' ' + ISNULL(middlename,'') as name,	gender FROM OntoDiabetes_User JOIN OntoDiabetes_PatientDetails on OntoDiabetes_PatientDetails.userid = OntoDiabetes_User.id  where type='Patient'  and OntoDiabetes_User.id not in (SELECT patient_id FROM [OntoDiabetes_Doctor_Patient])";

	// send and execute SQL query in Database software
	ResultSet rs = st.executeQuery(query);
%>


<div class="container-fluid">

	<!-- Page Heading -->
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">Patient Allocation</h1>
	</div>

	<div class="container">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">

				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">Assign yourself
							patients</h6>
					</div>
					<div class="card-body">

						<div class="row">
							<div class="col-lg-12">
								<div class="p-5">
									<form class="user" action="assignPatient" method="post">

										<div class="col-md-12 row">
											<%
											while (rs.next()) {
											%>


											<div class="col-xl-6 col-md-6 mb-4">
												<div class="card border-left-primary shadow h-100 py-2">
													<div class="card-body">
														<div class="row no-gutters align-items-center">
															<div class="col mr-2">
																<div
																	class="text-xs font-weight-bold text-primary text-uppercase mb-1">
																	Patient</div>
																<div class="h5 mb-0 font-weight-bold text-gray-800">
																	<input type="checkbox" name="patients"
																		value="<%=rs.getString("userid")%>">
																	<%=rs.getString("name")%></div>
															</div>
															<div class="col-auto">
																<i
																	class="fas fa-<%=rs.getString("gender").toLowerCase()%> fa-2x text-gray-300"></i>
															</div>
														</div>
													</div>
												</div>
											</div>


											<%
											}
											} catch (Exception e) {
											%><%=e%>
											<%
											}

											con.close();
											%>

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
											class="btn btn-primary btn-user btn-block"
											value="Add Patient" />
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



<%@ include file="masterDoc2.jsp"%>
