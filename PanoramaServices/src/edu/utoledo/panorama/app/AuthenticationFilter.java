package edu.utoledo.panorama.app;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.interception.SecurityPrecedence;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.spi.CorsHeaders;

import edu.utoledo.panorama.dao.DiscussionForumDAO;
import edu.utoledo.panorama.services.PanoramaCipher;
import edu.utoledo.panorama.vo.ExceptionResponseVO;

@Provider
@ServerInterceptor
@SecurityPrecedence
public class AuthenticationFilter implements ContainerRequestFilter, ContainerResponseFilter {

	private static final Logger logger = Logger.getLogger(AuthenticationFilter.class);
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String ERR_RESPONSE = "You are not authorized to perform this action";
    
	@Context HttpServletRequest request;
	
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	
    	ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();
        // for all getters
        if (method.isAnnotationPresent(PermitAll.class)) {
        	return;
        }
        
        // for post requests
        MultivaluedMap<String, String> headers = requestContext.getHeaders();
        List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
        if(authorization == null || authorization.isEmpty()) {
        	ExceptionResponseVO exceptionResponse = new ExceptionResponseVO();
    		exceptionResponse.setMessage(ERR_RESPONSE);
    		requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity( exceptionResponse )
					.build());
        } else {
        	
        	double userIdFromSession = getUserIdFromSession(request);
            double userIdFromRequestContext = getUserIdFromRequestContext(requestContext);
            if ( userIdFromSession != userIdFromRequestContext ) {
            	ExceptionResponseVO exceptionResponse = new ExceptionResponseVO();
        		exceptionResponse.setMessage(ERR_RESPONSE);
        		requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity( exceptionResponse )
    					.build());
            }
        }
        
        // check if the user is admin
        if (method.isAnnotationPresent(RolesAllowed.class)) {
        	
			try {
				String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
	            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
	            	ExceptionResponseVO exceptionResponse = new ExceptionResponseVO();
	        		exceptionResponse.setMessage(ERR_RESPONSE);
	        		requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity( exceptionResponse )
	    					.build());
	            }
	            String token = authorizationHeader.substring("Bearer".length()).trim();
	            PanoramaCipher panoramaCipher = new PanoramaCipher();
	        	String userCreds = panoramaCipher.decrypt(token);
	        	StringTokenizer stringTokenizer = new StringTokenizer(userCreds, "===");
	        	String userIdFromToken = "";
	        	if (stringTokenizer.hasMoreTokens()) {
	        		userIdFromToken = stringTokenizer.nextToken();
	        	}
	        	double userId = Double.parseDouble(userIdFromToken);
	        	String requiredRole = method.getAnnotation(RolesAllowed.class).value()[0];
	        	String currentRole = DiscussionForumDAO.getInstance().getUserRole(userId);
	        	if (currentRole != null && requiredRole.equals(requiredRole)) {
	        		
	        		// all is well
	        		return;
	        	} else {
	        		ExceptionResponseVO exceptionResponse = new ExceptionResponseVO();
	        		exceptionResponse.setMessage(ERR_RESPONSE);
	        		requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity( exceptionResponse )
	    					.build());
	        	}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
        	
        }
    }
    
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		
		MultivaluedMap<String, Object> headers = responseContext.getHeaders();
		headers.putSingle(CorsHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader("Origin") );
		headers.putSingle(CorsHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Accept, X-Requested-With, Authorization");
	}
	
	private double getUserIdFromSession(HttpServletRequest request) {
		
		double userId = -1;
		HttpSession session = request.getSession(false);
        if (session != null ) {
        	try {
        		
        		userId = Double.parseDouble(session.getAttribute(JSONService.SESSION_USER)+"");
        	} catch(Exception e) {
        		e.printStackTrace();
        	}
        }
        return userId;
	}
	

	private double getUserIdFromRequestContext(ContainerRequestContext requestContext) {
		
		double userId = -1;
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        String token = authorizationHeader.substring("Bearer".length()).trim();
        try {
        	
        	PanoramaCipher panoramaCipher = new PanoramaCipher();
        	String userCreds = panoramaCipher.decrypt(token);
        	StringTokenizer stringTokenizer = new StringTokenizer(userCreds, "===");
        	String userIdFromToken = "";
        	if (stringTokenizer.hasMoreTokens()) {
        		userIdFromToken = stringTokenizer.nextToken();
        	}
        	userId = Double.parseDouble(userIdFromToken);
        } catch (Exception e) {
        	
        	logger.error(e.getMessage(),e);
        }
        return userId;
	}
}
