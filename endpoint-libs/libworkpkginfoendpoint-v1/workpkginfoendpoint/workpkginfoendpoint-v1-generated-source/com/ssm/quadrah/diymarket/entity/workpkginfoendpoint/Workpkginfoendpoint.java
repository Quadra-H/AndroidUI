/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-02-14 18:40:25 UTC)
 * on 2014-02-27 at 19:22:52 UTC 
 * Modify at your own risk.
 */

package com.ssm.quadrah.diymarket.entity.workpkginfoendpoint;

/**
 * Service definition for Workpkginfoendpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link WorkpkginfoendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Workpkginfoendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.16.0-rc of the workpkginfoendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://quadrah-diy-market.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "workpkginfoendpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Workpkginfoendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Workpkginfoendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getWorkPkgInfo".
   *
   * This request holds the parameters needed by the the workpkginfoendpoint server.  After setting
   * any optional parameters, call the {@link GetWorkPkgInfo#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetWorkPkgInfo getWorkPkgInfo(java.lang.Long id) throws java.io.IOException {
    GetWorkPkgInfo result = new GetWorkPkgInfo(id);
    initialize(result);
    return result;
  }

  public class GetWorkPkgInfo extends WorkpkginfoendpointRequest<com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo> {

    private static final String REST_PATH = "workpkginfo/{id}";

    /**
     * Create a request for the method "getWorkPkgInfo".
     *
     * This request holds the parameters needed by the the workpkginfoendpoint server.  After setting
     * any optional parameters, call the {@link GetWorkPkgInfo#execute()} method to invoke the remote
     * operation. <p> {@link GetWorkPkgInfo#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetWorkPkgInfo(java.lang.Long id) {
      super(Workpkginfoendpoint.this, "GET", REST_PATH, null, com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetWorkPkgInfo setAlt(java.lang.String alt) {
      return (GetWorkPkgInfo) super.setAlt(alt);
    }

    @Override
    public GetWorkPkgInfo setFields(java.lang.String fields) {
      return (GetWorkPkgInfo) super.setFields(fields);
    }

    @Override
    public GetWorkPkgInfo setKey(java.lang.String key) {
      return (GetWorkPkgInfo) super.setKey(key);
    }

    @Override
    public GetWorkPkgInfo setOauthToken(java.lang.String oauthToken) {
      return (GetWorkPkgInfo) super.setOauthToken(oauthToken);
    }

    @Override
    public GetWorkPkgInfo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetWorkPkgInfo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetWorkPkgInfo setQuotaUser(java.lang.String quotaUser) {
      return (GetWorkPkgInfo) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetWorkPkgInfo setUserIp(java.lang.String userIp) {
      return (GetWorkPkgInfo) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetWorkPkgInfo setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetWorkPkgInfo set(String parameterName, Object value) {
      return (GetWorkPkgInfo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertWorkPkgInfo".
   *
   * This request holds the parameters needed by the the workpkginfoendpoint server.  After setting
   * any optional parameters, call the {@link InsertWorkPkgInfo#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo}
   * @return the request
   */
  public InsertWorkPkgInfo insertWorkPkgInfo(com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo content) throws java.io.IOException {
    InsertWorkPkgInfo result = new InsertWorkPkgInfo(content);
    initialize(result);
    return result;
  }

  public class InsertWorkPkgInfo extends WorkpkginfoendpointRequest<com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo> {

    private static final String REST_PATH = "workpkginfo";

    /**
     * Create a request for the method "insertWorkPkgInfo".
     *
     * This request holds the parameters needed by the the workpkginfoendpoint server.  After setting
     * any optional parameters, call the {@link InsertWorkPkgInfo#execute()} method to invoke the
     * remote operation. <p> {@link InsertWorkPkgInfo#initialize(com.google.api.client.googleapis.serv
     * ices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param content the {@link com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo}
     * @since 1.13
     */
    protected InsertWorkPkgInfo(com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo content) {
      super(Workpkginfoendpoint.this, "POST", REST_PATH, content, com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo.class);
    }

    @Override
    public InsertWorkPkgInfo setAlt(java.lang.String alt) {
      return (InsertWorkPkgInfo) super.setAlt(alt);
    }

    @Override
    public InsertWorkPkgInfo setFields(java.lang.String fields) {
      return (InsertWorkPkgInfo) super.setFields(fields);
    }

    @Override
    public InsertWorkPkgInfo setKey(java.lang.String key) {
      return (InsertWorkPkgInfo) super.setKey(key);
    }

    @Override
    public InsertWorkPkgInfo setOauthToken(java.lang.String oauthToken) {
      return (InsertWorkPkgInfo) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertWorkPkgInfo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertWorkPkgInfo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertWorkPkgInfo setQuotaUser(java.lang.String quotaUser) {
      return (InsertWorkPkgInfo) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertWorkPkgInfo setUserIp(java.lang.String userIp) {
      return (InsertWorkPkgInfo) super.setUserIp(userIp);
    }

    @Override
    public InsertWorkPkgInfo set(String parameterName, Object value) {
      return (InsertWorkPkgInfo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listWorkPkgInfo".
   *
   * This request holds the parameters needed by the the workpkginfoendpoint server.  After setting
   * any optional parameters, call the {@link ListWorkPkgInfo#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListWorkPkgInfo listWorkPkgInfo() throws java.io.IOException {
    ListWorkPkgInfo result = new ListWorkPkgInfo();
    initialize(result);
    return result;
  }

  public class ListWorkPkgInfo extends WorkpkginfoendpointRequest<com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.CollectionResponseWorkPkgInfo> {

    private static final String REST_PATH = "workpkginfo";

    /**
     * Create a request for the method "listWorkPkgInfo".
     *
     * This request holds the parameters needed by the the workpkginfoendpoint server.  After setting
     * any optional parameters, call the {@link ListWorkPkgInfo#execute()} method to invoke the remote
     * operation. <p> {@link ListWorkPkgInfo#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @since 1.13
     */
    protected ListWorkPkgInfo() {
      super(Workpkginfoendpoint.this, "GET", REST_PATH, null, com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.CollectionResponseWorkPkgInfo.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListWorkPkgInfo setAlt(java.lang.String alt) {
      return (ListWorkPkgInfo) super.setAlt(alt);
    }

    @Override
    public ListWorkPkgInfo setFields(java.lang.String fields) {
      return (ListWorkPkgInfo) super.setFields(fields);
    }

    @Override
    public ListWorkPkgInfo setKey(java.lang.String key) {
      return (ListWorkPkgInfo) super.setKey(key);
    }

    @Override
    public ListWorkPkgInfo setOauthToken(java.lang.String oauthToken) {
      return (ListWorkPkgInfo) super.setOauthToken(oauthToken);
    }

    @Override
    public ListWorkPkgInfo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListWorkPkgInfo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListWorkPkgInfo setQuotaUser(java.lang.String quotaUser) {
      return (ListWorkPkgInfo) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListWorkPkgInfo setUserIp(java.lang.String userIp) {
      return (ListWorkPkgInfo) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListWorkPkgInfo setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public ListWorkPkgInfo setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListWorkPkgInfo set(String parameterName, Object value) {
      return (ListWorkPkgInfo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeWorkPkgInfo".
   *
   * This request holds the parameters needed by the the workpkginfoendpoint server.  After setting
   * any optional parameters, call the {@link RemoveWorkPkgInfo#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveWorkPkgInfo removeWorkPkgInfo(java.lang.Long id) throws java.io.IOException {
    RemoveWorkPkgInfo result = new RemoveWorkPkgInfo(id);
    initialize(result);
    return result;
  }

  public class RemoveWorkPkgInfo extends WorkpkginfoendpointRequest<Void> {

    private static final String REST_PATH = "workpkginfo/{id}";

    /**
     * Create a request for the method "removeWorkPkgInfo".
     *
     * This request holds the parameters needed by the the workpkginfoendpoint server.  After setting
     * any optional parameters, call the {@link RemoveWorkPkgInfo#execute()} method to invoke the
     * remote operation. <p> {@link RemoveWorkPkgInfo#initialize(com.google.api.client.googleapis.serv
     * ices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveWorkPkgInfo(java.lang.Long id) {
      super(Workpkginfoendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveWorkPkgInfo setAlt(java.lang.String alt) {
      return (RemoveWorkPkgInfo) super.setAlt(alt);
    }

    @Override
    public RemoveWorkPkgInfo setFields(java.lang.String fields) {
      return (RemoveWorkPkgInfo) super.setFields(fields);
    }

    @Override
    public RemoveWorkPkgInfo setKey(java.lang.String key) {
      return (RemoveWorkPkgInfo) super.setKey(key);
    }

    @Override
    public RemoveWorkPkgInfo setOauthToken(java.lang.String oauthToken) {
      return (RemoveWorkPkgInfo) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveWorkPkgInfo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveWorkPkgInfo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveWorkPkgInfo setQuotaUser(java.lang.String quotaUser) {
      return (RemoveWorkPkgInfo) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveWorkPkgInfo setUserIp(java.lang.String userIp) {
      return (RemoveWorkPkgInfo) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveWorkPkgInfo setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveWorkPkgInfo set(String parameterName, Object value) {
      return (RemoveWorkPkgInfo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateWorkPkgInfo".
   *
   * This request holds the parameters needed by the the workpkginfoendpoint server.  After setting
   * any optional parameters, call the {@link UpdateWorkPkgInfo#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo}
   * @return the request
   */
  public UpdateWorkPkgInfo updateWorkPkgInfo(com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo content) throws java.io.IOException {
    UpdateWorkPkgInfo result = new UpdateWorkPkgInfo(content);
    initialize(result);
    return result;
  }

  public class UpdateWorkPkgInfo extends WorkpkginfoendpointRequest<com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo> {

    private static final String REST_PATH = "workpkginfo";

    /**
     * Create a request for the method "updateWorkPkgInfo".
     *
     * This request holds the parameters needed by the the workpkginfoendpoint server.  After setting
     * any optional parameters, call the {@link UpdateWorkPkgInfo#execute()} method to invoke the
     * remote operation. <p> {@link UpdateWorkPkgInfo#initialize(com.google.api.client.googleapis.serv
     * ices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param content the {@link com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo}
     * @since 1.13
     */
    protected UpdateWorkPkgInfo(com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo content) {
      super(Workpkginfoendpoint.this, "PUT", REST_PATH, content, com.ssm.quadrah.diymarket.entity.workpkginfoendpoint.model.WorkPkgInfo.class);
    }

    @Override
    public UpdateWorkPkgInfo setAlt(java.lang.String alt) {
      return (UpdateWorkPkgInfo) super.setAlt(alt);
    }

    @Override
    public UpdateWorkPkgInfo setFields(java.lang.String fields) {
      return (UpdateWorkPkgInfo) super.setFields(fields);
    }

    @Override
    public UpdateWorkPkgInfo setKey(java.lang.String key) {
      return (UpdateWorkPkgInfo) super.setKey(key);
    }

    @Override
    public UpdateWorkPkgInfo setOauthToken(java.lang.String oauthToken) {
      return (UpdateWorkPkgInfo) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateWorkPkgInfo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateWorkPkgInfo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateWorkPkgInfo setQuotaUser(java.lang.String quotaUser) {
      return (UpdateWorkPkgInfo) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateWorkPkgInfo setUserIp(java.lang.String userIp) {
      return (UpdateWorkPkgInfo) super.setUserIp(userIp);
    }

    @Override
    public UpdateWorkPkgInfo set(String parameterName, Object value) {
      return (UpdateWorkPkgInfo) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Workpkginfoendpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Workpkginfoendpoint}. */
    @Override
    public Workpkginfoendpoint build() {
      return new Workpkginfoendpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link WorkpkginfoendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setWorkpkginfoendpointRequestInitializer(
        WorkpkginfoendpointRequestInitializer workpkginfoendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(workpkginfoendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
