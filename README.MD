USER AUTH SERVICE Group3 ABSTRACT •	This project provides sign in, login, and logout features. •	A mechanism to allow the user to access protected resources after a successful login. •	An error reporting mechanism.

Git link: https://github.com/sbose1/UserAuthenticationAPI.git

Platform Scenarios:

User authentication mobile applicationusing a REST API built with Node, Express and MongoDB. Implementation scenarios handled:

let a user signup
save user data
let a returning user log in
keep a logged in user’s session alive between page visits
The Authentication Flow The overall flow: •	user data is stored in MongoDB •	CRUD functions are built in an Express API — Create (register), Read (login) •	an Mobile application calls the API and deals with the responses •	the Express API generates a JSON Web Token upon registration or login, and passes this to the Mobile application •	the mobile application stores the JWT in order to maintain the user’s session •	it checks the validity of the JWT when displaying protected views • the mobile application passes the JWT back to Express when calling protected API routes. JWTs are preferred over cookies for maintaining the session state in the browser. Cookies are better for maintaining state when using a server-side application. The REST API

Creating the MongoDB Data Schema with Mongoose There’s a simple user schema defined in /api/models/users.js. Generating a JSON Web Token (JWT) One more thing the Mongoose model needs to be able to do is generate a JWT, so that the API can send it out as a response. A Mongoose method is ideal here too, as it means we can keep the code in one place and call it whenever needed. We’ll need to call it when a user registers and when a user logs in. To create the JWT, we’ll use a module called jsonwebtoken which needs to be installed in the application, so run this on the command line: npm install jsonwebtoken --save Then require this in the users.js model file: var jwt = require('jsonwebtoken'); This module exposes a sign method that we can use to create a JWT, simply passing it the data we want to include in the token, plus a secret that the hashing algorithm will use. The data should be sent as a JavaScript object, and include an expiry date in an exp property. Configure API Endpoints With the API we’ve got two things to do:

make the controllers functional
secure the /api/profile route so that only authenticated users can access it. Code the Register and Login API Controllers
The Register API Controller The register controller needs to do the following:

take the data from the submitted form and create a new Mongoose model instance
call the setPassword method we created earlier to add the salt and the hash to the instance
save the instance as a record to the database
generate a JWT
send the JWT inside the JSON response. The Login API Controller The login controller hands over pretty much all control to Passport, although you could (and should) add some validation beforehand to check that the required fields have been sent.
Securing an API Route The final thing to do in the back end is make sure that only authenticated users can access the /api/profile route. The way to validate a request is to ensure that the JWT sent with it is genuine, by using the secret again. This is why you should keep it a secret and not in the code.

Design Objectives and Modules: 	User Registration- To enable a user be part of the shared community of Makers net 	User Login- Enables validation and user login, as per existing user profiles 	User profile- User landing page with User Profile picture, posts, About section and image gallery.

Architecture overview: A RESTful API accepting JSON data format client requests. To enable all the capability services of the system and subsystem(s) the architecture for the project includes roughly MVC (Model View Controller) layered components working cohesively to provide expected functionalities and enable navigation within the website. Each View page of the website is backed by one or more functions of the logical tier of the Web application server. The modules consist of the Servlet Controller(s) implement handler functions for orchestration and implementation of intended functionalities and business logic The object state is managed using Model classes.

Technology stack used: RESTful API UI/Frontend: Mobile App Middle tier: Node and Express Backend Database: supported using MongoDB Data transition format: JSON
