<!DOCTYPE html>
<html>
<head>
<title>Google Calendar API Quickstart</title>
<meta charset='utf-8' />
</head>
<body>
	<p>Google Calendar API Quickstart</p>
	
	<input type="text" id="email">
<!-- 	<input type="password"> -->

	<pre id="content"></pre>

	<script type="text/javascript">
    	var provider = new firebase.auth.GoogleAuthProvider();
    	provider.addScope("https://www.googleapis.com/auth/calendar");
    	
    	firebase.auth().useDeviceLanguage();
    	
    	firebase.auth().signInWithRedirect(provider).then(function(result) {
    		// This gives you a Google Access Token. You can use it to access the Google API.
    		var token = result.credential.accessToken;
    		// The signed-in user info.
    		var user = result.user;
    		// ...
    	}).catch(function(error) {
    		// Handle Errors here.
    		var errorCode = error.code;
    		var errorMessage = error.message;
    		// The email of the user's account used.
    		var email = error.email;
    		// The firebase.auth.AuthCredential type that was used.
    		var credential = error.credential;
    		// ...
    	});
    	
    	firebase.auth().getRedirectResult().then(function(result) {
    		if (result.credential) {
    		// This gives you a Google Access Token. You can use it to access the Google API.
    			var token = result.credential.accessToken;
    		    // ...
    		}
    		// The signed-in user info.
    		var user = result.user;
    	}).catch(function(error) {
    		// Handle Errors here.
    		var errorCode = error.code;
    		var errorMessage = error.message;
    		// The email of the user's account used.
    		var email = error.email;
    		// The firebase.auth.AuthCredential type that was used.
    		var credential = error.credential;
    		// ...
    	});
    

    </script>

</body>
</html>