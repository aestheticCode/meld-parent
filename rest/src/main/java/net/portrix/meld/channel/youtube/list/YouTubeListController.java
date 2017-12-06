package net.portrix.meld.channel.youtube.list;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.common.collect.Lists;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.List;

@Path("channel")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class YouTubeListController {

    /*
     * Default HTTP transport to use to make HTTP requests.
     */
    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    /*
     * Default JSON factory to use to deserialize JSON.
     */
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    public Credential authorize(List<String> scopes, String credentialDatastore) throws IOException {

        // Load client secrets.
        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("client_secrets.json");

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(inputStream));

        // Checks that the defaults have been replaced (Default = "Enter X here").
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://console.developers.google.com/project/_/apiui/credential "
                            + "into src/main/resources/client_secrets.json");
            System.exit(1);
        }

        // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(System.getProperty("user.home") + "/.oauth-credentials"));
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(credentialDatastore);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                TRANSPORT, JSON_FACTORY, clientSecrets, scopes).setCredentialDataStore(datastore)
                .build();

        // Build the local server and bind it to port 8080
        LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(60000).build();

        // Authorize.
        return new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
    }


    @GET
    @Path("youtube/list")
    public void list() {

        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");


        YouTube youtube = null;
        try {
            youtube = new YouTube.Builder(TRANSPORT, JSON_FACTORY, authorize(scopes, "meld"))
                    .setApplicationName("meld").build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            ChannelListResponse execute1 = youtube.channels().list("snippet,contentDetails").setMine(true).execute();

            PlaylistListResponse execute = youtube.playlists().list("snippet,contentDetails").setMine(true).execute();

            PlaylistItemListResponse contentDetails = youtube.playlistItems().list("snippet,contentDetails").setPlaylistId("UUYHTRSoH9KRdNBNBSjcu5Wg").execute();

            System.out.println(execute);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
