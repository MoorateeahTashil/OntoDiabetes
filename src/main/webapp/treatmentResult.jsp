<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="masterDoc.jsp"%>
<%@ page import="java.io.File"%>
<%@ page import="OntoDiabetes.DatabaseConnection"%>
<%@ page import="org.swrlapi.sqwrl.SQWRLResult"%>
<%@ page import="org.semanticweb.owlapi.model.OWLClass"%>
<%@ page import="org.semanticweb.owlapi.model.OWLClassAssertionAxiom"%>
<%@ page import="org.semanticweb.owlapi.model.OWLDataFactory"%>
<%@ page import="org.semanticweb.owlapi.model.OWLDataProperty"%>
<%@ page import="org.semanticweb.owlapi.apibinding.OWLManager"%>
<%@ page
	import="org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom"%>
<%@ page import="org.semanticweb.owlapi.model.OWLNamedIndividual"%>
<%@ page import="org.semanticweb.owlapi.model.OWLOntology"%>
<%@ page
	import="org.semanticweb.owlapi.model.OWLOntologyCreationException"%>
<%@ page import="org.semanticweb.owlapi.model.OWLOntologyManager"%>
<%@ page
	import="org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterPreferences"%>
<%@ page
	import="org.semanticweb.owlapi.model.OWLOntologyStorageException"%>
<%@ page import="org.semanticweb.owlapi.model.PrefixManager"%>
<%@ page import="org.semanticweb.owlapi.util.DefaultPrefixManager"%>
<%@ page import="org.swrlapi.core.SWRLAPIRule"%>
<%@ page import="org.swrlapi.core.SWRLRuleEngine"%>
<%@ page import="org.swrlapi.exceptions.SWRLBuiltInException"%>
<%@ page import="org.swrlapi.factory.SWRLAPIFactory"%>
<%@ page import="org.swrlapi.parser.SWRLParseException"%>
<%@ page import="org.swrlapi.sqwrl.SQWRLQueryEngine"%>
<%@ page import="org.swrlapi.sqwrl.exceptions.SQWRLException"%>
<%
if (session.getAttribute("userID") == null) {
	String redirectURL = "sessionExpired.jsp";
	response.sendRedirect(redirectURL);
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
String test = "";
String testResult = "";

// establish the connection
%>
<%
con = DriverManager.getConnection(url, user, password);
try {
	Statement st = con.createStatement();
	Statement st1 = con.createStatement();

	String query = "select userid,cast(round(bmi,2) as numeric(36,2)) as bmi,surname,lastname,middlename,gender,age from OntoDiabetes_PatientDetails  where userid="
	+ session.getAttribute("userID") + ";";
	String query1 = "select * from [OntoDiabetes_TestResults] where [patientId]=" + session.getAttribute("userID")
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
		<h1 class="h3 mb-0 text-gray-800">Patient Treatment Results</h1>
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
									<form class="user" action="treatmentResult" method="post">
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

										<input type="submit" ID="buttonSave"
											class="btn btn-primary btn-user btn-block"
											value="Process Results" />
									</form>




								</div>
							</div>
						</div>

						<div class="col-md-12 row">

						<%
						String uncontrolled = (String) session.getAttribute("uncontrolled");
						String acceptable = (String) session.getAttribute("acceptable");
						String optimal = (String) session.getAttribute("optimal");
						String exercise = (String) session.getAttribute("exercise");
						String complications = (String) session.getAttribute("complications");
						String medicines = (String) session.getAttribute("medicines");

						session.removeAttribute("uncontrolled");
						session.removeAttribute("acceptable");
						session.removeAttribute("optimal");
						session.removeAttribute("exercise");
						session.removeAttribute("complications");
						session.removeAttribute("medicines");

						%>
						<%
						if (acceptable == null || acceptable.isEmpty()) {
						} else {
						%>

							<div class="col-xl-6 col-md-6 mb-4">
								<div class="card border-left-primary shadow h-100 py-2">
									<div class="card-body">
										<div class="row no-gutters align-items-center">
											<div class="col mr-2">
												<div
													class="text-xs font-weight-bold text-primary text-uppercase mb-1">
													Acceptable Conditions</div>
												<div class="h5 mb-0 font-weight-bold text-gray-800">
													<p><%=acceptable%></p>
												</div>
											</div>
											<div class="col-auto">
												<i class="fas fa-band-aid fa-2x text-gray-300"></i>
											</div>
										</div>
									</div>
								</div>
							</div>
						<%
						}
						%>

						<%
						if (optimal == null || optimal.isEmpty()) {
						} else {
						%>

							<div class="col-xl-6 col-md-6 mb-4">
								<div class="card border-left-primary shadow h-100 py-2">
									<div class="card-body">
										<div class="row no-gutters align-items-center">
											<div class="col mr-2">
												<div
													class="text-xs font-weight-bold text-primary text-uppercase mb-1">
													Optimal Conditions</div>
												<div class="h5 mb-0 font-weight-bold text-gray-800">
													<p><%=optimal%></p>
												</div>
											</div>
											<div class="col-auto">
												<i class="fas fa-hand-holding-medical fa-2x text-gray-300"></i>
											</div>
										</div>
									</div>
								</div>
							</div>
						<%
						}
						%>

						<%
						if (uncontrolled == null || uncontrolled.isEmpty()) {
						} else {
						%>

							<div class="col-xl-6 col-md-6 mb-4">
								<div class="card border-left-primary shadow h-100 py-2">
									<div class="card-body">
										<div class="row no-gutters align-items-center">
											<div class="col mr-2">
												<div
													class="text-xs font-weight-bold text-primary text-uppercase mb-1">
													Uncontrollable Conditions</div>
												<div class="h5 mb-0 font-weight-bold text-gray-800">
													<p><%=uncontrolled%></p>
												</div>
											</div>
											<div class="col-auto">
												<i class="fas fa-viruses fa-2x text-gray-300"></i>
											</div>
										</div>
									</div>
								</div>
							</div>
						<%
						}
						%>
						
						
									<%
						if (exercise == null || exercise.isEmpty()) {
						} else {
						%>

							<div class="col-xl-6 col-md-6 mb-4">
								<div class="card border-left-primary shadow h-100 py-2">
									<div class="card-body">
										<div class="row no-gutters align-items-center">
											<div class="col mr-2">
												<div
													class="text-xs font-weight-bold text-primary text-uppercase mb-1">
													Treatment</div>
												<div class="h5 mb-0 font-weight-bold text-gray-800">
													<p><%=exercise%></p>
												</div>
											</div>
											<div class="col-auto">
												<i class="fas fa-prescription-bottle fa-2x text-gray-300"></i>
											</div>
										</div>
									</div>
								</div>
							</div>
						<%
						}
						%>

						<%
						if (complications == null || complications.isEmpty()) {
						} else {
						%>

							<div class="col-xl-6 col-md-6 mb-4">
								<div class="card border-left-primary shadow h-100 py-2">
									<div class="card-body">
										<div class="row no-gutters align-items-center">
											<div class="col mr-2">
												<div
													class="text-xs font-weight-bold text-primary text-uppercase mb-1">
													Complications</div>
												<div class="h5 mb-0 font-weight-bold text-gray-800">
													<p><%=complications%></p>
												</div>
											</div>
											<div class="col-auto">
												<i class="fas fa-head-side-virus fa-2x text-gray-300"></i>
											</div>
										</div>
									</div>
								</div>
							</div>
						<%
						}
						%>
						
						
						
						<%
						if (medicines == null || medicines.isEmpty()) {
						} else {
						%>

							<div class="col-xl-6 col-md-6 mb-4">
								<div class="card border-left-primary shadow h-100 py-2">
									<div class="card-body">
										<div class="row no-gutters align-items-center">
											<div class="col mr-2">
												<div
													class="text-xs font-weight-bold text-primary text-uppercase mb-1">
													Medicines</div>
												<div class="h5 mb-0 font-weight-bold text-gray-800">
													<p><%=medicines%></p>
												</div>
											</div>
											<div class="col-auto">
												<i class="fas fa-clinic-medical fa-2x text-gray-300"></i>
											</div>
										</div>
									</div>
								</div>
							</div>
						<%
						}
						%>
						</div>
					</div>
				</div>


			</div>
		</div>

	</div>
	<%@ include file="masterDoc2.jsp"%>