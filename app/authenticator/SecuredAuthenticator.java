package authenticator;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class SecuredAuthenticator extends Security.Authenticator {

	@Override
	public String getUsername(Context context) {
//		String token = context.session().get("token");
		String token = context.request().getHeader("token");
		return token;
	}

	@Override
	public Result onUnauthorized(Context arg0) {
		return unauthorized();
	}
}