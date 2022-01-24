package org.tpjad.services;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.SymbolConstants;

import org.apache.tapestry5.commons.Configuration;
import org.apache.tapestry5.commons.MappedConfiguration;
import org.apache.tapestry5.commons.OrderedConfiguration;
import org.apache.tapestry5.http.services.*;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.tpjad.services.interfaces.BookServiceInterface;
import org.tpjad.services.interfaces.UserServiceInterface;
import org.tynamo.security.Security;
import org.tynamo.security.SecuritySymbols;
import org.tynamo.security.services.SecurityFilterChainFactory;
import org.tynamo.security.services.SecurityModule;
import org.tynamo.security.services.impl.SecurityFilterChain;

import java.io.IOException;
import java.util.UUID;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
@SubModule({ SecurityModule.class})
public class AppModule
{
    @Inject
    static Session session;
    public static void bind(ServiceBinder binder)
    {
        // binder.bind(MyServiceInterface.class, MyServiceImpl.class);
        binder.bind(AuthorizingRealm.class, UserRealm.class).withId(UserRealm.class.getSimpleName());
        binder.bind(UserServiceInterface.class,UserService.class);
        binder.bind(BookServiceInterface.class,BookService.class);
        // Make bind() calls on the binder object to define most IoC services.
        // Use service builder methods (example below) when the implementation
        // is provided inline, or requires more initialization than simply
        // invoking the constructor.
    }

    public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration)
    {
        // The values defined here (as factory default overrides) are themselves
        // overridden with application defaults by DevelopmentModule and QaModule.

        // The application version is primarily useful as it appears in
        // any exception reports (HTML or textual).
        configuration.override(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");

        // This is something that should be removed when going to production, but is useful
        // in the early stages of development.
        configuration.override(SymbolConstants.PRODUCTION_MODE, false);
    }

    public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration)
    {
        // Contributions to ApplicationDefaults will override any contributions to
        // FactoryDefaults (with the same key). Here we're restricting the supported
        // locales to just "en" (English). As you add localised message catalogs and other assets,
        // you can extend this list of locales (it's a comma separated series of locale names;
        // the first locale name is the default when there's no reasonable match).
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");

        // You should change the passphrase immediately; the HMAC passphrase is used to secure
        // the hidden field data stored in forms to encrypt and digitally sign client-side data.
        configuration.add(SymbolConstants.HMAC_PASSPHRASE, "change this immediately-" + UUID.randomUUID());

        configuration.add(SecuritySymbols.LOGIN_URL, "/login");
        configuration.add(SecuritySymbols.UNAUTHORIZED_URL, "/login");
        configuration.add(SecuritySymbols.SUCCESS_URL, "/");
    }

	/**
	 * Use annotation or method naming convention: <code>contributeApplicationDefaults</code>
	 */

	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void setupEnvironment(MappedConfiguration<String, Object> configuration)
	{
        // Support for jQuery is new in Tapestry 5.4 and will become the only supported
        // option in 5.5.
		configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
		configuration.add(SymbolConstants.BOOTSTRAP_ROOT, "context:mybootstrap");
	}


    /**
     * This is a service definition, the service will be named "TimingFilter". The interface,
     * RequestFilter, is used within the RequestHandler service pipeline, which is built from the
     * RequestHandler service configuration. Tapestry IoC is responsible for passing in an
     * appropriate Logger instance. Requests for static resources are handled at a higher level, so
     * this filter will only be invoked for Tapestry related requests.
     *
     *
     * Service builder methods are useful when the implementation is inline as an inner class
     * (as here) or require some other kind of special initialization. In most cases,
     * use the static bind() method instead.
     *
     *
     * If this method was named "build", then the service id would be taken from the
     * service interface and would be "RequestFilter".  Since Tapestry already defines
     * a service named "RequestFilter" we use an explicit service id that we can reference
     * inside the contribution method.
     */
    public RequestFilter buildTimingFilter(final Logger log)
    {
        return new RequestFilter()
        {
            public boolean service(Request request, Response response, RequestHandler handler)
            throws IOException
            {
                long startTime = System.currentTimeMillis();

                try
                {
                    // The responsibility of a filter is to invoke the corresponding method
                    // in the handler. When you chain multiple filters together, each filter
                    // received a handler that is a bridge to the next filter.

                    return handler.service(request, response);
                } finally
                {
                    long elapsed = System.currentTimeMillis() - startTime;

                    log.info("Request time: {} ms", elapsed);
                }
            }
        };
    }

//    public static void contributeWebSecurityManager(Configuration<Realm> configuration, @Autobuild UserRealm realm) {
//        configuration.add(realm);
//    }



    @Contribute(HttpServletRequestFilter.class)
    @Security
    public static void setupSecurity(OrderedConfiguration<SecurityFilterChain> configuration,
                                     SecurityFilterChainFactory factory, WebSecurityManager securityManager) {
//		if (securityManager instanceof DefaultSecurityManager) {
//			DefaultSecurityManager defaultManager = (DefaultSecurityManager) securityManager;
//
//			// BlowfishCipher cipher = new BlowfishCipher();
//			// defaultManager.setRememberMeCipherKey(cipher.getKey().getEncoded());
//			// defaultManager.setRememberMeCipher(cipher);
//			ServletContainerSessionManager sessionManager = new ServletContainerSessionManager();
//			defaultManager.setSessionManager(sessionManager);
//			defaultManager.setSubjectFactory(subjectFactory);
//		}

//		/authc/signup = anon
//		/authc/** = authc
//
//		/user/signup = anon
//		/user/** = user
//		/roles/user/** = roles[user]
//		/roles/manager/** = roles[manager]
//		/perms/view/** = perms[news:view]
//		/perms/edit/** = perms[news:edit]

        configuration.add("authc_signup", factory.createChain("/authc/signup").add(factory.anon()).build());
        configuration.add("authc", factory.createChain("/authc/**").add(factory.authc()).build());
        configuration.add("partlyauthc", factory.createChain("/partlyauthc/**").add(factory.anon()).build());
        configuration.add("contributed", factory.createChain("/contributed/**").add(factory.authc()).build());
        configuration.add("user_signup", factory.createChain("/user/signup").add(factory.anon()).build());
        configuration.add("user", factory.createChain("/user/**").add(factory.user()).build());
        configuration.add("roles_user", factory.createChain("/roles/user/**").add(factory.roles(), "user").build());
        configuration.add("roles_manager", factory.createChain("/roles/manager/**").add(factory.roles(), "manager").build());
        configuration.add("perms_view", factory.createChain("/perms/view/**").add(factory.perms(), "news:view").build());
        configuration.add("perms_edit", factory.createChain("/perms/edit/**").add(factory.perms(), "news:edit").build());


        configuration.add("hidden", factory.createChain("/hidden/**").add(factory.notfound()).build());
    }
    @Contribute(WebSecurityManager.class)
    public static void addRealms(Configuration<Realm> configuration, @Autobuild UserRealm realm) {
        configuration.add(realm);
    }
    /**
     * This is a contribution to the RequestHandler service configuration. This is how we extend
     * Tapestry using the timing filter. A common use for this kind of filter is transaction
     * management or security. The @Local annotation selects the desired service by type, but only
     * from the same module.  Without @Local, there would be an error due to the other service(s)
     * that implement RequestFilter (defined in other modules).
     */
    @Contribute(RequestHandler.class)
    public void addTimingFilter(OrderedConfiguration<RequestFilter> configuration,
        @Local RequestFilter filter)
    {
        // Each contribution to an ordered configuration has a name, When necessary, you may
        // set constraints to precisely control the invocation order of the contributed filter
        // within the pipeline.

        configuration.add("Timing", filter);
    }
}
