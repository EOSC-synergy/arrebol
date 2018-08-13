package org.fogbowcloud.arrebol.pool;

import org.fogbowcloud.arrebol.core.models.Resource;
import org.fogbowcloud.arrebol.core.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResourcePool implements ResourceSubject {

    private List<ResourceObserver> resourcesObservers;

    private List<Resource>  freeResources = new ArrayList<Resource>();
    private Map<String, Resource> resourcePool = new ConcurrentHashMap<String, Resource>();

    public ResourcePool() {
        this.resourcesObservers = new ArrayList<ResourceObserver>();
    }

    public List<Resource> getFreeResources() {
        return this.freeResources;
    }

    public void registerObserver(ResourceObserver o) {
        this.resourcesObservers.add(o);
    }

    public void removeObserver(ResourceObserver o) {
        this.resourcesObservers.remove(o);
    }

    public void notifyObservers(Resource resource) {
        for (ResourceObserver observer: this.resourcesObservers) {
            observer.update(resource);
        }
    }
}
