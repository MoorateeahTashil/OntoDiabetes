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
String tension = "";
String fatigue = "";
String hunger = "";
String lossweight = "";
String vision = "";
String tingling = "";
String patches = "";
String infections = "";
String healing = "";
String relative = "";
String polyuria = "";
String polydipsia = "";
String adverse = "";
String miscarriage = "";
String stillbirth = "";

// establish the connection
%>
<%
con = DriverManager.getConnection(url, user, password);
try {
	Statement st = con.createStatement();
	Statement st1 = con.createStatement();

	String query = "select userid,cast(round(bmi,2) as numeric(36,2)) as bmi,surname,lastname,middlename,gender,age from OntoDiabetes_PatientDetails  where userid="
	+ request.getParameter("patientid") + ";";
	String query1 = "select * from [OntoDiabetes_Symptoms]  where patient_id=" + request.getParameter("patientid")
	+ ";";
	// send and execute SQL query in Database software
	ResultSet rs = st.executeQuery(query);
	ResultSet rs1 = st1.executeQuery(query1);

	while (rs.next()) {
		id = rs.getString("userid");
		name = rs.getString("surname") + " " + rs.getString("lastname") + " " + rs.getString("middlename");
		gender = rs.getString("gender");
		age = rs.getString("age");
		bmi = rs.getString("bmi");

	}

	while (rs1.next()) {

		tension = rs1.getString("tension");
		fatigue = rs1.getString("fatigue");
		hunger = rs1.getString("hunger");
		lossweight = rs1.getString("lossweight");
		vision = rs1.getString("vision");
		tingling = rs1.getString("tingling");
		patches = rs1.getString("patches");
		infections = rs1.getString("infections");
		healing = rs1.getString("healing");
		relative = rs1.getString("relative");
		polyuria = rs1.getString("polyuria");
		polydipsia = rs1.getString("polydipsia");
		adverse = rs1.getString("adverse");
		miscarriage = rs1.getString("miscarriage");
		stillbirth = rs1.getString("stillbirth");

	}

} catch (Exception e) {

}

con.close();
%>

<body>
	<!-- Page Heading -->
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">Patient Symptoms</h1>
	</div>
	<div class="container">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">

				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">Review Patient
							Symptoms</h6>
					</div>
					<div class="card-body">

						<div class="row">
							<div class="offset-lg-1 col-lg-9">
								<div class="p-5">
									<form class="user" action="reviewPatientSymptoms" method="post">
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


										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Hypertension : </b>
												</div>
												<div class="col-md-6"><%=tension%></div>
											</div>

										</div>

										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Unusual fatigue : </b>
												</div>
												<div class="col-md-6"><%=fatigue%></div>
											</div>

										</div>

										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Unusual hunger: </b>
												</div>
												<div class="col-md-6"><%=hunger%></div>
											</div>

										</div>

										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Unexpected Weight Lost : </b>
												</div>
												<div class="col-md-6"><%=lossweight%></div>
											</div>

										</div>

										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Blurred Vision : </b>
												</div>
												<div class="col-md-6"><%=vision%></div>
											</div>

										</div>

										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Tingling in your hands or feet : </b>
												</div>
												<div class="col-md-6"><%=tingling%></div>
											</div>

										</div>

										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Patches of Dark Skin : </b>
												</div>
												<div class="col-md-6"><%=patches%></div>
											</div>

										</div>


										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Prone to Infections : </b>
												</div>
												<div class="col-md-6"><%=infections%></div>
											</div>

										</div>


										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Slow healing sores and wound : </b>
												</div>
												<div class="col-md-6"><%=healing%></div>
											</div>

										</div>


										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>First Degree Relative : </b>
												</div>
												<div class="col-md-6"><%=relative%></div>
											</div>

										</div>


										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Polyuria : </b>
												</div>
												<div class="col-md-6"><%=polyuria%></div>
											</div>

										</div>

										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Polydipsia : </b>
												</div>
												<div class="col-md-6"><%=polydipsia%></div>
											</div>

										</div>




										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Adverse during Pregnancy : </b>
												</div>
												<div class="col-md-6"><%=adverse%></div>
											</div>

										</div>

										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Miscarraige during Pregnancy : </b>
												</div>
												<div class="col-md-6"><%=miscarriage%></div>
											</div>

										</div>

										<div class="form-group">
											<div class="col-md-12 row">
												<div class="col-md-6">
													<b>Stillbirth during Pregnancy : </b>
												</div>
												<div class="col-md-6"><%=stillbirth%></div>
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
												Patient has no symptoms related to diabetes.
											</div>
										</div>
										<%
										} else {
										%>

										<div class="card mb-4">
											<div class="card-headers">Reasons why patient need to
												take plasma glucose test (FPG)</div>
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
									<form class="user" action="doTest" method="post">
									<input type="text" id="patient_ID" name="patient_ID"
											style="display: none;" value="<%=id%>">
										<div class="form-group">
											<select id="Tests" name="Tests"
												class="form-control form-control-dropdown" required>
												<option value="" disabled selected hidden>Should
													the patient do plasma glucose test (FPG)?</option>
												<option value="Yes">Yes</option>
												<option value="No">No</option>
											</select>
										</div>

										<input type="submit" ID="buttonSave1"
											class="btn btn-primary btn-user btn-block" value="Save" />
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