
import { inject } from '@angular/core';
import { patchState, signalStore, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { switchMap } from 'rxjs';
import { tapResponse } from '@ngrx/operators';
import { AppState } from '@store/app-state';
import { SearchObject } from '@models/search-object';
import { Page } from '@models/page.model';
import { SessionDTO } from '@models/bw/co/roguesystems/comm/session/session-dto';
import { SessionApi } from '@services/bw/co/roguesystems/comm/session/session-api';

export type SessionApiState = AppState<any, any> & {};

const initialState: SessionApiState = {
  data: null,
  dataList: [],
  dataPage: new Page<any>(),
  searchCriteria: new SearchObject<any>(),
  error: null,
  loading: false,
  success: false,
  messages: [],
  loaderMessage: ''
};

export const SessionApiStore = signalStore(
  { providedIn: 'root' },
  withState(initialState),
  withMethods((store: any) => {
    const sessionApi = inject(SessionApi);
    return {
      reset: () => {
        patchState(store, initialState);
      },
      findById: rxMethod<{id: string | any }>(
        switchMap((data: any) => {
          patchState(store, { loading: true, loaderMessage: 'Loading ...' });
          return sessionApi.findById(data.id, ).pipe(
            tapResponse({
              next: (data: SessionDTO | any) => {
                //patchState(
                  //store, 
                  // { 
                  //    data, 
                  //    loading: false, 
                  //    error: false,
                  //    success: true, 
                  //    messages: [] 
                  //}
                //);
              },
              error: (error: any) => {
                patchState(
                  store, { 
                    error, 
                    loading: false, 
                    success: false,
                    messages: [error?.error ? error.error : error] 
                  }
                );
              },
            }),
          );
        }),
      ),
      findByReference: rxMethod<{reference: string | any }>(
        switchMap((data: any) => {
          patchState(store, { loading: true, loaderMessage: 'Loading ...' });
          return sessionApi.findByReference(data.reference, ).pipe(
            tapResponse({
              next: (data: SessionDTO | any) => {
                //patchState(
                  //store, 
                  // { 
                  //    data, 
                  //    loading: false, 
                  //    error: false,
                  //    success: true, 
                  //    messages: [] 
                  //}
                //);
              },
              error: (error: any) => {
                patchState(
                  store, { 
                    error, 
                    loading: false, 
                    success: false,
                    messages: [error?.error ? error.error : error] 
                  }
                );
              },
            }),
          );
        }),
      ),
      getAll: rxMethod<void>(
        switchMap(() => {
          patchState(store, { loading: true, loaderMessage: 'Loading ...' });
          return sessionApi.getAll().pipe(
            tapResponse({
              next: (data: SessionDTO[] | any[]) => {
                //patchState(
                  //store, 
                  // { 
                  //    data, 
                  //    loading: false, 
                  //    error: false,
                  //    success: true, 
                  //    messages: [] 
                  //}
                //);
              },
              error: (error: any) => {
                patchState(
                  store, { 
                    error, 
                    loading: false, 
                    success: false,
                    messages: [error?.error ? error.error : error] 
                  }
                );
              },
            }),
          );
        }),
      ),
      getAllPaged: rxMethod<{pageNumber: number | any , pageSize: number | any }>(
        switchMap((data: any) => {
          patchState(store, { loading: true, loaderMessage: 'Loading ...' });
          return sessionApi.getAllPaged(data.pageNumber, data.pageSize, ).pipe(
            tapResponse({
              next: (data: Page<SessionDTO> | any) => {
                //patchState(
                  //store, 
                  // { 
                  //    data, 
                  //    loading: false, 
                  //    error: false,
                  //    success: true, 
                  //    messages: [] 
                  //}
                //);
              },
              error: (error: any) => {
                patchState(
                  store, { 
                    error, 
                    loading: false, 
                    success: false,
                    messages: [error?.error ? error.error : error] 
                  }
                );
              },
            }),
          );
        }),
      ),
      getBySurvey: rxMethod<{surveyId: string | any }>(
        switchMap((data: any) => {
          patchState(store, { loading: true, loaderMessage: 'Loading ...' });
          return sessionApi.getBySurvey(data.surveyId, ).pipe(
            tapResponse({
              next: (data: SessionDTO[] | any[]) => {
                //patchState(
                  //store, 
                  // { 
                  //    data, 
                  //    loading: false, 
                  //    error: false,
                  //    success: true, 
                  //    messages: [] 
                  //}
                //);
              },
              error: (error: any) => {
                patchState(
                  store, { 
                    error, 
                    loading: false, 
                    success: false,
                    messages: [error?.error ? error.error : error] 
                  }
                );
              },
            }),
          );
        }),
      ),
      getBySurveyPaged: rxMethod<{surveyId: string | any , pageNumber: number | any , pageSize: number | any }>(
        switchMap((data: any) => {
          patchState(store, { loading: true, loaderMessage: 'Loading ...' });
          return sessionApi.getBySurveyPaged(data.surveyId, data.pageNumber, data.pageSize, ).pipe(
            tapResponse({
              next: (data: Page<SessionDTO> | any) => {
                //patchState(
                  //store, 
                  // { 
                  //    data, 
                  //    loading: false, 
                  //    error: false,
                  //    success: true, 
                  //    messages: [] 
                  //}
                //);
              },
              error: (error: any) => {
                patchState(
                  store, { 
                    error, 
                    loading: false, 
                    success: false,
                    messages: [error?.error ? error.error : error] 
                  }
                );
              },
            }),
          );
        }),
      ),
      getBySurveyReference: rxMethod<{surveyReference: string | any }>(
        switchMap((data: any) => {
          patchState(store, { loading: true, loaderMessage: 'Loading ...' });
          return sessionApi.getBySurveyReference(data.surveyReference, ).pipe(
            tapResponse({
              next: (data: SessionDTO[] | any[]) => {
                //patchState(
                  //store, 
                  // { 
                  //    data, 
                  //    loading: false, 
                  //    error: false,
                  //    success: true, 
                  //    messages: [] 
                  //}
                //);
              },
              error: (error: any) => {
                patchState(
                  store, { 
                    error, 
                    loading: false, 
                    success: false,
                    messages: [error?.error ? error.error : error] 
                  }
                );
              },
            }),
          );
        }),
      ),
      getBySurveyReferencePaged: rxMethod<{surveyReference: string | any , pageNumber: number | any , pageSize: number | any }>(
        switchMap((data: any) => {
          patchState(store, { loading: true, loaderMessage: 'Loading ...' });
          return sessionApi.getBySurveyReferencePaged(data.surveyReference, data.pageNumber, data.pageSize, ).pipe(
            tapResponse({
              next: (data: Page<SessionDTO> | any) => {
                //patchState(
                  //store, 
                  // { 
                  //    data, 
                  //    loading: false, 
                  //    error: false,
                  //    success: true, 
                  //    messages: [] 
                  //}
                //);
              },
              error: (error: any) => {
                patchState(
                  store, { 
                    error, 
                    loading: false, 
                    success: false,
                    messages: [error?.error ? error.error : error] 
                  }
                );
              },
            }),
          );
        }),
      ),
      next: rxMethod<{reference: string | any }>(
        switchMap((data: any) => {
          patchState(store, { loading: true, loaderMessage: 'Loading ...' });
          return sessionApi.next(data.reference, ).pipe(
            tapResponse({
              next: (data: SessionDTO | any) => {
                //patchState(
                  //store, 
                  // { 
                  //    data, 
                  //    loading: false, 
                  //    error: false,
                  //    success: true, 
                  //    messages: [] 
                  //}
                //);
              },
              error: (error: any) => {
                patchState(
                  store, { 
                    error, 
                    loading: false, 
                    success: false,
                    messages: [error?.error ? error.error : error] 
                  }
                );
              },
            }),
          );
        }),
      ),
      remove: rxMethod<{id: string | any }>(
        switchMap((data: any) => {
          patchState(store, { loading: true, loaderMessage: 'Loading ...' });
          return sessionApi.remove(data.id, ).pipe(
            tapResponse({
              next: (data: boolean | any) => {
                //patchState(
                  //store, 
                  // { 
                  //    data, 
                  //    loading: false, 
                  //    error: false,
                  //    success: true, 
                  //    messages: [] 
                  //}
                //);
              },
              error: (error: any) => {
                patchState(
                  store, { 
                    error, 
                    loading: false, 
                    success: false,
                    messages: [error?.error ? error.error : error] 
                  }
                );
              },
            }),
          );
        }),
      ),
      save: rxMethod<{session: SessionDTO | any }>(
        switchMap((data: any) => {
          patchState(store, { loading: true, loaderMessage: 'Loading ...' });
          return sessionApi.save(data.session, ).pipe(
            tapResponse({
              next: (data: SessionDTO | any) => {
                //patchState(
                  //store, 
                  // { 
                  //    data, 
                  //    loading: false, 
                  //    error: false,
                  //    success: true, 
                  //    messages: [] 
                  //}
                //);
              },
              error: (error: any) => {
                patchState(
                  store, { 
                    error, 
                    loading: false, 
                    success: false,
                    messages: [error?.error ? error.error : error] 
                  }
                );
              },
            }),
          );
        }),
      ),
      search: rxMethod<{criteria: string | any }>(
        switchMap((data: any) => {
          patchState(store, { loading: true, loaderMessage: 'Loading ...' });
          return sessionApi.search(data.criteria, ).pipe(
            tapResponse({
              next: (data: SessionDTO[] | any[]) => {
                //patchState(
                  //store, 
                  // { 
                  //    data, 
                  //    loading: false, 
                  //    error: false,
                  //    success: true, 
                  //    messages: [] 
                  //}
                //);
              },
              error: (error: any) => {
                patchState(
                  store, { 
                    error, 
                    loading: false, 
                    success: false,
                    messages: [error?.error ? error.error : error] 
                  }
                );
              },
            }),
          );
        }),
      ),
    }
  }),
);
