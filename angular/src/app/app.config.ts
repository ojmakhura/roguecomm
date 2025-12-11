import { ApplicationConfig, importProvidersFrom, inject } from '@angular/core';
import { provideRouter, RouteReuseStrategy, withHashLocation } from '@angular/router';

import { routes } from './app.routes';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideHttpClient, withFetch, withInterceptors, withInterceptorsFromDi, HttpClient } from '@angular/common/http';
import { CUSTOM_DATE_FORMATS } from './@shared/custom-date-formats';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { RouteReusableStrategy } from './@core/route-reusable-strategy';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { apiPrefixInterceptor } from './@core/http/api-prefix.interceptor';
import { errorHandlerInterceptor } from './@core/http/error-handler.interceptor';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { provideToastr } from 'ngx-toastr';
import { AppEnvStore } from './store/app-env.state';
import {
  AutoRefreshTokenService,
  createInterceptorCondition,
  INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
  IncludeBearerTokenCondition,
  includeBearerTokenInterceptor,
  provideKeycloak,
  UserActivityService,
  withAutoRefreshToken,
} from 'keycloak-angular';

export class CustomTranslateLoader implements TranslateLoader {
  constructor(private http: HttpClient) {}

  getTranslation(lang: string): Observable<any> {
    return this.http.get(`/i18n/${lang}.json`).pipe(
      catchError(() => of({}))
    );
  }
}

export function initFactory() {
  const envStore = inject(AppEnvStore);

  return async () => { };
}

export const provideKeycloakAndInterceptor = (env: any) => {
  const urlConditions = [
    createInterceptorCondition<IncludeBearerTokenCondition>({
      // eslint-disable-next-line no-useless-escape
      urlPattern: new RegExp(`^${env.apiUrl}(\/.*)?$`, 'i'),
      bearerPrefix: 'Bearer',
    }),
    createInterceptorCondition<IncludeBearerTokenCondition>({
      // eslint-disable-next-line no-useless-escape
      urlPattern: new RegExp(`^${env.authDomain}(\/.*)?$`, 'i'),
      bearerPrefix: 'Bearer',
    }),
    // you can add more interceptors in this array...
  ];

  // in our case, we put the identity configuration in the environment files
  // const { identityServerUrl, clientId, realm } = environment.auth;

  return [
    provideKeycloak({
      config: {
        url: env.authDomain,
        realm: env.realm,
        clientId: env.clientId,
      },
      initOptions: {
        onLoad: 'check-sso',
        checkLoginIframe: true,
      },
      features: [
        withAutoRefreshToken({
          sessionTimeout: 300000,
          onInactivityTimeout: 'logout',
        }),
      ],
      providers: [AutoRefreshTokenService, UserActivityService],
    }),
    { provide: INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG, useValue: urlConditions },
  ];
};

export function HttpLoaderFactory(http: HttpClient) {
  return new CustomTranslateLoader(http);
}

export const appConfig = (env: any) => {
  return {
    providers: [
      provideRouter(routes, withHashLocation()),
      provideKeycloakAndInterceptor(env),
      provideAnimations(),
      provideHttpClient(
        withFetch(),
        withInterceptorsFromDi(),
        withInterceptors([
          apiPrefixInterceptor, 
          errorHandlerInterceptor, 
        ]),
      ),
      provideToastr({
        timeOut: 3000,
        positionClass: 'toast-top-right',
        preventDuplicates: true,
        progressBar: true,
        closeButton: true,
        newestOnTop: true,
        enableHtml: true,
        tapToDismiss: true,
        maxOpened: 5,
        autoDismiss: true
      }),
      importProvidersFrom(
        TranslateModule.forRoot({
          defaultLanguage: 'en',
          loader: {
            provide: TranslateLoader,
            useFactory: HttpLoaderFactory,
            deps: [HttpClient]
          }
        }),
      ),
      { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'outline' } },
      {
        provide: RouteReuseStrategy,
        useClass: RouteReusableStrategy,
      },
      { provide: MAT_DATE_FORMATS, useValue: CUSTOM_DATE_FORMATS },
    ],
  } as ApplicationConfig;
};