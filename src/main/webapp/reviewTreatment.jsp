<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="masterDoc.jsp"%>
<%@ page import="OntoDiabetes.DatabaseConnection"%>

<%
if (session.getAttribute("userID") == null) {
	String redirectURL = "sessionExpired.jsp";
	response.sendRedirect(redirectURL);
} else {
	if (session.getAttribute("userType").toString().toLowerCase().equals("patient")) {

		String redirectURL = "noAccess.jsp";
		response.sendRedirect(redirectURL);

	}
}
%>



<%
final Connection con;
Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
// variables
DatabaseConnection db = new DatabaseConnection();

final String url = db.getUrlConnection();
final String user = db.getUser();
final String password = db.getPassword();

String id = "";
String name = "";
String gender = "";
String age = "";
String bmi = "";
String test ="";
String testResult = "";


// establish the connection
%>
<%
con = DriverManager.getConnection(url, user, password);
try {
	Statement st = con.createStatement();
	Statement st1 = con.createStatement();

	String query = "select userid,cast(round(bmi,2) as numeric(36,2)) as bmi,surname,lastname,middlename,gender,age from OntoDiabetes_PatientDetails  where userid="
	+ request.getParameter("patientid") + ";";
	String query1 = "select * from [OntoDiabetes_TestResults] where [patientId]=" + request.getParameter("patientid")
	+ ";";
	// send and execute SQL query in Database software
	ResultSet rs = st.executeQuery(query);

	while (rs.next()) {
		id = rs.getString("userid");
		name = rs.getString("surname") + " " + rs.getString("lastname") + " " + rs.getString("middlename");
		gender = rs.getString("gender");
		age = rs.getString("age");
		bmi = rs.getString("bmi");

	}



} catch (Exception e) {

}

con.close();
%>

<body>
	<!-- Page Heading -->
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">Patient Treatment</h1>
	</div>
	<div class="container">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">

				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">Review Patient
							Treatment</h6>
					</div>
					<div class="card-body">

						<div class="row">
							<div class="offset-lg-1 col-lg-9">
								<div class="p-5">
									<form class="user" action="reviewTreatment" method="post">
										<input type="text" id="patientID" name="patientID"
											style="display: none;" value="<%=id%>">


										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Name : </b>
												</div>
												<div class="col-md-6"><%=name%></div>
											</div>

										</div>


										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Gender : </b>
												</div>
												<div class="col-md-6"><%=gender%></div>
											</div>

										</div>



										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Age : </b>
												</div>
												<div class="col-md-6"><%=age%></div>
											</div>

										</div>



										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>BMI : </b>
												</div>
												<div class="col-md-6"><%=bmi%></div>
											</div>

										</div>
										
										
								


										<%
										String message = (String) session.getAttribute("errorMessage");
										session.removeAttribute("errorMessage");
										%>


										<%
										if (message == null || message.isEmpty()) {
										} else {

											if (message == "NoSymptoms") {
										%>
										<div class="card mb-4">
											<div class="card-headerss">No Symptoms</div>
											<div class="card-body">
												<p ID="errorMessageLabel" />
												Patient has <b>NOT</b> been diagnosed with diabetes and neither with other complications.
											</div>
										</div>
										<%
										} else {
										%>

										<div class="card mb-4">
											<div class="card-headers">Results</div>
											<div class="card-body">
												<p ID="errorMessageLabel" /><%=message%>
											</div>
										</div>

										<%
										}
										}
										%>


										<input type="submit" ID="buttonSave"
											class="btn btn-primary btn-user btn-block"
											value="Automatic Processing" />
									</form>
									<br />
									<%
									if (message == null || message.isEmpty()) {
									} else {
									%>
									<form class="user" action="reviewTest" method="get">
									<input type="text" id="patient_ID" name="patient_ID"
											style="display: none;" value="<%=id%>">
								<input type="text" id="messages" name="messages"
											style="display: none;" value="<%=message%>">

										<input type="submit" ID="buttonSave1"
											class="btn btn-primary btn-user btn-block" value="Notify the patient" />
									</form>
									<%
									}
									%>
								</div>
							</div>
						</div>

					</div>
				</div>


			</div>
		</div>

	</div>
	<%@ include file="masterDoc2.jsp"%>