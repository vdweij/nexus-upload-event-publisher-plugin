package nl.fizzit.nexus.plugin;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.sisu.goodies.common.ComponentSupport;
import org.sonatype.sisu.goodies.eventbus.EventBus;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import org.sonatype.nexus.events.EventSubscriber;
import org.sonatype.nexus.proxy.events.RepositoryItemEventStore;

/**
 *
 */
@Named(UploadEventPublisher.ID)
public class UploadEventPublisher extends ComponentSupport implements EventSubscriber {

    public static final String ID = "upload-event-publisher";

    private final EventBus eventBus;

    private final List<ArtifactPublisher> publishers;

    /**
     * 
     * @param eventBus a reference to the EventBus.
     * @param publishers a list of ArtifactPublisher implementations.
     */
    @Inject
    public UploadEventPublisher(final EventBus eventBus, final List<ArtifactPublisher> publishers) {
        this.eventBus = Preconditions.checkNotNull(eventBus);
        this.publishers = Preconditions.checkNotNull(publishers);

        if (publishers.isEmpty()) {
            log.warn("No ArtifactPublisher components detected");
        } else if (log.isDebugEnabled()) {
            log.debug("Artifact Publishers:");
            for (ArtifactPublisher publisher : publishers) {
                log.debug("  {}", publisher.getName());
            }
        }
    }

    /**
     * 
     * @param evt the event representing an artifact upload. 
     */
    @Subscribe
    public void publishArtifactUploadEvent(final RepositoryItemEventStore evt) {
        for (ArtifactPublisher publisher : publishers) {
            publisher.publish(evt.getItem());
        }
    }

}
