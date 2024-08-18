import {PassedInitialConfig} from "angular-auth-oidc-client";
import {AUTH_URL, CLIENT_ID} from "../constants";

export const authConfig: PassedInitialConfig = {
  config: {
    authority: `${AUTH_URL}`,
    redirectUrl: window.location.origin,
    postLogoutRedirectUri: window.location.origin,
    clientId: `${CLIENT_ID}`,
    scope: 'openid profile offline_access',
    responseType: 'code',
    silentRenew: true,
    useRefreshToken: true,
    renewTimeBeforeTokenExpiresInSeconds: 30,
  }
}
