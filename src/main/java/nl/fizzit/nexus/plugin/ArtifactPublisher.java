package nl.fizzit.nexus.plugin;

import org.sonatype.nexus.proxy.item.StorageItem;

/**
 * 
 */
public interface ArtifactPublisher {
    
    /**
     * Publish given item.
     * 
     * @param item a StorageItem 
     */
    void publish(StorageItem item);
    
    /**
     * 
     * @return name of ArtifactPublisher. 
     */
    String getName();
}
