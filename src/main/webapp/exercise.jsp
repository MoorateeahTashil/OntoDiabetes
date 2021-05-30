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
		<h1 class="h3 mb-0 text-gray-800">Exercise</h1>
	</div>

	<div class="container">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">

				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">Exercise
							Guidelines</h6>
					</div>
					<div class="card-body">

						<div class="row">
							<div class="col-lg-12">
								<div class="p-5">
									Exercise may improve insulin sensitivity and assist in
									diminishing elevated blood glucose level into the normal range.
									<br /> The diabetic health care team will be required to
									analyse the risks and benefits of exercise.<br /> Exercise
									includes walking, brisk walking, jogging, cycling, swimming,
									dancing.<br /> <br /> <strong>Preparing for exercise</strong> <br /> <br /> 
									<ul>
										<li>Young individual in good metabolic control can safely
											participate in most activities.</li>
										<li>Middle age and older individual to be encouraged to
											be physically active.</li>
										<li>Need for proper warm-up and cool-down period.</li>
										<li>Warm-up: 5-10 minutes of aerobic activity (walking,
											cycling etc.) at a low-intensity level.</li>
										<li>After warm-up, muscles to be gently stretched for
											another 5-10 minutes.</li>
										<li>Cool-down period to last 5-10 minutes and gradually
											to bring heart rate to pre-exercise level.</li>
										<li>Use silica gel or air midsoles, as well as polyester
											or cotton polyester socks to prevent blisters and keep the
											feet dry.</li>
										<li>Proper footwear is essential.</li>
										<li>Self monitoring for blisters and other potential
											damage to feet before and after exercise.</li>
										<li>Need for proper hydration before, during and after
											exercise.</li>

									</ul>
									<br /> <br /> <strong>Benefits of exercise</strong> <br /> <br /> 
									<ul>
										<li>Substantial.</li>
										<li>Consistent beneficial effect of regular exercise
											training on carbohydrate metabolism and insulin sensitivity.</li>
										<li>Improvement in insulin sensitivity and prevent
											cardiovascular disease.</li>
										<li>Effective in reducing levels of triglyceride rich
											VLDL.</li>
										<li>Improvement in levels of HDL.</li>
										<li>Reduction of blood pressure level, specially in
											hyperinsulinemic subjects.</li>
										<li>Exercise enhances weight loss.</li>
										<li>Loss of intra-abdominal fat.</li>
										<li>Exercise helpful in the prevention or delay in the
											onset of type 2 diabetes.</li>
										<li>Protects from osteoporosis in post menopausal women.</li>
										<li>In the elderly, progressive decrease in fitness and
											muscle mass and strength with ageing is in part preventable
											by maintaining regular exercise.</li>
										<li>Promoting exercise as a vital component of the
											prevention, as well as management of type 2 diabetes must be
											viewed as a high priority.</li>
									</ul>
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

