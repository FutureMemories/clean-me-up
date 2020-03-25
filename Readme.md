## Clean-me-up

### Changelog

* EmailApi (Controller):
    * Added validation.
    * Now only accepts POST-requests, changed from accepting any type of HTTP-method.
    * Added basic test.
* EmailHandler:
    * Interface extracted and implementation renamed to EmailHandlerImpl.
    * Username and passwords are now configured through application.properties.
    * Validation and return value removed: This is a bit contentious but my reasoning was that the input data was validated at the controller layer anyhow, and I wanted to simplify the code.
    * Logging removed. Was leaking sensitive data.
* Configuration:
* Misc:
    * Added custom exception handler to turn validation errors into 400: Bad Request-responses rather than
the default 500: Internal Server Error-responses Spring wanted to use.



### Stuff I wish I had time to fix
* Spring always responds with application/json when a required parameter is missing completely. I was hoping to have this return text/plain just like the actual parameter validators, but for some reason the custom error handler does not seem to apply here.
* More tests. The validation is covered by a basic unit-test but it would be nice to have more tests.
* I decided to NOT test the EmailHandlerImpl as it's now only a thin wrapper around SmtpHandler, but if I was to add back some validation logic to it I would have it return an instance of a custom error type. 
