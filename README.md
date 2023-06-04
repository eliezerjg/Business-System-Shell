## System Core - Multiplatform Monolith Backend


<p>
This project includes:
</p>

<h4>Properties:</h4>
<li>Production and Development profile</li>
<BR>


<h4>Spring Security Endpoints Protection:</h4>
<li>Public Registration and login</li>
<li>Customer protected endpoint by authentication</li>
<li>Cloudflare DNS protection filter</li>

<BR>


<h4>Tests:</h4>
<li>Junit 4 Unit Tests</li>
<li>Mockito for mocking</li>
<BR>

<h4>Authentication:</h4>
<li>JWT Bearer Token</li>
<BR>

<h4>Exception Handling:</h4>
<li>Default treatments for exception handling (with http status codes)</li>
<BR>

<h4>Documentaion:</h4>
<li> Swagger UI / Spring DOC. </li>
<BR>

<h4>Steps do getstarted:</h4>
<li>1 - Change the default private token in Auth for multiple instances.</li>
<li>2 - Protect your FrontEnd with Cloudflare DNS.</li>
<li>3 - Follow the Structures folder and add your business logic.</li>
<li>4 - Feel free to use this version for personal use or open source projects and contribute for this project.</li>
<li>5 - Remember to set up the users Role for your business logic.</li>
<BR>

<h4>For (FrontEnd / Mobile) Developers:</h4>
<p> So we have this flux to implementation:</p>
<p>1 - Customer (Register / Login) ==> Get JWT TOKEN</p>
<p>2 - Simulate the sessions storing this token until receive unauthorized</p>
<p>3 - If unauthorized => FORCE LOGIN AGAIN => SAVE THE JWT</p>
<BR>

<h4>For better use:</h4>
<li>1 - Run the tests and give a look at Postman Collection.</li>
<li>2 - SWAGGER DOCUMENTATION: https://localhost:8080/swagger-ui/index.html  .</li>






