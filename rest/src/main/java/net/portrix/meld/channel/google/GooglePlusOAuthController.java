package net.portrix.meld.channel.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.plus.model.ActivityFeed;
import com.google.api.services.plus.model.PeopleFeed;
import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import net.portrix.generic.rest.jsr339.Name;
import org.apache.tools.ant.taskdefs.Classloader;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;

@Path("channel")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GooglePlusOAuthController {

    /*
     * Default HTTP transport to use to make HTTP requests.
     */
    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    /*
     * Default JSON factory to use to deserialize JSON.
     */
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    private Credential authorize() throws Exception {
        // load client secrets
        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("client_secrets.json");

        InputStreamReader reader = new InputStreamReader(inputStream);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                TRANSPORT,
                JSON_FACTORY,
                clientSecrets,
                Lists.newArrayList(PlusScopes.all()))
                .build();
        // authorize
        LocalServerReceiver.Builder builder = new LocalServerReceiver.Builder();
        builder.setPort(60000);
        LocalServerReceiver receiver = builder.build();
        Credential authorize = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        receiver.stop();
        return authorize;
    }


    @GET
    @Path("google/plus/signIn")
    public Response signIn(@Context HttpServletRequest req) throws Exception {

        // Create a new authorized API client.
        Plus service = new Plus.Builder(TRANSPORT, JSON_FACTORY, authorize())
                .setApplicationName("Meld")
                .build();


        // Get a list of people that this user has shared with this app.
        ActivityFeed people = service.activities().list("me", "public").execute();

        return Response.ok().build();
    }

    static void getContent(InputStream inputStream, ByteArrayOutputStream outputStream)
            throws IOException {
        // Read the response into a buffered stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        int readChar;
        while ((readChar = reader.read()) != -1) {
            outputStream.write(readChar);
        }
        reader.close();
    }




}
