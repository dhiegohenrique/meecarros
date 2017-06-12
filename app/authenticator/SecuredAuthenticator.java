package authenticator;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class SecuredAuthenticator extends Security.Authenticator {

	@Override
	public String getUsername(Context context) {
		return context.request().getHeader("token");
	}

	@Override
	public Result onUnauthorized(Context arg0) {
		return unauthorized("Acesso não autorizado. Informe um token válido.");
	}
}