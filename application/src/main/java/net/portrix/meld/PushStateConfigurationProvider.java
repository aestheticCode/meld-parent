package net.portrix.meld;

import org.ocpsoft.logging.Logger;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Direction;
import org.ocpsoft.rewrite.config.Log;
import org.ocpsoft.rewrite.servlet.config.*;

import javax.servlet.ServletContext;

@RewriteConfiguration
public class PushStateConfigurationProvider extends HttpConfigurationProvider
{
    @Override
    public Configuration getConfiguration(final ServletContext context)
    {
        return ConfigurationBuilder.begin()
                .addRule()
                .when(Direction.isInbound().and(Path.matches("/usercontrol/{path}"))
                        .andNot(Resource.exists("/usercontrol/{path}"))
                        .andNot(ServletMapping.includes("/usercontrol/{path}")))
                .perform((Log.message(Logger.Level.INFO, "Forwarding to index.html from {path}").and(Forward.to("/index.html"))))
                .where("path").matches(".*")

                .addRule()
                .when(Direction.isInbound().and(Path.matches("/channel/{path}"))
                        .andNot(Resource.exists("/channel/{path}"))
                        .andNot(ServletMapping.includes("/channel/{path}")))
                .perform((Log.message(Logger.Level.INFO, "Forwarding to index.html from {path}").and(Forward.to("/index.html"))))
                .where("path").matches(".*")

                .addRule()
                .when(Direction.isInbound().and(Path.matches("/social/{path}"))
                        .andNot(Resource.exists("/social/{path}"))
                        .andNot(ServletMapping.includes("/channel/{path}")))
                .perform((Log.message(Logger.Level.INFO, "Forwarding to index.html from {path}").and(Forward.to("/index.html"))))
                .where("path").matches(".*");


    }

    @Override
    public int priority()
    {
        /* This provides ordering if you have multiple configurations */
        return 10;
    }
}